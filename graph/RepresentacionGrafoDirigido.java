package graph;

import actividad2.QueueLink;
import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

public class RepresentacionGrafoDirigido {

    // ==== MÉTODOS DE REPRESENTACIÓN ====

    // FORMA FORMAL: muestra vértices y aristas dirigidas
    public static <E> void mostrarFormal(GraphLink<E> grafo) {
        System.out.print("V = { ");
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst();
        while (current != null) {
            System.out.print(current.getData().getData());
            if (current.getNext() != null) System.out.print(", ");
            current = current.getNext();
        }
        System.out.println(" }");

        System.out.print("E = { ");
        Nodo<Vertex<E>> origen = grafo.listVertex.getFirst();
        boolean primero = true;
        while (origen != null) {
            Nodo<Edge<E>> arista = origen.getData().listAdj.getFirst();
            while (arista != null) {
                if (!primero) System.out.print(", ");
                System.out.print("(" + origen.getData().getData() + " -> " +
                        arista.getData().getRefDest().getData() + ")");
                primero = false;
                arista = arista.getNext();
            }
            origen = origen.getNext();
        }
        System.out.println(" }");
    }

    // LISTA DE ADYACENCIA DIRIGIDA
    public static <E> void mostrarListaAdyacencia(GraphLink<E> grafo) {
        Nodo<Vertex<E>> actual = grafo.listVertex.getFirst();
        while (actual != null) {
            System.out.print(actual.getData().getData() + " -> ");
            Nodo<Edge<E>> arista = actual.getData().listAdj.getFirst();
            while (arista != null) {
                System.out.print(arista.getData().getRefDest().getData());
                if (arista.getNext() != null) System.out.print(" ");
                arista = arista.getNext();
            }
            System.out.println();
            actual = actual.getNext();
        }
    }

    // MATRIZ DE ADYACENCIA DIRIGIDA
    public static <E> void mostrarMatrizAdyacencia(GraphLink<E> grafo) {
        int n = grafo.listVertex.length();
        Vertex<E>[] vertices = new Vertex[n];

        Nodo<Vertex<E>> current = grafo.listVertex.getFirst();
        int i = 0;
        while (current != null) {
            vertices[i++] = current.getData();
            current = current.getNext();
        }

        System.out.print("    ");
        for (i = 0; i < n; i++) {
            System.out.print(vertices[i].getData() + " ");
        }
        System.out.println();

        for (i = 0; i < n; i++) {
            System.out.print(vertices[i].getData() + " | ");
            for (int j = 0; j < n; j++) {
                boolean conectado = false;
                Nodo<Edge<E>> arista = vertices[i].listAdj.getFirst();
                while (arista != null) {
                    if (arista.getData().getRefDest().equals(vertices[j])) {
                        conectado = true;
                        break;
                    }
                    arista = arista.getNext();
                }
                System.out.print((conectado ? "1" : "0") + " ");
            }
            System.out.println();
        }
    }

    // ==== EJERCICIO 9: ANÁLISIS DE GRAFO DIRIGIDO ====

    // CONEXO DIRIGIDO (fuertemente conexo: de todo vértice hay camino hacia todos los demás)
    public static <E> boolean esConexoDirigido(GraphLink<E> grafo) {
        Nodo<Vertex<E>> nodoActual = grafo.listVertex.getFirst();

        while (nodoActual != null) {
            Vertex<E> origen = nodoActual.getData();

            ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
            QueueLink<Vertex<E>> cola = new QueueLink<>();
            cola.enqueue(origen);
            visitados.insertLast(origen);

            while (!cola.isEmpty()) {
                try {
                    Vertex<E> actual = cola.dequeue();
                    Nodo<Edge<E>> arista = actual.listAdj.getFirst();
                    while (arista != null) {
                        Vertex<E> vecino = arista.getData().getRefDest();
                        if (visitados.search(vecino) == -1) {
                            cola.enqueue(vecino);
                            visitados.insertLast(vecino);
                        }
                        arista = arista.getNext();
                    }
                } catch (Exception e) {
                    return false;
                }
            }

            if (visitados.length() < grafo.listVertex.length()) {
                return false;
            }

            nodoActual = nodoActual.getNext();
        }

        return true;
    }

    // PLANO DIRIGIDO (usa la fórmula de Euler modificada: e ≤ 3v - 6)
    public static <E> boolean esPlano(GraphLink<E> grafo) {
        int v = grafo.listVertex.length();
        int e = contarTotalAristas(grafo);
        return e <= 3 * v - 6;
    }

    // Método auxiliar para contar aristas
    private static <E> int contarTotalAristas(GraphLink<E> grafo) {
        int total = 0;
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst();
        while (current != null) {
            total += current.getData().listAdj.length();
            current = current.getNext();
        }
        return total;
    }

    // ISOMORFO (versión simplificada: compara estructura exacta)
    public static <E> boolean esIsomorfo(GraphLink<E> g1, GraphLink<E> g2) {
        if (g1.listVertex.length() != g2.listVertex.length()) return false;
        if (contarTotalAristas(g1) != contarTotalAristas(g2)) return false;

        Nodo<Vertex<E>> nodo1 = g1.listVertex.getFirst();
        Nodo<Vertex<E>> nodo2 = g2.listVertex.getFirst();

        while (nodo1 != null && nodo2 != null) {
            ListaEnlazada<Edge<E>> ady1 = nodo1.getData().listAdj;
            ListaEnlazada<Edge<E>> ady2 = nodo2.getData().listAdj;
            if (ady1.length() != ady2.length()) return false;
            nodo1 = nodo1.getNext();
            nodo2 = nodo2.getNext();
        }

        return true;
    }

    // AUTO COMPLEMENTARIO (el complemento es isomorfo al grafo original)
    public static <E> boolean esAutoComplementario(GraphLink<E> grafo) {
        GraphLink<E> complemento = new GraphLink<>();

        // Clonar vértices
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst();
        while (current != null) {
            complemento.insertVertex(current.getData().getData());
            current = current.getNext();
        }

        // Insertar solo las aristas que NO existen en el grafo original
        Nodo<Vertex<E>> origen = grafo.listVertex.getFirst();
        while (origen != null) {
            Nodo<Vertex<E>> destino = grafo.listVertex.getFirst();
            while (destino != null) {
                E datoOrigen = origen.getData().getData();
                E datoDestino = destino.getData().getData();
                if (!datoOrigen.equals(datoDestino) && !grafo.searchEdge(datoOrigen, datoDestino)) {
                    complemento.insertEdge(datoOrigen, datoDestino);
                }
                destino = destino.getNext();
            }
            origen = origen.getNext();
        }

        return esIsomorfo(grafo, complemento);
    }



}
