package chairsandco;

public class ChairsandCo {

    public static void main(String[] args) {
        //pegar o pedido do cliente
        
        
        Estoque e = new Estoque();
        SetorRH srh = new SetorRH();
        SetorPlanejamento spl = new SetorPlanejamento(e);
        SetorProducao spr = new SetorProducao(e, srh);
        System.out.println("Estado inicial do Estoque: ");
        System.out.println("Quantidade de cadeiras: " + e.cadeiras.size());
        CDD c = spl.planeja(20, 1, "jardim");
        spr.produzir(c);
        for (int i = 0; i < e.cadeiras.size(); i++) {
            System.out.println("Cadeira " + i + " : " + e.cadeiras.get(i).getTipo()); 
        }
        //apresentar as cadeiras prontas e aguardar o pagamento
    }
}