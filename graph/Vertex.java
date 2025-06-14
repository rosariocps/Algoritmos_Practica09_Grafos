package graph;

import linkedlist.ListaEnlazada;

public class Vertex<E> {
    private E data;
    protected ListaEnlazada<Edge<E>> listAdj; // lista de adyacencia
    // para almacenar las aristas que conectan este vértice con otros

    public Vertex(E data) {
        this.data = data;
        listAdj = new ListaEnlazada<Edge<E>>();
    }

    public E getData() {
        return data;
    }
    //para comparar vértices por su dato
    public boolean equals(Object o) { //parametro de tipo Object para poder comparar cualquier objeto)
        //si el objeto o es una instancia de Vertex
        //se usa <?> porque el tipo generico (<E>) puede ser cualquiera (String, Integer, etc.)
        if (o instanceof Vertex<?>) { 
            Vertex<E> v = (Vertex<E>) o; //hacemos un cast de o al tipo Vertex<E>
            //compara el valor almacenado en el vertice actual (this.data) con el valor del vértice recibido como argumento (v.data)
            return this.data.equals(v.data); 
            //si son iguales devuelve true si no devuelve false
        }
        return false; //si el objeto o no era un vertice se devuelve false
    }

    public String toString() {
        return this.data + " --> " + this.listAdj.toString() + "\n";
    }
}
