package graph;

public class TestGraph {
    public static void main(String[] args) {
        GraphLink<String> grafo = new GraphLink<>();
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");

        grafo.insertEdge("A", "B");
        grafo.insertEdge("A", "C");

        System.out.println(grafo);

        System.out.println("¿Existe arista A-B? " + grafo.searchEdge("A", "B"));
        System.out.println("¿Existe arista B-C? " + grafo.searchEdge("B", "C"));
    }
}
