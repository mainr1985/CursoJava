package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;
	
	//método para abrir a conexão
	public static Connection getConection() {
		if (conn == null) {
			Properties props = loadProperties();
			String url = props.getProperty("dburl");
			try {
				conn = DriverManager.getConnection(url, props);
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		return conn;
	}
	
	//método para fechar a conexão
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException (e.getMessage());				
			}
		}
	}
	
	private static Properties loadProperties() {
			
		try (FileInputStream fs = new FileInputStream("db.properties")){ //abre o arquivo contendo os dados da conexão com o banco
			Properties props = new Properties();
			props.load(fs);
			return props;
		} 
		
		catch (IOException e) {
			throw new DbException(e.getMessage()); //pega a exceção da classe criada
		}	
	}
	
	//método para finalizar a conexão do statement. 
	public static void closeStatement(Statement st) {
		 if (st!=null) { //se estiver conectado...
			 try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage()); //chama a exceção personalizada que lança uma runtime exception (não precisa tratar toda hora).
				
			}
		 }
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs!=null) {
			try {
				rs.close();
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}