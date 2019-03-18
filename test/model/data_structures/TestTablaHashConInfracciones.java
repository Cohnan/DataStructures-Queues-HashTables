package model.data_structures;

import static org.junit.Assert.*;

import javax.swing.text.html.HTMLDocument.Iterator;

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
	public void testLinProb() {

		int total = 0;
		Integer[] arreglo = new Integer[10000];

		for(Integer s:nCargas){
			total+=s;
		}

		//Inicialmente
		System.out.println("Factor de Carga LinProb" + thLinProb.factorCarga);


		//Verificación
		System.out.println(thLinProb.numRehash);

		thLinProb = new LinProbTH<>(total);

		setUpEscenario(thLinProb, total);

		//Consultas GET
		int contador  = 0;
	
		
		while(contador<=10000){
			int numAleatorio = (int) (Math.random()*total)+1;
			java.util.Iterator<Integer> iterador = thLinProb.iterator();
			Integer llaves = iterador.next();
			int numero = 0;
			while(numero<= numAleatorio){
				llaves = iterador.next();
				numero++;		}
			arreglo[contador] = llaves;
			contador ++;
		}
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < arreglo.length; i++) {
			thLinProb.get(arreglo[i]);
		}

		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime-startTime;
		System.out.println(thLinProb.factorCarga);
		System.out.println(thLinProb.numRehash);
		System.out.println(totalTime);
		System.out.println(thLinProb.n);
		System.out.println(thLinProb.m);

	}

	
	/**
	 * 
	 */
	@Test
	public void testSepChain() {

		int total = 0;
		Integer[] arreglo = new Integer[10000];

		for(Integer s:nCargas){
			total+=s;
		}

		//Inicialmente
		System.out.println("Factor de Carga SepChain" + thSepChain.factorCarga);


		//Verificación
		System.out.println(thSepChain.numRehash);

		thSepChain = new SepChainTH<>(total);

		setUpEscenario(thSepChain, total);

		//Consultas GET
		int contador  = 0;
	
		
		while(contador<=10000){
			int numAleatorio = (int) (Math.random()*total)+1;
			java.util.Iterator<Integer> iterador = thSepChain.iterator();
			Integer llaves = iterador.next();
			int numero = 0;
			while(numero<= numAleatorio){
				llaves = iterador.next();
				numero++;		}
			arreglo[contador] = llaves;
			contador ++;
		}
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < arreglo.length; i++) {
			thSepChain.get(arreglo[i]);
		}

		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime-startTime;
		System.out.println(thSepChain.factorCarga);
		System.out.println(thSepChain.numRehash);
		System.out.println(totalTime);
		System.out.println(thSepChain.n);
		System.out.println(thSepChain.m);

	}

	
}
