package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((profesor == null) ? 0 : profesor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tutoria other = (Tutoria) obj;
		if (nombre == null) 
		{
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (profesor == null) 
		{
			if (other.profesor != null)
				return false;
		} else if (!profesor.equals(other.profesor))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return String.format("profesor=%s, nombre=%s", profesor.toString(), getNombre());
	}
	
}
