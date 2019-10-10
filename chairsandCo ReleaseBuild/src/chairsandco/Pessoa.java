package chairsandco;

/**
 *
 * @author Allrthur
 */
abstract public class Pessoa {
    private String nome;
    private int id, tel, cel, cpf; //id sistema de identificacao interna
    private end endereco;
    
    public Pessoa(String nome, int id, int tel, int cel, int cpf, end endereco){
        this.nome = nome;
        this.id = id;
        this.tel = tel;
        this.cel = cel;
        this.cpf = cpf;
        this.endereco = endereco;
    }   
    //gets
    public String getnome(){return this.nome;}
    public int getid(){return this.id;}
    public int gettel(){return this.tel;}
    public int getcel(){return this.cel;}
    public int getcpf(){return this.cpf;}
    public end getendereco(){return this.endereco;}
    //sets
    public void setnome(String novonome){this.nome = novonome;}
    public void setid(int novoid){this.id = novoid;}
    public void settel(int novotel){this.tel = novotel;}
    public void setcel(int novocel){this.cel = novocel;}
    public int setcpf(){return this.cpf;}
    public void setendereco(end novoendereco){this.endereco = novoendereco;}
    //misc functions
    public void print(){
        System.out.println("nome: " + nome + "\nID: " + id);
        System.out.println("telefone: " + tel);
        System.out.println("celular: " + cel);
        System.out.println("cpf: " + cpf);
        this.endereco.print();
    }
    
}
