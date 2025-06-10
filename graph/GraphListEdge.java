package graph;

import actividad2.QueueLink;
import java.util.ArrayList;
import linkedlist.ListaEnlazada;

// ------------ EJERCICIO 3: GRAFO A PARTIR DE LISTAS DE ARISTAS (VERSION ARRAYLIST) -----------

public class GraphListEdge<V, E> {
    ArrayList<VertexObj<V, E>> secVertex;
    ArrayList<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ArrayList<>();
        this.secEdge = new ArrayList<>();
    }

    // insertVertex(E dato): inserta el vértice si no existe
    public void insertVertex(V dato) {
        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(dato)) {
                System.out.println("El vértice ya existe.");
                return;
            }
        }

        int nuevaPos = secVertex.size();
        secVertex.add(new VertexObj<>(dato, nuevaPos));
    }

    // insertEdge(E origen, E destino): inserta una arista si no existe
    public void insertEdge(V origen, V destino) {
        VertexObj<V, E> vert1 = null, vert2 = null;

        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(origen)) vert1 = vertex;
            if (vertex.info.equals(destino)) vert2 = vertex;
        }

        if (vert1 == null || vert2 == null) {
            System.out.println("Uno o ambos vértices no existen.");
            return;
        }

        for (EdgeObj<V, E> edge : secEdge) {
            if ((edge.endVertex1.equals(vert1) && edge.endVertex2.equals(vert2)) ||
                (edge.endVertex1.equals(vert2) && edge.endVertex2.equals(vert1))) {
                System.out.println("La arista ya existe.");
                return;
            }
        }

        int nuevaPos = secEdge.size();
        secEdge.add(new EdgeObj<>(vert1, vert2, null, nuevaPos));
    }

    // searchVertex(E dato): retorna true si el vértice existe
    public boolean searchVertex(V dato) {
        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(dato)) return true;
        }
        return false;
    }

    // searchEdge(E origen, E destino): retorna true si la arista existe
    public boolean searchEdge(V origen, V destino) {
        VertexObj<V, E> vert1 = null, vert2 = null;

        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(origen)) vert1 = vertex;
            if (vertex.info.equals(destino)) vert2 = vertex;
        }

        if (vert1 == null || vert2 == null) return false;

        for (EdgeObj<V, E> edge : secEdge) {
            if ((edge.endVertex1.equals(vert1) && edge.endVertex2.equals(vert2)) ||
                (edge.endVertex1.equals(vert2) && edge.endVertex2.equals(vert1))) {
                return true;
            }
        }

        return false;
    }

    // bfs(E inicio): recorrido en anchura usando QueueLink y ListaEnlazada
    public void bfs(V inicio) {
        VertexObj<V, E> start = null;
        for (VertexObj<V, E> vertex : secVertex) {
            if (vertex.info.equals(inicio)) {
                start = vertex;
                break;
            }
        }

        if (start == null) {
            System.out.println("El vértice inicial no existe.");
            return;
        }

        QueueLink<VertexObj<V, E>> cola = new QueueLink<>();
        ListaEnlazada<VertexObj<V, E>> visitados = new ListaEnlazada<>();

        cola.enqueue(start);
        visitados.insertLast(start);

        System.out.print("Recorrido BFS desde " + inicio + ": ");

        try {
            while (!cola.isEmpty()) {
                VertexObj<V, E> actual = cola.dequeue();
                System.out.print(actual.info + " ");

                for (EdgeObj<V, E> edge : secEdge) {
                    VertexObj<V, E> vecino = null;

                    if (edge.endVertex1.equals(actual)) {
                        vecino = edge.endVertex2;
                    } else if (edge.endVertex2.equals(actual)) {
                        vecino = edge.endVertex1;
                    }

                    if (vecino != null && visitados.search(vecino) == -1) {
                        cola.enqueue(vecino);
                        visitados.insertLast(vecino);
                    }
                }
            }
        } catch (actividad1.ExceptionIsEmpty e) {
            System.out.println("Error en la cola: " + e.getMessage());
        }

        System.out.println();
    }
}
