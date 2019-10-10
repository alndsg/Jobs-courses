package chairsandco;

import chairsandco.Estoque; // classe estoque
import java.util.ArrayList; // classe arrayList
import chairsandco.CDD;
import chairsandco.Cadeira;

public class SetorPlanejamento {
    private int qtd, tempo, gerentes, tecnicos;
    private ArrayList <Cadeira> listaCadeiras;
    private Estoque e = new Estoque();
    private CDD planejamento = new CDD();
    private int disponiveis = 0;
    
    
    public SetorPlanejamento(Estoque estoque){
        this.listaCadeiras = estoque.cadeiras;
        this.qtd = 0;
        this.tempo = 0;
    }
    
    // calculando a quantidade de funcionarios
    public void funcionarios(int qtd, int tempo){
        float x  = ((float)(qtd * 5))/tempo;
        // cast explícito para obter o valor real. Qtd de funcionarios tecnicos + gerentes
        int y = (int)Math.round(x); // arredondando para o próximo inteiro e cast explicito para int
        this.gerentes = (int)Math.ceil((float)y/10.0); //Cada 10 funcionarios temos 1 gerente
        this.tecnicos = Math.max(1, y - this.gerentes); 
        System.out.println("Precisamos de " + this.gerentes + " gerentes e " + this.tecnicos + " técnicos.");
    }
                
    public CDD planeja(int qtd, int tempo, int materias[]){ //planejar cadeira do tipo "customizado"
        int madeira, couro, pano, metal, plastico;
        madeira = materias[0];
        couro = materias[1];
        pano = materias[2];
        metal = materias[3];
        plastico = materias[4];
        Cadeira x = new Cadeira(madeira, couro, pano, metal, plastico);
        
        //procurar cadeiras no estoque
        for(int i = 0; i < qtd; i ++){
            Cadeira auxcad = e.getcadeira(madeira, couro, pano, metal, plastico);
            if(auxcad != null){this.disponiveis += 1;}
            else break;    
        }
        
        //se nao tiver: criar CDD
        int necessarios = qtd - disponiveis;
        System.out.println("Quantas cadeiras a produzir: " + necessarios);
        funcionarios(necessarios, tempo);
        this.planejamento.gerentes = this.gerentes;
        this.planejamento.tecnicos = this.tecnicos;
        this.planejamento.qtd = necessarios;
        this.planejamento.modelo = x;
        System.out.println("Gerentes alocados: " + this.planejamento.gerentes);
        System.out.println("Tecnicos alocados: " + this.planejamento.tecnicos);
        
        
        //cuspir CDD pra SetorProdução
        return this.planejamento;
    }
    
    public CDD planeja(int qtd, int tempo, String tipo){ //cadeira de um dos tipos prontos
        //procurar cadeiras no estoque
        Cadeira x = new Cadeira(tipo);
        for(int i = 0; i < qtd; i++){
            Cadeira auxcad = e.getcadeira(tipo);
            if(auxcad != null){this.disponiveis += 1;}
            else break;
        }
            
        //se nao tiver: criar CDD
        int necessarios = qtd - disponiveis;
        funcionarios(necessarios, tempo);
        this.planejamento.gerentes = this.gerentes;
        this.planejamento.tecnicos = this.tecnicos;
        this.planejamento.qtd = necessarios;
        this.planejamento.modelo = x;
        
        
        //cuspir CDD pra SetorProdução
        return this.planejamento;
    }
}

// está faltando o arrayList de cadeiras que vem de estoque