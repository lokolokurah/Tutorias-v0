package org.iesalandalus.programacion.tutorias.mvc.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Tutorias {

	private int capacidad, tamano;
	private Tutoria[] coleccionTutorias;
	
	public Tutorias(int capacidad) 
	{
		if (capacidad <= 0) 
		{
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionTutorias = new Tutoria[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	public Tutoria[] get()
	{
		return copiaProfundaTutorias();
	}
	
	//Clonación por copia profunda.
	private Tutoria[] copiaProfundaTutorias() 
	{
		//Cambiado capacidad x tamaño ya que si no pueden copiarse valores nulos :)
		Tutoria[] copiaProfundaTutorias = new Tutoria[tamano];
		for (int i = 0; !tamanoSuperado(i); i++) 
		{
			copiaProfundaTutorias[i] = new Tutoria(coleccionTutorias[i]);
		}
		return copiaProfundaTutorias;
	}
	
	public Tutoria[] get(Profesor profesor)
	{
		int indice = 0;
		Tutoria[] copiaProfesor = new Tutoria[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++)
		{
			if (coleccionTutorias[i].getProfesor().equals(profesor)) 
			{
				copiaProfesor[indice++] = new Tutoria(coleccionTutorias[i]);
			}
		}
		return copiaProfesor;
	}
	
	public int getTamano()
	{
		return tamano;
	}

	public int getCapacidad() 
	{
		return capacidad;
	}
	
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException 
	{
		if (tutoria == null) 
		{
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}
		if (capacidadSuperada(buscarIndice(tutoria))) 
		{
			throw new OperationNotSupportedException("ERROR: No se aceptan más tutorías.");
		}
		if (tamanoSuperado(buscarIndice(tutoria))) 
		{
			coleccionTutorias[buscarIndice(tutoria)] = new Tutoria(tutoria);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una tutoría con ese identificador.");
		}
	}
	
	private int buscarIndice(Tutoria tutoria) 
	{
		int indice = 0;
		boolean encontrado = false;
		while (!tamanoSuperado(indice) && !encontrado) 
		{
			if (coleccionTutorias[indice].equals(tutoria)) 
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
	
	public Tutoria buscar(Tutoria tutoria)
	{
		if (tutoria == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede buscar una tutoría nula.");
		}
		if (tamanoSuperado(buscarIndice(tutoria))) 
		{
			return null;
		} else {
			return new Tutoria(tutoria);
		}
	}
	
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException 
	{
		if (tutoria == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede borrar una tutoría nula.");
		}
		if (!tamanoSuperado(buscarIndice(tutoria))) 
		{
			desplazarUnaPosicionHaciaIzquierda(buscarIndice(tutoria));
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna tutoría con ese identificador.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) 
	{
		for (int i = indice; !tamanoSuperado(i); i++) 
		{
			coleccionTutorias[i] = coleccionTutorias[i + 1];
		}
		tamano--;
	}
}
