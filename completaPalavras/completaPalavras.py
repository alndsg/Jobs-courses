import re, string
from nltk.tokenize import word_tokenize
from anytree import *
from unicodedata import normalize

alfabeto = "abcdefghijklmnopqrstuvwxyz"

# Lista de nos da arvore
# Decidimos fazer 24 arvores, cada uma inicializando com uma letra do alfabeto
arvores = []


#### DECOMPOR A PALAVRA ####
def decomporPalavra(palavra):
    nova_palavra = palavra.lower()
    lista_palavras = []
    for i in range(len(nova_palavra)):
        lista_palavras.append(nova_palavra[:(i + 1)])
    return lista_palavras


# ex: "Marcelo" --> ['m','ma', 'mar', 'marc', 'marce','marcel', 'marcelo']
# Objetivo: criar nós na arvore com cada elemento dessa decomposicao


#### CONSTROI A LISTA DE ARVORES VAZIA ####
# A Arvore possui 2 componentes principais:
# --> Nome: para identificar a palavra digitada (a ser completada)
# --> Dicionario: contendo a chave com a palavra e o seu valor sendo quantas vezes ela apareceu.
def construirArvore():
    for i in range(len(alfabeto)):
        arvores.append(Node(alfabeto[i], dicionario={}))


# ex: ['a', 'b', 'c', ...'z']


#### SEPARA DO TEXTO AS PALAVRAS NORMALIZADAS ####
def pegarPalavras(texto):
    texto = texto.lower()
    texto_sem_acentos = normalize('NFKD', texto).encode('ASCII', 'ignore').decode('ASCII')
    texto_sem_numeros = re.sub(r'\d+', '', texto_sem_acentos)
    texto_sem_pontuacao = re.sub('[' + string.punctuation + ']', '', texto_sem_numeros)
    palavras = word_tokenize(texto_sem_pontuacao)

    for elem in palavras:
        if elem == 'a':
            palavras.remove('a')
        elif elem == 'e':
            palavras.remove('e')
        elif elem == 'o':
            palavras.remove('o')

    return palavras


# Ex : "Um exemplo quálquer." --> ['um', 'exemplo', 'qualquer']


#### PEGAR O INDEX NA LISTA DE ARVORES ####
# Objetivo: acessar a arvore relativa a primeira letra da sugestao
def pegarIndex(letra):
    for i in range(len(alfabeto)):
        if alfabeto[i] == letra:
            return i
    return 0


# exemplo de input : fr --> Arvore relativa a letra 'f'


#### ADICIONAR PALAVRA NA ARVORE RELATIVA A SUA LETRA INICIAL ####
def adicionarPalavra(palavra):
    checa_no = find(arvores[pegarIndex(palavra[0])], lambda node: node.name == palavra)

    # Verifica se a palavra ja foi inserida na arvore (no caso, no dicionario)
    # Caso for, incrementa sua chave no dicionario, a fim de aumentar a incidencia da palavra, que afeta na busca
    if checa_no and (palavra in checa_no.dicionario):
        incrementarDicionario(palavra)
    else:
        palavra_decomposta = decomporPalavra(palavra)

        for i in range(len(palavra_decomposta)):
            criarFolha(palavra_decomposta[i], palavra)


#### CRIA FOLHA NA ARVORE, CASO NAO EXISTA NA ARVORE ####
def criarFolha(palavra, palavra_inteira):
    raiz = arvores[pegarIndex(palavra[0])]
    checa_no = find(raiz, lambda node: node.name == palavra)

    if checa_no:
        checa_no.dicionario[palavra_inteira] = 1
    else:
        arvore_pai = find(raiz, lambda node: node.name == palavra[:-1])
        folha = Node(palavra, parent=arvore_pai, dicionario={palavra_inteira: 1})


#### INCREMENTA O VALOR NO DICIONARIO DA PALAVRA EM QUESTAO ####
def incrementarDicionario(palavra):
    raiz = arvores[pegarIndex(palavra[0])]
    palavra_decomposta = decomporPalavra(palavra)
    lista_nos = findall(raiz, filter_=lambda node: node.name in palavra_decomposta)

    for node in lista_nos:
        valor = node.dicionario[palavra]
        node.dicionario.update({palavra: valor + 1})


#### REALIZA O 'TREINAMENTO', OU SEJA, CRIA/ATUALIZA A ARVORE DE ACORDO COM O TEXTO PASSADO ####
def treinar(texto):
    novo_texto = pegarPalavras(texto)

    for i in novo_texto:
        adicionarPalavra(i)


