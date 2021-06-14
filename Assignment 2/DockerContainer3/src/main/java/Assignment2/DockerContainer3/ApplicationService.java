package Assignment2.DockerContainer3;


import java.sql.*;
import java.util.ArrayList;

/*							References																	*/
/* [1] "Spring Boot Tutorial", https://www.javatpoint.com/ [online]										*/
/*         https:https://www.javatpoint.com/spring-boot-tutorial										*/
/* [3] "Bootstrap Tutorial",  w3schools.com [online]				   									*/
/*         https:https://www.w3schools.com/bootstrap/  													*/

public class ApplicationService {

    public ArrayList<String> fetchOnlineUsers() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://34.93.143.112/DOCKERAPPLICATION","root","");
        
        ArrayList<String> list = new ArrayList<String>();

        Statement statement = connection.createStatement();

        String sql = "SELECT UR.userName FROM USER_STATE UE INNER JOIN USER_REGISTRATION UR ON UE.userID = UR.userID WHERE UE.sessionID is not null AND isOnline = 1 ";

        ResultSet rs = statement.executeQuery(sql);

        while(rs.next())
        {
            list.add(rs.getString("userName").toString());
        }

        return list;
    }
}
