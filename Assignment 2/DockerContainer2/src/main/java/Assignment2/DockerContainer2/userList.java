package Assignment2.DockerContainer2;

import java.util.ArrayList;

public class userList {
	
	private ArrayList<String> userList;
	
	public userList(ArrayList<String> userList)
	{
		this.userList = userList;
	}
	
	public ArrayList<String> getUserList()
	{
		return userList;
	}
	
	public void setUserList(ArrayList<String> list)
	{
		userList = list;
	}
}
