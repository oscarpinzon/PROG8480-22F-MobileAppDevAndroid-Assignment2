package www.oplibrary.com;

public class User {
    public int id;
    public String username;
    public String emailId;
    public String password;

    public User(int id, String username, String emailId, String password) {
        this.id = id;
        this.username = username;
        this.emailId = emailId;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }
}
