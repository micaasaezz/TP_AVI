package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import servicios.Consultas;

public class UConexion {
	private static UConexion instance;
	
	private UConexion() {
		
	}
	
	public static UConexion getInstance() {
		if(instance == null){
			instance = new UConexion();
		}
		return instance;
	}
	
	// armar una conexión a base de datos
	public Connection connectBD() throws SQLException, ClassNotFoundException {
		ResourceBundle rs = ResourceBundle.getBundle("framework");
		Class.forName(rs.getString("driver"));
		Connection connection = DriverManager.getConnection(
				rs.getString("url"),
				rs.getString("user"),
				rs.getString("pass")); 
		return connection;
	}
}
