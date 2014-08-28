package cms.communication.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class Database{ 
	
	private Connection conn;
	private Statement statement;
	private PreparedStatement preparedStatement;
	
	public Database(){ 
		String url = "jdbc:mysql://132.205.84.209:3306/"; 
		String dbName = "mycelia";
		String userName = "root"; 
		String password = "mycelia"; 
		
		String driver = "com.mysql.jdbc.Driver";
		try { 
			Class.forName(driver).newInstance(); 
			conn = DriverManager.getConnection(url+dbName,userName,password);
			dostuff();
			closeconn();
		}catch (Exception e){ 
			System.out.println("e>Database connection error");
			e.printStackTrace(); 
		} 
	}
	
	private void closeconn() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("e>Error closing connection");
			e.printStackTrace();
		}
	}

	private void dostuff(){
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM nodestatus");
			writeResultSet(resultSet);
			
			
			/*preparedStatement = conn.prepareStatement("INSERT INTO nodestatus values"
			        		  + "(node_id, temp, cpu, ram, part)"
			        		  + "VALUES"
			        		  + "(?, ?, ?, ?, ?)");

			      preparedStatement.setString(1, "1");
			      preparedStatement.setString(2, "35.4");
			      preparedStatement.setString(3, "9.3");
			      preparedStatement.setString(4, "430.0");
			      preparedStatement.setString(5, "2150");
			      preparedStatement.executeUpdate();
			*/
			
			
			statement = conn.createStatement();
			ResultSet resultSetTwo = statement.executeQuery("SELECT * FROM nodestatus");
			writeResultSet(resultSetTwo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
	    	int no = resultSet.getInt("node_id");
	    	double temp = resultSet.getDouble("temp");
	    	double cpu = resultSet.getDouble("cpu");
	    	double ram = resultSet.getDouble("ram");
	    	int part = resultSet.getInt("part");
	    	System.out.println(id + " " + no + " " + temp + " " + cpu + " " + ram + " " + part);
		}
		System.out.println();
	}

}