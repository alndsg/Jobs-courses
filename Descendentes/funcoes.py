deuses = []
pais = []
casados = []
stats = []
dict_genero = {}
NUM_DEUSES = 57

## Inicializa todas as matrizes ##
def criarMatrizes():
    for i in range(NUM_DEUSES):
        pais.append([0] * NUM_DEUSES)

    for i in range(NUM_DEUSES):
        casados.append([0] * NUM_DEUSES)

    for i in range(NUM_DEUSES):
        stats.append([])

## Printa todos os pais e uma lista de seus filhos ##
def printarPais():
    for i in range(NUM_DEUSES):
        filhos = getFilhos(deuses[i])
        print(i, deuses[i], filhos)

## Printa todos as relacoes de casamento entre os deuses ##
def printarCasados():
    for i in range(NUM_DEUSES):
        parceiros = getParceiro(deuses[i])
        if (parceiros != []):
            for parceiro in parceiros:
                print(i, "(", deuses[i], ",", parceiro, ")")
        else:
            print(i, "(", deuses[i], ",", parceiros, ")")

## Printa todos os stats dos deuses (se possuir) ##
def printarStats():
    for i in range(NUM_DEUSES):
        print(i, deuses[i], stats[i])

## Printa todos os deuses ##
def printarDeuses():
    for i in range(NUM_DEUSES):
        print(i, deuses[i])

## Printa os dicionarios dos deuses, identificando seu genero ##
def printarDict():
    for i in range(NUM_DEUSES):
        print(deuses[i], ",", dict_genero.get(deuses[i]))

## Adiciona uma relacao de pai e filho, na matriz de pais, em valores binarios (true ou false) ##
## ex: pai['A']['B'] == 1 --> A é pai de B (true) ##
##   : pai['C']['D'] == 0 --> C não é pai de D (false)##
def adicionarPai(pai, filho):
    x = getPos(pai)
    y = getPos(filho)
    pais[x][y] = 1

## Adiciona o casamento entre os deuses ##
## É bidirecional, i.e, (esposa,marido) e (marido,esposa) ##
def adicionarCasamento(homem, mulher):
    casados[getPos(homem)][getPos(mulher)] = 1
    casados[getPos(mulher)][getPos(homem)] = 1

## Adiciona os stats dos deuses ##
## ex: Odin --> Allfather ##
def adicionarStats(nome, stat):
    stats[getPos(nome)].append(stat)

## Adiciona o nome e o genero(Deus ou Deusa) no dicionario relativo ao genero ##
def adicionarDeus(nome, genero):
    deuses.append(nome)
    dict_genero[nome] = genero

## Retorna a pos do deus(a) na lista ##
## É importante achar o indice, pois as matrizes tambem funcionam de acordo com esse indice ##
def getPos(nome):
    for i in range(NUM_DEUSES):
        if deuses[i] == nome:
            return i
    return -1

## Retorna uma lista com os filhos ##
## Um deus pode ter nenhum ou varios filhos ##
def getFilhos(nome):
    filhos = []
    pai = getPos(nome)
    for i in range(NUM_DEUSES):
        if pais[pai][i] == 1:
            filhos.append(deuses[i])
    return filhos

## Retorna se os deuses sao casados ou nao ##
## 0 = não casados ##
## 1 = casados ##
def getCasamento(marido, mulher):
    return casados[getPos(marido)][getPos(mulher)]

## Retorna uma lista com os stats do deus ##
## Um deus pode nao ter nenhum stat ##
def getStats(nome):
    return stats[getPos(nome)]

## Retorna uma lista com todos os parceiros do deus ##
## Um deus pode ter nenhum ou varios parceiros ##
def getParceiro(nome):
    listaParceiros = []
    pos = getPos(nome)
    
    for i in range(NUM_DEUSES):
        if casados[pos][i] == 1:
            listaParceiros.append(deuses[i])
            
    return sorted(listaParceiros)

## Retorna uma lista com todos os pais do deus ##
## Um deus pode ter somente um pai, ou nenhum ##
def getPais(nome):
    filho = getPos(nome)
    listaPais = []
    for pai in range(NUM_DEUSES):
        if pais[pai][filho] == 1:
           listaPais.append(deuses[pai])
    return sorted(listaPais)

## Retorna uma lista com os avos do deus ##
def getAvos(nome):
    pais = getPais(nome)
    avos = []

    ## Buscar avos ##
    for pai in pais:
        listaAvos = getPais(pai)

        ## Adicionar avos a partir de uma lista de avos ##
        for avo in listaAvos:
            avos.append(avo)

    return sorted(avos)


###############
## PERGUNTAS ##
###############

## PERGUNTA 1 ##
## Quem é filho de X que é filho da mulher de Y? ##
## ex: X=Odin, Y=Frigga ##
##   : Forward Chaining ##

## Retorna uma lista com todos os filhos legitimos do pai ##
def getFilhosLegitimos(pai):
    filhosPai = getFilhos(pai)
    listaParceiro = getParceiro(pai)
    filhosLegitimos = []

    ## Buscar filhos dos parceiros ##
    for parceiro in listaParceiro:
        filhosParceiro = getFilhos(parceiro)

        ## Buscar filhos legitimos ##
        for i in filhosPai:
            for j in filhosParceiro:
                if i==j:
                    filhosLegitimos.append(i)

    return sorted(filhosLegitimos)

## PERGUNTA 2 ##
## Qual é o tio de X? ##
## ex: X=Balder ##
##   : Backward Chaining ##

## Retorna uma lista com todos os tios do deus ##
def getTios(nome):
    listaPais = getPais(nome)
    avos = getAvos(nome)
    tios = []

    ## Adicionar filhos dos avos ##
    for avo in avos:
        filhosAvo = getFilhos(avo)
        for filho in filhosAvo:
            tios.append(filho)

    ## Remover os pais ##
    for pai in listaPais:
        if pai in tios:
            tios.remove(pai)

    return sorted(tios)
        
## PERGUNTA 3 ##
## X descende de Y ? ##
## ex: X=Magni, Y=Locke ##
##   : Forward Chaining ##

## Verifica se um deus1 é descendente de deus2 ##
## Retorna true se é descendente, ou false caso contrario ##
def getDescendencia(deus1, deus2):
    filhos = getFilhos(deus2)

    for filho in filhos:
        if filho == deus1:
            return True
        if getDescendencia(deus1, filho):
            return True

    return False

## PERGUNTA 4 ##
## O pai do deus da X é o deus da Y ?##
## ex: X=thunder, Y=logic ##
##   : Forward Chaining ##

## Verifica se o deus do stat_filho é filho do deus do stat_pai ##
## Retorna true se for verdade, false caso contrario ##
def getPaiDadoStats(stat_filho, stat_pai):
    if stat_filho == stat_pai:
        return False
    listaFilhos = []
    listaPais = []

    for i in range(len(stats)):
        deus = deuses[i]
        listaStats = getStats(deus)

        ## Identificar o(s) pai(s) e o(s) filho(s) ##
        ## Pode ser mais de um, uma vez que há por exemplo 2 deuses do amor(love) ##
        for stat in listaStats:
            if stat == stat_filho:
                listaFilhos.append(deus)
            if stat == stat_pai:
                listaPais.append(deus)

    ## Para cada filho da lista de filhos, verifica se um de seus pais é igual ao pai ##
    for filho in listaFilhos:
        paisDoFilho = getPais(filho)
        for pai in listaPais:
            for paiDoFilho in paisDoFilho:
                if paiDoFilho == pai:
                    return True

    return False