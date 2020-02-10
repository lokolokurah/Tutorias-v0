package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.util.Objects;

public class Tutoria {

	private String nombre;
	private Profesor profesor;	

	public Tutoria(Profesor profesor, String nombre) 
	{
		super();
		setProfesor(profesor);
		setNombre(nombre);
	}

	public Tutoria(Tutoria tutoriaCopia) 
	{
		if (tutoriaCopia == null) 
		{
			throw new NullPointerException("ERROR: No es posible copiar una tutoría nula.");
		}
		setProfesor(tutoriaCopia.profesor);
		setNombre(tutoriaCopia.nombre);
	}
	
	public Profesor getProfesor() 
	{
		return profesor;
	}

	private void setProfesor(Profesor profesor) 
	{
		if (profesor == null) 
		{
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		profesor = new Profesor(profesor);
		this.profesor = profesor;	
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
		if (nombre.trim().isEmpty())
		{
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = nombre.trim();
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(nombre, profesor);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) 
		{
			return true;
		}
		if (!(obj instanceof Tutoria))
		{
			return false;
		}
		Tutoria other = (Tutoria) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(profesor, other.profesor);
	}

	@Override
	public String toString() 
	{
		return String.format("profesor=%s, nombre=%s", profesor.toString(), getNombre());
	}
	
}
