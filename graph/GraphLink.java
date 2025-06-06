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

    // INSERTAR VÉRTICE – siguiendo el pseudocódigo exacto
    public void insertVertex(E dato) {
        // Paso 1: buscar el dato en la listaVertex del grafo
        if (searchVertex(dato) != null) {
            throw new RuntimeException("El vértice ya existe");
        }

        // Si no existe
        Vertex<E> nuevoVertex = new Vertex<>(dato); // crear nuevoVertex(dato)
        listVertex.insertLast(nuevoVertex);         // ListaVertex.add(nuevoVertex)
    }

    // INSERTAR ARISTA (grafo NO dirigido) – según pseudocódigo
    public void insertEdge(E vertexO, E vertexD, int weight) {
        // 1. Validar que existan vertexO y vertexD en la lista de vértices
        Vertex<E> origen = searchVertex(vertexO);
        Vertex<E> destino = searchVertex(vertexD);

        if (origen == null || destino == null) {
            throw new RuntimeException("Uno o ambos vértices no existen");
        }

        // 2. Crear nueva arista de O → D
        Edge<E> nuevoEdge1 = new Edge<>(destino, weight);

        // 3. Crear nueva arista de D → O
        Edge<E> nuevoEdge2 = new Edge<>(origen, weight);

        // 4. Insertar ambas aristas en las listas correspondientes
        origen.listAdj.insertLast(nuevoEdge1);
        destino.listAdj.insertLast(nuevoEdge2);
    }

    // Mantengo también la versión sin peso como adicional
    public void insertEdge(E verOri, E verDes) {
        insertEdge(verOri, verDes, -1);
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

        Nodo<Vertex<E>> current = listVertex.getFirst();
        while (current != null) {
            current.getData().listAdj.removeNodo(new Edge<>(vertexToRemove));
            current = current.getNext();
        }

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
