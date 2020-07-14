package servicios;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;
import clases.Persona;
import utilidades.UBean;
import utilidades.UConexion;

public class Consultas {
	private static Connection connection;
	
	public static void setConnection(Connection c) {
		Consultas.connection = c;
	}

	// get all elements
	public static List<Persona> getAll(Class c) throws SQLException {
		List<Persona> lista = new ArrayList<>();
		String query = "SELECT * FROM ";
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		query = query.concat(t.nombre());
		
		PreparedStatement ps = connection.prepareCall(query);
		ResultSet rs =  ps.executeQuery();

		while (rs.next()) {
			Persona p = new Persona(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("surname"),
					rs.getString("dni"),
					rs.getInt("edad")
				);
			lista.add(p);
		}
		return lista;
	}
	
	// guardar en la base de datos el objeto
	public static void guardar(Object o) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		String query = "INSERT INTO ";
		Class c = o.getClass();
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		query = query.concat(t.nombre() + " (");
		
		List<Field> atributos = UBean.obtenerAtributos(o);
		String values = " values (";
		for (int i = 0; i < atributos.size(); i++) {
			Field f = atributos.get(i);
			if (f.getAnnotation(Id.class) != null) {
				continue;
			}
			String s = f.getAnnotation(Columna.class).nombre();
			values = values.concat("?");
			if (i != atributos.size()-1) {
				s = s.concat(", ");
				values = values.concat(", ");
			} else {
				s = s.concat(")");
				values = values.concat(")");
			}
			query = query.concat(s);
		}
		query = query.concat(values);
		
		PreparedStatement ps = connection.prepareCall(query);
		for (int i = 0; i < atributos.size(); i++) {
			Field field = atributos.get(i);
			if (field.getAnnotation(Id.class) != null) {
				continue;
			}
			if (field.getType().equals(String.class)) {
				ps.setString(i, (String) UBean.ejecutarGet(o, field.getName()));
			} else if (field.getType().equals(Integer.class)) {
				ps.setInt(i, (int) UBean.ejecutarGet(o, field.getName()));				
			}
		}
		ps.execute();
	}
	
	// modificar todas las columnas, excepto Id
	public static void modificar(Object o) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		String query = "UPDATE ";
		Class c = o.getClass();
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		query = query.concat(t.nombre() + " SET ");
		
		int id = 0;
		List<Field> atributos = UBean.obtenerAtributos(o);
		for (int i = 0; i < atributos.size(); i++) {
			Field f = atributos.get(i);
			if (f.getAnnotation(Id.class) != null) {
				id = (int) UBean.ejecutarGet(o, f.getName());
				continue;
			}
			String s = f.getAnnotation(Columna.class).nombre();
			s = s.concat("=?");
			if (i != atributos.size()-1) {
				s = s.concat(", ");
			}
			query = query.concat(s);			
		}
		query = query.concat(" WHERE id=" + id);
		
		PreparedStatement ps = connection.prepareCall(query);
		for (int i = 0; i < atributos.size(); i++) {
			Field field = atributos.get(i);
			if (field.getAnnotation(Id.class) != null) {
				continue;
			}
			if (field.getType().equals(String.class)) {
				ps.setString(i, (String) UBean.ejecutarGet(o, field.getName()));
			} else if (field.getType().equals(Integer.class)) {
				ps.setInt(i, (int) UBean.ejecutarGet(o, field.getName()));
			}
		}
		ps.execute();		
	}
	
	// eliminar el registro de la base de datos
	public static void eliminar(Object o) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		String query = "DELETE FROM ";
		Class c = o.getClass();
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		query = query.concat(t.nombre() + " WHERE id=");
		
		List<Field> atributos = UBean.obtenerAtributos(o);
		for (int i = 0; i < atributos.size(); i++) {
			Field f = atributos.get(i);
			if (f.getAnnotation(Id.class) != null) {
				query = query.concat(String.valueOf(UBean.ejecutarGet(o, f.getName())));		
				break;
			}			
		}
		
		PreparedStatement ps = connection.prepareCall(query);
		ps.execute();
	}
	
	// devolver un objeto del tipo definido en el param Class, con todos sus datos cargados.
	public static <T> T obtenerPorId(Class c, Object id) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, NoSuchMethodException, SecurityException {
		Object obj = c.getConstructor(new Class[0]).newInstance();
		String query = "SELECT * FROM ";
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		query = query.concat(t.nombre() + " WHERE id=" + id);
		
		List<Field> atributos = UBean.obtenerAtributos(obj); // c.getDeclaredFields();
		PreparedStatement ps = connection.prepareCall(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			for (int i = 0; i < atributos.size(); i++) {
				Field f = atributos.get(i);
				if (f.getAnnotation(Columna.class) != null) {
					String name = f.getAnnotation(Columna.class).nombre();
					Object value = rs.getObject(name);
					UBean.ejecutarSet(obj, name, value);	
				}
			}
		}
		return (T) obj;
	}
	 
}
