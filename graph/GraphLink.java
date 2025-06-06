package graph;

import actividad1.ExceptionIsEmpty;
import actividad1.StackArray;
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

    public void removeVertex(E v) {
        Vertex<E> vertexToRemove = searchVertex(v);
        if (vertexToRemove == null) return;

        //para eliminar todas las aristas que apuntan a este vertice desde otros vertices
        Nodo<Vertex<E>> current = listVertex.getFirst();
        while (current != null) {
            current.getData().listAdj.removeNodo(new Edge<>(vertexToRemove));
            current = current.getNext();
        }

        //para eliminar el vertice de la lista de vertices
        listVertex.removeNodo(vertexToRemove);
    }

    public void removeEdge(E v, E z) {
        Vertex<E> vertV = searchVertex(v);
        Vertex<E> vertZ = searchVertex(z);
        if (vertV == null || vertZ == null) return;

        vertV.listAdj.removeNodo(new Edge<>(vertZ));
        vertZ.listAdj.removeNodo(new Edge<>(vertV)); // grafo no dirigido
    }

    public void dfs(E v) {
        Vertex<E> start = searchVertex(v);
        if (start == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
        StackArray<Vertex<E>> pila = new StackArray<>(100);

        try {
            pila.push(start);
            while (!pila.isEmpty()) {
                Vertex<E> actual = pila.pop();
                if (visitados.search(actual) == -1) {
                    System.out.print(actual.getData() + " ");
                    visitados.insertLast(actual);

                    Nodo<Edge<E>> adyacente = actual.listAdj.getFirst();
                    while (adyacente != null) {
                        Vertex<E> destino = adyacente.getData().getRefDest();
                        if (visitados.search(destino) == -1) {
                            pila.push(destino);
                        }
                        adyacente = adyacente.getNext();
                    }
                }
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error en DFS: " + e.getMessage());
        }
    }
}
