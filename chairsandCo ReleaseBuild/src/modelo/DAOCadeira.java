package modelo;
//Data Acess Obj

import chairsandco.Cadeira;
import java.util.List;

public class DAOCadeira {
    public List<Cadeira> getLista(){
        return Dados.listaProduto;
    }
    
    public boolean salvar(Cadeira obj){
        if(obj.getCodigo() == null){
            Integer codigo = Dados.listaProduto.size() + 1;
            obj.setCodigo(codigo);
            Dados.listaProduto.add(obj);
        }
        return true;
    }
    public boolean remover(Cadeira obj){
        Dados.listaProduto.remove(obj);
        return true;
    }
}