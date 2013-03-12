package com.google.gwt.sample.stockwatcher.server;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.stockwatcher.client.MyService;
import com.mysql.jdbc.*;


public class MyServiceImpl extends RemoteServiceServlet implements MyService {

  /**
	 * 
	 */
	private static final long serialVersionUID = 7081621504101146086L;

	public String myMethod(String s) {
		// Do something interesting with 's' here on the server.
	  
	  String str = "Result: ";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();
	     stat.executeUpdate("drop table if exists people;");
	
	     stat.executeUpdate("create table people (name varchar(20), occupation varchar(20));");
	
	     PreparedStatement prep = (PreparedStatement) conn
	           .prepareStatement("insert into people values (?, ?);");
	     prep.setString(1, "Gandhi");
	     prep.setString(2, "politics");
	     prep.execute();
	
	     ResultSet rs = stat.executeQuery("select * from people;");
	     while (rs.next()) {
	        str += "name = " + rs.getString("name");
	        str += "\njob = " + rs.getString("occupation");
	     }
	     rs.close();
	     conn.close();
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
    return str;
  }

	/**										*
	 * Connects to local database db_lab3	*
	 * 										*/
	private Connection connect(){
		
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "db_lab3";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "invitado"; 
		String password = "invipass";
		try {
			Class.forName(driver).newInstance();
			conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
			System.out.println("Connected to the database");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**									*
	 * Disconnects from local database	*
	 * 									*/
	private void disconnect(Connection conn){
		
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
