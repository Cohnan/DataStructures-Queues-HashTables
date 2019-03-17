package model.data_structures;

import java.util.Deque;
import java.util.Iterator;

public class SepChainTH<K, V> implements ITablaHash<K, V> {

	
	/**
	 * Arreglo que guarda los nodos (cada nodo tiene una llave y un valor)
	 */
	private Nodo<K>[] nodos;
	/**
	 * Capacidad de la tabla
	 */
	private int m;
	/**
	 * Número de llaves en la tabla
	 */
	private int n;
	private final int factorCarga = 5;

	public SepChainTH (int pM) {
		m = pM;		
		nodos = (Nodo<K>[]) new Nodo[m];
	}

	@Override
	public Iterator<K> iterator() {

		return new Iterator<K>() {
			int iActual = -1; // Ultima posicion vista
			Nodo<K> nActual = null; // Ultimo nodo visto
			int contador = 0; // Cuantos ha visto hasta ahora

			@Override
			public boolean hasNext() {
				if (contador >= n) return false;
				return true;
			}
			@Override
			public K next() {
				if(contador>=n){
					return null;
				}
				if (contador == 0) {
					iActual = siguienteNoNulo(iActual + 1);
					nActual = nodos[iActual];
					contador += 1;
					return nActual.darObjeto();
				}
				//if(nActual!=null){
				if(nActual.darSiguiente()!=null){
					contador ++;
					nActual = nActual.darSiguiente();
					return nActual.darObjeto();
				}
				//}
				// En caso de necesitar pasar al siguiente hash del arreglo
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
	}

	@Override
	public void put(K key, V value) {
		/*
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
		 */
		if ((n + 1.) / m >= factorCarga) rehash();

		int i = hash(key);
		Nodo<K> nodoPrevio = nodos[i];

		if (nodoPrevio == null) {
			n++;
			nodos[i] = new Nodo<K>(key, value);
			return;
		}

		while (true) {
			if(key.equals(nodoPrevio.darObjeto())){
				nodoPrevio.cambiarValor(value);
				return;
			}

			if (nodoPrevio.darSiguiente() == null) break;
			nodoPrevio = nodoPrevio.darSiguiente();
		} 

		n++;
		nodoPrevio.cambiarSiguiente(new Nodo<K>(key, value));
	}

	@Override
	public V get(K key) {

		int i = hash(key);
		for(Nodo<K> x = nodos[i];x!=null;x = x.darSiguiente()){
			if(key.equals(x.darObjeto())){
				return (V)x.darValor();
			}
		}
		return null;
	}

	@Override
	public V delete(K key) {
		
		int i = hash(key);		
		V auxiliar = null;
		Nodo<K> actual = nodos[i];
		if(actual == null)return null;
		if(actual.darObjeto().equals(key)){
			auxiliar = (V) nodos[i].darValor();
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
	return null;
}

private int hash(K key){
	return (key.hashCode() & 0x7fffffff)%m;
}

private void rehash(){

	Queue<K> llaves = new Queue<>();
	Queue<V> valores = new Queue<>();
	Iterator<K> iterador = iterator();
	K actual;

	while (iterador.hasNext()){   // Cambiar el lugar donde se definia actual fue fundamental
		actual = iterador.next();
		llaves.enqueue(actual);
		valores.enqueue(get(actual));
		//delete(actual);
	}
	if (llaves.size() != n) System.out.println("Se esta comiendo alguna llaveee!!");
	SepChainTH<K, V> nueva = new SepChainTH<>(m*2);
	for (int i = 0; i < n; i++) {
		nueva.put(llaves.dequeue(), valores.dequeue());
	}

	this.m = 2*m;
	this.nodos = nueva.nodos;
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
	while (i < m && nodos[i] == null) i++;
	if (i == m) i = -1;
	return i;
}

}
