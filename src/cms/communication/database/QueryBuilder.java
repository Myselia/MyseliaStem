package cms.communication.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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

	public void dostuff(){
		Random rand = new Random();
		try {			
			preparedStatement = connection.prepareStatement("INSERT INTO nodestatus"
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
