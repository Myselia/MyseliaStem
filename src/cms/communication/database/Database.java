package cms.communication.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cms.monitoring.LogSystem;

 
public class Database{ 
	public Database(){ 
		String url = "jdbc:mysql://132.205.84.209:3306/"; 
		String dbName = "mycelia";
		String userName = "root"; 
		String password = "mycelia"; 
		
		String driver = "com.mysql.jdbc.Driver";
		try { 
			Class.forName(driver).newInstance(); 
			Connection conn = DriverManager.getConnection(url+dbName,userName,password);
			dostuff(conn);
			conn.close();
			
		}catch (Exception e){ 
			System.out.println("e>Database connection error");
			e.printStackTrace(); 
		} 
	}
	private void dostuff(Connection conn){
		try {
			Statement statement = conn.createStatement();
			long time = System.nanoTime();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM nodestatus");
			writeResultSet(resultSet);
			time = System.nanoTime() - time;
			LogSystem.log(true, false, time + "");
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
	}

}

