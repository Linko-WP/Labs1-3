package com.google.gwt.sample.stockwatcher.server;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.google.gwt.sample.stockwatcher.client.GreetingService;
import com.google.gwt.sample.stockwatcher.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;



/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	public String testSQL() {
	      String str = "Result: ";
	      try {

	         Class.forName("com.mysql.jdbc.Driver").newInstance();
	         Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db_lab3",
		                       "invitado","invipass");

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
