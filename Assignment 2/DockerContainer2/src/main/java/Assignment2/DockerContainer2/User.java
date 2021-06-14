package Assignment2.DockerContainer2;

public class User {


    private String userEmail;
    private String userPassword;

    public User(String userPassword, String userEmail) {
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }


    public String getUserEmail()
    {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getUserPassword()
    {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

}
