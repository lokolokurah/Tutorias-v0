package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.negocio.Citas;
import org.junit.BeforeClass;
import org.junit.Test;

public class CitasTest {

	private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
	private static final String ERROR_INSERTAR_CITA_NULA = "ERROR: No se puede insertar una cita nula.";
	private static final String ERROR_BORRAR_CITA_NULA = "ERROR: No se puede borrar una cita nula.";
	private static final String ERROR_BUSCAR_CITA_NULA = "ERROR: No se puede buscar una cita nula.";
	private static final String ERROR_NO_MAS_CITAS = "ERROR: No se aceptan más citas.";
	private static final String ERROR_SESION_NULA = "ERROR: La sesión no puede ser nula.";
	private static final String ERROR_ALUMNO_NULO = "ERROR: El alumno no puede ser nulo.";
	private static final String ERROR_CITA_EXISTE = "ERROR: Ya existe una cita con esa hora.";
	private static final String ERROR_CITA_BORRAR_NO_EXISTE = "ERROR: No existe ninguna cita con esa hora.";
	private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
	private static final String SESION_NULA = "Debería haber saltado una excepción indicando que no se puede operar con una sesión con hora no válida.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
	private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
	private static final String CITAS_NO_CREADAS = "Debería haber creado las citas correctamente.";
	private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
	private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
	private static final String CITA_NO_ESPERADA = "La cita devuelta no es la que debería ser.";
	private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";
	
