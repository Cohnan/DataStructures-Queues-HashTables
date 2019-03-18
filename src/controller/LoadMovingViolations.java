package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;

import model.data_structures.ArregloDinamico;
import model.data_structures.IArregloDinamico;
import model.data_structures.ITablaHash;
import model.vo.VOMovingViolation;

public class LoadMovingViolations {
	/**
	 * Carga los datos del semestre indicado indicado
	 * @param n Numero de semestre del anio (entre 1 y 2)
	 * @return Cola con el numero de datos cargados por mes del semestre
	 */
	public static IArregloDinamico<Integer> loadMovingViolations(int n, ITablaHash<Integer, IArregloDinamico<VOMovingViolation>>[] paraCarga)
	{
		IArregloDinamico<Integer> numeroDeCargas = new ArregloDinamico<>();
		if(n == 1)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_January_2018.json", 
					    	     "Moving_Violations_Issued_in_February_2018.json",
					    	     "Moving_Violations_Issued_in_March_2018.json",
//					    	     "Moving_Violations_Issued_in_April_2018.json",
//					    	     "Moving_Violations_Issued_in_May_2018.json",
//					    	     "Moving_Violations_Issued_in_June_2018.json"
					    	     }, paraCarga);
		}
		else if(n == 2)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_July_2018.json",
								 "Moving_Violations_Issued_in_August_2018.json",
								 "Moving_Violations_Issued_in_September_2018.json", 
								 "Moving_Violations_Issued_in_October_2018.json",
								 "Moving_Violations_Issued_in_November_2018.json",
								 "Moving_Violations_Issued_in_December_2018.json"
								 }, paraCarga);
		}
		else
		{
			throw new IllegalArgumentException("No existe ese semestre en un annio.");
		}
		return numeroDeCargas;
	}
	
	private static  class JReader implements Iterable<String> {
		// Clase hecha simulando el comportamiento de los Readers
		private BufferedReader bufReader;
		private int lastReadChar;
		
		public JReader(File file) throws IOException {
			bufReader = new BufferedReader(new FileReader(file));
			while (bufReader.read() == '[');
			lastReadChar = bufReader.read();
		}
		
		public String readJson() {
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
			
			// To satisfy Invariant: find the next ']' or ','
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
				public String next() {return readJson();}
			};
		}
	}
	
	/**
	 * Metodo ayudante
	 * Carga la informacion sobre infracciones de los archivos a una pila y una cola ordenadas por fecha.
	 * Dado un arreglo con los nombres de los archivos a cargar
	 * @returns ArregloDinamico con el numero de datos cargados por mes del cuatrimestre
	 */
	private static IArregloDinamico<Integer> loadMovingViolations(String[] movingViolationsFilePaths, ITablaHash<Integer, IArregloDinamico<VOMovingViolation>>[] paraCarga){
		JReader reader = null;
		Gson gson = new Gson();
		VOMovingViolation infraccionAct;
		IArregloDinamico<VOMovingViolation> valorAct;
		
		IArregloDinamico<Integer> numeroDeCargas = new ArregloDinamico<>();
		
		int contadorInf; // Cuenta numero de infracciones en cada archivo
		try {
			for (String filePath : movingViolationsFilePaths) {
				reader = new JReader(new File("data/"+filePath));
				contadorInf = 0;
				
				// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
				for (String json : reader) {
					// Crear infraccion dado el json actual
					infraccionAct = gson.fromJson(json, VOMovingViolation.class);
					
					// Agregar esta infraccion a las hash tables, asegurandose de no reescribir
					valorAct = paraCarga[0].get(infraccionAct.getAddressID()); // El mismo para ambas tablas 
					if(valorAct == null) {
						valorAct = new ArregloDinamico<VOMovingViolation>();
					}
					valorAct.agregar(infraccionAct);
					
					for (ITablaHash<Integer, IArregloDinamico<VOMovingViolation>> tabla : paraCarga) {
						tabla.put(infraccionAct.getAddressID(), valorAct);
					}
					
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
}