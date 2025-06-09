package graph;

import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

public class TestGraph {
    public static void main(String[] args) {
        // === PRUEBAS GENERALES DEL GRAFO ===
        GraphLink<String> grafo = new GraphLink<>();

        // insertar vertices
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");

        // insertar aristas
        grafo.insertEdge("A", "B");
        grafo.insertEdge("A", "C");
        grafo.insertEdge("B", "D");
        grafo.insertEdge("C", "D");

        // mostrar grafo
        System.out.println("Grafo actual:");
        System.out.println(grafo);

        // buscar aristas
        System.out.println("¿Existe arista A-B? " + grafo.searchEdge("A", "B"));
        System.out.println("¿Existe arista B-C? " + grafo.searchEdge("B", "C"));
        System.out.println("¿Existe arista C-D? " + grafo.searchEdge("C", "D"));

        // prueba DFS
        System.out.print("DFS desde A: ");
        grafo.dfs("A");
        System.out.println();

        // eliminar aristas y vertices
        System.out.println("\nEliminando arista A-B...");
        grafo.removeEdge("A", "B");
        System.out.println(grafo);

        System.out.println("Eliminando vértice C...");
        grafo.removeVertex("C");
        System.out.println(grafo);

        System.out.print("DFS desde A después de eliminar C: ");
        grafo.dfs("A");
        System.out.println();

        // === PRUEBA DE BFS ===
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

        // PRUEBA DE BFS CON CAMINO MÁS CORTO
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

        System.out.println("Estructura del grafo:");
        System.out.println(grafo3);

        // buscar camino mas corto de 1 a 6
        System.out.println("\nCamino más corto de 1 a 6:");
        ListaEnlazada<String> camino = grafo3.bfsPath("1", "6");

        Nodo<String> actual = camino.getFirst();
        while (actual != null) {
            System.out.print(actual.getData());
            if (actual.getNext() != null) {
                System.out.print(" --> ");
            }
            actual = actual.getNext();
        }
        System.out.println();

        // PRUEBA DE MÉTODO SHORTPATH
        System.out.println("\nPRUEBA DE MÉTODO SHORTPATH");
        GraphLink<String> grafo4 = new GraphLink<>();
        grafo4.insertVertex("A");
        grafo4.insertVertex("B");
        grafo4.insertVertex("C");
        grafo4.insertVertex("D");
        grafo4.insertVertex("E");

        grafo4.insertEdgeWeight("A", "B", 4);
        grafo4.insertEdgeWeight("A", "C", 2);
        grafo4.insertEdgeWeight("B", "C", 1);
        grafo4.insertEdgeWeight("B", "D", 5);
        grafo4.insertEdgeWeight("C", "D", 8);
        grafo4.insertEdgeWeight("C", "E", 10);
        grafo4.insertEdgeWeight("D", "E", 2);

        System.out.println("\nEstructura del grafo ponderado:");
        System.out.println(grafo4);

        System.out.println("Camino más corto de A a E:");
        ListaEnlazada<String> camino2 = grafo4.shortPath("A", "E");

        Nodo<String> nodoCamino = camino2.getFirst();
        while (nodoCamino != null) {
            System.out.print(nodoCamino.getData());
            if (nodoCamino.getNext() != null) {
                System.out.print(" --> ");
            }
            nodoCamino = nodoCamino.getNext();
        }
        System.out.println();


        // === EJERCICIO 5: TIPO DE GRAFO ===
        System.out.println("\n=== EJERCICIO 5: TIPO DE GRAFO ===");
        GraphLink<String> grafoTipo = new GraphLink<>();
        grafoTipo.insertVertex("A");
        grafoTipo.insertVertex("B");
        grafoTipo.insertVertex("C");
        grafoTipo.insertVertex("D");
        grafoTipo.insertEdge("A", "B");
        grafoTipo.insertEdge("B", "C");
        grafoTipo.insertEdge("C", "D");
        grafoTipo.insertEdge("D", "A"); // esto forma un ciclo (C4)
 
        System.out.println("Grado de A: " + TipoGrafoUtils.gradoNodo(grafoTipo, "A"));
        System.out.println("¿Es Camino? " + TipoGrafoUtils.esCamino(grafoTipo));
        System.out.println("¿Es Ciclo? " + TipoGrafoUtils.esCiclo(grafoTipo));
        System.out.println("¿Es Rueda? " + TipoGrafoUtils.esRueda(grafoTipo));
        System.out.println("¿Es Completo? " + TipoGrafoUtils.esCompleto(grafoTipo));

        // === EJERCICIO 6: FORMAS DE REPRESENTACION ===
        System.out.println("\n=== EJERCICIO 6: FORMAS DE REPRESENTACION ===");

        GraphLink<String> grafoRep = new GraphLink<>();
        grafoRep.insertVertex("X");
        grafoRep.insertVertex("Y");
        grafoRep.insertVertex("Z");

        grafoRep.insertEdge("X", "Y");
        grafoRep.insertEdge("Y", "Z");

        // a) forma formal
        System.out.println("\n- Forma formal:");
        TipoGrafoUtils.mostrarFormal(grafoRep);

        // b) lista de adyacencias
        System.out.println("\n- Lista de adyacencia:");
        TipoGrafoUtils.mostrarListaAdyacencia(grafoRep);

        // c) matriz de adyacencia
        System.out.println("\n- Matriz de adyacencia:");
        TipoGrafoUtils.mostrarMatrizAdyacencia(grafoRep);


        // === EJERCICIO 7: GRAFO DIRIGIDO - GRADOS Y TIPO ===
        System.out.println("\n=== EJERCICIO 7: GRAFO DIRIGIDO ===");

        GraphLink<String> grafoDir = new GraphLink<>();
        grafoDir.insertVertex("A");
        grafoDir.insertVertex("B");
        grafoDir.insertVertex("C");
        grafoDir.insertVertex("D");

        // Grafo dirigido tipo ciclo A → B → C → D → A
        grafoDir.insertEdge("A", "B");
        grafoDir.insertEdge("B", "C");
        grafoDir.insertEdge("C", "D");
        grafoDir.insertEdge("D", "A");

        // Mostrar grados de entrada y salida
        for (String v : new String[]{"A", "B", "C", "D"}) {
            int entrada = TipoGrafoUtils.gradoEntrada(grafoDir, v);
            int salida = TipoGrafoUtils.gradoSalida(grafoDir, v);
            System.out.println("Vértice " + v + ": entrada=" + entrada + ", salida=" + salida);
        }

        // Verificar tipo de grafo dirigido
        System.out.println("\n¿Es Camino Dirigido? " + TipoGrafoUtils.esCaminoDirigido(grafoDir));
        System.out.println("¿Es Ciclo Dirigido? " + TipoGrafoUtils.esCicloDirigido(grafoDir));
        System.out.println("¿Es Rueda Dirigida? " + TipoGrafoUtils.esRuedaDirigida(grafoDir));

    }

}
