
package modelo;

import chairsandco.Cadeira;
import chairsandco.Estoque;
import chairsandco.SetorPlanejamento;
import chairsandco.SetorProducao;
import chairsandco.SetorRH;
import java.util.ArrayList;
import java.util.List;


public class Dados {
    public static List<SCliente> listaCliente = new ArrayList<>();
    public static List<Cadeira> listaProduto = new ArrayList<>();
    public static List<Venda> listaVenda = new ArrayList<>();
    public static Estoque estoque = new Estoque();
    public static SetorPlanejamento spn = new SetorPlanejamento(estoque);
    public static SetorRH srh = new SetorRH();
    public static SetorProducao spr = new SetorProducao(estoque, srh);
}
