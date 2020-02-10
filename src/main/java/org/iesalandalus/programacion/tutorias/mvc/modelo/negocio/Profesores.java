package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

public class Profesores {

	private int capacidad, tamano;
	private Profesor[] coleccionProfesores;
	
	public Profesores(int capacidad) 
	{
		if (capacidad <= 0) 
		{
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionProfesores = new Profesor[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	public Profesor[] get()
	{
		return copiaProfundaProfesores();
	}
	
	//Clonación por copia profunda.
	private Profesor[] copiaProfundaProfesores() 
	{
		Profesor[] copiaProfundaProfesores = new Profesor[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) 
		{
			copiaProfundaProfesores[i] = new Profesor(coleccionProfesores[i]);
		}
		return copiaProfundaProfesores;
	}
	
	public int getTamano()
	{
		return tamano;
	}

	public int getCapacidad() 
	{
		return capacidad;
	}
	
	public void insertar(Profesor profesor) throws OperationNotSupportedException 
	{
		if (profesor == null) 
		{
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		if (capacidadSuperada(buscarIndice(profesor))) 
		{
			throw new OperationNotSupportedException("ERROR: No se aceptan más profesores.");
		}
		if (tamanoSuperado(buscarIndice(profesor))) 
		{
			coleccionProfesores[buscarIndice(profesor)] = new Profesor(profesor);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		}
	}
	
	private int buscarIndice(Profesor profesor) 
	{
		int indice = 0;
		boolean encontrado = false;
		while (!tamanoSuperado(indice) && !encontrado) 
		{
			if (coleccionProfesores[indice].equals(profesor)) 
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
	
	public Profesor buscar(Profesor profesor)
	{
		if (profesor == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}
		if (tamanoSuperado(buscarIndice(profesor))) 
		{
			return null;
		} else {
			return new Profesor(profesor);
		}
	}
	
	public void borrar(Profesor profesor) throws OperationNotSupportedException 
	{
		if (profesor == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		if (!tamanoSuperado(buscarIndice(profesor))) 
		{
			desplazarUnaPosicionHaciaIzquierda(buscarIndice(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) 
	{
		for (int i = indice; !tamanoSuperado(i); i++) 
		{
			coleccionProfesores[i] = coleccionProfesores[i + 1];
		}
		tamano--;
	}
}
