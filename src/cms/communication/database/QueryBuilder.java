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
	
	public QueryBuilder(){
		
	}
	
	
	public void printLastFive(Connection connection){
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM connectiontracker ORDER BY id DESC LIMIT 5");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println("e>Error accessing last five");
			e.printStackTrace();
		}
		
	}

	public void sendNew(Connection connection){
		try {			

			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			e.nextElement();
			e.nextElement();
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Enumeration<InetAddress> ee = n.getInetAddresses();
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
	    	String ip = resultSet.getString("ip");
	    	String ts = resultSet.getString("ts");

	    	System.out.println("Connection number: " + id + " from " + ip + " at " + ts);
		}
		System.out.println();
	}

}
