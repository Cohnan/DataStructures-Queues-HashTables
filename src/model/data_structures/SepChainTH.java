package model.data_structures;

import java.util.Deque;
import java.util.Iterator;

public class SepChainTH<K, V> implements ITablaHash<K, V> {

	private Nodo<K>[] nodos;
	private int m;
	private int n;

	public SepChainTH (int pM) {
		m = pM;		
		nodos = (Nodo<K>[]) new Nodo[m];
	}

	@Override
	public Iterator<K> iterator() {

		return new Iterator<K>() {
			int iActual = 0;
			Nodo<K> nActual = null;
			int contador = 0;
			@Override
			public boolean hasNext() {
				if (contador>=n) return false;
				return true;
			}
			@Override
			public K next() {
				if(contador>=n){
					return null;
				}
				if(nActual!=null){
					if(nActual.darSiguiente()!=null){
						contador ++;
						nActual = nActual.darSiguiente();
						return nActual.darObjeto();
					}
				}
				while(contador<n){
					iActual++;
					if(nodos[iActual]!=null){
						nActual = nodos[iActual];
						contador++;
						return nActual.darObjeto();
					}
				}
				return null;
			}
		};
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

		n++;
		rehash();
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
			V auxiliar = null;
			Nodo<K> actual = nodos[i];
			if(actual.equals(key)){
				auxiliar = (V)nodos[i].darValor();
				nodos[i] = actual.darSiguiente();
				n--;
				return auxiliar;
			}
			else{
				while(actual.darSiguiente()!=null){
					if(actual.darSiguiente().equals(key)){
						auxiliar = (V) actual.darSiguiente().darValor();
						actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
						n--;
						return auxiliar;
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

	private void rehash(){
		if(n/m >=5){
			Queue<K> llaves = new Queue<>();
			Queue<V> valores = new Queue<>();
			Iterator<K> iterador = iterator();
			K actual = iterador.next();
			
			while(iterador.hasNext()){
				llaves.enqueue(actual);
				valores.enqueue(get(actual));
				delete(actual);
				actual = iterador.next();
			}
		
			SepChainTH<K, V> nueva = new SepChainTH<>(m*2);
			for (int i = 0; i < llaves.size(); i++) {
				nueva.put(llaves.dequeue(), valores.dequeue());
			}

		}


	}

	public int darTamano(){
		return n;
	}

}
