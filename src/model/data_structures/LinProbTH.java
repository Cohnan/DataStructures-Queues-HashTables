package model.data_structures;

import java.util.Iterator;

public class LinProbTH<K, V> implements ITablaHash<K, V> {

	private K[] keys;
	private V[] values;
	private int m;
	private int n;
	private final double factorCarga = 0.75; 


	public LinProbTH (int pM){
		m = pM;
		keys = (K[]) new Object[m];
		values = (V[]) new Object[m];
		n = 0;
	}

	@Override
	public Iterator<K> iterator() {
		return new Iterator<K>() {
			int iActual = siguienteNoNulo(0); // Guarda el indice del elemento a devolver -1 si no hay mas
			@Override
			public boolean hasNext() {
				return (iActual != -1);
			}
			@Override
			public K next() {
				if (iActual == -1) return null;
				K llaveAct = keys[iActual];
				iActual = siguienteNoNulo(iActual + 1);
				return llaveAct;
			}
		};

	}

	@Override
	public void put(K key, V value) {
		boolean existe = false;
		
		if ((n + 1.) / m > factorCarga) rehash(2 * m); //TODO cambiar por algun primo
		
		int i;
		for(i = hash(key); keys[i] !=null; i = (i+1)%m){
			if(keys[i].equals(key)){
				existe = true;
				break;
			}
		}

		if(!existe)n++;
		
		keys[i] = key;
		values[i] = value;
		//System.out.println("Agregado '" + key + "' en la posicion " + i);	
	}



	@Override
	public V get(K key) {
		for(int i = hash(key);keys[i]!=null; i = (i+1)%m){
			if(key.equals(keys[i])){
				return values[i];
			}
		}
		return null;
	}

	@Override
	public V delete(K key) {

		for(int i = hash(key);keys[i] !=null; i = (i+1)%m){
			if(key.equals(keys[i])){
				keys[i] = null;
				//No estoy seguro
				V auxiliar = values[i];
				values[i] = null;
				n--;		
				
				//if (n/m <= 0.25 && m >= 2) rehash(m/2); // Evita que quede muy vacia
				
				return auxiliar;
			}
		}
		return null;
	}

	private int hash(K key){
		return (key.hashCode() & 0x7fffffff)%m;
	}

	private void rehash(int newM){
		int contador = 0;
		LinProbTH<K, V> nueva = new LinProbTH<>(newM); 
		for (int i = 0; contador < n; i++) {
			if (keys[i] != null ) {
				nueva.put(keys[i], values[i]);
				contador += 1;
			}
		}
		
		this.keys = nueva.keys;
		this.values = nueva.values;
		this.m = newM;
	}

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
}