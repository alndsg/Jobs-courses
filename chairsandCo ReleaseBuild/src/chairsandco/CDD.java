/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chairsandco;

/**
 *
 * @author Allrthur
 */
public class CDD {
    Cadeira modelo;          
    int qtd;//tempo estimado até o produzir todas as cadeiras
    int tecnicos, gerentes;//quantidades de funcionarios a colocar no projeto
    
    
    public CDD(Cadeira modelo, int qtd, int funcionarios[]){
        this.modelo = modelo;
        this.qtd = qtd;
        tecnicos = funcionarios[0];
        gerentes = funcionarios[1];
    }

    CDD() {}
}
