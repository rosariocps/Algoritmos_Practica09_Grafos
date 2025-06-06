package graph;

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
    }
}
