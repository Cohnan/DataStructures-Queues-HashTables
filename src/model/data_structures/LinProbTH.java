package model.data_structures;

import java.util.Iterator;

public class LinProbTH<K, V> implements ITablaHash<K, V> {
	
	private K[] keys;
	private V[] values;
	private int m;
	
	public LinProbTH (int pM){
		m = pM;
		keys = (K[]) new Object[m];
		values = (V[]) new Object[m];
		
	}

	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(K key, V value) {
	
		int i;
		for(i = hash(key); keys[i] !=null;i = (i+1)%m){
			if(keys[i].equals(key)){
				break;
			}
		}
	
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

}
