package cms.communication.database;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;

public class QueryBuilder {
	
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Connection connection;
	
	public QueryBuilder(){
		
	}
	
	
	public void printlastfive(){
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM nodestatus ORDER BY id DESC LIMIT 5");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println("e>Error accessing last five");
			e.printStackTrace();
		}
		
	}

	public void sendNew(Connection connection){
		try {			

			Enumeration e = NetworkInterface.getNetworkInterfaces();
			e.nextElement();
			e.nextElement();
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Enumeration ee = n.getInetAddresses();
			InetAddress i = (InetAddress) ee.nextElement();
	        String ip = i.getHostAddress(); //---------------------THIS
	        
	        Date dateobj = new Date();
	        String date = dateobj.toString();//--------------------------THIS
			
			preparedStatement = connection.prepareStatement("INSERT INTO connectiontracker"
			        		  + "(ip, ts)"
			        		  + "VALUES"
			        		  + "(?, ?);");
			preparedStatement.setString(1, ip);
			preparedStatement.setString(2, date);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
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
