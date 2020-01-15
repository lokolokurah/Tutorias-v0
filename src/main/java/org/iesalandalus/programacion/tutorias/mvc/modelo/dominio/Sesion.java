package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Sesion {

	private static final LocalTime HORA_COMIENZO_CLASES = LocalTime.of(16, 00);
	private static final LocalTime HORA_FIN_CLASES = LocalTime.of(22, 15);
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private LocalDate fecha;
	private LocalTime horaInicio, horaFin;
	private int minutosDuracion;
	private Tutoria tutoria;
	
	public Sesion(Tutoria tutoria, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int minutosDuracion) 
	{
		super();
		setTutoria(tutoria);
		setFecha(fecha);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setMinutosDuracion(minutosDuracion);
		comprobarValidezSesion();
	}
	
	public Sesion(Sesion sesionCopia) 
	{
		if (sesionCopia==null) 
		{
			throw new NullPointerException("ERROR: No es posible copiar una sesión nula.");
		}
		setTutoria(sesionCopia.tutoria);
		setFecha(sesionCopia.fecha);
		setHoraInicio(sesionCopia.horaInicio);
		setHoraFin(sesionCopia.horaFin);
		setMinutosDuracion(sesionCopia.minutosDuracion);
	}

	public Tutoria getTutoria() 
	{
		return tutoria;
	}

	public void setTutoria(Tutoria tutoria) 
	{
		if (tutoria == null)
		{
			throw new NullPointerException("ERROR: La tutoría no puede ser nula.");
		}
		this.tutoria = new Tutoria(tutoria);
	}
	
	public LocalDate getFecha() 
	{
		return fecha;
	}

	public void setFecha(LocalDate fecha) 
	{
		if (fecha == null)
		{
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		if (fecha.compareTo(LocalDate.now()) <= 0)
		{
			throw new IllegalArgumentException("ERROR: Las sesiones de deben planificar para fechas futuras.");
		}
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() 
	{
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) 
	{
		if (horaInicio == null) 
		{
			throw new NullPointerException("ERROR: La hora de inicio no puede ser nula.");
		}
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() 
	{
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) 
	{
		if (horaFin == null) 
		{
			throw new NullPointerException("ERROR: La hora de fin no puede ser nula.");
		}
		this.horaFin = horaFin;
	}

	public int getMinutosDuracion() 
	{
		return minutosDuracion;
	}

	public void setMinutosDuracion(int minutosDuracion) 
	{
		if (minutosDuracion == 0) 
		{
			throw new IllegalArgumentException("ERROR: Los minutos de duración no son válidos.");
		}
		this.minutosDuracion = minutosDuracion;
	}
	
	private void comprobarValidezSesion()
	{
		if (horaInicio.isAfter(HORA_FIN_CLASES) || horaInicio.isBefore(HORA_COMIENZO_CLASES) || horaInicio.equals(HORA_FIN_CLASES)) 
		{
			throw new IllegalArgumentException("ERROR: La hora de inicio no es válida.");
		}
		if (horaFin.isBefore(HORA_COMIENZO_CLASES) || horaFin.isAfter(HORA_FIN_CLASES) || horaFin.equals(HORA_COMIENZO_CLASES))
		{
			throw new IllegalArgumentException("ERROR: La hora de fin no es válida.");
		}

		if (horaFin.equals(horaInicio) || horaInicio.isAfter(horaFin)) 
		{
			throw new IllegalArgumentException("ERROR: Las hora para establecer la sesión no son válidas.");
		}
		//Using java.time.temporal.ChronoUnit to Find the Difference in Minutes
		int duracionTotal = (int) ChronoUnit.MINUTES.between(horaFin, horaInicio);
		if (duracionTotal % minutosDuracion != 0) 
		{
			throw new IllegalArgumentException(
					"ERROR: Los minutos de duración no es divisor de los minutos establecidos para toda la sesión.");
		}
	}

	public static Sesion getSesionFicticia(Tutoria tutoria, LocalDate fecha) 
	{
		return new Sesion(tutoria, fecha, LocalTime.of(19, 00), LocalTime.of(22, 00), 30);
	}

	
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((horaFin == null) ? 0 : horaFin.hashCode());
		result = prime * result + ((horaInicio == null) ? 0 : horaInicio.hashCode());
		result = prime * result + minutosDuracion;
		result = prime * result + ((tutoria == null) ? 0 : tutoria.hashCode());
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
		Sesion other = (Sesion) obj;
		if (fecha == null) 
		{
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (horaFin == null) 
		{
			if (other.horaFin != null)
				return false;
		} else if (!horaFin.equals(other.horaFin))
			return false;
		if (horaInicio == null) 
		{
			if (other.horaInicio != null)
				return false;
		} else if (!horaInicio.equals(other.horaInicio))
			return false;
		if (minutosDuracion != other.minutosDuracion)
			return false;
		if (tutoria == null) 
		{
			if (other.tutoria != null)
				return false;
		} else if (!tutoria.equals(other.tutoria))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return String.format("tutoria=%s, fecha=%s, horaInicio=%s, horaFin=%s, minutosDuracion=%s",getTutoria(), getFecha().format(FORMATO_FECHA), getHoraInicio(), getHoraFin(), getMinutosDuracion());
	}

}
