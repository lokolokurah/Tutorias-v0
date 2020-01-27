package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.negocio.Tutorias;
import org.junit.BeforeClass;
import org.junit.Test;

public class TutoriasTest {

	private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
	private static final String ERROR_INSERTAR_TUTORIA_NULA = "ERROR: No se puede insertar una tutoría nula.";
	private static final String ERROR_BORRAR_TUTORIA_NULA = "ERROR: No se puede borrar una tutoría nula.";
	private static final String ERROR_BUSCAR_TUTORIA_NULA = "ERROR: No se puede buscar una tutoría nula.";
	private static final String ERROR_NO_MAS_TUTORIAS = "ERROR: No se aceptan más tutorías.";
	private static final String ERROR_TUTORIA_EXISTE = "ERROR: Ya existe una tutoría con ese identificador.";
	private static final String ERROR_TUTORIA_BORRAR_NO_EXISTE = "ERROR: No existe ninguna tutoría con ese identificador.";
	private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
	private static final String TUTORIA_NULA = "Debería haber saltado una excepción indicando que no se puede operar con una tutoría con identificador no válido.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
	private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
	private static final String TUTORIAS_NO_CREADAS = "Debería haber creado las tutorías correctamente.";
	private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
	private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
	private static final String TUTORIA_NO_ESPERADA = "La tutoría devuelta no es la que debería ser.";
	private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";
	
