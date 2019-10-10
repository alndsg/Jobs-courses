from funcoes import *

def lerDeuses():
    f = open("deuses.txt", "r")
    f1 = f.readlines()
    for linha in f1:
        nome = ""
        genero = ""
        aux = False
        if "godess" in linha:
            genero = "Deusa"
            nome = linha[7:-3]
        else:
            genero = "Deus"
            nome = linha[4:-3]
        adicionarDeus(nome, genero)
    f.close()

def lerPais():
    f = open("pais.txt", "r")
    f1 = f.readlines()
    for linha in f1:
        relacao = linha[7:-3]
        relacao = relacao.split(",")
        adicionarPai(relacao[0], relacao[1])
    f.close()

def lerCasados():
    f = open("casados.txt", "r")
    f1 = f.readlines()
    for linha in f1:
        relacao = linha[8:-3]
        relacao = relacao.split(",")
        adicionarCasamento(relacao[0], relacao[1])
    f.close()

def lerStats():
    f = open("stats.txt", "r")
    f1 = f.readlines()
    for linha in f1:
        relacao = linha[5:-3]
        relacao = relacao.split(",")
        adicionarStats(relacao[0], relacao[1])
    f.close()


def montarBase():
    criarMatrizes()
    lerDeuses()
    lerPais()
    lerCasados()
    lerStats()

def fazerPerguntas():
    print("##########\nPergunta 1")
    print("Filhos legitimos Odin: ", getFilhosLegitimos("odin"))
    print("Filhos legitimos Thor: ", getFilhosLegitimos("thor"))
    print("##########\nPergunta 2")
    print("Tios de Balder: ", getTios("balder"))
    print("Tios de Thor: ", getTios("thor"))
    print("Tios de Odin: ", getTios("odin"))
    print("##########\nPergunta 3")
    print("Magni é descendente de Loki = ", getDescendencia("magni", "loki"))
    print("Thor é descendente de Odin = ", getDescendencia("thor", "odin"))
    print("Loki é descendente de Odin = ", getDescendencia("loki", "odin"))
    print("##########\nPerguntas 4")
    print("Deus do trovao é filho do deus da logica = ", getPaiDadoStats("thunder", "logic"))
    print("Deus do amor é filho do deus da logica = ", getPaiDadoStats("love", "logic"))
    print("Deus do amor é filho do deus do conhecimento = ", getPaiDadoStats("love", "knowledge"))
    print("Deus do amor é filho do deus do trovao = ", getPaiDadoStats("love", "thunder"))
    print("##########")

