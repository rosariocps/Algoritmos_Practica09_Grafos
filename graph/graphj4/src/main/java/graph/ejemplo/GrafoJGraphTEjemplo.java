package graph.ejemplo; // paquete donde esta mi clase

// importo las clases necesarias de jgrapht
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

import java.util.List; // para trabajar con listas

public class GrafoJGraphTEjemplo {

    public static void main(String[] args) {

        // creo un grafo no dirigido y con pesos, usando aristas de tipo DefaultWeightedEdge
        Graph<String, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // agrego los vertices, que en este caso son ciudades
        grafo.addVertex("Arequipa");
        grafo.addVertex("Lima");
        grafo.addVertex("Cusco");
        grafo.addVertex("Tacna");
        grafo.addVertex("Puno");

        // agrego las conexiones entre ciudades, y a cada conexion le asigno un peso (distancia en km por ejemplo)
        grafo.setEdgeWeight(grafo.addEdge("Arequipa", "Lima"), 14.0);
        grafo.setEdgeWeight(grafo.addEdge("Arequipa", "Cusco"), 6.0);
        grafo.setEdgeWeight(grafo.addEdge("Arequipa", "Tacna"), 10.0);
        grafo.setEdgeWeight(grafo.addEdge("Cusco", "Puno"), 4.0);

        // muestro todos los vertices del grafo
        System.out.println("Vértices del grafo: " + grafo.vertexSet());

        // muestro todas las aristas del grafo junto con su peso
        System.out.println("Aristas del grafo:");
        for (DefaultWeightedEdge e : grafo.edgeSet()) {
            // obtengo el vertice de origen de la arista
            String origen = grafo.getEdgeSource(e);
            // obtengo el vertice de destino de la arista
            String destino = grafo.getEdgeTarget(e);
            // obtengo el peso de esa arista
            double peso = grafo.getEdgeWeight(e);
            // imprimo la conexion entre origen y destino con su peso
            System.out.println(origen + " <--> " + destino + " (peso: " + peso + ")");
        }

        // creo un inspector para verificar si el grafo es conexo (es decir, si todos los nodos estan conectados entre si)
        ConnectivityInspector<String, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(grafo);
        boolean esConexo = inspector.isConnected();
        // imprimo si es conexo o no
        System.out.println("¿El grafo es conexo? " + esConexo);

        // creo un objeto para usar el algoritmo de dijkstra y encontrar el camino mas corto
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(grafo);
        var camino = dijkstra.getPath("Lima", "Puno"); // busco el camino de lima a puno

        // si el camino existe, lo muestro
        if (camino != null) {
            // obtengo la lista de ciudades que forman el camino
            List<String> vertices = camino.getVertexList();
            // obtengo la distancia total del camino
            double distancia = camino.getWeight();

            // muestro el camino y la distancia total
            System.out.println("Camino más corto de Lima a Puno: " + vertices);
            System.out.println("Distancia total: " + distancia);
        } else {
            // si no hay camino posible, lo indico
            System.out.println("No existe un camino entre Lima y Puno.");
        }
    }
}
