package database;

import java.sql.*;

public class DBQuery {
	public static Connection connection;
	
	public DBQuery(){
		
	}
	
	public static void makeCon(){
		try {
			connection = DriverManager.getConnection(DBCreator.JDBC_URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String query) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		return rs;
	}
	
	public static ResultSetMetaData getMetadata(String query) throws SQLException{
		Connection connection = DriverManager.getConnection(DBCreator.JDBC_URL);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		return rsmd;
	}
	
	public static void main(String[] args) throws SQLException{ 
		/*Connection connection = DriverManager.getConnection(DBCreator.JDBC_URL);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from proc");
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
		if(connection != null) connection.close();*/
		
	}
}
