package eu.cartsc.fermate.servlet;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class mySqlConnection {
	String user = "root";
	String psw = "liera";
	String url = "jdbc:mysql://localhost:3306/fermate";

	public Connection connetti() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.printf("Driver non trovati");
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(url, user, psw);
		} catch (SQLException e) {
			System.out.printf("Connessione fallita\r");
			e.printStackTrace();
		}
		if (conn != null)
			System.out.printf("Connessione al db aperta\r");

		return conn;
	}

}