	private static Tutoria tutoria1;
	private static Tutoria tutoria2;
	private static Tutoria tutoria3;
	private static Tutoria tutoriaRepetida;
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		tutoria1 = new Tutoria(Profesor.getProfesorFicticio("11223344B"), "Resolución de dudas Unidad 5");
		tutoria2 = new Tutoria(Profesor.getProfesorFicticio("11223344B"), "Revisión tarea presencial primer trimestre");
		tutoria3 = new Tutoria(Profesor.getProfesorFicticio("22334455Y"), "Resolución de dudas Unidad 6");
		tutoriaRepetida = new Tutoria(tutoria1);
	}
	
	@Test
	public void constructorCapacidadValidaCreaTutoriasCorrectamente() {
		Tutorias tutorias = new Tutorias(5);
		assertThat(TUTORIAS_NO_CREADAS, tutorias, not(nullValue()));
		assertThat(TUTORIAS_NO_CREADAS, tutorias.getCapacidad(), is(5));
		assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(0));
	}
	
	@Test
	public void constructorCapacidadNoValidaLanzaExcepcion() {
		Tutorias tutorias = null;
		try {
			tutorias = new Tutorias(0);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, tutorias, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		try {
			tutorias = new Tutorias(-1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, tutorias, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarTutoriaValidaConTutoriasVaciasInsertaTutoriaCorrectamente() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			Tutoria[] copiaTutorias = tutorias.get();
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(1));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria1), not(sameInstance(tutoria1)));
			assertThat(OPERACION_NO_REALIZADA, copiaTutorias[0], is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaTutorias[0], not(sameInstance(tutoria1)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarDosTutoriasValidasInsertaTutoriasCorrectamente() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			Tutoria[] copiaTutorias = tutorias.get();
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(2));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria1), not(sameInstance(tutoria1)));
			assertThat(OPERACION_NO_REALIZADA, copiaTutorias[0], is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaTutorias[0], not(sameInstance(tutoria1)));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(tutoria2));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria2), not(sameInstance(tutoria2)));
			assertThat(OPERACION_NO_REALIZADA, copiaTutorias[1], is(tutoria2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaTutorias[1], not(sameInstance(tutoria2)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarTresTutoriasValidasInsertaTutoriasCorrectamente() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			Tutoria[] copiaTutorias = tutorias.get();
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(3));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria1), not(sameInstance(tutoria1)));
			assertThat(OPERACION_NO_REALIZADA, copiaTutorias[0], is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaTutorias[0], not(sameInstance(tutoria1)));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(tutoria2));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria2), not(sameInstance(tutoria2)));
			assertThat(OPERACION_NO_REALIZADA, copiaTutorias[1], is(tutoria2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaTutorias[1], not(sameInstance(tutoria2)));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria3), is(tutoria3));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria3), not(sameInstance(tutoria3)));
			assertThat(OPERACION_NO_REALIZADA, copiaTutorias[2], is(tutoria3));
			assertThat(REFERENCIA_NO_ESPERADA, copiaTutorias[2], not(sameInstance(tutoria3)));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarTutoriaNulaLanzaExcepcion() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(null);
			fail(TUTORIA_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_INSERTAR_TUTORIA_NULA));
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(0));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarTutoriaRepetidaLanzaExcepcion() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			tutorias.insertar(tutoriaRepetida);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_TUTORIA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria3);
			tutorias.insertar(tutoriaRepetida);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_TUTORIA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoriaRepetida);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_TUTORIA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarTutoriaValidaConTutoriasLlenasLanzaExcepcion() {
		Tutorias tutorias = new Tutorias(2);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_NO_MAS_TUTORIAS));
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(2));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria1), not(sameInstance(tutoria1)));
			assertThat(OPERACION_NO_REALIZADA, tutorias.get()[0], is(tutoria1));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(tutoria2));
			assertThat(REFERENCIA_NO_ESPERADA, tutorias.buscar(tutoria2), not(sameInstance(tutoria2)));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getProfesorValidoDevuelveTutoriasProfesor() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			Tutoria[] tutoriasProfesor = tutorias.get(Profesor.getProfesorFicticio("11223344B"));
			assertThat(OPERACION_NO_REALIZADA, tutoriasProfesor[0], is(tutoria1));
			assertThat(REFERENCIA_NO_ESPERADA, tutoriasProfesor[0], not(sameInstance(tutoria1)));
			assertThat(OPERACION_NO_REALIZADA, tutoriasProfesor[1], is(tutoria2));
			assertThat(REFERENCIA_NO_ESPERADA, tutoriasProfesor[1], not(sameInstance(tutoria2)));
			assertThat(OBJETO_DEBERIA_SER_NULO, tutoriasProfesor[2], is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarTutoriaExistenteBorraTutoriaCorrectamente() throws OperationNotSupportedException {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.borrar(tutoria1);
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(0));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.borrar(tutoria1);
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(1));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(tutoria2));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.borrar(tutoria2);
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(1));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(tutoria1));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			tutorias.borrar(tutoria1);
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(2));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(nullValue()));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(tutoria2));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria3), is(tutoria3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			tutorias.borrar(tutoria2);
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(2));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(nullValue()));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(tutoria1));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria3), is(tutoria3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.insertar(tutoria2);
			tutorias.insertar(tutoria3);
			tutorias.borrar(tutoria3);
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(2));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria3), is(nullValue()));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria1), is(tutoria1));
			assertThat(TUTORIA_NO_ESPERADA, tutorias.buscar(tutoria2), is(tutoria2));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarTutoriaNoExistenteLanzaExcepcion() {
		Tutorias citas = new Tutorias(5);
		try {
			citas.insertar(tutoria1);
			citas.borrar(tutoria2);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_TUTORIA_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Tutorias(5);
		try {
			citas.insertar(tutoria1);
			citas.insertar(tutoria2);
			citas.borrar(tutoria3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_TUTORIA_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarTutoriaNulaLanzaExcepcion() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.borrar(null);
			fail(TUTORIA_NULA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BORRAR_TUTORIA_NULA));
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void buscarTutoriaNulaLanzaExcepcion() {
		Tutorias tutorias = new Tutorias(5);
		try {
			tutorias.insertar(tutoria1);
			tutorias.buscar(null);
			fail(TUTORIA_NULA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BUSCAR_TUTORIA_NULA));
			assertThat(TAMANO_NO_ESPERADO, tutorias.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}

}
