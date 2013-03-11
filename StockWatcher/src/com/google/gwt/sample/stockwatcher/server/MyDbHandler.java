package com.google.gwt.sample.stockwatcher.server;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.*;



public class MyDbHandler {

	public String testSQL() {
	      String str = "Result: ";
	      try {
	    	  Class.forName("com.mysql.jdbc.Driver");
	    	  Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/db_lab3" +
	    	                      "user=invitado&password=invipass");
	         
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
	
}
