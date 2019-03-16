package model.data_structures;

import junit.framework.TestCase;
import model.data_structures.*;

public class THSepChainTest extends TestCase {
	/*
	 * Atributos 
	 */
	private ITablaHash<String, Integer> tabla;
	private final int numeroEscenarios = 100;
	private final int tamanoMax = 100;
	
	//int nEscenariosEliminar = numeroEscenarios/10; // Numero de escenarios en los que se probara el metodo
	//int nEscenariosObtener = numeroEscenarios/10; // Maximo numero de escenarios enlos que probara el metodo obtener
	//int nEscenariosCambiar = numeroEscenarios/10; // Maximo numero de escenarios enlos que probara el metodo cambiar

	/*
	 * Escenarios
	 */
	// Arreglo con n elementos
	private void setUpEscenario(int n, int max) {
		// En caso de no especificarse un tamanio inicial para la tabla, se elige n/2, 
		// donde n es el numero de elementos a agregar
		if (max == -1) max = (n+1)/2;
		
		tabla = new SepChainTH<String, Integer>(max); // TODO Unica linea a modificar para cambiar tabla en test
		for (int i = 0; i < n; i++) {
			tabla.put("Elemento " + i, i);
		}
	}

	/*
	 * Metodos para Pruebas
	 */
	/**
	 * Prueba el constructor con diferentes tamanios iniciales para cada escenario. Asume que darTamano() funciona correctamente
	 */
	public void testConstructor() {
		for (int n = 1; n < numeroEscenarios; n++) {
			setUpEscenario(n, 1);
			assertTrue("Escenario: " + n + " con tamanio inicial 1. El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, 10);
			assertTrue("Escenario: " + n + " con tamanio inicial 10. El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, tamanoMax);
			assertTrue("Escenario: " + n + " con tamanio inicial " + tamanoMax + ". El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, -1);
			assertTrue("Escenario: " + n + " con tamanio inicial " + n/2 + ". El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
		}
	}
	
	/**
	 * Prueba el metodo darTamano()
	 */
	public void testDarTamano() {
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, -1);
			assertTrue("Escenario: " + n + " con tamanio inicial " + n/2 + ". El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			
			setUpEscenario(n, 1);
			assertTrue("Escenario: " + n + " con tamanio inicial 1. El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			
			setUpEscenario(n, 10);
			assertTrue("Escenario: " + n + " con tamanio inicial 10. El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			
			setUpEscenario(n, tamanoMax);
			assertTrue("Escenario: " + n + " con tamanio inicial " + tamanoMax + ". El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
		}
	}
	
	/**
	 * Prueba el metodo get(). Asume que darTamano() funciona correctamente
	 */
	public void testGet() {
		Integer[] tamanosInic = new Integer[] {-1, 1, 10, tamanoMax};// Arreglo con los tamanos iniciales de las tablas para cada escenario
		for (int n = 1; n <= n; n++) {
			
			for (Integer tamano : tamanosInic) {
				setUpEscenario(n, tamano);		
				// Obtener los elementos
				Integer valor;
				for (int i = 0; i < n; i++) {
					valor = tabla.get("Elemento " + i);
					// Verificar que el objeto es el esperado
					assertTrue("Escenario: " + n + " de tamanio " + tamano + ". El dato esperado era: " + i
							+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
					// Verificar que no ha cambiado el tamano del arreglo
					assertTrue("Escenario: " + n + " de tamanio " + tamano + ". El arreglo deberia tener " + n + " elementos."
							+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
				}
			}
		}
	}
	
	/**
	 * Prueba el metodo put. Asume que el metodo get() funciona correctamente
	 */
	public void testPut() {
		int nAgregar;
		Integer valor;
		
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, -1);
			nAgregar = 2*n;
			
			// Agrega nAgregar elementos nuevos
			for (int i = 0; i < nAgregar; i++) {
				tabla.put("Nuevo elemento " + i, i);
				
				// Comprobar que el elemento fue agregado
				valor = tabla.get("Nuevo elemento " + i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + i + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
			}
			// Comprobar que el tamanio de la tabla es el esperado
			assertTrue("Escenario: " + n + ". El arreglo deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
			
			// Modifica nAgregar/2 elementos existentes
			for (int i = 0; i < nAgregar/2; i++) {
				tabla.put("Nuevo elemento " + i, -i);
				
				// Comprobar que el elemento fue modificado correctamente
				valor = tabla.get("Nuevo elemento " + i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + (-i) + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor == -i);
			}
			// Comprobar que no se cambio el tamanio del arreglo
			assertTrue("Escenario: " + n + ". No debio cambiar de tamanio. El arreglo deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
		}
	}
	
	/**
	 * Prueba los metodos de eliminacion de elementos
	 */
	/*
	public void testEliminarEnPos() {
		int npe; // Posiciones a eliminar en cada escenario
		
		int[] posicionesEliminar;
		for (int n = 1; n <= nEscenariosEliminar; n++) {
			setUpEscenario(n, 0);
			
			// Elegir numero aleatorio de posiciones a eliminar
			Random random = new Random(System.currentTimeMillis());
			npe = random.nextInt(n+1);
						
			// Elegir las posiciones a eliminar
			posicionesEliminar = new int[npe];
			int i;
			for (int k = 0; k < npe; k++) {
				i = (n-1) - k*(n/npe); // Orden descendente necesario para esta implementacion
				posicionesEliminar[k]= i;
			}
			
			// Eliminar los elementos desde el mas grande
			int eliminados = 0;
			String dato;
			for (int k = 0; k < npe; k++) {
				i = posicionesEliminar[k];
				dato = arreglo.eliminarEnPos(i);
				eliminados += 1;
				// Verificar que se elimino el elemento correcto
				assertTrue("Escenario: " + n + ". El dato esperado era: " + "Elemento " + (i)
						+ ", pero se obtuvo " + dato, dato.equals("Elemento " + (i)));
				assertTrue("Escenario: " + n + ". El arreglo deberia tener " + (n - eliminados) + " elementos."
						+ " Pero tiene " + arreglo.darTamano(), arreglo.darTamano() == (n - eliminados));
				// Verificar que el elemento que ahora se encuentra en la posicon i es el i + 1
				if (i < (arreglo.darTamano()-1) && ((n/npe) != 1)) {
					assertTrue("Escenario: " + n + ". El " + i + "-esimo elemento deberia ser: Elemento " + (i+1)
							+ ", pero se obtuvo " + dato, arreglo.darObjeto(i).equals("Elemento " + (i+1)));
				}
			}
		}
	}
	*/
	/**
	 * Prueba el metodo iterator()
	 *//*
	public void testIterator() {
		int n = 1000;
		
		// Probar el iterador para los 2 posibles constructores para un n grande cualquiera
		for (Integer max : new Integer[]{0, tamanoMax}){
			setUpEscenario(n, max);
			
			int i = 0;
			for(String dato: arreglo) {
				assertTrue("Escenario: " + n + ". El " + i + "-esimo elemento deberia ser: Elemento " + i
						+ ", pero se obtuvo " + dato, arreglo.darObjeto(i).equals("Elemento " + i));
				i += 1;
			}
			// Verificar que solo se identifican n elementos
			assertTrue("Escenario: " + n + ". El iterador deberia identificar y devolver " + n + " elementos", i == n);
		}
	}*/
}
