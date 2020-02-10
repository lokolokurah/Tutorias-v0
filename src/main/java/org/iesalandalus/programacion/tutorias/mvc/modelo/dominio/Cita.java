package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cita {

	public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private LocalTime hora;
	private Sesion sesion;
	private Alumno alumno;
	
	public Cita(Alumno alumno, Sesion sesion, LocalTime hora) 
	{
		super();
		setAlumno(alumno);
		setSesion(sesion);
		setHora(hora);
	}
	
	public Cita(Cita citaCopia)
	{
		if (citaCopia==null) 
		{
			throw new NullPointerException("ERROR: No es posible copiar una cita nula.");
		}
		setAlumno(citaCopia.alumno);
		setSesion(citaCopia.sesion);
		setHora(citaCopia.hora);
	}

	public Alumno getAlumno() 
	{
		return alumno;
	}

	private void setAlumno(Alumno alumno)
	{
		if (alumno == null) 
		{
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		this.alumno = new Alumno(alumno);
	}
	
	public Sesion getSesion() 
	{
		return sesion;
	}

	private void setSesion(Sesion sesion) 
	{
		if (sesion == null) 
		{
			throw new NullPointerException("ERROR: La sesión no puede ser nula.");
		}
		this.sesion = new Sesion(sesion);
	}

	public LocalTime getHora() 
	{
		return hora;
	}

	private void setHora(LocalTime hora)
	{
		if (hora == null)
		{
			throw new NullPointerException("ERROR: La hora no puede ser nula.");
		}
		if (hora.compareTo(sesion.getHoraInicio())<0 || hora.compareTo(sesion.getHoraFin())>0)
		{
			throw new IllegalArgumentException("ERROR: La hora debe estar comprendida entre la hora de inicio y fin de la sesión.");
		}
		if (hora.getMinute()%sesion.getMinutosDuracion()!=0) 
		{
			throw new IllegalArgumentException("ERROR: La hora debe comenzar en un múltiplo de los minutos de duración.");
		}
		this.hora = hora;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(alumno, hora, sesion);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) 
		{
			return true;
		}
		if (!(obj instanceof Cita)) 
		{
			return false;
		}
		Cita other = (Cita) obj;
		return Objects.equals(alumno, other.alumno) && Objects.equals(hora, other.hora)
				&& Objects.equals(sesion, other.sesion);
	}

	@Override
	public String toString() 
	{
		return  String.format("alumno=%s, sesion=%s, hora=%s", alumno.toString(), sesion.toString(), getHora());
	}
	
}
