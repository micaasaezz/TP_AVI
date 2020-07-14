import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import clases.Persona;
import servicios.Consultas;
import utilidades.UConexion;

public class Program {
	public static void main(String[] args) {
		try {
			Persona p = new Persona("Marcos", "Gueri", "9999999", new Integer(28));

			UConexion uc = UConexion.getInstance();
			Consultas.setConnection(uc.connectBD()); 
			
			List<Persona> lista = Consultas.getAll(Persona.class);
			
			// ADD
			/*System.out.println(lista.size());
			Consultas.guardar(p);
			lista = Consultas.getAll(Persona.class);
			System.out.println(lista.size());*/
			
			// MODIFICAR
			/*int index = 4;
			System.out.println(lista.get(index));
			p = lista.get(index);
			p.setName("cambio");
			p.setSurname("cambio");
			Consultas.modificar(p);
			System.out.println(lista.get(index));*/
			
			// DELETE
			/*System.out.println(lista.size());
			p = lista.get(lista.size()-1);
			Consultas.eliminar(p);
			lista = Consultas.getAll(Persona.class);
			System.out.println(lista.size());*/
			
			// GET BY ID
			/*p = lista.get(1);
			Object obj = Consultas.obtenerPorId(p.getClass(), p.getId());
			System.out.println(obj.toString());*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
