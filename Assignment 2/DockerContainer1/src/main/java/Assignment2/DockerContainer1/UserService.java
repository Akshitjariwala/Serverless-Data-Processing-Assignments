package Assignment2.DockerContainer1;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import javax.servlet.http.HttpServletRequest;

public class UserService {

    public int saveUser(User user) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
		
        Connection connection = DriverManager.getConnection("jdbc:mysql://34.93.143.112/DOCKERAPPLICATION","root","");
		/*
		 * Connection connection =
		 * DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dockerapplication",
		 * "root","Aksh@1804");
		 */
        
        String sql = "INSERT INTO USER_REGISTRATION(userName,userPassword,userEmail,userTopic) VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1,user.getUserName());
        ps.setString(2,user.getUserPassword());
        ps.setString(3,user.getUserEmail());
        ps.setString(4,user.getUserTopic());

        int result = ps.executeUpdate();

        return result;
    }

    public int saveUserState(User user,HttpServletRequest request) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
		
        Connection connection = DriverManager.getConnection("jdbc:mysql://34.93.143.112/DOCKERAPPLICATION","root",""); 
		/*
		 * Connection connection =
		 * DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dockerapplication",
		 * "root","Aksh@1804");
		 */
        Statement statement = connection.createStatement();

        String sql1 = "SELECT * FROM USER_REGISTRATION WHERE userEmail ='"+user.getUserEmail()+"'";

        ResultSet rs = statement.executeQuery(sql1);

        String sql = "INSERT INTO USER_STATE(userID,sessionID,isOnline,lastLoggedIn,lastLoggedOut) VALUES(?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        int id=0;

        if(rs.next())
        {
            id = rs.getInt("userID");
        }

        ps.setInt(1,id);
        ps.setString(2,null);
        ps.setString(3,"false");
        ps.setTimestamp(4, null);
        ps.setTimestamp(5, null);

        int result = ps.executeUpdate();

        return result;
    }
}
