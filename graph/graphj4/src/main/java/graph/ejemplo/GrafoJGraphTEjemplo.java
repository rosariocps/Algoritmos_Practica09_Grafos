package graph.ejemplo;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import java.util.List;

public class GrafoJGraphTEjemplo {

    public static void main(String[] args) {
        // creamos un grafo no dirigido con aristas simples (sin pesos ni bucles)
        Graph<String, DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);

        // agregamos vertices (ciudades)
        grafo.addVertex("Arequipa");
        grafo.addVertex("Lima");
        grafo.addVertex("Cusco");
        grafo.addVertex("Tacna");
        grafo.addVertex("Puno");

        // agregamos aristas (conexiones entre ciudades)
        grafo.addEdge("Arequipa", "Lima");
        grafo.addEdge("Arequipa", "Cusco");
        grafo.addEdge("Arequipa", "Tacna");
        grafo.addEdge("Cusco", "Puno");

        // mostramos vertices y aristas
        System.out.println("Vertices del grafo: " + grafo.vertexSet());
        System.out.println("Aristas del grafo: " + grafo.edgeSet());

        // verificar si el grafo es conexo
        ConnectivityInspector<String, DefaultEdge> inspector = new ConnectivityInspector<>(grafo);
        boolean esConexo = inspector.isConnected();
        System.out.println("¿El grafo es conexo? " + esConexo);

        // encontrar el camino más corto de Lima a Puno usando Dijkstra
        DijkstraShortestPath<String, DefaultEdge> dijkstra = new DijkstraShortestPath<>(grafo);
        List<String> camino = dijkstra.getPath("Lima", "Puno").getVertexList();

        System.out.println("Camino mas corto de Lima a Puno: " + camino);
    }
}
