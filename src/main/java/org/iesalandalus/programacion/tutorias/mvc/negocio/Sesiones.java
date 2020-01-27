package org.iesalandalus.programacion.tutorias.mvc.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Sesiones {

	private int capacidad, tamano;
	private Sesion[] coleccionSesiones;
	
	public Sesiones(int capacidad) 
	{
		if (capacidad <= 0) 
		{
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionSesiones = new Sesion[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	public Sesion[] get()
	{
		return coleccionSesiones;
	}
	
	//Clonación por copia profunda.
	private Sesion[] copiaProfundaSesiones() 
	{
		Sesion[] copiaProfundaSesiones = new Sesion[tamano];
		for (int i = 0; i < tamano; i++) 
		{
			copiaProfundaSesiones[i] = new Sesion(coleccionSesiones[i]);
		}
		return copiaProfundaSesiones;
	}
	
	public Sesion[] get(Tutoria tutoria)
	{
		Sesion[] copiaTutoria = new Sesion[tamano];
		for (int i = 0; i < tamano; i++)
		{
			if (coleccionSesiones[i].getTutoria().equals(tutoria)) 
			{
				copiaTutoria[i] = new Sesion(coleccionSesiones[i]);
			}
		}
		return copiaTutoria;
	}
	
	public int getTamano()
	{
		return tamano;
	}

	public int getCapacidad() 
	{
		return capacidad;
	}
	
	public void insertar(Sesion sesion) throws OperationNotSupportedException 
	{
		if (sesion == null) 
		{
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}
		if (capacidadSuperada(buscarIndice(sesion))) 
		{
			throw new OperationNotSupportedException("ERROR: No se aceptan más sesiones.");
		}
		if (tamanoSuperado(buscarIndice(sesion))) 
		{
			coleccionSesiones[buscarIndice(sesion)] = new Sesion(sesion);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una sesión con esa fecha.");
		}
	}
	
	private int buscarIndice(Sesion sesion) 
	{
		int indice = 0;
		boolean encontrado = false;
		while (!tamanoSuperado(indice) && !encontrado) 
		{
			if (coleccionSesiones[indice].equals(sesion)) 
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
	
	public Sesion buscar(Sesion sesion)
	{
		if (sesion == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede buscar una sesión nula.");
		}
		if (tamanoSuperado(buscarIndice(sesion))) 
		{
			return null;
		} else {
			return new Sesion(sesion);
		}
	}
	
	public void borrar(Sesion sesion) throws OperationNotSupportedException 
	{
		if (sesion == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede borrar una sesión nula.");
		}
		if (!tamanoSuperado(buscarIndice(sesion))) 
		{
			desplazarUnaPosicionHaciaIzquierda(buscarIndice(sesion));
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna sesión con esa fecha.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) 
	{
		for (int i = indice; !tamanoSuperado(i); i++) 
		{
			coleccionSesiones[i] = coleccionSesiones[i + 1];
		}
		tamano--;
	}

}
