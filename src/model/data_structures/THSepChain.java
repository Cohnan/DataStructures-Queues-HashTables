package model.data_structures;

import java.util.Iterator;

public class THSepChain<K, V> implements ITablaHash<K, V> {
	
	private IArregloDinamico<K> keys;
	private IArregloDinamico<V> values;
	private int m;
	
	public THSepChain (int pM){
		keys = new ArregloDinamico<K>();
		values = new ArregloDinamico<V>();
		m = pM;
		
		for (int i = 0; i < m; i++) {
			keys.agregar(null);
			values.agregar(null);
		}
		
		
	}

	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return keys.iterator();
	}

	@Override
	public void put(K key, V value) {
	
		int i;
		for(i = hash(key); keys.darObjeto(i)!=null;i = (i+1)%m){
			if(keys.darObjeto(i).equals(key)){
				break;
			}
		}
	
		keys.cambiarEnPos(i, key);
		values.cambiarEnPos(i, value);
		// TODO Auto-generated method stub	
	}
	
	

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		
		for(int i = hash(key);keys.darObjeto(i) !=null; i = (i+1)%m){
			if(key.equals(keys.darObjeto(i))){
				return values.darObjeto(i);
			}
		}
		
		return null;
	}

	@Override
	public V delete(K key) {
		
		for(int i = hash(key);keys.darObjeto(i) !=null; i = (i+1)%m){
			if(key.equals(keys.darObjeto(i))){
				keys.cambiarEnPos(i, null);
				//No estoy seguro
				V auxiliar = values.darObjeto(i);
				values.cambiarEnPos(i, null);
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