#### BUSCA UTILIZANDO BUSCA LOCAL ####
# No caso, consiste em ver qual palavra teve a maior incidencia (apareceu mais vezes)
def completarPalavraBuscaLocal(input):
    for raiz in arvores:
        if raiz.name == input[0]:
            arvore_escolhida = raiz

    arvore_escolhida = find(arvore_escolhida, lambda node: node.name == input)
    dicionario = arvore_escolhida.dicionario
    maior_num = 0
    chave_escolhida = ''
    for chave in dicionario:
        if dicionario[chave] > maior_num:
            chave_escolhida = chave
            maior_num = dicionario[chave]

    return chave_escolhida


#### BUSCA UTILIZANDO A* ####
# No caso, o A star consiste em:
# g(x) --> relativo a quantos nos ela andou, ou seja, esta relacionado com o TAMANHO da palavra
#      --> quanto maior a palavra, maior o custo
# h(x) --> Consiste na media das incidencias das palavras no dicionario, juntamente com o peso (-0.75)
#      --> ex: dicionario = {'exemplo':3, 'estudar':2, 'escrever': 5}
#            : h(x) =   ( (-0.75)*(3+2+5) )/3
#      --> motivacao: Levar em consideraçao a incidencia das palavras, mas tambem a quantidade de palavras ja armazenadas
#                   : peso negativo: como consideramos o MENOR custo, concluimos em utilizar o peso -0.75 (decisao de projeto)
# f(x) --> soma de g(x) e h(x)
def completarPalavraAStar(input):
    raiz = arvores[pegarIndex(input[0])]
    raiz = find(raiz, lambda node: node.name == input)
    frontier = [raiz]
    explored = []

    while (True):
        if not frontier:
            return ""
        head_frontier = frontier[0]
        frontier.remove(head_frontier)
        if verificarSeSolucao(head_frontier, raiz):
            return head_frontier.name
        explored.append(head_frontier)
        for child in head_frontier.children:
            if child not in frontier or child not in explored:
                frontier = ordenaFronteira(frontier, child)


#### VERIFICA SE O NO ATUAL CONSISTE NUMA SOLUCAO ####
def verificarSeSolucao(no_atual, raiz):
    node = find(raiz, lambda node: node.name == no_atual.name)

    for chave in node.dicionario:
        if chave == node.name:
            return True

    return False


#### FUNCAO HEURISTICA DA BUSCA A* ####
def funcaoHeuristica(node):
    soma = 0
    peso = -0.75
    qtd = len(node.dicionario)

    for chave in node.dicionario:
        soma += node.dicionario[chave]

    return (soma * peso) / qtd


#### CUSTO NO CAMINHO, I.E., SERIA O G(X) DA BUSCA A* ####
def pathCost(no):
    custo = len(no.path)
    return custo


#### REALIZA A ORDENACAO DA FRONTEIRA NA BUSCA A* ####
def ordenaFronteira(frontier, child):
    frontier.append(child)
    ordenado = False
    while not ordenado:
        ordenado = True
        for i in range(len(frontier) - 1):
            if pathCost(frontier[i]) > pathCost(frontier[i + 1]):
                frontier[i], frontier[i + 1] = frontier[i + 1], frontier[i]
                ordenado = False
    return frontier


# exemplo = "Frase a exemplo12. E Outra frase 3 o qualquer, ta certo. Nao sei o que escrever escrita que ??!????"
construirArvore()


# treinar(exemplo)

def lerArquivo(nome_arq):
    f = open(nome_arq, "r")

    if f.mode == 'r':
        conteudo = f.read()

    print(pegarPalavras(conteudo))

    return conteudo


#### LE O CONTEUDO, E REALIZA O TREINAMENTO ####
conteudo = lerArquivo("exemplo.txt")
treinar(conteudo)

#### CORPO DO PROGRAMA, CONTENDO A INTERFACE COM O USUARIO ####
while (True):

    entrada = input("Digite a palavra a ser completada: ")

    if entrada == '0':
        break
    raiz = arvores[pegarIndex(entrada[0])]
    if not find(raiz, lambda node: node.name == entrada):
        print("Obrigado pela nova palavra, ela foi adicionada a minha arvore.")
        treinar(entrada)
    else:
        recomendacao_busca_local = completarPalavraBuscaLocal(entrada)
        recomendacao_busca_estrela = completarPalavraAStar(entrada)
        print("Palavra dada: ", entrada, "\nRecomendacao busca local: ", recomendacao_busca_local,
              "\nRecomendacao A*: ", recomendacao_busca_estrela, "\n")