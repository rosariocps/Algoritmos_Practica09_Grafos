package graph;

import actividad1.ExceptionIsEmpty;
import actividad1.StackArray;
import actividad2.QueueLink;
import ejercicio1.StackLink;
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

    //MÉTODO BFS (RECORRRIDO EN ANCHURA DESDE EL VERTICE "v")
    public void bfs(E v) {
        Vertex<E> verticeInicial = searchVertex(v);
        if (verticeInicial == null) {
            System.out.println("El vértice inicial no existe.");
            return;
        }

        QueueLink<Vertex<E>> cola = new QueueLink<>();
        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();

        cola.enqueue(verticeInicial);
        visitados.insertLast(verticeInicial);

        System.out.print("Recorrido en anchura: ");

        while (!cola.isEmpty()) {
            try {
                Vertex<E> verticeActual = cola.dequeue();
                System.out.print(verticeActual.getData() + " ");

                Nodo<Edge<E>> nodoArista = verticeActual.listAdj.getFirst();
                while (nodoArista != null) {
                    Vertex<E> verticeVecino = nodoArista.getData().getRefDest();

                    if (visitados.search(verticeVecino) == -1) {
                        cola.enqueue(verticeVecino);
                        visitados.insertLast(verticeVecino);
                    }

                    nodoArista = nodoArista.getNext();
                }
            } catch (ExceptionIsEmpty e) {
                System.out.println("Error en la cola: " + e.getMessage());
            }
        }

        System.out.println();
    }

    // Clase auxiliar para guardar pares hijo → padre
    private class Par {
        Vertex<E> hijo;
        Vertex<E> padre;

        public Par(Vertex<E> hijo, Vertex<E> padre) {
            this.hijo = hijo;
            this.padre = padre;
        }
    }

    private Vertex<E> buscarPadre(Vertex<E> hijo, ListaEnlazada<Par> padres) {
        Nodo<Par> actual = padres.getFirst();
        while (actual != null) {
            if (actual.getData().hijo.equals(hijo)) {
                return actual.getData().padre;
            }
            actual = actual.getNext();
        }
        return null;
    }

    // METODO BFSPATH (CAMINO MÁS CORTO DESDE "v" HASTA "z")
    public ListaEnlazada<E> bfsPath(E v, E z) {
        ListaEnlazada<E> camino = new ListaEnlazada<>();
        Vertex<E> verticeInicial = searchVertex(v);
        Vertex<E> verticeDestino = searchVertex(z);

        if (verticeInicial == null || verticeDestino == null) {
            System.out.println("Uno de los vértices no existe.");
            return camino;
        }

        QueueLink<Vertex<E>> cola = new QueueLink<>();
        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
        ListaEnlazada<Par> padres = new ListaEnlazada<>();

        cola.enqueue(verticeInicial);
        visitados.insertLast(verticeInicial);
        padres.insertLast(new Par(verticeInicial, null));

        boolean encontrado = false;

        while (!cola.isEmpty()) {
            try {
                Vertex<E> verticeActual = cola.dequeue();

                if (verticeActual.equals(verticeDestino)) {
                    encontrado = true;
                    break;
                }

                Nodo<Edge<E>> nodoArista = verticeActual.listAdj.getFirst();
                while (nodoArista != null) {
                    Vertex<E> verticeVecino = nodoArista.getData().getRefDest();

                    if (visitados.search(verticeVecino) == -1) {
                        cola.enqueue(verticeVecino);
                        visitados.insertLast(verticeVecino);
                        padres.insertLast(new Par(verticeVecino, verticeActual));
                    }

                    nodoArista = nodoArista.getNext();
                }

            } catch (ExceptionIsEmpty e) {
                System.out.println("Error en la cola: " + e.getMessage());
            }
        }

        if (!encontrado) {
            System.out.println("No existe un camino de " + v + " a " + z);
            return camino;
        }

        // Reconstruir el camino hacia atrás usando StackLink
        StackLink<E> pila = new StackLink<>();
        Vertex<E> actual = verticeDestino;
        while (actual != null) {
            pila.push(actual.getData());
            actual = buscarPadre(actual, padres);
        }

        // Pasar los datos de la pila a la lista final (en orden correcto)
        try {
            while (!pila.isEmpty()) {
                camino.insertLast(pila.pop());
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error al reconstruir el camino: " + e.getMessage());
        }

        return camino;
    }


}
