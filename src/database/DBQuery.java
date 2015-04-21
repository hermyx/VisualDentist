package database;

import java.sql.*;

public class DBQuery {

	public static final String SQL_STATEMENT = "select * from proc";
	
	public static void main(String[] args) throws SQLException{ 
		Connection connection = DriverManager.getConnection(DBCreator.JDBC_URL);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(SQL_STATEMENT);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		for(int i=1;i<=columnCount;i++){
			System.out.format("%20s", rsmd.getColumnName(i) + " | ");
		}
		
		while(rs.next()){
			System.out.println("");
			for(int i=1; i<=columnCount;i++){
				System.out.format("%20s", rs.getString(i) + " | ");
			}
		}
		
		if(statement != null) statement.close();
		if(connection != null) connection.close();
	}
}
