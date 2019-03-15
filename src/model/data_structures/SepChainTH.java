package model.data_structures;

import java.util.Iterator;

public class SepChainTH<K, V> implements ITablaHash<K, V> {

	private Nodo<K>[] nodos;
	private int m;
	
	public SepChainTH (int pM) {
		m = pM;		
		nodos = (Nodo<K>[]) new Nodo[m];
	}
	
	@Override
	public Iterator<K> iterator() {
	return null;
		// TODO Auto-generated method stub		
	}

	@Override
	public void put(K key, V value) {
	
		int i = hash(key);
		for(Nodo<K> x = nodos[i];x!=null;x = x.darSiguiente()){
			if(key.equals(x.darObjeto())){
			x.cambiarValor(value);
			return;
			}
		}
		
		nodos[i] = new Nodo<K>(key, value);

		// TODO Auto-generated method stu
	}

	@Override
	public V get(K key) {
		
		int i = hash(key);
		for(Nodo<K> x = nodos[i];x!=null;x = x.darSiguiente()){
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
	if(nodos[i].equals(null)) return null;
	else{
		
		Nodo<K> actual = nodos[i];
		if(actual.equals(key)){
			nodos[i] = actual.darSiguiente();
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
