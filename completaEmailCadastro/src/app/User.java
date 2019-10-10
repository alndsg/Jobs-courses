package app;

/**
 * usuario
 */
public class User {
    // attributes
    private String fullName;
    private String numberID;
    private String number;
    private String email;
    private String uffEmail;
    private String status;

    // essentials methods: set
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setnumberID(String numberID){
        this.numberID = numberID;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUffEmail(String uffEmail) {
        this.uffEmail = uffEmail;        
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // essentials methods: get
    public String getFullName() {
        return fullName;
    }
    public String getnumberID(){
        return numberID;
    }
    public String getNumber() {
        return number;
    }
    public String getEmail() {
        return email;
    }
    public String getuffEmail() {
        return uffEmail;
    }
    public String getStatus() {
        return status;
    }

}