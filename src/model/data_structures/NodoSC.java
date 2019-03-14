package model.data_structures;

public class NodoSC<K,V> {
		/**
		 * Lista encadenada sencilla cada nodo conoce el siguiente
		 */
		private NodoSC<K,V> siguiente;

		/**
		 * Objeto (informaci�n) que almacena el nodo
		 */
		private K key;
		private V value;

		/**
		 * Constructor
		 */
		public NodoSC(K pKey, V pValue){
			key = pKey;
			value = pValue;
			siguiente = null;
		}


		/**
		 * M�todo para saber el siguiente nodo
		 */
		public NodoSC<K,V> darSiguiente(){
			return siguiente;
		}

		/**
		 * M�todo para cambiar el siguiente nodo del nodo actual
		 */
		public void cambiarSiguiente(NodoSC<K,V> pNuevoSiguiente){
			siguiente = pNuevoSiguiente;
		}

		/**
		 * Retorna el objeto que alamcena el nodo
		 */
		public K darKey(){
			return key;
		}

		public V darValue(){
			return value;
		}
		
	}

