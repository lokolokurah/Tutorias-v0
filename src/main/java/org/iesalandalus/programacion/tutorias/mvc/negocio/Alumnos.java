package org.iesalandalus.programacion.tutorias.mvc.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;

public class Alumnos {

	private int capacidad, tamano;
	private Alumno[] coleccionAlumnos;
	
	public Alumnos(int capacidad) 
	{
		if (capacidad <= 0) 
		{
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionAlumnos = new Alumno[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	public Alumno[] get()
	{
		return coleccionAlumnos;
	}
	
	public int getTamano()
	{
		return tamano;
	}

	public int getCapacidad() 
	{
		return capacidad;
	}
	
	public void insertar(Alumno alumno) throws OperationNotSupportedException 
	{
		if (alumno == null) 
		{
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}
		if (capacidadSuperada(buscarIndice(alumno))) 
		{
			throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
		}
		if (tamanoSuperado(buscarIndice(alumno))) 
		{
			coleccionAlumnos[buscarIndice(alumno)] = new Alumno(alumno);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese expediente.");
		}
	}
	
	private int buscarIndice(Alumno alumno) 
	{
		int indice = 0;
		boolean encontrado = false;
		while (!tamanoSuperado(indice) && !encontrado) 
		{
			if (coleccionAlumnos[indice].equals(alumno)) 
			{
				encontrado = true;
			} else {
				indice++;
			}
		}
		return indice;
	}
	
	private boolean tamanoSuperado(int indice)
	{
		return indice >= tamano;
	}
	
	private boolean capacidadSuperada(int indice)
	{
		return indice >= capacidad;
	}
	
	public Alumno buscar(Alumno alumno)
	{
		if (alumno == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		if (tamanoSuperado(buscarIndice(alumno))) 
		{
			return null;
		} else {
			return new Alumno(alumno);
		}
	}
	
	public void borrar(Alumno alumno) throws OperationNotSupportedException 
	{
		if (alumno == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		if (!tamanoSuperado(buscarIndice(alumno))) 
		{
			desplazarUnaPosicionHaciaIzquierda(buscarIndice(alumno));
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese expediente.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) 
	{
		for (int i = indice; !tamanoSuperado(i); i++) 
		{
			coleccionAlumnos[i] = coleccionAlumnos[i + 1];
		}
		tamano--;
	}
}
