package database;
import java.sql.*;

public class DBCreator {
	public static Connection connection;

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:pdb;create = true";
	
	public DBCreator(){
		makeCon();
	}
	
	public static void makeCon(){
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(JDBC_URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void exec(String statement)throws SQLException{
		connection.createStatement().execute(statement);
	}
	
	public static void create(){
		try {
			connection.close();
			DBCreator.makeCon();
			String statement = "";
			statement = "create table proc (pID INT NOT NULL DEFAULT -1 PRIMARY KEY, pName varchar(20), pCost INT)";
			DBCreator.exec(statement);
			statement = "create table pat (pID INT NOT NULL DEFAULT -1 PRIMARY KEY, pName varchar(20), "
					+ "pAddress varchar(75), pPhone BIGINT, pInvoiceList INT)";
			DBCreator.exec(statement);
			statement = "create table inv (iID INT NOT NULL DEFAULT -1 PRIMARY KEY,"
					+ "iIsPaid BOOLEAN, iDate varchar(5), iPaymentList INT, iProcedureList INT)";
			DBCreator.exec(statement);
			statement = "create table pay (pID INT NOT NULL DEFAULT -1 PRIMARY KEY,"
					+ "pDate varchar(5), pAmount INT)";
			DBCreator.exec(statement);
			statement = "insert into proc (pID, pName, pCost) values"
					+ "(0, 'X-Ray', 50),"
					+ "(1, 'Filling', 100),"
					+ "(2, 'Extraction', 75),"
					+ "(3, 'Dentures', 150),"
					+ "(4, 'Apiectomie', 100)";
			DBCreator.exec(statement);
			statement = "create table invL (invLID INT NOT NULL,"
					+ "invID INT NOT NULL)";
			DBCreator.exec(statement);
			statement = "create table payL (payLID INT NOT NULL,"
					+ "payID INT NOT NULL)";
			DBCreator.exec(statement);
			statement = "create table procL (procLID INT NOT NULL,"
					+ "procID INT NOT NULL)";
			DBCreator.exec(statement);
		} catch (SQLException e){
			System.out.println("The database already existed");
		}
		
	}
	
	public static void reset() throws SQLException{
		connection.close();
		DBCreator.makeCon();
		String statement = "";
		statement = "Delete from PAT where pID <> -1";
		exec(statement);
		statement = "Delete from INV where iID <> -1";
		exec(statement);
		statement = "Delete from PAY where pID <> -1";
		exec(statement);
		statement = "Delete from INVL where invID <> -1";
		exec(statement);
		statement = "Delete from PAYL where payID <> -1";
		exec(statement);
		statement = "Delete from PROCL where procID <> -1";
		exec(statement);
	}
	
	public final static void main(String[] args) throws SQLException{
		DBCreator.makeCon();
		/*ResultSet rs = DBQuery.executeQuery("select * from syscs_diag.lock_table");
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
		}*/
		String statement = "";
		statement = "drop table proc";
		DBCreator.exec(statement);
		statement = "drop table pat";
		DBCreator.exec(statement);
		statement = "drop table inv";
		DBCreator.exec(statement);
		statement = "drop table pay";
		DBCreator.exec(statement);
		statement = "drop table invL";
		DBCreator.exec(statement);
		statement = "drop table payL";
		DBCreator.exec(statement);
		statement = "drop table procL";
		DBCreator.exec(statement);
		/*statement = "create table proc (pID INT NOT NULL DEFAULT -1 PRIMARY KEY, pName varchar(20), pCost INT)";
		DBCreator.exec(statement);
		statement = "create table pat (pID INT NOT NULL DEFAULT -1 PRIMARY KEY, pName varchar(20), "
				+ "pAddress varchar(75), pPhone BIGINT, pInvoiceList INT)";
		DBCreator.exec(statement);
		statement = "create table inv (iID INT NOT NULL DEFAULT -1 PRIMARY KEY,"
				+ "iIsPaid BOOLEAN, iDate varchar(5), iPaymentList INT, iProcedureList INT)";
		DBCreator.exec(statement);
		statement = "create table pay (pID INT NOT NULL DEFAULT -1 PRIMARY KEY,"
				+ "pDate varchar(5), pAmount INT)";
		DBCreator.exec(statement);
		statement = "insert into proc (pID, pName, pCost) values"
				+ "(0, 'X-Ray', 50),"
				+ "(1, 'Filling', 100),"
				+ "(2, 'Extraction', 75),"
				+ "(3, 'Dentures', 150),"
				+ "(4, 'Apiectomie', 100)";
		DBCreator.exec(statement);
		statement = "create table invL (invLID INT NOT NULL,"
				+ "invID INT NOT NULL)";
		DBCreator.exec(statement);
		statement = "create table payL (payLID INT NOT NULL,"
				+ "payID INT NOT NULL)";
		DBCreator.exec(statement);
		statement = "create table procL (procLID INT NOT NULL,"
				+ "procID INT NOT NULL)";
		DBCreator.exec(statement);*/
	}
}
