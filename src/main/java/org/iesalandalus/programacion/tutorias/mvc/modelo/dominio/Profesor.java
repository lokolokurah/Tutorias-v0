package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profesor {
	
	private static final String ER_NOMBRE = "([a-zA-ZÁÉÍÓÚáéíóú]+)(\\s+([a-zA-ZÁÉÍÓÚáéíóú]+))+";
	private static final String ER_DNI = "([0-9]{8})([A-Za-z])";
	private static final String ER_CORREO = "[(\\w\\.)+|(\\w)+]+@\\w+\\.\\w{2,3}";
	private String nombre, dni, correo;

	public Profesor(String nombre,String dni, String correo) 
	{
		super();
		setNombre(nombre);
		setDni(dni);
		setCorreo(correo);
	}

	public Profesor(Profesor profesorCopia) 
	{
		if (profesorCopia == null) 
		{
			throw new NullPointerException("ERROR: No es posible copiar un profesor nulo.");
		}
		setNombre(profesorCopia.nombre);
		setDni(profesorCopia.dni);
		setCorreo(profesorCopia.correo);
	}
	

	public String getNombre() 
	{
		return nombre;
	}

	private void setNombre(String nombre) 
	{
		if (nombre == null) 
		{
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (!nombre.matches(ER_NOMBRE)) 
		{
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}
	
	private String formateaNombre(String nombre) 
	{
		nombre = nombre.replaceAll("\\s+", " ");
		nombre = nombre.trim();
		String[] palabras = nombre.split(" ");
		StringBuilder copiaNombre = new StringBuilder();
		for (int i = 0; i <= palabras.length - 1; i++) 
		{
			palabras[i] = palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase();
			copiaNombre.append(palabras[i] + " ");
		}
		nombre = copiaNombre.toString();
		return nombre.trim();
	}

	public String getDni() 
	{
		return dni;
	}

	public void setDni(String dni) 
	{
		if (dni == null) 
		{
			throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
		}
		if (!dni.matches(ER_DNI)) 
		{
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		}
		if (comprobarLetraDni(dni) == false) 
		{
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
		}
		this.dni = dni;
	}
	
	private boolean comprobarLetraDni(String dni)
	{
		boolean letraCorrecta = false;
		Pattern patronDni = Pattern.compile(ER_DNI);
		Matcher comparadorDni = patronDni.matcher(dni);
		comparadorDni.matches();
		int numerosDni = Integer.parseInt(comparadorDni.group(1));
		int resto = numerosDni % 23;
		String[] letraEsperada = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q",
				"V", "H", "L", "C", "K", "E" };
		if (comparadorDni.group(2).equals(letraEsperada[resto])) 
		{
			letraCorrecta = true;
		}
		return letraCorrecta;
	}
	
	public String getCorreo()
	{
		return correo;
	}

	private void setCorreo(String correo)
	{
		if (correo == null)
		{
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) 
		{
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}
	
	public static Profesor getProfesorFicticio(String dni) 
	{
		return new Profesor("Jaime El Poderoso", dni, "jaimeelpoderoso@ficticio.com");
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof Profesor))
		{
			return false;
		}
		Profesor other = (Profesor) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() 
	{
		return String.format("nombre=%s (%s), DNI=%s, correo=%s", getNombre(), getIniciales(), getDni(), getCorreo());
	}
	
	private String getIniciales() 
	{
		String iniciales = "";
		String[] palabras = getNombre().split(" ");
		for (int i = 0; i <= palabras.length - 1; i++) 
		{
			iniciales = iniciales + palabras[i].charAt(0);
		}
		return iniciales.toUpperCase();
	}

}
