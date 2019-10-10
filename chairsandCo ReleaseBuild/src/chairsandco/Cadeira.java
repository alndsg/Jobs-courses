
package chairsandco;

import java.io.Serializable;

/**
 *
 * @author Allrthur
 */
public class Cadeira implements Serializable {
    
    //public Integer madeira, couro, pano, metal, plastico, codigo; //quanto de cada material é feita a cadeira
    private String tipo;//tipo de cadeira, ou se e customizada
    private Double preco; 
    private int madeira, couro, pano, metal, plastico;
    private Integer codigo;
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    

    public int getMadeira() {
        return madeira;
    }

    public void setMadeira(int madeira) {
        this.madeira = madeira;
    }

    public int getCouro() {
        return couro;
    }

    public void setCouro(int couro) {
        this.couro = couro;
    }

    public int getPano() {
        return pano;
    }

    public void setPano(int pano) {
        this.pano = pano;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getPlastico() {
        return plastico;
    }

    public void setPlastico(int plastico) {
        this.plastico = plastico;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public Cadeira(Integer madeira, Integer couro, Integer pano, Integer metal, Integer plastico){
        this.madeira = madeira;
        this.couro = couro;    
        this.pano = pano;    
        this.metal = metal;
        this.plastico = plastico;
        this.tipo = "customizado";
    }

    public Cadeira(){
        
    }
    
    public Cadeira(String tipo){
        
        if("escritorio".equals(tipo)){
        
            this.madeira = 0;
            this.couro = 0;
            this.pano = 1;    
            this.metal = 0;
            this.plastico = 0;
            this.tipo = tipo;
        }
        if("jardim".equals(tipo)){
        
            this.madeira = 1;
            this.couro = 0;
            this.pano = 1;    
            this.metal = 0;
            this.plastico = 0;
            this.tipo = tipo;
        }
 
    }
}
