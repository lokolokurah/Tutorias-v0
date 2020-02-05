package org.iesalandalus.programacion.tutorias.mvc.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;


public class Citas {

	private int capacidad, tamano;
	private Cita[] coleccionCitas;
	
	public Citas(int capacidad) 
	{
		if (capacidad <= 0) 
		{
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionCitas = new Cita[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	public Cita[] get()
	{
		return copiaProfundaCitas();
	}
	
	//Clonación por copia profunda.
	private Cita[] copiaProfundaCitas() 
	{
		Cita[] copiaProfundaCitas = new Cita[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) 
		{
			copiaProfundaCitas[i] = new Cita(coleccionCitas[i]);
		}
		return copiaProfundaCitas;
	}
	
	public Cita[] get(Sesion sesion)
	{
		//get sesion nula lanza excepción (test)
		if (sesion==null) 
		{
			throw new NullPointerException("ERROR: La sesión no puede ser nula.");
		}
		int indice = 0;
		Cita[] copiaSesion = new Cita[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++)
		{
			if (coleccionCitas[i].getSesion().equals(sesion)) 
			{
				/* Para evitar indices vacios o nulos creamos una variable indice
				* que solo se incremente al copiar una sesión con la condición del if. */
				copiaSesion[indice++] = new Cita(coleccionCitas[i]);
			}
		}
		return copiaSesion;
	}
	
	public Cita[] get(Alumno alumno)
	{
		//get alumno nulo lanza excepción (test)
		if (alumno==null)
		{
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		int indice = 0;
		Cita[] copiaAlumno = new Cita[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++)
		{
			if (coleccionCitas[i].getAlumno().equals(alumno)) 
			{
				copiaAlumno[indice++] = new Cita(coleccionCitas[i]);
			}
		}
		return copiaAlumno;
	}
	
	public int getTamano()
	{
		return tamano;
	}

	public int getCapacidad() 
	{
		return capacidad;
	}
	
	public void insertar(Cita cita) throws OperationNotSupportedException 
	{
		if (cita == null) 
		{
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		if (capacidadSuperada(buscarIndice(cita))) 
		{
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		}
		if (tamanoSuperado(buscarIndice(cita))) 
		{
			coleccionCitas[buscarIndice(cita)] = new Cita(cita);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una cita con esa hora.");
		}
	}
	
	private int buscarIndice(Cita cita) 
	{
		int indice = 0;
		boolean encontrado = false;
		while (!tamanoSuperado(indice) && !encontrado) 
		{
			if (coleccionCitas[indice].equals(cita)) 
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
	
	public Cita buscar(Cita cita)
	{
		if (cita == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede buscar una cita nula.");
		}
		if (tamanoSuperado(buscarIndice(cita))) 
		{
			return null;
		} else {
			return new Cita(cita);
		}
	}
	
	public void borrar(Cita cita) throws OperationNotSupportedException 
	{
		if (cita == null) 
		{
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		if (!tamanoSuperado(buscarIndice(cita))) 
		{
			desplazarUnaPosicionHaciaIzquierda(buscarIndice(cita));
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita con esa hora.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) 
	{
		for (int i = indice; !tamanoSuperado(i); i++) 
		{
			coleccionCitas[i] = coleccionCitas[i + 1];
		}
		tamano--;
	}

}