	private static Cita cita1;
	private static Cita cita2;
	private static Cita cita3;
	private static Cita citaRepetida;
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		Sesion sesion1 = new Sesion(new Tutoria(Profesor.getProfesorFicticio("11223344B"), "Tutoria 1"), LocalDate.now().plusDays(7), LocalTime.of(16, 0), LocalTime.of(18, 0), 15);
		Sesion sesion2 = new Sesion(new Tutoria(Profesor.getProfesorFicticio("11223344B"), "Tutoria 1"), LocalDate.now().plusDays(8), LocalTime.of(16, 0), LocalTime.of(18, 0), 15);
		cita1 = new Cita(Alumno.getAlumnoFicticio("bob@gmail.com"), sesion1, LocalTime.of(16, 0));
		cita2 = new Cita(Alumno.getAlumnoFicticio("bob@gmail.com"), sesion2, LocalTime.of(16, 15));
		cita3 = new Cita(Alumno.getAlumnoFicticio("patricio@gmail.com"), sesion1, LocalTime.of(16, 30));
		citaRepetida = new Cita(cita1);
	}
	
	@Test
	public void constructorCapacidadValidaCreaCitasCorrectamente() {
		Citas citaes = new Citas(5);
		assertThat(CITAS_NO_CREADAS, citaes, not(nullValue()));
		assertThat(CITAS_NO_CREADAS, citaes.getCapacidad(), is(5));
		assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(0));
	}
	
	@Test
	public void constructorCapacidadNoValidaLanzaExcepcion() {
		Citas citaes = null;
		try {
			citaes = new Citas(0);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, citaes, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		try {
			citaes = new Citas(-1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, citaes, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarCitaValidaConCitasVaciasInsertaCitaCorrectamente() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			Cita[] copiaCitas = citas.get();
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, copiaCitas[0], is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaCitas[0], not(sameInstance(cita1)));
			assertThat(OBJETO_DEBERIA_SER_NULO, copiaCitas[1], is(nullValue()));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarDosCitasValidasInsertaCitasCorrectamente() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			Cita[] copiaCitas = citas.get();
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, copiaCitas[0], is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaCitas[0], not(sameInstance(cita1)));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita2), not(sameInstance(cita2)));
			assertThat(OPERACION_NO_REALIZADA, copiaCitas[1], is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaCitas[1], not(sameInstance(cita2)));
			assertThat(OBJETO_DEBERIA_SER_NULO, copiaCitas[2], is(nullValue()));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarTresCitasValidasInsertaCitasCorrectamente() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			Cita[] copiaCitas = citas.get();
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, copiaCitas[0], is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaCitas[0], not(sameInstance(cita1)));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita2), not(sameInstance(cita2)));
			assertThat(OPERACION_NO_REALIZADA, copiaCitas[1], is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaCitas[1], not(sameInstance(cita2)));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita3), is(cita3));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita3), not(sameInstance(cita3)));
			assertThat(OPERACION_NO_REALIZADA, copiaCitas[2], is(cita3));
			assertThat(REFERENCIA_NO_ESPERADA, copiaCitas[2], not(sameInstance(cita3)));
			assertThat(OBJETO_DEBERIA_SER_NULO, copiaCitas[3], is(nullValue()));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarCitaNulaLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(null);
			fail(SESION_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_INSERTAR_CITA_NULA));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(0));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarCitaRepetidaLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			citas.insertar(citaRepetida);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita2);
			citas.insertar(cita1);
			citas.insertar(cita3);
			citas.insertar(citaRepetida);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita2);
			citas.insertar(cita3);
			citas.insertar(cita1);
			citas.insertar(citaRepetida);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarCitaValidaConCitasLlenasLanzaExcepcion() {
		Citas citas = new Citas(2);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_NO_MAS_CITAS));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, citas.get()[0], is(cita1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita2), not(sameInstance(cita2)));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getSesionValidaDevuelveCitasSesion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			Cita[] citasSesion = citas.get(new Sesion(new Tutoria(Profesor.getProfesorFicticio("11223344B"), "Tutoria 1"), LocalDate.now().plusDays(7), LocalTime.of(16, 0), LocalTime.of(18, 0), 30));
			assertThat(OPERACION_NO_REALIZADA, citasSesion[0], is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citasSesion[0], not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, citasSesion[1], is(cita3));
			assertThat(REFERENCIA_NO_ESPERADA, citasSesion[1], not(sameInstance(cita3)));
			assertThat(OBJETO_DEBERIA_SER_NULO, citasSesion[2], is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getSesionNulaLanzaExcepcion() {
		Citas citas = new Citas(5);
		Cita[] citasSesion = null;
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			Sesion sesion = null;
			citasSesion = citas.get(sesion);
			fail(SESION_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_SESION_NULA));
			assertThat(OBJETO_DEBERIA_SER_NULO, citasSesion, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getAlumnoValidoDevuelveCitasAlumno() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			Cita[] citasAlumno = citas.get(Alumno.getAlumnoFicticio("bob@gmail.com"));
			assertThat(OPERACION_NO_REALIZADA, citasAlumno[0], is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citasAlumno[0], not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, citasAlumno[1], is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, citasAlumno[1], not(sameInstance(cita2)));
			assertThat(OBJETO_DEBERIA_SER_NULO, citasAlumno[2], is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getAlumnoNuloLanzaExcepcion() {
		Citas citas = new Citas(5);
		Cita[] citasAlumno = null;
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			Alumno alumno = null;
			citasAlumno = citas.get(alumno);
			fail(SESION_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_ALUMNO_NULO));
			assertThat(OBJETO_DEBERIA_SER_NULO, citasAlumno, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarCitaExistenteBorraCitaCorrectamente() throws OperationNotSupportedException {
		Citas citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.borrar(cita1);
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(0));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.insertar(cita2);
			citaes.borrar(cita1);
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(1));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita2), is(cita2));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.insertar(cita2);
			citaes.borrar(cita2);
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(1));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita1), is(cita1));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita2), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.insertar(cita2);
			citaes.insertar(cita3);
			citaes.borrar(cita1);
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita1), is(nullValue()));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita2), is(cita2));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita3), is(cita3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.insertar(cita2);
			citaes.insertar(cita3);
			citaes.borrar(cita2);
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita2), is(nullValue()));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita1), is(cita1));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita3), is(cita3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.insertar(cita2);
			citaes.insertar(cita3);
			citaes.borrar(cita3);
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita3), is(nullValue()));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita1), is(cita1));
			assertThat(CITA_NO_ESPERADA, citaes.buscar(cita2), is(cita2));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarCitaNoExistenteLanzaExcepcion() {
		Citas citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.borrar(cita2);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.insertar(cita2);
			citaes.borrar(cita3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(2));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarCitaNulaLanzaExcepcion() {
		Citas citaes = new Citas(5);
		try {
			citaes.insertar(cita1);
			citaes.borrar(null);
			fail(SESION_NULA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BORRAR_CITA_NULA));
			assertThat(TAMANO_NO_ESPERADO, citaes.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void buscarCitaNulaLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.buscar(null);
			fail(SESION_NULA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BUSCAR_CITA_NULA));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}

}
