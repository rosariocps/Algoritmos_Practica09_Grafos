package graph;

import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

public class TestGraph {
    public static void main(String[] args) {
        GraphLink<String> grafo = new GraphLink<>();

        //insertar vértices
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");

        //insertar aristas
        grafo.insertEdge("A", "B");
        grafo.insertEdge("A", "C");
        grafo.insertEdge("B", "D");
        grafo.insertEdge("C", "D");

        //mostrar el grafo
        System.out.println("Grafo actual:");
        System.out.println(grafo);

        //buscar aristas
        System.out.println("¿Existe arista A-B? " + grafo.searchEdge("A", "B"));
        System.out.println("¿Existe arista B-C? " + grafo.searchEdge("B", "C"));
        System.out.println("¿Existe arista C-D? " + grafo.searchEdge("C", "D"));

        //prueba DFS
        System.out.print("DFS desde A: ");
        grafo.dfs("A");
        System.out.println();

        System.out.println("\nEliminando arista A-B...");
        grafo.removeEdge("A", "B");
        System.out.println(grafo);

        System.out.println("Eliminando vértice C...");
        grafo.removeVertex("C");
        System.out.println(grafo);

        System.out.print("DFS desde A después de eliminar C: ");
        grafo.dfs("A");
        System.out.println();

        GraphLink<String> grafo2 = new GraphLink<>();

        grafo2.insertVertex("1");
        grafo2.insertVertex("2");
        grafo2.insertVertex("3");
        grafo2.insertVertex("6");
        grafo2.insertVertex("7");
        grafo2.insertVertex("8");

        grafo2.insertEdge("1", "2");
        grafo2.insertEdge("1", "3");
        grafo2.insertEdge("2", "6");
        grafo2.insertEdge("2", "7");
        grafo2.insertEdge("3", "7");
        grafo2.insertEdge("7", "8");

        System.out.println("Estructura del grafo:");
        System.out.println(grafo2);

        System.out.print("Recorrido BFS desde 1: ");
        grafo2.bfs("1");


        GraphLink<String> grafo3 = new GraphLink<>();

        grafo3.insertVertex("1");
        grafo3.insertVertex("2");
        grafo3.insertVertex("3");
        grafo3.insertVertex("4");
        grafo3.insertVertex("5");
        grafo3.insertVertex("6");

        grafo3.insertEdge("1", "2");
        grafo3.insertEdge("1", "5");
        grafo3.insertEdge("2", "3");
        grafo3.insertEdge("2", "5");
        grafo3.insertEdge("3", "4");
        grafo3.insertEdge("4", "5");
        grafo3.insertEdge("4", "6");

        // Mostrar la estructura del grafo
        System.out.println("Estructura del grafo:");
        System.out.println(grafo3);

        // Buscar y mostrar el camino más corto de 1 a 6 usando bfsPath
        System.out.println("\nCamino más corto de 1 a 6:");

        ListaEnlazada<String> camino = grafo3.bfsPath("1", "6");

        // Mostrar camino con flechas (→)
        Nodo<String> actual = camino.getFirst();
        while (actual != null) {
            System.out.print(actual.getData());
            if (actual.getNext() != null) {
                System.out.print(" --> ");
            }
            actual = actual.getNext();
        }
        System.out.println(); // Salto de línea final
    }
}
