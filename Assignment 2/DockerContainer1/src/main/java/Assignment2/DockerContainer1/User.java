package Assignment2.DockerContainer1;

public class User {

    private String userName;
    private String userPassword;
    private String userEmail;
    private String userTopic;

    public User(String userName, String userPassword, String userEmail, String userTopic) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userTopic = userTopic;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserPassword()
    {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public String getUserEmail()
    {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getUserTopic()
    {
        return this.userTopic;
    }

    public void setUserTopic(String userTopic)
    {
        this.userTopic = userTopic;
    }

    @Override
    public String toString()
    {
        return String.format("User [Name=%s, Email=%s, Topic=%s]", userName, userEmail, userTopic);
    }

}
