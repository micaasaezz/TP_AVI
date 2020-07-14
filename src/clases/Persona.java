package clases;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;

@Tabla(nombre = "personas")
public class Persona {
	
	@Id
	@Columna(nombre = "id")
	private Integer id;
	@Columna(nombre = "name")
	private String name;
	@Columna(nombre = "surname")
	private String surname;
	@Columna(nombre = "dni")
	private String dni;
	@Columna(nombre = "edad")
	private Integer edad;
	
	public Persona() { }
	public Persona(Integer id, String name, String surname, String dni, Integer edad) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.edad = edad;
	}
	public Persona(String name, String surname, String dni, Integer edad) {
		this.id = -1;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.edad = edad;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDni() {
		return this.dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Integer getEdad() {
		return this.edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Persona [id=" + id + ", name=" + name + ", surname=" + surname + ", dni=" + dni + ", edad=" + edad
				+ "]";
	}
		
}
