package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {

	public static void main (String[] args) {
		
		Connection conn = DB.getConection();
		PreparedStatement ps = null; 
			
		try {
			DB.getConection();
			ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			ps.setInt(1, 3);
						
			int rowsAffected = ps.executeUpdate(); //pra ver quantas linhas foram afetadas pelo update
			
			System.out.println("Done! " + rowsAffected);
			
		}
		
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
				
		finally {
			DB.closeStatement(ps);
			DB.closeConnection();	
		}		
	}
}