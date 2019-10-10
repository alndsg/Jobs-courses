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
public class Funcionario extends Pessoa {
    
    private int bankid;//representacao de numero de conta cartao etc
    public String cargo;//representa a funcao que o funcionario exerce no momento
    private int tempocontrato;//quantos meses faltam de contrato
    
    public Funcionario(String nome, int id, int tel, int cel, int cpf, end endereco, 
        int bankid, String cargo, int tempocontrato){
        super(nome, id, tel, cel, cpf, endereco);
        this.bankid = bankid;
        this.cargo = cargo;
        this.tempocontrato = tempocontrato;
    }
    
    public int getbankid(){return this.bankid;}
    public void setbankid(int novobankid){this.bankid = novobankid;}
    public int gettempocontrato(){return this.tempocontrato;}
    public void contratoatualiza(){--this.tempocontrato;}//passa o tempo um mes a frente
    
    
}
