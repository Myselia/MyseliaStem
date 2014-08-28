package cms.databank.structures;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cms.monitoring.LogSystem;
 
public class Database{ 
	
	private static final String driver = "com.mysql.jdbc.Driver";
	
	private Connection connection;
	
	private String name;
	private String url;
	private String dbName;
	private String userName;
	private String password;
	
	public Database(String name, String url, String dbName, String userName, String password){ 
		this.name = name;
		this.url = url; 
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;		
	}
	
	public void startConnection(){
		try { 
			Class.forName(driver).newInstance(); 
			connection = DriverManager.getConnection(url+dbName,userName,password);
			LogSystem.log(true, false, "DATABASE CONNECTION STARTED");
		}catch (Exception e){ 
			System.out.println("e>Database connection error");
			e.printStackTrace(); 
		} 
	}
	
	public void closeConnection(){
		try {
			connection.close();
			LogSystem.log(true, false, "DATABASE CONNECTION CLOSED");
		} catch (SQLException e) {
			System.out.println("e>Error closing connection");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return connection;
	}

}