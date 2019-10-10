/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chairsandco;

import java.util.ArrayList;

/**
 *
 * @author Allrthur
 */
public class Estoque {
    public int madeira, couro, pano, metal, plastico;
    public ArrayList <Cadeira> cadeiras = new ArrayList<>();   
    
    public Estoque(){}
    
    public void reestoque(){
        madeira += 500;
        couro += 500;
        pano += 500;
        metal += 500;
        plastico += 500;
        System.out.println("Reestocando materiais.");
    }
    
    public int getmadeira(){return madeira;}
    public void addmadeira(int toadd){this.madeira += toadd;}
    
    public int getcouro(){return couro;}
    public void addcouro(int toadd){this.couro += toadd;}
    
    public int getpano(){return pano;}
    public void addpano(int toadd){this.pano += toadd;}
    
    public int getmetal(){return metal;}
    public void addmetal(int toadd){this.metal += toadd;}
    
    public int getplastico(){return plastico;}
    public void addplastico(int toadd){this.plastico += toadd;}
    
    public void addcadeira(Cadeira toadd){this.cadeiras.add(toadd);}
    
   
    public Cadeira getcadeira (String tipo){
               
        for(int i=0;i<cadeiras.size();i++){
            if (tipo.equals (this.cadeiras.get(i).getTipo())){
                return this.cadeiras.get(i);
                    
            }  
        }
        return null;
    }

    public Cadeira getcadeira (int madeira, int couro, int pano, int metal, int plastico){
        
        for(int i=0;i<cadeiras.size();i++){
            if (this.cadeiras.get(i).getPano() == pano && this.cadeiras.get(i).getMadeira() == madeira && this.cadeiras.get(i).getCouro() == couro
                && this.cadeiras.get(i).getMetal() == metal && this.cadeiras.get(i).getPlastico() == plastico){
                    
                return this.cadeiras.get(i);
                    
            } 
        }
        return null;          
    }
    
    public Cadeira removecadeira(String tipo){
        Cadeira aux = getcadeira(tipo);
        if(aux != null){this.cadeiras.remove(aux);}
        else return null;
        return aux;
    }
    
    //getcadeiratipo
    //getcadeiracustomizada
    
    /*getcadeira a ser adcionado*/
    
}
