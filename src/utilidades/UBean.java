package utilidades;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UBean {
	
	// Devuelve un ArrayList<Field> con todos los atributos que posee el parámetro Object.
	public static List<Field> obtenerAtributos(Object o){
		List<Field> atributos = new ArrayList<>();
		Class c = o.getClass();
		for (Field f : c.getDeclaredFields()) {
			atributos.add(f);
		}
		return atributos;		
	}
	
	// Se debe ejecutar el método Setter del String dentro del Object.
	// ej string="nombre" tengo que hacer o.getNombre o o.setNombre(valor)
	public static void ejecutarSet(Object o, String att, Object valor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class c = o.getClass();
		String methodName = "set".concat(att.substring(0,1).toUpperCase() + att.substring(1));
		for (Method m : c.getDeclaredMethods()) {
			if (m.getName().equals(methodName)) {
				m.invoke(o, valor);	
			}
		}
	}
	
	// Devolverá el valor del atributo pasado por parámetro, ejecutando el getter dentro del objeto.
	public static Object ejecutarGet(Object o, String att) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class c = o.getClass();
		Object ret = null;
		String methodName = "get".concat(att.substring(0,1).toUpperCase() + att.substring(1));
		for (Method m : c.getDeclaredMethods()) {
			if (m.getName().equals(methodName)) {
				ret = m.invoke(o, new Object[0]);
			}
		}
		return ret;		
	}
}
