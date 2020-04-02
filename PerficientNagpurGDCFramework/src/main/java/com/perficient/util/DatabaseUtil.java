package com.perficient.util;

/**
 * Utility to Access The database and pass queries to it.
 * @author ameya.pagore
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.Status;

public class DatabaseUtil {

	Connection connection;
	protected ExtentTest test;
	protected Log log = LogFactory.getLog(this.getClass());
	
	public DatabaseUtil( ExtentTest extentTest) {
		test = extentTest;
	}

/**
 * This method connects to DataBase.	
 * @param username
 * @param password
 * @param url
 * @param environment
 */
public  void connectToDb(String username, String password, String url, String environment)
{
	if(environment.equalsIgnoreCase("QA")){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(url,username,password);
			if(connection!=null){
				System.out.println("Connected to database "+ url);
				test.log(Status.PASS, "Connected to database "+ url);
			}
		} catch (Exception e)
		{
			System.out.println("Not able to connect to database: " + url);
			test.log(Status.FAIL, "Not able to connect to database: " + url );
			e.printStackTrace();
		}
	}
	else{
		System.out.println("failed to connect to " + environment +"database");
	}
}

/**
 * This method runs select query.
 * @param selectQuery
 * @param updateQuery
 * @throws ClassNotFoundException
 */
public void selectQuery(String selectQuery) throws ClassNotFoundException{
	try {
		Statement stmt= connection.createStatement(); 
		if(connection!=null){
			
			 ResultSet rs= stmt.executeQuery(selectQuery);
			 test.log(Status.PASS, "Read Data from database.");
			
			//This is just an example for reference. User will have to change the loop a per data their database.
			while(rs.next()) {
				int EmpId= rs.getInt("employeeNumber");
				String firstName= rs.getString("firstName");
				String lastName= rs.getString("lastName");
				String extension = rs.getString("extension");
				String email = rs.getString("email");
				String reportTo = rs.getString("reportsTo");
				String job = rs.getString("jobTitle");
				System.out.println(EmpId+"\t"+firstName+"\t"+lastName+"\t"+extension+"\t"+email+"\t"+reportTo+"\t"+job+"\t");
				}
		}
	} catch (SQLException e) {
			e.printStackTrace();
	}	
}


/**
 * This method runs the update query.
 * @param selectQuery
 * @param updateQuery
 * @throws ClassNotFoundException
 */
public void updateQuery( String updateQuery) throws ClassNotFoundException{
	try {
		Statement stmt= connection.createStatement(); 
		if(connection!=null){
			
			 stmt.executeUpdate(updateQuery);
			 test.log(Status.PASS, "Update Data from database.");
		}
	} catch (SQLException e) {
			e.printStackTrace();
	}	
}

/**
 * This method closes the connection to database.
 */
public void closeDb()
{	
	try
	{
		connection.close();
		test.log(Status.PASS, "Connection to Database Closed.");
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

/**
 * This method connects to database with access.
 */
public  void connectDbWithAccess(String username, String password){
	       String database="DatabaseName.mdb";
		   String url="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};  DBQ=" + database + ";DriverID=22;READONLY=true";  
		   try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		
		   Connection c=DriverManager.getConnection(url,username,password); 
		   Statement st=c.createStatement(); 
		   }
		   catch (Exception e) {
			e.printStackTrace();
		}
	}
}



