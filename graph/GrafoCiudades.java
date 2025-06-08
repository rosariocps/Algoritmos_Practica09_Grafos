import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class GrafoCiudades {
    public static void main(String[] args) {
        // Crear un grafo no dirigido con peso
        Graph<String, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // Agregar vértices
        grafo.addVertex("Arequipa");
        grafo.addVertex("Lima");
        grafo.addVertex("Cusco");
        grafo.addVertex("Puno");

        // Agregar aristas con pesos (distancias en kilómetros)
        grafo.setEdgeWeight(grafo.addEdge("Arequipa", "Lima"), 1013);
        grafo.setEdgeWeight(grafo.addEdge("Arequipa", "Cusco"), 509);
        grafo.setEdgeWeight(grafo.addEdge("Arequipa", "Puno"), 328);
        grafo.setEdgeWeight(grafo.addEdge("Cusco", "Puno"), 389);

        // Mostrar los vértices
        System.out.println("Ciudades en el grafo:");
        for (String ciudad : grafo.vertexSet()) {
            System.out.println("- " + ciudad);
        }

        // Mostrar las aristas con peso
        System.out.println("\nRutas entre ciudades:");
        for (DefaultWeightedEdge edge : grafo.edgeSet()) {
            String origen = grafo.getEdgeSource(edge);
            String destino = grafo.getEdgeTarget(edge);
            double peso = grafo.getEdgeWeight(edge);
            System.out.println(origen + " <-> " + destino + " : " + peso + " km");
        }
    }
}
