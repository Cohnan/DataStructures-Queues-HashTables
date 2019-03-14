package model.data_structures;

import java.util.Iterator;

public class THLinProb<K, V> implements ITablaHash<K, V> {

	private IArregloDinamico<Nodo<K>> nodos;
	private int m;
	
	public THLinProb (int pM) {
		nodos = new ArregloDinamico<Nodo<K>>();
		m = pM;
		for (int i = 0; i < m; i++) {
			nodos.agregar(null);
		}
		
		
	}
	
	@Override
	public Iterator<K> iterator() {
	return null;
		// TODO Auto-generated method stub		
	}

	@Override
	public void put(K key, V value) {
	
		int i = hash(key);
		for(Nodo<K> x = nodos.darObjeto(i);x!=null;x = x.darSiguiente()){
			if(key.equals(x.darObjeto())){
			x.cambiarValor(value);
			return;
			}
		}
		
		Nodo<K> nuevo = new Nodo<K>(key);
		nuevo.cambiarValor(value);
		nodos.cambiarEnPos(i, nuevo);
		// TODO Auto-generated method stu
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V delete(K key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int hash(K key){
		return Math.abs(key.hashCode())%m;
		//SE PUEDE HACER MEJOR PERO NO SUPE COMO
	}

}
