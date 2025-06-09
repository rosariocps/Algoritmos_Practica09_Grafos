package graph;

import actividad1.ExceptionIsEmpty;
import actividad2.QueueLink;
import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

public class GraphListEdge<V, E> {
    ListaEnlazada<VertexObj<V, E>> secVertex;
    ListaEnlazada<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ListaEnlazada<>();
        this.secEdge = new ListaEnlazada<>();
    }

    // MÉTODO INSERTVERTEX (inserta el vértice v si no existe en la lista de vértices)
    public void insertVertex(V v) {
        Nodo<VertexObj<V, E>> actual = secVertex.getFirst();

        while (actual != null) {
            if (actual.getData().info.equals(v)) {
                System.out.println("El vértice ya existe.");
                return;
            }
            actual = actual.getNext();
        }

        int nuevaPosicion = secVertex.length();
        VertexObj<V, E> nuevoVertice = new VertexObj<>(v, nuevaPosicion);
        secVertex.insertLast(nuevoVertice);
    }

    // MÉTODO INSERTEDGE (inserta una arista entre los vértices v y z si no existe ya)
    public void insertEdge(V v, V z) {
        VertexObj<V, E> vert1 = null;
        VertexObj<V, E> vert2 = null;

        Nodo<VertexObj<V, E>> nodoV = secVertex.getFirst();
        while (nodoV != null) {
            if (nodoV.getData().info.equals(v)) {
                vert1 = nodoV.getData();
            }
            if (nodoV.getData().info.equals(z)) {
                vert2 = nodoV.getData();
            }
            nodoV = nodoV.getNext();
        }

        if (vert1 == null || vert2 == null) {
            System.out.println("Uno o ambos vértices no existen.");
            return;
        }

        Nodo<EdgeObj<V, E>> nodoE = secEdge.getFirst();
        while (nodoE != null) {
            EdgeObj<V, E> arista = nodoE.getData();
            if ((arista.endVertex1.equals(vert1) && arista.endVertex2.equals(vert2)) ||
                (arista.endVertex1.equals(vert2) && arista.endVertex2.equals(vert1))) {
                System.out.println("La arista ya existe.");
                return;
            }
            nodoE = nodoE.getNext();
        }

        int nuevaPos = secEdge.length();
        EdgeObj<V, E> nuevaArista = new EdgeObj<>(vert1, vert2, null, nuevaPos);
        secEdge.insertLast(nuevaArista);
    }

    // MÉTODO SEARCHVERTEX (retorna true si el vértice v existe, false en caso contrario)
    public boolean searchVertex(V v) {
        Nodo<VertexObj<V, E>> actual = secVertex.getFirst();
        while (actual != null) {
            if (actual.getData().info.equals(v)) {
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }

    // MÉTODO SEARCHEDGE (retorna true si existe una arista entre los vértices v y z, false en caso contrario)
    public boolean searchEdge(V v, V z) {
        VertexObj<V, E> vert1 = null;
        VertexObj<V, E> vert2 = null;

        Nodo<VertexObj<V, E>> nodoV = secVertex.getFirst();
        while (nodoV != null) {
            if (nodoV.getData().info.equals(v)) {
                vert1 = nodoV.getData();
            }
            if (nodoV.getData().info.equals(z)) {
                vert2 = nodoV.getData();
            }
            nodoV = nodoV.getNext();
        }

        if (vert1 == null || vert2 == null) {
            return false;
        }

        Nodo<EdgeObj<V, E>> nodoE = secEdge.getFirst();
        while (nodoE != null) {
            EdgeObj<V, E> arista = nodoE.getData();
            if ((arista.endVertex1.equals(vert1) && arista.endVertex2.equals(vert2)) ||
                (arista.endVertex1.equals(vert2) && arista.endVertex2.equals(vert1))) {
                return true;
            }
            nodoE = nodoE.getNext();
        }

        return false;
    }

    // MÉTODO BFS (realiza recorrido en anchura desde el vértice v y muestra los vértices visitados)
    public void bfs(V v) {
        VertexObj<V, E> inicio = null;

        Nodo<VertexObj<V, E>> nodoV = secVertex.getFirst();
        while (nodoV != null) {
            if (nodoV.getData().info.equals(v)) {
                inicio = nodoV.getData();
                break;
            }
            nodoV = nodoV.getNext();
        }

        if (inicio == null) {
            System.out.println("El vértice inicial no existe.");
            return;
        }

        QueueLink<VertexObj<V, E>> cola = new QueueLink<>();
        ListaEnlazada<VertexObj<V, E>> visitados = new ListaEnlazada<>();

        cola.enqueue(inicio);
        visitados.insertLast(inicio);

        System.out.print("Recorrido BFS desde " + v + ": ");

        try {
            while (!cola.isEmpty()) {
                VertexObj<V, E> actual = cola.dequeue();
                System.out.print(actual.info + " ");

                Nodo<EdgeObj<V, E>> nodoE = secEdge.getFirst();
                while (nodoE != null) {
                    EdgeObj<V, E> arista = nodoE.getData();
                    VertexObj<V, E> vecino = null;

                    if (arista.endVertex1.equals(actual)) {
                        vecino = arista.endVertex2;
                    } else if (arista.endVertex2.equals(actual)) {
                        vecino = arista.endVertex1;
                    }

                    if (vecino != null && visitados.search(vecino) == -1) {
                        cola.enqueue(vecino);
                        visitados.insertLast(vecino);
                    }

                    nodoE = nodoE.getNext();
                }
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error en la cola: " + e.getMessage());
        }

        System.out.println();
    }

}