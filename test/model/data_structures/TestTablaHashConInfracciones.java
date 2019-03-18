package model.data_structures;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.LoadMovingViolations;
import model.vo.VOMovingViolation;

public class TestTablaHashConInfracciones {
	/**
	 * Atributos
	 */
	private static LinProbTH<Integer, IArregloDinamico<VOMovingViolation>> tabla1;
	
	private static SepChainTH<Integer, IArregloDinamico<VOMovingViolation>> tabla2;
	
	IArregloDinamico<Integer> nCargas;
	
	/**
	 * Establecimiento de escenarios
	 * @param tabla
	 * @param capInicial
	 */
	private void setUpEscenario(int nTabla, int capInicial ) {
		if (nTabla == 1) {
			tabla1 = new LinProbTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			tabla2 = null;
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {tabla1});
		} else if (nTabla == 2) {
			tabla2 = new SepChainTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			tabla1 = null;
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {tabla2});
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
