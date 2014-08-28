package cms.communication.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
 
public class Database{ 
	
	private Connection conn;
	
	private ResultSet resultSet;
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
		}catch (Exception e){ 
			System.out.println("e>Database connection error");
			e.printStackTrace(); 
		} 
	}
	
	public void closeconn() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("e>Error closing connection");
			e.printStackTrace();
		}
	}
	
	public void printlastfive(){
		try {
			preparedStatement = conn.prepareStatement("SELECT * FROM nodestatus ORDER BY id DESC LIMIT 5");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println("e>Error accessing last five");
			e.printStackTrace();
		}
		
	}

	public void dostuff(){
		Random rand = new Random();
		try {			
			preparedStatement = conn.prepareStatement("INSERT INTO nodestatus"
			        		  + "(node_id, temp, cpu, ram, part)"
			        		  + "VALUES"
			        		  + "(?, ?, ?, ?, ?);");
			preparedStatement.setInt(1, Math.abs(rand.nextInt())%10);
			preparedStatement.setDouble(2, Math.abs(rand.nextDouble())%5 * 10 +10);
			preparedStatement.setDouble(3, Math.abs(rand.nextDouble())%10 * 5);
			preparedStatement.setDouble(4, 430.0);
			preparedStatement.setInt(5, 2150);
			preparedStatement.executeUpdate();
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