package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import com.google.gson.Gson;

import model.data_structures.*;
import model.util.Sort;
import model.vo.VOMovingViolation;
import view.MovingViolationsManagerView;

@SuppressWarnings("unused")
public class Controller {
	
	/*
	 * Atributos
	 */
	public static final String[] movingViolationsFilePaths = new String[] {"data/Moving_Violations_Issued_in_January_2018.json", "data/Moving_Violations_Issued_in_February_2018.json", "data/Moving_Violations_Issued_in_March_2018.json","data/Moving_Violations_Issued_in_April_2018.json"};

	private static MovingViolationsManagerView view;
	
	private static int semestreCargado;
	
	private static LinProbTH<Integer, IArregloDinamico<VOMovingViolation>> thLinProb;
	
	private static SepChainTH<Integer, IArregloDinamico<VOMovingViolation>> thSepChain;
	
	/*
	 * Constructor
	 */
	public Controller() {
		view = new MovingViolationsManagerView();

		thLinProb = null;
		thSepChain = null;
	}
	
	/*
	 * Metodos para requerimientos
	 */
	private IArregloDinamico<Integer> loadMovingViolations(int n, ITablaHash<Integer, IArregloDinamico<VOMovingViolation>>[] tablas) {
		return LoadMovingViolations.loadMovingViolations(n, tablas);
	}
	
	/**
	 * Mostrar infracciones que terminaron en accidente dada una dirección (usando LinearProbingHT)
	 * @param addressId
	 * @return ArregloDinamico con las infracciones deseadas, ordenadas cronologicamente
	 */
	public IArregloDinamico<VOMovingViolation> requerimiento1LinProb(int addressId) {
		return requerimiento1Auxiliar(addressId, thLinProb);
	}
	
	/**
	 * Mostrar infracciones que terminaron en accidente dada una dirección (usando SeparateChainingHT)
	 * @param addressId
	 * @return ArregloDinamico con las infracciones deseadas, ordenadas cronologicamente
	 */
	public IArregloDinamico<VOMovingViolation> requerimiento1SepChain(int addressId) {
		return requerimiento1Auxiliar(addressId, thSepChain);
	}
	
	/**
	 * Metodo auxiliar para mostrar infracciones que terminaron en accidente dada una dirección
	 * usando una de las tablas de hash existentes
	 * @param addressId
	 * @param thAUsar tabla de hash que se usará para extraer la información
	 * @return ArregloDinamico con las infracciones deseadas, ordenadas cronologicamente
	 */
	public IArregloDinamico<VOMovingViolation> requerimiento1Auxiliar(int addressId, ITablaHash<Integer, IArregloDinamico<VOMovingViolation>> thAUsar) {
		IArregloDinamico<VOMovingViolation> general = thAUsar.get(addressId);
		IArregloDinamico<VOMovingViolation> respuesta = new ArregloDinamico<VOMovingViolation>();
		
		for (VOMovingViolation infraccion : general) {
			if (infraccion.getAccidentIndicator()) respuesta.agregar(infraccion);
		}
		
		Sort.ordenarShellSort(respuesta, new VOMovingViolation.TicketIssueOrder());
		return respuesta;
	}
	
	public void run() {
		int nDatos = 0; // Para saber el numero total de datos cargados
		int nMuestra = 0; // En caso de generarse una muestra, variable donde se guarda el tamanio dado por el cliente
		long[] tiempos;// Para calcular tiempos de agregacion y eliminacion de elementos

		Scanner sc = new Scanner(System.in);
		boolean fin = false;

		while(!fin)
		{
			view.printMenu();

			int option = sc.nextInt();
			
			switch(option)
			{
			case 0:
				// Cargar infracciones
				thLinProb = new LinProbTH<Integer, IArregloDinamico<VOMovingViolation>>(600000); 
				thSepChain = new SepChainTH<Integer, IArregloDinamico<VOMovingViolation>>(600000);
				view.printMovingViolationsLoadInfo(this.loadMovingViolations(1, 
						new ITablaHash[] {thLinProb, thSepChain}));
				semestreCargado = 1;
				break;

			case 1:
				// Requerimiento 1a
				view.printMessage("Ingrese un AddressID: ");
				view.printMovingViolationsReq1(requerimiento1LinProb(sc.nextInt()));
				break;
			case 2:
				// Requerimiento 1b
				view.printMessage("Ingrese un AddressID: ");
				view.printMovingViolationsReq1(requerimiento1SepChain(sc.nextInt()));
				break;
			case 10:	
				fin=true;
				sc.close();
				break;
			}
		}
	}
	
	
	/**
	 * Metodo para buscar strings en un array de strings, usado para deducir la posicion
	 * de las columnas esperadas en cada archivo.
	 * @param array
	 * @param string
	 * @return
	 */
	private int buscarArray(String[] array, String string) {
		int i = 0;

		while (i < array.length) {
			if (array[i].equalsIgnoreCase(string)) return i;
			i += 1;
		}
		return -1;
	}

	/*
	 * Metodos ayudantes
	 */
	/**
	 * Convertir fecha a un objeto LocalDate
	 * @param fecha fecha en formato dd/mm/aaaa con dd para dia, mm para mes y aaaa para agno
	 * @return objeto LD con fecha
	 */
	private static LocalDate convertirFecha(String fecha)
	{
		return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	/**
	 * Convertir fecha y hora a un objeto LocalDateTime
	 * @param fecha fecha en formato yyyy-MM-dd'T'HH:mm:ss'.000Z' con dd para dia, mm para mes y yyy para agno, HH para hora, mm para minutos y ss para segundos
	 * @return objeto LDT con fecha y hora integrados
	 */
	private static LocalDateTime convertirFecha_Hora_LDT(String fechaHora)
	{
		return LocalDateTime.parse(fechaHora, DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss"));

    }
}
