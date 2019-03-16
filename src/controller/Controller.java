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

	//private static IArregloDinamico<VOMovingViolation> movingVOLista;
	
	private static int semestreCargado;
	
	private static LinProbTH<Integer, IArregloDinamico<VOMovingViolation>> thLinProb;
	
	private static SepChainTH<Integer, IArregloDinamico<VOMovingViolation>> thSepChain;
	
	private static HashMap<Integer, IArregloDinamico<VOMovingViolation>> prueba;
	/*
	 * Constructor
	 */
	public Controller() {
		view = new MovingViolationsManagerView();

		thLinProb = null;
		thSepChain = null;
		prueba = null;
	}
	
	/*
	 * Metodos para requerimientos
	 */
	public IArregloDinamico<Integer> loadMovingViolations(){
		return loadMovingViolations(1);
	}
	/**
	 * Carga los datos del semestre indicado indicado
	 * @param n Numero de semestre del anio (entre 1 y 2)
	 * @return Cola con el numero de datos cargados por mes del semestre
	 */
	private IArregloDinamico<Integer> loadMovingViolations(int n)
	{
		IArregloDinamico<Integer> numeroDeCargas = new ArregloDinamico<>();
		if(n == 1)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_January_2018.json", 
					    	     "Moving_Violations_Issued_in_February_2018.json",
					    	     "Moving_Violations_Issued_in_March_2018.json",
					    	     //"Moving_Violations_Issued_in_April_2018.json",
					    	     //"Moving_Violations_Issued_in_May_2018.json",
					    	     //"Moving_Violations_Issued_in_June_2018.json"
					    	     });
			semestreCargado = 1;
		}
		else if(n == 2)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_July_2018.json",
								 "Moving_Violations_Issued_in_August_2018.json",
								 "Moving_Violations_Issued_in_September_2018.json", 
								 "Moving_Violations_Issued_in_October_2018.json",
								 "Moving_Violations_Issued_in_November_2018.json",
								 "Moving_Violations_Issued_in_December_2018.json"
								 });
			semestreCargado = 2;
		}
		else
		{
			throw new IllegalArgumentException("No existe ese semestre en un annio.");
		}
		return numeroDeCargas;
	}
	
	private class JReader implements Iterable<String> { // TODO STATIC?
		private BufferedReader bufReader;
		private int lastReadChar;
		
		public JReader(File file) throws IOException {
			bufReader = new BufferedReader(new FileReader(file));
			while (bufReader.read() == '[');
			lastReadChar = bufReader.read();
		}
		
		public String next2() {
			// Assume last read char was a ',' o ']'
			if (lastReadChar == ']') {
				System.out.println("Nunca deberia llegar a aca si usa hasNext()");
				return null;
			}
			// Si fue una coma, busco el siguiente '{'
			while (read()!= '{');
			
			StringBuilder jsonText = new StringBuilder();
			jsonText.append((char) '{');
			lastReadChar = read();
			
			while (lastReadChar != '}') { 
				jsonText.append((char) lastReadChar);
				lastReadChar = read();
			} jsonText.append('}');
			
			// Invariant: find the next ']' or ','
			while (lastReadChar != ']' && lastReadChar != ',') lastReadChar = read(); 
			
			//System.out.println("Analizando : " + jsonText.toString());
			return jsonText.toString();
		}
		
		private int read() { // TODO mejorar
			try {
				return bufReader.read();
			} catch (IOException e) {
				e.printStackTrace();
				lastReadChar = ']';
				return ']';
			}
		}
		
		
		public void close() throws IOException {
			bufReader.close();
		}

		@Override
		public Iterator<String> iterator() {
			return new Iterator<String>() {
				public boolean hasNext() {
					//System.out.println("Entro a hasNext leyendo el caracter '" + (char)(lastReadChar) + "'");
					return lastReadChar != ']' && lastReadChar != -1;
				}
				public String next() {return next2();}
			};
		}
	}
	
	/**
	 * Metodo ayudante
	 * Carga la informacion sobre infracciones de los archivos a una pila y una cola ordenadas por fecha.
	 * Dado un arreglo con los nombres de los archivos a cargar
	 * @returns ArregloDinamico con el numero de datos cargados por mes del cuatrimestre
	 */
	private IArregloDinamico<Integer> loadMovingViolations(String[] movingViolationsFilePaths){
		// TODO
		JReader reader = null;
		Gson gson = new Gson();
		VOMovingViolation infraccionAct;
		IArregloDinamico<VOMovingViolation> valorAct;
		
		IArregloDinamico<Integer> numeroDeCargas = new ArregloDinamico<>();
		
		int contadorInf; // Cuenta numero de infracciones en cada archivo
		try {
			thLinProb = new LinProbTH<Integer, IArregloDinamico<VOMovingViolation>>(500000); // TODO quitar comentario
			//thSepChain = new SepChainTH<Integer, IArregloDinamico<VOMovingViolation>>(500000); // TODO quitar comentario

			for (String filePath : movingViolationsFilePaths) {
				reader = new JReader(new File("data/"+filePath));
				contadorInf = 0;
				
				// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
				for (String json : reader) {
					// Crear infraccion dado el json actual
					infraccionAct = gson.fromJson(json, VOMovingViolation.class);
					
					// Agregar esta infraccion a las hash tables, asegurandose de no reescribir
					valorAct = thLinProb.get(infraccionAct.getAddressID()); // El mismo para ambas tablas 
					if(valorAct == null) {
						valorAct = new ArregloDinamico<VOMovingViolation>();
					}
					valorAct.agregar(infraccionAct);
					
					thLinProb.put(infraccionAct.getAddressID(), valorAct); // TODO esta linea sobra o es necesaria?
					//thSepChain.put(infraccionAct.getAddressID(), valorAct); // Esta linea sobra o es necesaria
					
					contadorInf += 1;
				}
				// Se agrega el numero de infracciones cargadas en este archivo al resultado 
				numeroDeCargas.agregar(contadorInf);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return numeroDeCargas;
	}
	
	/**
	 * TODO
	 * @param addressId
	 * @return
	 */
	public IArregloDinamico<VOMovingViolation> requerimiento1a(int addressId) {
		IArregloDinamico<VOMovingViolation> general = thLinProb.get(addressId);
		IArregloDinamico<VOMovingViolation> respuesta = new ArregloDinamico<VOMovingViolation>();
		
		for (VOMovingViolation infraccion : general) {
			if (infraccion.getAccidentIndicator()) respuesta.agregar(infraccion);
		}
		
		Sort.ordenarShellSort(respuesta, new VOMovingViolation.TicketIssueOrder());
		return respuesta;
	}
	
	/**
	 * TODO
	 * @param addressId
	 * @return
	 */
	public IArregloDinamico<VOMovingViolation> requerimiento1b(int addressId) {
		IArregloDinamico<VOMovingViolation> general = thSepChain.get(addressId);
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
				view.printMovingViolationsLoadInfo(this.loadMovingViolations());
				break;

			case 1:
				// Requerimiento 1a
				view.printMessage("Ingrese un AddressID: ");
				view.printMovingViolationsReq1(requerimiento1a(sc.nextInt()));
				break;
			case 2:
				// Requerimiento 1b
				view.printMessage("Ingrese un AddressID: ");
				view.printMovingViolationsReq1(requerimiento1b(sc.nextInt()));
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
