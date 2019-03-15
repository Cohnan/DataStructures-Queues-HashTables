import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.data_structures.ArregloDinamico;

class TestTHSepChain {
	/*
	 * Atributos 
	 */
	private THSepChain<String, String> tabla;
	private final int numeroEscenarios = 1000;
	private final int tamanoMax = 100;
	
	int nEscenariosEliminar = numeroEscenarios/10; // Numero de escenarios en los que se probara el metodo
	int nEscenariosObtener = numeroEscenarios/10; // Maximo numero de escenarios enlos que probara el metodo obtener
	int nEscenariosCambiar = numeroEscenarios/10; // Maximo numero de escenarios enlos que probara el metodo cambiar

	/*
	 * Escenarios
	 */
	// Arreglo con n elementos
	private void setUpEscenario(int n, int max) {
		if (max == 0) 
			arreglo = new ArregloDinamico<String>();
		else
			arreglo = new ArregloDinamico<String>(max);
		for (int i = 0; i < n; i++) {
			arreglo.agregar("Elemento " + i);
		}
	}
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testTHLinProb() {
		fail("Not yet implemented");
	}

	@Test
	void testIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testPut() {
		fail("Not yet implemented");
	}

	@Test
	void testGet() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
