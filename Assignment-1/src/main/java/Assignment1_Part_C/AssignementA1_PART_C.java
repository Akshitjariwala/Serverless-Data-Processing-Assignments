package Assignment1_Part_C;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

/* 								Reference 											   */
/* [1] "Java JDBC Tutorial",javatpoint.com [online]  								   */
/*  	Available: https://www.javatpoint.com/example-to-connect-to-the-mysql-database */
/*		Accessed : 24th May 2021                                                       */
/* [2] "Amazon Simple Storage Service", docs.aws.amazon.com [online]				   */
/*  	https://docs.aws.amazon.com/AmazonS3/latest/userguide/download-objects.html    */
/*  	Accessed : 24th May 2021 													   */
/* [3] "Working with AWS Credentials",  docs.aws.amazon.com [online]				   */
/*		https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html   */
/*  	Accessed : 24th May 2021 													   */

@SpringBootApplication
public class AssignementA1_PART_C {

	public static void main(String args[])
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userauthentication","root","Aksh@1804");
			
			
			Regions region = Regions.US_EAST_1;
			BasicSessionCredentials creds = new BasicSessionCredentials(
					"ASIAVJDCQO5A4C4SHZW2",
					"FsQqXD9xwbHFTdK9QO5LiHVQIWkML27PNSUVQljA",
			"IQoJb3JpZ2luX2VjEHAaCXVzLXdlc3QtMiJHMEUCIGWDHFlvDAXhOpTxwJP+GoBIULbhCAR6wDtjDVO7u2n9AiEArJWRhZZibOB+RyB4yjjErTrLkG9mdUSJab6oFhKkUnYqqAIIGRAAGgwzNjMxMzEzMzY1MTMiDC4Prez19pw8M6UQNiqFAsyLZng/pNKxRd6wCldcbO7E0o1iymjGMMlh4jqQQO7Al2xoKYivub3qZHjH3fvoFRGUSyLfBHgYWIIbTs2ZeKLrCRmtcGyaBGNJ01LfSvX5MMkk0WWn1nz05icDDy+OdOLAj48tHxm+Q39mY/z1A8L29Sfwd+cYyIYd6xxI6W4fcRNgghcNco39Dc/yCEtiX0XAy2Xx4MG0CL9KjuSZnzHnbRE/e8DZ8Vw92T+HcYXqG5h2vugVxmqVQafEQ0x7cOgeUenL6ZfjzXvzZQB22t5op0SvjBgdBZLBGapUnQ156Ae/1cLkwZM6+sIXiIwSuqVPyjNYQYcrHIcdx8WGUL82JHhm4zC8xbSFBjqdAYUjQp9uvU/Nisn2BRB81IBVN9hLwhZUVzFNV24mtIpz0gdiyJIJTpVcecO8AU/MoCC0hqD+CXiQgf/97rDXmzIljWXo7Wup8yDAg9QvBoTS0Y3FBxnI2C6JjmIhWnkY33V8YC7kCuG3Zdapx8/0PWGxgi2EBK5/PehN37VJL7ydERHY2Je54t4eab1qGA8d//dZ4FfkajYjiepBiQQ=");
			AmazonS3 s3Bucket = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(region).build();
			String bucketName = "lookuptablebucket",fileKey = "Lookup5410.txt",ln=null,value=null;
			char key;
			S3Object object = s3Bucket.getObject(new GetObjectRequest(bucketName,fileKey));
			InputStream lookUp = object.getObjectContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(lookUp));
			final HashMap<Character,String> map = new HashMap<Character,String>();
			
			while((ln = buf.readLine()) != null)
			{
				key = ln.charAt(0);
				if(key == 'A')
				{
					continue;
				}
				value = ln.substring(1).replaceAll("\\s", "");
				map.put(key,value);
				
			}
			
			lookUp.close();
			
			int option;
			
			do
			{
				System.out.println("Choose your action.");
				System.out.println("1 -> Insert User");
				System.out.println("2 -> Fetch User");
				System.out.println("3 -> Exit");
				
				BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
				option = Integer.parseInt(buffer.readLine());
				
				switch(option)
				{
					case 1 : insertUser(map,con);
							break;
					case 2 : retrivePassword(map,con);
							break;
					default : System.exit(0);
							break;
				}
			}while(option != 3);
			
			System.exit(0);
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}

	
	public static void insertUser(HashMap<Character,String> map,Connection con)
	{
		try
		{
			int userId=0;
			String password,encryptedPassword,SQL;
			System.out.println("Creating new User");
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter User ID :");
			userId=sc.nextInt();

			System.out.println("Enter Password :");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			password = br.readLine().replaceAll("\\s", "");
			encryptedPassword = encryptPWD(password,map);
			
			if(con != null)
			{
				SQL = "INSERT INTO userLogin(UserID,Password) VALUES(?,?)";
				
				String SQL1 = "SELECT * FROM userLogin WHERE UserID = ?"; 
				
				PreparedStatement statement = con.prepareStatement(SQL1);
				statement.setInt(1, userId);
				ResultSet user = statement.executeQuery();
				
				if(user.next())
				{
					System.out.println("User already exists in the database.");
				}
				else
				{
					PreparedStatement insertStatement = con.prepareStatement(SQL);
					insertStatement.setInt(1,userId);
					insertStatement.setString(2,encryptedPassword);
					
					int result = insertStatement.executeUpdate();
					
					if(result > 0)
					{
						System.out.println("User Created Successfully.");
						System.out.println("UserID : "+userId);
						System.out.println("Password before Encryption : "+password);
						System.out.println("Password after Encryption : "+encryptedPassword);
					}
				}
			}
			else
			{
				System.out.println("Database connection not established.");
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
			

	}
	
	public static String encryptPWD(String password,HashMap<Character,String> map)
	{
		Character c;
		String encPassword=null,buffer = null;
		
		for(int i=0;i<password.length();i++)
		{
			c = password.charAt(i);
			buffer = encLookUp(c,map);
			if(encPassword == null)
			{
				encPassword = buffer;
			}else
			{
				encPassword = encPassword+buffer;
			}
		}
		
		return encPassword;
	}
	
	public static String encLookUp(Character c,HashMap<Character,String> map)
	{
		String encyChar = null;
		for(Map.Entry<Character,String> m:map.entrySet())
		{
			if(c == m.getKey())
			{
				encyChar = m.getValue().toString();
			}
		}
		
		return encyChar;
	}
	
	public static String decryptPWD(String password,HashMap<Character,String> map)
	{
		String decPwd=null;
		String buffer = null;
		Character c;
		
		for(int i=0;i<password.length();i++)
		{
			buffer = password.substring(i,i+2);
			i++;
			if(decPwd == null)
			{
				c = decLookUp(buffer,map);
				decPwd = c.toString();
			}
			else
			{
				decPwd = decPwd + decLookUp(buffer,map);
			}
			
		}
		
		return decPwd;
	}
	
	public static char decLookUp(String s,HashMap<Character,String> map)
	{
		char decChar='a';
		
		for(Map.Entry<Character,String> m:map.entrySet())
		{
			if(s.equals(m.getValue()))
			{
				decChar = m.getKey();
			}
		}
		
		return decChar;
	}
	
	public static void retrivePassword(HashMap<Character,String> map,Connection con)
	{
		
		try
		{
			int userId;
			String SQL;
			
			System.out.println("Enter User ID : ");
			Scanner input = new Scanner(System.in);
			userId = input.nextInt();
			String dPassword;
			
			System.out.println("Fetching user from database...");
			
			SQL = "SELECT * FROM userLogin WHERE UserID = ? ";
			
			if(con != null)
			{
				PreparedStatement statement = con.prepareStatement(SQL);
				statement.setInt(1, userId);
				ResultSet users = statement.executeQuery();
				
				while(users.next())
				{
					dPassword = decryptPWD(users.getString(2),map);
					System.out.println("User ID : "+users.getInt(1));
					System.out.println("Decrypted Password : "+dPassword);
				}
				
			}
			else
			{
				System.out.println("Database connection not established.");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		
		
	}
	
}
