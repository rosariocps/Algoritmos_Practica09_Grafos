package graph;

public class TestGraphListEdge {
    public static void main(String[] args) {
        GraphListEdge<String, Integer> grafo = new GraphListEdge<>();

        // Insertar vértices
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");
        grafo.insertVertex("A"); // repetido

        // Verificar búsqueda de vértices
        System.out.println("\n¿Existe vértice A? " + grafo.searchVertex("A"));
        System.out.println("¿Existe vértice X? " + grafo.searchVertex("X"));

        // Insertar aristas
        grafo.insertEdge("A", "B");
        grafo.insertEdge("A", "C");
        grafo.insertEdge("B", "D");
        grafo.insertEdge("C", "D");
        grafo.insertEdge("A", "B"); // arista repetida

        // Verificar búsqueda de aristas
        System.out.println("\n¿Existe arista A-B? " + grafo.searchEdge("A", "B"));
        System.out.println("¿Existe arista B-C? " + grafo.searchEdge("B", "C"));
        System.out.println("¿Existe arista C-D? " + grafo.searchEdge("C", "D"));
        System.out.println("¿Existe arista A-X? " + grafo.searchEdge("A", "X"));

        // Recorrido BFS desde A
        System.out.println("\nRecorrido BFS desde A:");
        grafo.bfs("A");

        // Recorrido BFS desde vértice inexistente
        System.out.println("\nRecorrido BFS desde X:");
        grafo.bfs("X");
    }
}