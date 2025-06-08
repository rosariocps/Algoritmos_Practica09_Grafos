package graph;

public class MainTipoGrafo {
    public static void main(String[] args) {
        // creamos un grafo no dirigido de tipo string
        GraphLink<String> grafo = new GraphLink<>();

        // insertamos vertices al grafo: A, B, C, D
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");

        // conectamos los vertices formando un ciclo:
        // A-B-C-D-A → todos tienen grado 2
        grafo.insertEdge("A", "B");
        grafo.insertEdge("B", "C");
        grafo.insertEdge("C", "D");
        grafo.insertEdge("D", "A");

        // mostramos el grado del nodo A
        System.out.println("grado de A: " + TipoGrafoUtils.gradoNodo(grafo, "A"));

        // mostramos si el grafo es un camino (P4)
        System.out.println("¿es camino? " + TipoGrafoUtils.esCamino(grafo));

        // mostramos si el grafo es un ciclo (C4)
        System.out.println("¿es ciclo? " + TipoGrafoUtils.esCiclo(grafo));

        // mostramos si el grafo es una rueda (W4)
        System.out.println("¿es rueda? " + TipoGrafoUtils.esRueda(grafo));

        // mostramos si el grafo es completo (K4)
        System.out.println("¿es completo? " + TipoGrafoUtils.esCompleto(grafo));
    }
}
