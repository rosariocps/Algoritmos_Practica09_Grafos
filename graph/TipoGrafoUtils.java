package graph;

import linkedlist.Nodo;

public class TipoGrafoUtils {

    // metodo para obtener el grado de un nodo
    public static <E> int gradoNodo(GraphLink<E> grafo, E nodo) {
        Vertex<E> v = grafo.searchVertex(nodo); // buscamos el vertice con el dato
        if (v == null) return -1; // si no existe retornamos -1
        int grado = 0; // iniciamos contador
        Nodo<Edge<E>> current = v.listAdj.getFirst(); // obtenemos la primera arista
        while (current != null) { // recorremos la lista de aristas
            grado++; // sumamos 1 por cada arista
            current = current.getNext(); // pasamos al siguiente nodo
        }
        return grado; // retornamos el grado
    }

    // metodo para saber si el grafo es un camino (px)
    public static <E> boolean esCamino(GraphLink<E> grafo) {
        int grado1 = 0, grado2 = 0; // contadores de nodos con grado 1 y 2
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst(); // recorremos los vertices
        while (current != null) {
            int grado = current.getData().listAdj.length(); // contamos las aristas del vertice
            if (grado == 1) grado1++; // si tiene grado 1 sumamos
            else if (grado == 2) grado2++; // si tiene grado 2 sumamos
            else return false; // si no es 1 ni 2, no es camino
            current = current.getNext(); // siguiente vertice
        }
        return grado1 == 2 && grado2 >= 1; // debe haber exactamente 2 nodos con grado 1
    }

    // metodo para saber si es un ciclo (cx)
    public static <E> boolean esCiclo(GraphLink<E> grafo) {
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst(); // recorremos los vertices
        while (current != null) {
            if (current.getData().listAdj.length() != 2)
                return false; // si no tiene exactamente 2 conexiones, no es ciclo
            current = current.getNext(); // siguiente vertice
        }
        return true; // si todos tienen grado 2, es ciclo
    }

    // metodo para saber si es rueda (wx)
    public static <E> boolean esRueda(GraphLink<E> grafo) {
        int n = grafo.listVertex.length(); // cantidad de nodos
        int gradoCentro = 0; // contador para nodo central
        int grado3 = 0; // contador para nodos del ciclo
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst(); // recorremos los vertices
        while (current != null) {
            int grado = current.getData().listAdj.length(); // contamos conexiones
            if (grado == n - 1) gradoCentro++; // si esta conectado con todos
            else if (grado == 3) grado3++; // si esta conectado con 3 (nodo del ciclo)
            else return false; // si no cumple con ninguna, no es rueda
            current = current.getNext(); // siguiente vertice
        }
        return gradoCentro == 1 && grado3 == n - 1; // debe haber 1 centro y el resto ciclo
    }

    // metodo para saber si es completo (kx)
    public static <E> boolean esCompleto(GraphLink<E> grafo) {
        int n = grafo.listVertex.length(); // total de nodos
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst(); // recorremos los vertices
        while (current != null) {
            if (current.getData().listAdj.length() != n - 1)
                return false; // si no esta conectado con todos los demas, no es completo
            current = current.getNext(); // siguiente vertice
        }
        return true; // si todos tienen conexiones completas, es completo
    }
}
