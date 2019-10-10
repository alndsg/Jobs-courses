package chairsandco;

/**
 *
 * @author Allrthur
 */
public class end {
    public String endereco, complemento;
    public int num, bl, apt, CEP;
    
    public end(String endereco, int num, int CEP){ //criando endereco simples
        //campos nulos
        this.bl = -1;
        this.apt = this.bl;
        this.complemento = "n/a";
        //campos preenchidos
        this.endereco = endereco;
        this.num = num;
        this.CEP = CEP;
    }
    public void print(){
        System.out.println("endereco: " + endereco + " " + num + " " + bl + " " + apt);
        System.out.println("CEP: " + CEP);
        System.out.println("complemento: " + complemento);
    }
    
    public String toString(){
        return this.endereco;
    }
}
