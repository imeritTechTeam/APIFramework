package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconnection {
	public static Connection con;
	
	public static void dbstartup()
	{
	
	try{  
		  Class.forName("com.mysql.cj.jdbc.Driver");  
		  con = DriverManager.getConnection("jdbc:mysql://testimppinstance.cy4e3hacstmv.us-west-2.rds.amazonaws.com:3306/impp_demo_v2_09122017","imppdb","rds@ws#2017");  		 
		  System.out.println("Database connected");	  		 		 
		  }
	  	catch(Exception e)
	  	{
	  		System.out.println(e); 
		}  
	}

}
