import pandas as pd
import numpy as np
import sklearn
from sklearn.model_selection import cross_validate
from sklearn.naive_bayes import MultinomialNB
from sklearn.svm import LinearSVC
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import *
from sklearn.utils import shuffle
from sklearn.neighbors import KNeighborsClassifier
from sklearn import linear_model, preprocessing
from sklearn import tree
from sklearn.ensemble import RandomForestClassifier


#### PROCESSAR DADOS ####
dados = pd.read_csv("base/base_dados.csv", encoding = 'utf-8')
le = preprocessing.LabelEncoder()
pais = le.fit_transform(list(dados["country"]))
designacao = le.fit_transform(list(dados["designation"]))
pontos = le.fit_transform(list(dados["points"]))
preco = le.fit_transform(list(dados["price"]))
provincia = le.fit_transform(list(dados["province"]))
variedade = le.fit_transform(list(dados["variety"]))
adega = le.fit_transform(list(dados["winery"]))
regiao = le.fit_transform(list(dados["region_1"]))


#### PRINTAR BASE DE DADOS ####
'''
print("pais", pais)
print("pontos", pontos)
print("designacao", designacao)
print("preco", preco)
print("provincia", provincia)
print("variedade", variedade)
print("adega", adega)
print("regiao", regiao)
'''

#### TREINAMENTO ####
#x = list(zip(pontos, designacao, preco, provincia, variedade, adega, regiao))
#y = list(pais)
x = np.array(list(zip(pontos, designacao, preco, provincia, variedade, adega, regiao)))
y = np.array(list(pais))
x_train, x_test, y_train, y_test = sklearn.model_selection.train_test_split(x, y, test_size=0.1)


#### CLASSIFICACAO ####
#### num_vizinhos padrao: 3 #### 
#### tam_de_teste padrao: 0.6 ####
def classificar(modelo_de_classificacao, num_bags, tam_de_teste, num_vizinhos, num_computacoes, depth, x_train, x_test, y_train, y_test):
    model = []
    metricas = [0] * 5
    resultados = []

    if modelo_de_classificacao == "KNN":
        model = KNeighborsClassifier(n_neighbors = num_vizinhos)
    elif modelo_de_classificacao == "Arvore Decisao" or modelo_de_classificacao == "Bagging":
        model = tree.DecisionTreeClassifier(max_depth=depth)

    predict = 'pais'
    if modelo_de_classificacao != "Bagging":

        #### COMENTADO A PARTE DO CROSS-VALIDATE, PORQUE DEMORA MUITO A EXECUÇAO ####
        '''
        scores = cross_validate(model, x_train, y_train, cv=num_computacoes,scoring=['accuracy', 'f1_macro', 'precision_macro', 'recall_macro'])
    
        fit = 0
        score = 0
        accuracy = 0
        f1 = 0
        precision = 0
        recall = 0
        for i in range(num_computacoes):
            fit += scores["fit_time"][i]
            score += scores["score_time"][i]
            accuracy += scores["test_accuracy"][i]
            f1 += scores['test_f1_macro'][i]
            precision += scores['test_precision_macro'][i]
            recall +=  scores['test_recall_macro'][i]
        
        print("##########\nResultados do cross-validate")
        print("fit time", fit/num_computacoes)
        print("score time", score/num_computacoes)
        print("accuracy", accuracy/num_computacoes)
        print("f1", f1/num_computacoes)
        print("precision", precision/num_computacoes)
        print("recall", recall/num_computacoes)
        print("##########\n")
        '''
        
        model.fit(x_train, y_train)
        resultados = model.predict(x_test)

        metricas[0] = model.score(x_test, y_test)
        metricas[1] = f1_score(y_test, resultados, average='macro') 
        metricas[2] = precision_score(y_test, resultados, average='macro')
        metricas[3] = recall_score(y_test, resultados, average='macro') 
        metricas[4] = zero_one_loss(y_test, resultados, normalize=False)
        printarMetricas(metricas, modelo_de_classificacao, 0)
    
    else:
        for i in range(num_bags):
            x_aux, x_lixo, y_aux, y_lixo = sklearn.model_selection.train_test_split(x_train, y_train, test_size=tam_de_teste)
            model.fit(x_aux, y_aux)
            resultados = model.predict(x_test)

            metricas[0] += model.score(x_test, y_test)
            metricas[1] += f1_score(y_test, resultados, average='macro') 
            metricas[2] += precision_score(y_test, resultados , average='macro')
            metricas[3] += recall_score(y_test, resultados, average='macro') 
            metricas[4] = zero_one_loss(y_test, resultados, normalize=False)
        printarMetricas(metricas, modelo_de_classificacao, num_bags)
    

#### PRINTAR METRICAS DAS CLASSIFICACOES ####
#### num_bags padrao: 9 ####
def printarMetricas(metricas, model, num_bags):
    if model == "KNN":
        print("##########\nKNN:")
    elif model == "Arvore Decisao":
        print("##########\nArvore Decisao:")
    elif model == "Bagging":
        print("##########\nBagging:")
        for i in range(len(metricas)):
            metricas[i] = metricas[i] / num_bags

    print("Acuracia: ", metricas[0])
    print("F1: ", metricas[1])
    print("Precisao: ", metricas[2])
    print("Recall", metricas[3])
    print("Zero One Loss (Numero de classificaçoes erradas) : ", metricas[4])
    print("##########\n")

#### HIPERPARAMETROS ####
## Numero de bags --> num_bags
## Tamanho do subteste do Bagging --> tam_de_testes
## K vizinhos --> num_vizinhos
## Numero de computacoes/Cross-Validate (cv) --> num_computacoes
## Profundidade Maxima da arvore --> max_depth

num_bags = 9
tam_de_testes = 0.6
num_vizinhos = 3
num_computacoes = 10
max_depth = 100

#### CHAMADAS DE CLASSIFAÇOES, PODENDO ALTERAR HIPERPARAMETROS ####
classificar("KNN", 0, 0, num_vizinhos, num_computacoes, 0, x_train, x_test, y_train, y_test)
classificar("Arvore Decisao", 0, 0, 0, num_computacoes, max_depth, x_train, x_test, y_train, y_test)
classificar("Bagging", num_bags, tam_de_testes, 0, num_computacoes, max_depth, x_train, x_test, y_train, y_test)
