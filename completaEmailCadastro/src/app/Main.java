package app;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Main {
    //static Scanner keyboard = new Scanner(System.in);

    public static User newUser(String dados[]){
        User user = new User();
        user.setFullName(dados[0]);
        user.setnumberID(dados[1]);
        user.setNumber(dados[2]);
        user.setEmail(dados[3]);
        user.setUffEmail(dados[4]);
        user.setStatus(dados[5]);
        return user;
    }

    public static void printUser(User user) {
        System.out.println(user.getFullName() + " " + user.getnumberID() + " "
        + user.getNumber() + " " + user.getEmail() + " " + user.getuffEmail() + " " + user.getStatus());
    }

    // Foi preciso colocar a funcionalidade de instaciar os usuarios(users) dentro da função. 
    // Por isso a função de ler o CSV também instancia os usuarios.
    public static ArrayList<User> readCSV(String sourceCSV) throws IllegalAccessException {
        //String sourceCSC = "/data/employee.csv";
        BufferedReader csvContent = null;
        String line = "";
        String separator = ",";
        String data[];
        ArrayList<User> users = new ArrayList<User>();

        try {
            csvContent = new BufferedReader(new FileReader(sourceCSV));
            line = csvContent.readLine();
            while((line = csvContent.readLine()) != null){
                data = line.split(separator);
                users.add(newUser(data));
            }
            csvContent.close();
            //System.out.println("Arquivo lido com sucesso.");
            return users;

        } catch(FileNotFoundException e){
            System.out.println("Arquivo de leitura não encontrado");
            //System.out.println("" + e.getMessage());
            throw new IllegalAccessException("" + e.getMessage());
    }
        catch(IOException e){
            System.out.println("Erro ao ler arquivo!");
            //System.out.println("" + e.getMessage());
            throw new IllegalArgumentException("" + e.getMessage());
            //return users;
        }
    }

    public static String[] nameUser(String fullNameUser) {
        String fullNameUserVetor[] = fullNameUser.split(" ");
        return fullNameUserVetor;
    }

    private static int getRandomNumberInRange(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("o intervalo final não pode ser menor que o intervalo inicial");
		}
        int randomInt = ThreadLocalRandom.current().nextInt(min, max) + 1; // +1 para adicionar o intervalo superior
       // System.out.println("ramdom: " + randomInt);
        return randomInt;
	}
    
    // Esta função recebe uma instancia da classe Scanner para evitar problemas(warning) com o fechamento do "keyboard.System.in"
    // Pois uma vez fechado(Sytem.in) o Java não consegue o reabrir.
    public static int checkEmailChoices(Scanner keyboard) {
        String choice = keyboard.nextLine();
        if(choice.length() == 1){
            try{
                int choiceRigth = Integer.parseInt(choice);
                choiceRigth--;
                if(choiceRigth == -1){ // escolheu parar: -1
                    //keyboard.close();
                    return choiceRigth;
                }
                if(choiceRigth < -1 || choiceRigth > 4){ // entrada inválida, pedir para digitar novamente: -2
                    //keyboard.close();
                    return -2;
                }
                //keyboard.close();
                return choiceRigth; // escolheu uma das opações de e-mail: choicheRight
            } catch(NumberFormatException e){ // entrada inválida, pedir para digitar novamente: -2
                //System.out.println("catch");
                //keyboard.close();
                return -2;
            }
        }
        //keyboard.close();
        return -2; // entrada inválida, pedir para digitar novamente: -2
        
    }

    public static String emailChoices(String[] nameUser, String rootEmail) {
        Scanner keyboard = new Scanner(System.in);
        int sizeName = nameUser.length -1;
        String[] choicesEmail = new String[5];
        choicesEmail[0] = (nameUser[0] + nameUser[1] + rootEmail).toLowerCase();
        choicesEmail[1] = (nameUser[0] + 
        Character.toString((nameUser[getRandomNumberInRange(1,sizeName)]).charAt(0)) + rootEmail).toLowerCase();
        choicesEmail[2] = (Character.toString(nameUser[0].charAt(0)) + 
        nameUser[getRandomNumberInRange(1, sizeName)] + rootEmail).toLowerCase();
        choicesEmail[3] = (nameUser[getRandomNumberInRange(1, sizeName)] + nameUser[0] + rootEmail).toLowerCase();
        choicesEmail[4] = (nameUser[0] + "_" + nameUser[getRandomNumberInRange(1, sizeName)] + rootEmail).toLowerCase();
        
        System.out.println("");
        System.out.println(nameUser[0] + ", por favor escolha uma das opções abaixo para o seu e-mail institucional:");
        for(int i = 0; i<5; i++){
            System.out.println(i + 1 + " - " + choicesEmail[i]);
        }
        System.out.println("");
        int j = checkEmailChoices(keyboard);
        while(j == -2){
            System.out.println("");
            System.out.println("Por favor insira uma das opções abaixo ou digite 0 para cancelar a ação.");
            System.out.println(nameUser[0] + ", por favor escolha uma das opções abaixo para o seu e-mail institucional:");
            System.out.println("");
            for(int i = 0; i<5; i++){
                System.out.println(i + 1 + " - " + choicesEmail[i]);
            }
            System.out.println("");
            j = checkEmailChoices(keyboard);
        }
        if(j == -1){
            keyboard.close();
            return null;
        }
        keyboard.close();
        return choicesEmail[j];
    }

    // Precisou mudar o retorno da função ArrayList<User> para object e assim retornar 3 valores(ArrayList<User>,String uffEmailNovo, String number)
    public static Object[] makeEmail(ArrayList<User> users, String numberID, String rootEmail) {
        Object[] usersUffEmailNumber = new Object[3];
        for(int i = 0; i<users.size(); i++){
            if(users.get(i).getnumberID().equals(numberID)){
                if(users.get(i).getStatus().equals("Inativo") || (!users.get(i).getuffEmail().equals(""))){
                    System.out.println("Usuario já possui e-mail constitucional ou usuario está inativo");
                    //printUser(users.get(i));
                    return null;
                }else{
                    //printUser(users.get(i));
                    String uffEmailNovo = emailChoices(nameUser(users.get(i).getFullName()), rootEmail);
                    users.get(i).setUffEmail(uffEmailNovo);
                    if(uffEmailNovo == null){
                        return null;
                    }else{
                        usersUffEmailNumber[0] = users;
                        usersUffEmailNumber[1] = uffEmailNovo;
                        usersUffEmailNumber[2] = users.get(i).getNumber();
                        return usersUffEmailNumber;
                    }
                    //printUser(users.get(i));
                }
                
            }
        }System.out.println("Usuario não encontrado");
        return null;
    }

    public static boolean writeOnFile(String sourceCSV, ArrayList<User> users) {
       try{
            FileWriter csvFile = new FileWriter(sourceCSV);
            PrintWriter contentWrite = new PrintWriter(csvFile);
            for(int i = 0; i<users.size(); i++){
                String line = users.get(i).getFullName() + "," + users.get(i).getnumberID() + "," + users.get(i).getNumber() + "," + users.get(i).getEmail()
                            + "," + users.get(i).getuffEmail() + "," + users.get(i).getStatus();
                contentWrite.println(line);
            }
            contentWrite.close();
            return true;
            
        } catch(FileNotFoundException e){
            System.out.println("Arquivo de gravação não encontrado");
            System.out.println("" + e.getMessage());
            return false;

        }catch(IOException e){
            System.out.println("Erro ao gravar arquivo!");
            System.out.println("" + e.getMessage());
            return false;
        }
    }
    public static boolean checkNumberID(String numberID) {
        if(numberID.length() == 6){
            try{
                int test = Integer.parseInt(numberID);
                return true;

            } catch(NumberFormatException e){
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        // Hiper parametros
        String sourceCSC =  "./data/employee1.csv";
        String sourceCSC2 = "./data/employee2.csv";

        String rootEmail = "@exemplo.com.br";

        Scanner keyboard = new Scanner(System.in);

        ArrayList<User> users = readCSV(sourceCSC);

        System.out.println("Digite o seu código: ");
        String numberID = keyboard.nextLine();

        if(checkNumberID(numberID)){
            
            Object[] usersUffEmailNumber = makeEmail(users, numberID, rootEmail);
            keyboard.close();

            if(usersUffEmailNumber != null){

                users = (ArrayList<User>) usersUffEmailNumber[0]; // warning de typeCast de Object para ArrayList<User>
                
                if(users.get(0) instanceof User){ // verficando o warning de typeCast de Object para ArrayList<User>
                    if(writeOnFile(sourceCSC2, users)){
                        System.out.println("");
                        System.out.printf("A criação de seu e-mail (%s) será feita nos próximos minutos. \n", usersUffEmailNumber[1]);
                        System.out.printf("Um SMS foi enviado para %s com a sua senha de acesso. \n", usersUffEmailNumber[2]);
                    }else{
                        System.out.printf("Houve um erro na criação do e-mail (%s) \n", usersUffEmailNumber[1]);
                    }
                }else{
                    //System.out.println("Erro de typeCast de Object para ArrayList<User>");
                }
            }else{
                //System.out.println("Fim da execução");
            }
        }else{
            System.out.println("Código inválido.");
        }
    }
}