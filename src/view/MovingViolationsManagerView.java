package view;

import model.data_structures.IArregloDinamico;
import model.vo.VOMovingViolation;

public class MovingViolationsManagerView 
{
	public MovingViolationsManagerView() {
		
	}
	
	public void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Taller 6----------------------");
		System.out.println("0. Cargar datos de infracciones en movimiento");
		System.out.println("1. Requerimiento 1a");
		System.out.println("2. Requerimiento 1b");
		System.out.println("10. Cerrar programa");
		System.out.println("Digite el numero de opcion para ejecutar la tarea, luego presione enter: (Ej., 1):");
		
	}
	
	/**
	 * Imprime la informaci�n sobre la carga de datos
	 * @param Arreglo Dinamico con la informacion de los datos cargados en cada mes
	 */
	public void printMovingViolationsLoadInfo(IArregloDinamico<Integer> resultados0) {
		int totalInfracciones = 0;
		int totalMeses = resultados0.darTamano();
		int infMes;
		System.out.println("  ----------Informaci�n Sobre la Carga------------------  ");
		for (int i = 0; i < totalMeses; i++) {
			infMes = resultados0.darObjeto(i);
			System.out.println("Infracciones Mes " + (i+1)+": " + infMes);
			totalInfracciones += infMes;
		}
		System.out.println("Total Infracciones Semestre: " + totalInfracciones);
	}
	/**
	 *Imprime el requerimiento 1 - TODO 
	 * @param TODO
	 */
	public void printMovingViolationsReq1(IArregloDinamico<VOMovingViolation> resultados1) {
		System.out.println("Las infracciones que ocurrieron en esa direccion y que terminaron en accidente son:");
		
		if (resultados1.darTamano() == 0) System.out.println("No ocurrieron infracciones con accidente en esa direccion.");
		
		for (VOMovingViolation infraccion : resultados1) {
			System.out.println("ObjectID: " + infraccion.getObjectId() + 
					"\tTicket Date: " + infraccion.getTicketIssueDate());
		}
		System.out.println();
	}

	public void printMessage(String mensaje) {
		System.out.println(mensaje);		
	}
}
