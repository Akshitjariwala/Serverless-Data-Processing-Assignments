package Assignment2.DockerContainer2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.client.RestTemplate;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/*							References																	*/
/* [1] "Spring Boot Tutorial", https://www.javatpoint.com/ [online]										*/
/*         https:https://www.javatpoint.com/spring-boot-tutorial										*/
/* [2] "RestTemplate Module",  docs.spring.io [online]				   									*/
/*         https:https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html  	*/
/* [3] "Bootstrap Tutorial",  w3schools.com [online]				   									*/
/*         https:https://www.w3schools.com/bootstrap/  													*/


public class ValidationService {

    public Boolean validateUser(User user) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
		
        Connection connection = DriverManager.getConnection("jdbc:mysql://34.93.143.112/DOCKERAPPLICATION","root","");

        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM USER_REGISTRATION WHERE userEmail = '"+user.getUserEmail()+"' AND userPassword ='"+user.getUserPassword()+"'";

        ResultSet result = statement.executeQuery(sql);

        if(result.next())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void updateUserState(User user, HttpServletRequest request) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
		
        Connection connection = DriverManager.getConnection("jdbc:mysql://34.93.143.112/DOCKERAPPLICATION","root","");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        Statement statement = connection.createStatement();
        HttpSession session = request.getSession();
        
        String sql1 = "SELECT * FROM USER_REGISTRATION WHERE userEmail ='"+user.getUserEmail()+"'";

        ResultSet rs = statement.executeQuery(sql1);

        int id=0;

        if(rs.next())
        {
            id = rs.getInt("userID");
            session.setAttribute("userName",rs.getString("userName"));
            session.setAttribute("userID",rs.getString("userID"));
        }

        String sql = "UPDATE USER_STATE SET sessionID = '"+session.getId()+"', lastLoggedIn = '"+timeStamp+"' , isOnline = true WHERE userID = '"+id+"'";

        statement.executeUpdate(sql);
    }

    public void makeUserOffline(HttpServletRequest request) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
		
        Connection connection = DriverManager.getConnection("jdbc:mysql://34.93.143.112/DOCKERAPPLICATION","root","");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        
        HttpSession session = request.getSession();
        
        Statement statement = connection.createStatement();

        String sql1 = "SELECT * FROM USER_STATE WHERE userID = "+session.getAttribute("userID")+"";

        ResultSet rs = statement.executeQuery(sql1);

        int id=0;

        if(rs.next())
        {
            id = rs.getInt("userID");
        }

        System.out.println(id);
        
        String sql = "UPDATE USER_STATE SET sessionID = null , lastLoggedOut = '"+timeStamp+"' , isOnline = false WHERE userID = '"+id+"'";

        System.out.println(sql);
        	
        statement.executeUpdate(sql);
    }
    
    public ArrayList<String> fetchOnlineUsers(RestTemplate restTemplate)
    {
    	String url = "https://dockercontainer3-32q7bf3xnq-de.a.run.app/home";
    	
    	ArrayList<String> list = restTemplate.getForObject(url,ArrayList.class);
    	
    	return list;
    }
	
}
