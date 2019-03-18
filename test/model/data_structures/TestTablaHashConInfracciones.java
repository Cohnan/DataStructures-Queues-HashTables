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
	
	int totalConsultas = 10000;
	
	int[] tamaniosIniciales = new int[] {1, 10, 100, 1000, 10000, 100000, 600000};
	
	private static LinProbTH<Integer, IArregloDinamico<VOMovingViolation>> thLinProb;
	
	private static SepChainTH<Integer, IArregloDinamico<VOMovingViolation>> thSepChain;
	
	IArregloDinamico<Integer> nCargas;

	/**
	 * Establecimiento de escenarios
	 * @param tabla
	 * @param capInicial
	 */
	private void setUpEscenario(int nTabla, int capInicial ) {
		if (nTabla == 1) {
			thLinProb = new LinProbTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			thSepChain = null;
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {thLinProb});
		} else if (nTabla == 2) {
			thSepChain = new SepChainTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			thLinProb = null;
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {thSepChain});
		}
	}

	/**
	 * 
	 */
	@Test
	public void testLinProb() {
		for (int tamanioInicial : tamaniosIniciales) {
			setUpEscenario(1, tamanioInicial);
			System.out.println("\nProbando linProb con un tamaño inicial de " + tamanioInicial);
			
			int total = 0;
			Integer[] arreglo = new Integer[totalConsultas];
	
			for(Integer s:nCargas){
				total+=s;
			}
	
			//Inicialmente
			System.out.println("Factor de Carga LinProb" + thLinProb.factorCarga);
	
	
			//Verificaci�n
			System.out.println(thLinProb.numRehash);
	
			//Consultas GET
			int contador  = 0;
		
			
			while(contador<totalConsultas){
				int numAleatorio = (int) (Math.random()*total);
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
	}

	
	/**
	 * 
	 */
	@Test
	public void testSepChain() {
		setUpEscenario(2, 600000);
		System.out.println("\nProbando linProb con un tamaño inicial de " + 600000);
		
		int total = 0;
		Integer[] arreglo = new Integer[totalConsultas];

		for(Integer s:nCargas){
			total+=s;
		}

		//Inicialmente
		System.out.println("Factor de Carga SepChain" + thSepChain.factorCarga);


		//Verificaci�n
		System.out.println(thSepChain.numRehash);

		//Consultas GET
		int contador  = 0;
	
		
		while(contador<totalConsultas){
			int numAleatorio = (int) (Math.random()*total);
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
