package model.data_structures;

import java.util.Iterator;

public class LinProbTH<K, V> implements ITablaHash<K, V> {

	private K[] keys;
	private V[] values;
	private int m;
	private int n;


	public LinProbTH (int pM){
		m = pM;
		keys = (K[]) new Object[m];
		values = (V[]) new Object[m];
		n = 0;
	}

	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
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
		
		if ((n + 1.) / m > 0.75) rehash(2 * m); // Cambiado de lugar //TODO cambiar por algun primo
		
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
		// TODO Auto-generated method stub	
	}



	@Override
	public V get(K key) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
	}

	private int hash(K key){
		return Math.abs(key.hashCode())%m;
		//SE PUEDE HACER MEJOR PERO NO SUPE COMO
	}

	private void rehash(int newM){
		
			Queue<K> llaves = new Queue<>();
			Queue<V> valores = new Queue<>();
			int contador =0;
			for (int i = 0; i < m && contador<=n; i++) {
				if(keys[i]!=null){
					llaves.enqueue(keys[i]);
					valores.enqueue(values[i]);
					contador ++;
					//delete(keys[i]);  // Cambiado
				}
			}
			
			LinProbTH<K, V> nueva = new LinProbTH<>(newM); 
			for (int i = 0; i < llaves.size(); i++) {
				nueva.put(llaves.dequeue(), valores.dequeue());
			}
			// Cambiado: no se estaba haciendo nada con esta nueva lista hasta aca, asi que se agregaron las siguientes 3 lineas
			this.keys = nueva.keys;
			this.values = nueva.values;
			this.m = newM;
	}

	public int darTamano(){
		return n;
	}

}
