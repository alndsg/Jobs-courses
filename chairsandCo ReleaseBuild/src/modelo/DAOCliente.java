package modelo;
//Data Acess Obj

import java.util.List;

public class DAOCliente {
    public List<SCliente> getLista(){
        return Dados.listaCliente;
    }
    
    public boolean salvar(SCliente obj){
        if(obj.getCodigo() == null){
            Integer codigo = Dados.listaCliente.size() + 1;
            obj.setCodigo(codigo);
            Dados.listaCliente.add(obj);
        }
        return true;
    }
    public boolean remover(SCliente obj){
        Dados.listaCliente.remove(obj);
        return true;
    }
}