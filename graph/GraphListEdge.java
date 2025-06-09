package graph;

import linkedlist.ListaEnlazada;

public class GraphListEdge<V, E> {
    ListaEnlazada<VertexObj<V, E>> secVertex;
    ListaEnlazada<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ListaEnlazada<>();
        this.secEdge = new ListaEnlazada<>();
    }
}