package model.data_structures;

import static org.junit.Assert.*;

import java.util.Comparator;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.Test;

import controller.LoadMovingViolations;
import model.util.Sort;
import model.vo.VOMovingViolation;

public class TestTablaHashConInfracciones {
	/**
	 * Atributos
	 */
	
	int totalConsultas = 1000;
	
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
			thSepChain = null;
			thLinProb = new LinProbTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {thLinProb});
		} else if (nTabla == 2) {
			thLinProb = null;
			thSepChain = new SepChainTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {thSepChain});
		}
	}

	/**
	 * 
	 */
	@Test
	public void testLinProb() {
//		for (int tamanioInicial : tamaniosIniciales) {
			setUpEscenario(1, 10000);
			System.out.println("\nProbando linProb con un tamaÃ±o inicial de " + 10000);
			
			int total = 0;
			Integer[] arreglo = new Integer[totalConsultas];
	
			for(Integer s:nCargas){
				total+=s;
			}
			
			System.out.println("Total de datos" + total);
	
			//Inicialmente
			System.out.println("Número de Llaves " + thLinProb.n);
			System.out.println("Capacidad del arreglo " + thLinProb.m);
	
			//Consultas GET
			int contador  = 0;
			int numAleatorio = 0;
			
			while(contador<totalConsultas){
				numAleatorio = (int) (Math.random()*thLinProb.n);
//				System.out.println(numAleatorio);
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
			
			//Resultados
			System.out.println("Número de Rehash's " + thLinProb.numRehash);
			System.out.println("Tiempo Total de Get's " + totalTime);
			System.out.println("Número de Llaves " + thLinProb.n);
			System.out.println("Capacidad Final del Arreglo " + thLinProb.m);
//		}
	}

	
	/**
	 * 
	 */
	@Test
	public void testSepChain() {
//		for (int tamanioInicial : tamaniosIniciales) {
			setUpEscenario(2, 10000);
			System.out.println("\nProbando setpChain con un tamaÃ±o inicial de " + 10000);
			
			int total = 0;
			Integer[] arreglo = new Integer[totalConsultas];
	
			for(Integer s:nCargas){
				total+=s;
			}
			
			System.out.println("Total de datos" + total);
	
			//Inicialmente
			System.out.println("Número de Llaves " + thSepChain.n);
			System.out.println("Capacidad del arreglo " + thSepChain.m);
	
			//Consultas GET
			int contador  = 0;
			int numAleatorio = 0;
			
			while(contador<totalConsultas){
				numAleatorio = (int) (Math.random()*thSepChain.n);
//				System.out.println(numAleatorio);
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
			
			//Resultados
			System.out.println("Número de Rehash's " + thSepChain.numRehash);
			System.out.println("Tiempo Total de Get's " + totalTime);
			System.out.println("Número de Llaves " + thSepChain.n);
			System.out.println("Capacidad Final del Arreglo " + thSepChain.m);
//		}
	}

	/**
	 * Genera un arreglo de numeros entre 0 y max (sin incluirlo) de tamaÃ±o tamano
	 * @para max Numero maximo deseado 
	 * @param tamano
	 * @return El arreglo deseado
	 */
	private IArregloDinamico<Integer> generarListaOrdenada(int max, int tamano) {
		IArregloDinamico<Integer> posiciones  =  new ArregloDinamico<>(tamano);
		// Generar posiciones
		for (int i = 0; i < tamano; i++){
			posiciones.agregar((int)(Math.random() * max));
		}
		
		while(!Sort.isSorted(posiciones, Comparator.<Integer>naturalOrder())) {
			Sort.ordenarShellSort(posiciones); //Rapido para listas parcialmente ordenadas
			for (int i = 0; i < tamano-1; i++) {
				while (posiciones.darObjeto(i) == posiciones.darObjeto(i+1)) posiciones.cambiarEnPos(i,(int)(Math.random() * max));
			}
		}
		return posiciones;
	}
}
