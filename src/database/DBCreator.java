package database;
import java.sql.*;

public class DBCreator {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:pdb;create = true";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		//connection.createStatement().execute("create table proc (pName varchar(20) PRIMARY KEY, pCost varchar(10))");
		connection.createStatement().execute("insert into proc(pName, pCost) values "
				+ "('X-Ray',100),"
				+ "('Filling',150)");
	}
}
