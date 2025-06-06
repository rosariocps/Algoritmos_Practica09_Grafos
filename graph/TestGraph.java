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
    }
}
