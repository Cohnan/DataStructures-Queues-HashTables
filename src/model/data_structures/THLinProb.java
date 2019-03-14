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
		nuevo.cambiarSiguiente(nodos.darObjeto(i));
		nodos.cambiarEnPos(i, nuevo);
		// TODO Auto-generated method stu
	}

	@Override
	public V get(K key) {
		
		int i = hash(key);
		for(Nodo<K> x = nodos.darObjeto(i);x!=null;x = x.darSiguiente()){
			if(key.equals(x.darObjeto())){
				return (V)x.darValor();
			}
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V delete(K key) {
		
	int i = hash(key);
	if(nodos.darObjeto(i).equals(null)) return null;
	else{
		
		Nodo<K> actual = nodos.darObjeto(i);
		if(actual.equals(key)){
			nodos.cambiarEnPos(i, actual.darSiguiente());
		}
		else{
			while(actual.darSiguiente()!=null){
				
				if(actual.darSiguiente().equals(key)){
					actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
				}
				
				actual = actual.darSiguiente();
			}
		}
	}
		
		
		
		
		// TODO Auto-generated method stub
		return null;
	}
	
	private int hash(K key){
		return Math.abs(key.hashCode())%m;
		//SE PUEDE HACER MEJOR PERO NO SUPE COMO
	}

}
