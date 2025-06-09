package graph;

import actividad1.ExceptionIsEmpty;
import actividad2.QueueLink;
import ejercicio1.StackLink;
import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

public class GraphLink<E> {
    protected ListaEnlazada<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListaEnlazada<>();
    }

    // INSERTAR VERTICE 
    public void insertVertex(E dato) {
        //1ro buscamos el dato en la listaVertex del grafo
        if (searchVertex(dato) != null) {
            //si esta lanzamos excepcion
            throw new RuntimeException("El vértice ya existe");
        }
        //si no existe
        Vertex<E> nuevoVertex = new Vertex<>(dato); // creamos un nuevoVertex(dato)
        listVertex.insertLast(nuevoVertex);         // y lo añadimos a ListaVertex
    }

    // INSERTAR ARISTA (grafo NO dirigido)
    public void insertEdge(E vertexO, E vertexD, int weight) {
        //1ro validamos que existan vertexO y vertexD en la lista de vertices
        Vertex<E> origen = searchVertex(vertexO);
        Vertex<E> destino = searchVertex(vertexD);
        //si alguno de los vertices no fue encontrado, se lanza un error
        if (origen == null || destino == null) {
            throw new RuntimeException("Uno o ambos vertices no existen");
        }
        //2do Creamos nueva arista de O → D
        Edge<E> nuevoEdge1 = new Edge<>(destino, weight);
        //3ro Creamos nueva arista de D → O
        Edge<E> nuevoEdge2 = new Edge<>(origen, weight);
        //4to Insertamos ambas aristas en las listas correspondientes
        origen.listAdj.insertLast(nuevoEdge1);
        destino.listAdj.insertLast(nuevoEdge2);
    }
    //metodo sobrecargado - varios metodos con el mismo nombre pero con diferentes parametros
    //version sin peso como adicional
    public void insertEdge(E verOri, E verDes) {
        insertEdge(verOri, verDes, -1);
    }
    //BUSCAR UN VERTICE
    public Vertex<E> searchVertex(E data) {
        //creamos un nodo current que va apuntar al 1er nodo de la lista de vertices
        Nodo<Vertex<E>> current = listVertex.getFirst();
        //mientras no lleguemos al final de la lista
        while (current != null) {
            //Comparamos el valor dentro del vértice actual con el dato que estamos buscando
            //current.getData() de Nodo:Nos da el vertice actual (un objeto Vertex<E>)
            //.getData() de Vertex:esto accede al dato dentro de ese vertice, que es de tipo E
            //.equals(data) de Vertex:esto compara ese dato con el dato que estas buscando, usando .equals
            if (current.getData().getData().equals(data)) {
                return current.getData();  //si se encontro retornamos el vertice (objeto Vertex)
            }
            current = current.getNext(); //si no se encontro se pasa al siguiente nodo de la lista
        }
        return null; //si ya se recorrio la lista y no se encontro, retonamos null
    }

    public boolean searchEdge(E v, E z) {
        // buscamos el vertice que contiene el dato v
        Vertex<E> vertV = searchVertex(v);   
        // buscamos el vertice que contiene el dato z
        Vertex<E> vertZ = searchVertex(z);
        // si alguno de los dos vertices no existe, no puede haber arista
        if (vertV == null || vertZ == null) return false;
        // obtenemos el primer nodo de la lista de adyacencia del vertice v
        Nodo<Edge<E>> current = vertV.listAdj.getFirst();
        // recorremos la lista de aristas del vertice v
        while (current != null) {
            // si el destino de la arista actual es igual al vertice z
            if (current.getData().getRefDest().equals(vertZ)) {
                // entonces existe una arista entre v y z
                return true;
            }
            // pasamos al siguiente nodo en la lista
            current = current.getNext();
        }
        // si no encontramos la arista, retornamos false
        return false;
    }

    public String toString() {
        // obtenemos el primer nodo de la lista de vertices
        Nodo<Vertex<E>> current = listVertex.getFirst();
        // inicializamos una cadena vacia para guardar el resultado
        String result = "";
        // recorremos la lista de vertices
        while (current != null) {
            // agregamos la representacion del vertice actual al resultado
            result += current.getData().toString();
            // pasamos al siguiente nodo de la lista
            current = current.getNext();
        }
        // retornamos el texto con todos los vertices y sus conexiones
        return result;
    }

    public void removeVertex(E v) {
        // buscamos el vertice que contiene el dato v
        Vertex<E> vertexToRemove = searchVertex(v);
        // si no existe el vertice, salimos del metodo
        if (vertexToRemove == null) return;
        // obtenemos el primer nodo de la lista de vertices
        Nodo<Vertex<E>> current = listVertex.getFirst();
        // recorremos todos los vertices del grafo
        while (current != null) {
            // eliminamos cualquier arista que apunte al vertice a eliminar
            current.getData().listAdj.removeNodo(new Edge<>(vertexToRemove));
            // pasamos al siguiente vertice
            current = current.getNext();
        }
        // finalmente, eliminamos el vertice de la lista de vertices
        listVertex.removeNodo(vertexToRemove);
    }

    public void removeEdge(E v, E z) {
        // buscamos el vertice que contiene el dato v
        Vertex<E> vertV = searchVertex(v);
        // buscamos el vertice que contiene el dato z
        Vertex<E> vertZ = searchVertex(z);
        // si alguno de los dos vertices no existe, no hay nada que eliminar
        if (vertV == null || vertZ == null) return;
        // eliminamos la arista que va de v a z
        vertV.listAdj.removeNodo(new Edge<>(vertZ));
        // eliminamos la arista que va de z a v (porque el grafo es no dirigido)
        vertZ.listAdj.removeNodo(new Edge<>(vertV));
    }

    public void dfs(E v) {
        // buscamos el vertice inicial
        Vertex<E> start = searchVertex(v);
        if (start == null) {
            System.out.println("vertice no encontrado.");
            return;
        }
        // lista de vertices visitados
        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
        // usamos StackLink (pila con lista enlazada)
        StackLink<Vertex<E>> pila = new StackLink<>();
        try {
            pila.push(start); // agregamos el vertice inicial a la pila
            while (!pila.isEmpty()) {
                Vertex<E> actual = pila.pop(); // sacamos el ultimo vertice agregado
                if (visitados.search(actual) == -1) {
                    System.out.print(actual.getData() + " "); // mostramos el dato del vertice
                    visitados.insertLast(actual); // lo marcamos como visitado
                    Nodo<Edge<E>> adyacente = actual.listAdj.getFirst(); // recorremos sus aristas
                    while (adyacente != null) {
                        Vertex<E> destino = adyacente.getData().getRefDest();
                        if (visitados.search(destino) == -1) {
                            pila.push(destino); // si no fue visitado, lo agregamos a la pila
                        }
                        adyacente = adyacente.getNext(); // siguiente arista
                    }
                }
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("error en dfs: " + e.getMessage());
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
