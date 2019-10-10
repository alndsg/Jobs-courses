/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chairsandco;
import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author Allrthur
 */
public class SetorRH {
    
    Random rand = new Random();
    ArrayList corpo_tecnico = new ArrayList();
    ArrayList corpo_gerencial = new ArrayList();
    
    static final String NOMES[] = {"Joaquim", "Pedro", "Silvio", "Davi", 
        "Henrique", "Theodoro", "Joana", "Maria", "Leila", "Pietra", "Ana"};
    
    public SetorRH(){}
    public Funcionario contrata(String cargo){
        end novoend = new end("a", rand.nextInt(999), rand.nextInt(999999999));
        Funcionario novo = new Funcionario(NOMES[rand.nextInt(11)], 
                rand.nextInt(999999999), rand.nextInt(999999999), 
                rand.nextInt(999999999), rand.nextInt(999999999) ,novoend,
                rand.nextInt(999999999), cargo, rand.nextInt(999999999));
        if("tecnico".equals(cargo))corpo_tecnico.add(novo);
        if("gerencial".equals(cargo))corpo_gerencial.add(novo);
        return novo;
    }
    public void demite(String cargo){
        if("tecnico".equals(cargo)){
        } else {
            corpo_tecnico.remove(corpo_tecnico.size() - 1);
        }
        if("gerencial".equals(cargo)){
            corpo_gerencial.remove(corpo_gerencial.size() - 1);
        }
        
    }
    public int get_contratados(String cargo){
        if("tecnico".equals(cargo)) return corpo_tecnico.size();
        if("gerencial".equals(cargo)) return corpo_gerencial.size();
        return -1;
    }    
}
