
package modelo;

import java.util.List;

public class DAOVenda {
    public List<Venda> getLista(){
    return Dados.listaVenda;
    }
    
    public boolean salvar(Venda obj){
        if(obj.getCodigo() == null){
            Integer codigo = Dados.listaVenda.size() + 1;
            obj.setCodigo(codigo);
            Dados.listaVenda.add(obj);
        }
        return true;
    }
    public boolean remover(Venda obj){
        Dados.listaVenda.remove(obj);
        return true;
    }
}
