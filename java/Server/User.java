package Server;

public class User {
    private String login;
    private String password;
    private String email;

    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public void setLogin(String login) {
        this.login=login;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public void setEmail(String email) {
        this.email=email;
    }
}
