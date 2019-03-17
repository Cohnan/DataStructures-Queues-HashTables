package model.data_structures;

import java.util.Iterator;

public class LinProbTH<K, V> implements ITablaHash<K, V> {


	/**
	 * Arreglo que guarda las llaves
	 */
	private K[] keys;
	/**
	 * Arreglo que guarda los valores de cada llave
	 */
	private V[] values;
	/**
	 * Capacidad del arreglo
	 */
	private int m;

	/**
	 * Número de Datos
	 */
	private int n;

	/**
	 * Factor de carga máxmio
	 */
	private final double factorCarga = 0.75; 
	
	private int numRehash = 0;

	/**
	 * Constructor
	 */
	public LinProbTH (int pM){
		m = pM;
		keys = (K[]) new Object[m];
		values = (V[]) new Object[m];
		n = 0;
	}

	/**
	 * Iterador de la tabla
	 */
	@Override
	public Iterator<K> iterator() {
		return new Iterator<K>() {
			int iActual = siguienteNoNulo(0); // Guarda el indice del elemento a devolver -1 si no hay mas
			@Override

			//Método has next -> Si existe un elemento después o no
			public boolean hasNext() {
				return (iActual != -1);
			}
			@Override
			// Devuelve el siguiente elemento
			public K next() {
				if (iActual == -1) return null;
				K llaveAct = keys[iActual];
				iActual = siguienteNoNulo(iActual + 1);
				return llaveAct;
			}
		};

	}


	/**
	 * Para insertar un dato en la tabla
	 * Si ya existe la llave, se reemplaza el valor
	 */
	@Override
	public void put(K key, V value) {
		boolean existe = false;
		//Verificar si se debe hace rehash antes de insertar
		if ((n + 1.) / m > factorCarga) rehash(); //TODO cambiar por algun primo

		int i;
		// Recorre la tabla desde del hash buscando la siguiente posición vacía
		for(i = hash(key); keys[i] !=null; i = (i+1)%m){
			if(keys[i].equals(key)){
				existe = true;
				break;
			}
		}

		//Si no existía se aumenta en 1 el número de llaves
		if(!existe)n++;

		//Se asigna el valor de la llave en la posición 
		keys[i] = key;
		//Se asigna el valor del valor en la posición
		values[i] = value;
	}

	/**
	 * Método para buscar un valor de acuerdo a una llave
	 */
	@Override
	public V get(K key) {
		//Se recorren las llaver buscando la llave en cuestión
		for(int i = hash(key);keys[i]!=null; i = (i+1)%m){
			if(key.equals(keys[i])){
				//Al encontrarla se retorna el valor
				return values[i];
			}
		}
		// Si no se encuentra se retorna null
		return null;
	}

	@Override
	/**
	 * Método para eliminar una llave
	 */
	public V delete(K key) {

		if(key == null) return null;


		//Encontrar la posición de i
		int i = hash(key);
		while(!key.equals(keys[i])){
			i = (i+1)%m;
		}

		V respuesta = values[i];
		//Se elimina la llave y su valor
		keys[i] = null;
		values[i] = null;


		//Reorganizar
		i = (i+1)%m;

		while(keys[i]!=null){

			K keyAOrganizar = keys[i];
			V valueAOrganizar = values[i];
			keys[i] = null;
			values[i] = null;
			n--;
			put(keyAOrganizar,valueAOrganizar);
			i = (i+1)%m;
		}

		//Se reduce el número de llaves
		n--;
		// Se devuelve el valor asociado a la llave eliminada
		return respuesta;
	}
	/**
	 * Hash -> Devuelve un número entre 0 y M-1
	 */
	private int hash(K key){
		return (key.hashCode() & 0x7fffffff)%m;
	}


	/**
	 * Método para Rehash la tabla en caso de exceder el factor de carga
	 */
	private void rehash(){
		numRehash++;
		int contador = 0;
		//Se crea una nueva tabla con la capacidad dada por parámetro
		int numPrimo = siguientePrimo(m+1);
		LinProbTH<K, V> nueva = new LinProbTH<>(numPrimo); 
		for (int i = 0; contador < n; i++) {
			// Se guardan todos los valores de la tabla actual
			if (keys[i] != null ) {
				nueva.put(keys[i], values[i]);
				contador += 1;
			}
		}

		this.keys = nueva.keys;
		this.values = nueva.values;
		this.m = nueva.m;
	}
	/**
	 * Método para obtener el número de llaves en la tabla
	 */
	public int darTamano(){
		return n;
	}

	/**
	 * Busca el siguiente indice de la lista que no sea nula a partir y contando i
	 * @param i Se asume entre 0 y m-1
	 * @return El siguiente indice no nulo
	 */
	private int siguienteNoNulo(int i) {
		if (i >= m) return -1;
		while (i < m && keys[i] == null) i++;
		if (i == m) i = -1;
		return i;
	}
	

	/**
	 * Método para encontrar el siguiente número primo
	 */
	public int siguientePrimo(int numero){
		  int contador;
		  numero++;   
		  while(true){
		    contador = 0;
		    for(int i = 2; i <= Math.sqrt(numero); i ++){
		      if(numero % i == 0)  contador++;
		    }
		    if(contador == 0)
		      return numero;
		    else{
		      numero++;
		      continue;
		    }
		  }
		}

	
}