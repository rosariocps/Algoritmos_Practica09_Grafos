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

    // metodo para mostrar definicion formal del grafo
    public static <E> void mostrarFormal(GraphLink<E> grafo) {
        System.out.println("definicion formal:");
        System.out.print("v = { ");
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst(); // recorremos vertices
        while (current != null) {
            System.out.print(current.getData().getData() + " ");
            current = current.getNext();
        }
        System.out.println("}");
        System.out.print("e = { ");
        Nodo<Vertex<E>> vActual = grafo.listVertex.getFirst(); // recorremos de nuevo
        while (vActual != null) {
            Nodo<Edge<E>> arista = vActual.getData().listAdj.getFirst(); // obtenemos aristas
            while (arista != null) {
                E origen = vActual.getData().getData();
                E destino = arista.getData().getRefDest().getData();
                // evitamos repetir aristas (como es grafo no dirigido)
                if (origen.toString().compareTo(destino.toString()) < 0) {
                    System.out.print("(" + origen + "," + destino + ") ");
                }
                arista = arista.getNext(); // siguiente arista
            }
            vActual = vActual.getNext(); // siguiente vertice
        }
        System.out.println("}");
    }

    // metodo para mostrar la lista de adyacencia
    public static <E> void mostrarListaAdyacencia(GraphLink<E> grafo) {
        System.out.println("lista de adyacencia:"); // imprimimos un titulo

        Nodo<Vertex<E>> current = grafo.listVertex.getFirst(); // obtenemos el primer vertice del grafo

        while (current != null) { // recorremos todos los vertices uno por uno
            System.out.print(current.getData().getData() + " -> "); // imprimimos el nombre del vertice actual seguido de una flecha

            Nodo<Edge<E>> arista = current.getData().listAdj.getFirst(); // obtenemos la primera arista (conexion) de este vertice

            while (arista != null) { // recorremos todas las aristas de este vertice
                System.out.print(arista.getData().getRefDest().getData() + " "); // imprimimos el nombre del vertice al que esta conectado
                arista = arista.getNext(); // pasamos a la siguiente arista
            }

            System.out.println(); // salto de linea despues de mostrar todas las conexiones del vertice actual
            current = current.getNext(); // pasamos al siguiente vertice en la lista
        }
    }


    // metodo para mostrar la matriz de adyacencia
    public static <E> void mostrarMatrizAdyacencia(GraphLink<E> grafo) {
        int n = grafo.listVertex.length(); // cantidad de vertices
        Vertex<E>[] vertices = new Vertex[n]; // arreglo para guardar los vertices

        // copiamos los vertices en un arreglo para facilitar el acceso por posicion
        Nodo<Vertex<E>> current = grafo.listVertex.getFirst(); // obtenemos el primer nodo de la lista de vertices
        int i = 0; // iniciamos el indice en 0
        while (current != null) { // mientras haya nodos en la lista
            vertices[i++] = current.getData(); // guardamos el vertice en el arreglo y avanzamos el indice
            current = current.getNext(); // pasamos al siguiente nodo de la lista
        }

        System.out.println("matriz de adyacencia:"); // imprimimos el titulo
        System.out.print("    "); // espacio en blanco para alinear con la primera columna

        // imprimimos los nombres de los vertices en la primera fila (encabezado de columnas)
        for (i = 0; i < n; i++) {
            System.out.print(vertices[i].getData() + " "); // imprimimos el nombre del vertice
        }
        System.out.println(); // salto de linea

        // recorremos cada fila de la matriz
        for (i = 0; i < n; i++) {
            System.out.print(vertices[i].getData() + " "); // imprimimos el nombre del vertice en el lado izquierdo (fila)

            // recorremos cada columna de la fila
            for (int j = 0; j < n; j++) {
                boolean conectado = false; // asumimos que no hay conexion entre i y j

                // recorremos la lista de aristas del vertice en la fila actual
                Nodo<Edge<E>> arista = vertices[i].listAdj.getFirst();
                while (arista != null) { // mientras haya aristas
                    // si encontramos una arista que conecta con el vertice de la columna
                    if (arista.getData().getRefDest().equals(vertices[j])) {
                        conectado = true; // marcamos que si estan conectados
                        break; // salimos del bucle porque ya encontramos la conexion
                    }
                    arista = arista.getNext(); // pasamos a la siguiente arista
                }

                // imprimimos 1 si estan conectados, 0 si no lo estan
                System.out.print("  " + (conectado ? "1" : "0") + " ");
            }
            System.out.println(); // salto de linea para la siguiente fila
        }

    }
}
