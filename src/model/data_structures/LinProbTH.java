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
			int iActual = 0;
			@Override
			public boolean hasNext() {
				if (iActual>=n) return false;
				return true;
			}
			@Override
			public K next() {

				if(iActual>=n){
					return null;
				}
				while(iActual<=m){
					iActual++;
					if(keys[iActual]!=null){
						return keys[iActual];
					}
				}
				return null;				
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
}
