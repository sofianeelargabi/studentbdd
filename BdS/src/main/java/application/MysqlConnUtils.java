package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnUtils {

	public static Connection getMysqlConnection() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String bd = "bdstudent";
		String userName = "root";
		String password = "Sisi5962!";

		Class.forName("com.mysql.cj.jdbc.Driver");
		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + bd + "?serverTimezone=UTC&useSSL=false";
		Connection con = DriverManager.getConnection(connectionURL, userName, password);

		return con;
	}

}
