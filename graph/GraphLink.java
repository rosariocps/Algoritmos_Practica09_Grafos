package graph;

import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

public class GraphLink<E> {
    protected ListaEnlazada<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListaEnlazada<>();
    }

    public void insertVertex(E data) {
        if (searchVertex(data) == null) {
            listVertex.insertLast(new Vertex<>(data));
        }
    }

    public void insertEdge(E verOri, E verDes) {
        Vertex<E> ori = searchVertex(verOri);
        Vertex<E> des = searchVertex(verDes);
        if (ori == null || des == null) return;

        ori.listAdj.insertLast(new Edge<>(des));
        des.listAdj.insertLast(new Edge<>(ori)); // Grafo no dirigido
    }

    public Vertex<E> searchVertex(E data) {
        Nodo<Vertex<E>> current = listVertex.getFirst();
        while (current != null) {
            if (current.getData().getData().equals(data)) {
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    public boolean searchEdge(E v, E z) {
        Vertex<E> vertV = searchVertex(v);
        Vertex<E> vertZ = searchVertex(z);
        if (vertV == null || vertZ == null) return false;

        Nodo<Edge<E>> current = vertV.listAdj.getFirst();
        while (current != null) {
            if (current.getData().getRefDest().equals(vertZ)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public String toString() {
        Nodo<Vertex<E>> current = listVertex.getFirst();
        String result = "";
        while (current != null) {
            result += current.getData().toString();
            current = current.getNext();
        }
        return result;
    }
}
