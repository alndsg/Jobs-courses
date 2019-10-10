/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chairsandco;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Allrthur
 */
public class SetorProducao {
    
    private SetorRH rh;
    private Estoque estoque;
    private ArrayList<Cadeira> cadeiras = new ArrayList();
    private ArrayList<Cadeira> cadeirasrefeitas = new ArrayList();;
    private Cadeira cadeira;
    private CDD novo;
    Random rand = new Random();
    
    public SetorProducao(Estoque estoque, SetorRH rh){
        this.estoque = estoque;
        this.rh = rh;
    }
 
    public void produzir(CDD projeto){
        //acionar o setor de rh para contratar os funcionarios  
        cadeira = projeto.modelo;
        
        for (int i = 0;i<(projeto.gerentes);i++){
            rh.contrata("gerencial");
        }
        
        for (int i = 0;i<(projeto.tecnicos);i++){
            rh.contrata("tecnico");
        }
        
        //puxa do estoque o que Ã© pedido pelo projeto
        //isso faz sentido?
        for (int i=0; i<projeto.qtd;i++){
            if(estoque.getcouro()>=projeto.modelo.getCouro() && 
                    estoque.getmadeira()>=projeto.modelo.getMadeira() &&
                    estoque.getmetal()>=projeto.modelo.getMetal() &&
                    estoque.getpano()>=projeto.modelo.getPano() &&
                    estoque.getplastico()>=projeto.modelo.getPlastico()){
                estoque.addmadeira(-projeto.modelo.getMadeira());
                estoque.addcouro(-projeto.modelo.getCouro());
                estoque.addpano(-projeto.modelo.getPano());
                estoque.addmetal(-projeto.modelo.getMetal());
                estoque.addplastico(-projeto.modelo.getPlastico());
            }else{
                estoque.reestoque();
                --i; //Constrói essa cadeira de novo.
            }    
        }
        
        /*agora que jÃ¡ tenho o material, vou produzir as cadeiras.
        Antes de adiciona-las ao estoque vou testar quais estÃ£o boas e quais defeituosas
        */
        for(int i=0;i < projeto.qtd; i++){
            cadeiras.add(cadeira);
        }
        
        int defeituosas = rand.nextInt(cadeiras.size()-1) % 10;
       
        //teste (remove as x cadeiras defeituosas, que sÃ£o as primeiras adicionadas no cadeiras 
        for(int i=0;i<Math.min(defeituosas, cadeiras.size());i++){
            cadeiras.remove(i);
            System.out.println("Removendo cadeira defeituosa.");
        }
        
        //pÃµe no estoque o que foi produzido
        for(int i=0;i<cadeiras.size();i++){
            estoque.addcadeira(cadeiras.get(i));
            System.out.println("Adicionando cadeira no estoque.");
        }
        
        int funcionarios[] = {projeto.gerentes, projeto.tecnicos};
        //reproduzir defeituosos (nem usa esse funcionarios mas foi mais fÃ¡cil assim)
        novo = new CDD(cadeira, defeituosas, funcionarios);
        
        reproduzir(novo);

        //demite os funcionarios contratados
       for (int i = 0;i<(projeto.gerentes);i++){
            rh.demite("gerencial");
            System.out.println("Demitindo gerente.");
        }
        
        for (int i = 0;i<(projeto.tecnicos);i++){
            rh.demite("tecnico");
            System.out.println("Demitindo tecnico.");
        }
        cadeiras.clear();
        cadeirasrefeitas.clear();
    }
    
    private void reproduzir(CDD projeto){
        
        //puxa do estoque o que Ã© pedido pelo projeto novo
        for (int i=0; i<projeto.qtd;i++){
            if(estoque.getcouro()>=projeto.modelo.getCouro() && 
                    estoque.getmadeira()>=projeto.modelo.getMadeira() &&
                    estoque.getmetal()>=projeto.modelo.getMetal() &&
                    estoque.getpano()>=projeto.modelo.getPano() &&
                    estoque.getplastico()>=projeto.modelo.getPlastico()){
                estoque.addmadeira(-projeto.modelo.getMadeira());
                estoque.addcouro(-projeto.modelo.getCouro());
                estoque.addpano(-projeto.modelo.getPano());
                estoque.addmetal(-projeto.modelo.getMetal());
                estoque.addplastico(-projeto.modelo.getPlastico());
            }else{
                estoque.reestoque();
                --i;
            }
        }
        
        //pÃµe no estoque o que foi produzido (sem precisar testar) (poderia ter deixado pra adicionar todas as cadeiras (nÃ£o sÃ³ as defeituoas) sÃ³ aqui, assim sÃ³ precisaria de 1 arrayList)
        for(int i=0;i<projeto.qtd;i++){
            cadeirasrefeitas.add(cadeira);
            System.out.println("Construindo cadeira sem defeito.");
        }
        for(int i=0;i<cadeirasrefeitas.size();i++){
            estoque.addcadeira(cadeirasrefeitas.get(i));
            System.out.println("Adicionando cadeira no estoque.");
        }      
    }
}