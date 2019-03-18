package model.data_structures;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.LoadMovingViolations;
import model.vo.VOMovingViolation;

public class TestTablaHashConInfracciones {
	/**
	 * Atributos
	 */
	private static LinProbTH<Integer, IArregloDinamico<VOMovingViolation>> thLinProb;
	
	private static SepChainTH<Integer, IArregloDinamico<VOMovingViolation>> thSepChain;
	
	IArregloDinamico<Integer> nCargas;
	
	/**
	 * Establecimiento de escenarios
	 * @param tabla
	 * @param capInicial
	 */
	private void setUpEscenario(ITablaHash<Integer, IArregloDinamico<VOMovingViolation>> tabla, int capInicial ) {
		this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {tabla});
	}
	
	/**
	 * 
	 */
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
