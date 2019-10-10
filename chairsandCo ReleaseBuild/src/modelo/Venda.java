
package modelo;

import chairsandco.Cadeira;
import java.util.Objects;

public class Venda {
    
    private Integer codigo;
    private Cadeira produto;
    private Integer quantidade;
    private Double preco;
    private SCliente cliente;
    private Integer qtd_funcionarios;

    public Venda(){
    
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public Cadeira getProduto() {
        return produto;
    }

    public void setProduto(Cadeira produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public SCliente getCliente() {
        return cliente;
    }

    public void setCliente(SCliente cliente) {
        this.cliente = cliente;
    }

    public Integer getQtd_funcionarios() {
        return qtd_funcionarios;
    }

    public void setQtd_funcionarios(Integer qtd_funcionarios) {
        this.qtd_funcionarios = qtd_funcionarios;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
