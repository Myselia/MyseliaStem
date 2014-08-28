package cms.databank.structures;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class Database{ 
	
	private static final String driver = "com.mysql.jdbc.Driver";
	
	private Connection connection;
	
	private String url;
	private String dbName;
	private String userName;
	private String password;
	
	public Database(String url, String dbName, String userName, String password){ 
		this.url = url; 
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;		
	}
	
	public void startConnection(){
		try { 
			Class.forName(driver).newInstance(); 
			connection = DriverManager.getConnection(url+dbName,userName,password);
		}catch (Exception e){ 
			System.out.println("e>Database connection error");
			e.printStackTrace(); 
		} 
	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("e>Error closing connection");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return connection;
	}

}