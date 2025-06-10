package graph;

import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

public class TipoGrafoUtils {

    // ------------ EJERCICIO 5: GRAFO NO DIRIGIDO ------------
    //IDENTIFICACIÓN DE TIPOS (Grado, Camino, Ciclo, Rueda, Completo)

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

    // ------------ EJERCICIO 6: GRAFO NO DIRIGIDO ------------
    // REPRESENTACIÓN FORMAL, LISTA DE ADYACENCIA Y MATRIZ DE ADYACENCIA

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

    // ------------ EJERCICIO 7: GRAFO DIRIGIDO ------------
    // IDENTIFICACIÓN DE TIPOS (Grado, Camino, Ciclo, Rueda) USANDO GraphLink

    // GRADO DE ENTRADA (cuántas aristas llegan al vértice v)
    public static <E> int gradoEntrada(GraphLink<E> grafo, E v) {
        int grado = 0;
        Vertex<E> destino = grafo.searchVertex(v);
        if (destino == null) return -1;

        Nodo<Vertex<E>> actual = grafo.listVertex.getFirst();
        while (actual != null) {
            Nodo<Edge<E>> arista = actual.getData().listAdj.getFirst();
            while (arista != null) {
                if (arista.getData().getRefDest().equals(destino)) {
                    grado++;
                }
                arista = arista.getNext();
            }
            actual = actual.getNext();
        }

        return grado;
    }

    // GRADO DE SALIDA (cuántas aristas salen del vértice v)
    public static <E> int gradoSalida(GraphLink<E> grafo, E v) {
        Vertex<E> origen = grafo.searchVertex(v);
        if (origen == null) return -1;
        return origen.listAdj.length();
    }

    // CAMINO DIRIGIDO (A -> B -> C ->  ... -> Z, sin ciclos)
    public static <E> boolean esCaminoDirigido(GraphLink<E> grafo) {
        int gradoEntrada1 = 0;
        int gradoSalida1 = 0;

        Nodo<Vertex<E>> actual = grafo.listVertex.getFirst();
        while (actual != null) {
            E dato = actual.getData().getData();
            int entrada = gradoEntrada(grafo, dato);
            int salida = gradoSalida(grafo, dato);

            if (entrada == 0 && salida == 1) {
                gradoEntrada1++; // nodo de inicio
            } else if (entrada == 1 && salida == 0) {
                gradoSalida1++; // nodo de fin
            } else if (!(entrada == 1 && salida == 1)) {
                return false; // nodo intermedio no cumple
            }

            actual = actual.getNext();
        }

        return gradoEntrada1 == 1 && gradoSalida1 == 1;
    }

    // CICLO DIRIGIDO (cada nodo tiene entrada y salida 1)
    public static <E> boolean esCicloDirigido(GraphLink<E> grafo) {
        Nodo<Vertex<E>> actual = grafo.listVertex.getFirst();
        while (actual != null) {
            E dato = actual.getData().getData();
            if (gradoEntrada(grafo, dato) != 1 || gradoSalida(grafo, dato) != 1) {
                return false;
            }
            actual = actual.getNext();
        }
        return true;
    }

    // RUEDA DIRIGIDA (centro → todos los demás, y los demás conectados entre sí en ciclo)
    public static <E> boolean esRuedaDirigida(GraphLink<E> grafo) {
        int n = grafo.listVertex.length();
        int centro = 0;
        int ciclo = 0;

        Nodo<Vertex<E>> actual = grafo.listVertex.getFirst();
        while (actual != null) {
            E dato = actual.getData().getData();
            int entrada = gradoEntrada(grafo, dato);
            int salida = gradoSalida(grafo, dato);

            if (salida == n - 1 && entrada == 0) {
                centro++; // nodo central
            } else if (entrada == 2 && salida == 2) {
                ciclo++; // nodo del anillo
            } else {
                return false;
            }

            actual = actual.getNext();
        }

        return centro == 1 && ciclo == n - 1;
    }

    // ------------ EJERCICIO 7: GRAFO DIRIGIDO ------------
    // IDENTIFICACIÓN DE TIPOS (Grado, Camino, Ciclo, Rueda) USANDO GraphListEdgeListaEnlazada

    // GRADO DE ENTRADA: cuántas aristas llegan al vértice v
    public static <V, E> int gradoEntrada(GraphListEdgeListaEnlazada<V, E> grafo, V v) {
        int grado = 0;

        Nodo<EdgeObj<V, E>> actual = grafo.secEdge.getFirst();
        while (actual != null) {
            EdgeObj<V, E> arista = actual.getData();
            if (arista.endVertex2.info.equals(v)) {
                grado++;
            }
            actual = actual.getNext();
        }

        return grado;
    }

    // GRADO DE SALIDA: cuántas aristas salen del vértice v
    public static <V, E> int gradoSalida(GraphListEdgeListaEnlazada<V, E> grafo, V v) {
        int grado = 0;

        Nodo<EdgeObj<V, E>> actual = grafo.secEdge.getFirst();
        while (actual != null) {
            EdgeObj<V, E> arista = actual.getData();
            if (arista.endVertex1.info.equals(v)) {
                grado++;
            }
            actual = actual.getNext();
        }

        return grado;
    }

    // CAMINO DIRIGIDO: un nodo con entrada=0 y salida=1 (inicio),
    // otro con entrada=1 y salida=0 (fin), el resto con entrada=1 y salida=1
    public static <V, E> boolean esCaminoDirigido(GraphListEdgeListaEnlazada<V, E> grafo) {
        int gradoEntrada1 = 0;
        int gradoSalida1 = 0;

        Nodo<VertexObj<V, E>> actual = grafo.secVertex.getFirst();
        while (actual != null) {
            V dato = actual.getData().info;
            int entrada = gradoEntrada(grafo, dato);
            int salida = gradoSalida(grafo, dato);

            if (entrada == 0 && salida == 1) {
                gradoEntrada1++; // nodo de inicio
            } else if (entrada == 1 && salida == 0) {
                gradoSalida1++; // nodo de fin
            } else if (!(entrada == 1 && salida == 1)) {
                return false; // nodo intermedio no cumple
            }

            actual = actual.getNext();
        }

        return gradoEntrada1 == 1 && gradoSalida1 == 1;
    }

    // CICLO DIRIGIDO: todos los nodos con entrada=1 y salida=1
    public static <V, E> boolean esCicloDirigido(GraphListEdgeListaEnlazada<V, E> grafo) {
        Nodo<VertexObj<V, E>> actual = grafo.secVertex.getFirst();
        while (actual != null) {
            V dato = actual.getData().info;
            if (gradoEntrada(grafo, dato) != 1 || gradoSalida(grafo, dato) != 1) {
                return false;
            }
            actual = actual.getNext();
        }
        return true;
    }

    // RUEDA DIRIGIDA: un nodo con salida=n-1 y entrada=0 (centro),
    // y n-1 nodos con entrada=2 y salida=2 (círculo)
    public static <V, E> boolean esRuedaDirigida(GraphListEdgeListaEnlazada<V, E> grafo) {
        int n = grafo.secVertex.length();
        int centro = 0;
        int ciclo = 0;

        Nodo<VertexObj<V, E>> actual = grafo.secVertex.getFirst();
        while (actual != null) {
            V dato = actual.getData().info;
            int entrada = gradoEntrada(grafo, dato);
            int salida = gradoSalida(grafo, dato);

            if (salida == n - 1 && entrada == 0) {
                centro++; // nodo central
            } else if (entrada == 2 && salida == 2) {
                ciclo++; // nodo del anillo
            } else {
                return false;
            }

            actual = actual.getNext();
        }

        return centro == 1 && ciclo == n - 1;
    }

    // ------------ EJERCICIO 8: GRAFO NO DIRIGIDO ------------
    // REPRESENTACIÓN FORMAL, LISTA DE ADYACENCIA Y MATRIZ DE ADYACENCIA USANDO GraphListEdgeListaEnlazada

    // FORMA FORMAL: muestra vértices y aristas no dirigidas
    public static <V, E> void mostrarFormal(GraphListEdgeListaEnlazada<V, E> grafo) {
        System.out.println("definicion formal:");
        
        // Mostrar vértices
        System.out.print("v = { ");
        Nodo<VertexObj<V, E>> current = grafo.secVertex.getFirst();
        while (current != null) {
            System.out.print(current.getData().info + " ");
            current = current.getNext();
        }
        System.out.println("}");

        // Mostrar aristas (no dirigidas, evitar duplicados)
        System.out.print("e = { ");
        Nodo<EdgeObj<V, E>> edge = grafo.secEdge.getFirst();
        while (edge != null) {
            V v1 = edge.getData().endVertex1.info;
            V v2 = edge.getData().endVertex2.info;

            // Solo mostrar una vez (por orden alfabético / numérico)
            if (v1.toString().compareTo(v2.toString()) < 0) {
                System.out.print("(" + v1 + "," + v2 + ") ");
            }
            edge = edge.getNext();
        }
        System.out.println("}");
    }

    // LISTA DE ADYACENCIA: muestra cada vértice con sus vecinos
    public static <V, E> void mostrarListaAdyacencia(GraphListEdgeListaEnlazada<V, E> grafo) {
        System.out.println("lista de adyacencia:");
        Nodo<VertexObj<V, E>> nodoV = grafo.secVertex.getFirst();

        while (nodoV != null) {
            V actual = nodoV.getData().info;
            System.out.print(actual + " -> ");

            Nodo<EdgeObj<V, E>> nodoE = grafo.secEdge.getFirst();
            while (nodoE != null) {
                EdgeObj<V, E> arista = nodoE.getData();
                if (arista.endVertex1.info.equals(actual)) {
                    System.out.print(arista.endVertex2.info + " ");
                } else if (arista.endVertex2.info.equals(actual)) {
                    System.out.print(arista.endVertex1.info + " ");
                }
                nodoE = nodoE.getNext();
            }

            System.out.println();
            nodoV = nodoV.getNext();
        }
    }

    // MATRIZ DE ADYACENCIA: matriz simétrica de conexiones 0 o 1
    public static <V, E> void mostrarMatrizAdyacencia(GraphListEdgeListaEnlazada<V, E> grafo) {
        int n = grafo.secVertex.length();
        VertexObj<V, E>[] vertices = new VertexObj[n];

        // Guardar vértices en array
        Nodo<VertexObj<V, E>> current = grafo.secVertex.getFirst();
        int i = 0;
        while (current != null) {
            vertices[i++] = current.getData();
            current = current.getNext();
        }

        // Encabezado
        System.out.println("matriz de adyacencia:");
        System.out.print("    ");
        for (i = 0; i < n; i++) {
            System.out.print(vertices[i].info + " ");
        }
        System.out.println();

        // Contenido
        for (i = 0; i < n; i++) {
            System.out.print(vertices[i].info + " ");
            for (int j = 0; j < n; j++) {
                boolean conectado = false;

                Nodo<EdgeObj<V, E>> arista = grafo.secEdge.getFirst();
                while (arista != null) {
                    V a = arista.getData().endVertex1.info;
                    V b = arista.getData().endVertex2.info;

                    if ((a.equals(vertices[i].info) && b.equals(vertices[j].info)) ||
                        (a.equals(vertices[j].info) && b.equals(vertices[i].info))) {
                        conectado = true;
                        break;
                    }
                    arista = arista.getNext();
                }

                System.out.print("  " + (conectado ? "1" : "0") + " ");
            }
            System.out.println();
        }
    }

    // ------------ EJERCICIO 9: GRAFO DIRIGIDO - GraphLink ------------

    // ISOMORFO: compara dos grafos si tienen mismos vértices y conexiones (por nombre)
    public static <E> boolean isIsomorfo(GraphLink<E> g1, GraphLink<E> g2) {
        if (g1.listVertex.length() != g2.listVertex.length()) return false;
        
        Nodo<Vertex<E>> v1 = g1.listVertex.getFirst();
        while (v1 != null) {
            if (g2.searchVertex(v1.getData().getData()) == null) return false;
            v1 = v1.getNext();
        }

        Nodo<Vertex<E>> u1 = g1.listVertex.getFirst();
        while (u1 != null) {
            E dato = u1.getData().getData();
            Nodo<Edge<E>> a1 = u1.getData().listAdj.getFirst();
            while (a1 != null) {
                E destino = a1.getData().getRefDest().getData();
                if (!g2.searchEdge(dato, destino)) return false;
                a1 = a1.getNext();
            }
            u1 = u1.getNext();
        }

        return true;
    }

    // PLANO: heurística usando fórmula de Kuratowski para grafos dirigidos
    // Si m <= 3n - 6 (n >= 3), se asume plano (simplificación)
    public static <E> boolean esPlano(GraphLink<E> grafo) {
        int n = grafo.listVertex.length();
        int m = 0;
        Nodo<Vertex<E>> actual = grafo.listVertex.getFirst();
        while (actual != null) {
            m += actual.getData().listAdj.length();
            actual = actual.getNext();
        }

        return n < 3 || m <= 3 * n - 6;
    }

    // CONEXO: todos los vértices deben ser alcanzables desde cualquiera (DFS desde el primero)
    public static <E> boolean esConexo(GraphLink<E> grafo) {
        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
        Vertex<E> inicio = grafo.listVertex.getFirst().getData();
        dfsVisit(grafo, inicio, visitados);

        return visitados.length() == grafo.listVertex.length();
    }

    private static <E> void dfsVisit(GraphLink<E> grafo, Vertex<E> v, ListaEnlazada<Vertex<E>> visitados) {
        visitados.insertLast(v);
        Nodo<Edge<E>> arista = v.listAdj.getFirst();
        while (arista != null) {
            Vertex<E> vecino = arista.getData().getRefDest();
            if (visitados.search(vecino) == -1) {
                dfsVisit(grafo, vecino, visitados);
            }
            arista = arista.getNext();
        }
    }

    // AUTO COMPLEMENTARIO: eliminar todas las aristas, insertar las que faltan, comparar isomorfía
    public static <E> boolean esAutoComplementario(GraphLink<E> grafo) {
        GraphLink<E> complementario = new GraphLink<>();

        // copiar vértices
        Nodo<Vertex<E>> v = grafo.listVertex.getFirst();
        while (v != null) {
            complementario.insertVertex(v.getData().getData());
            v = v.getNext();
        }

        // insertar aristas que NO están en el grafo original
        Nodo<Vertex<E>> u = grafo.listVertex.getFirst();
        while (u != null) {
            Nodo<Vertex<E>> w = grafo.listVertex.getFirst();
            while (w != null) {
                E a = u.getData().getData();
                E b = w.getData().getData();
                if (!a.equals(b) && !grafo.searchEdge(a, b)) {
                    complementario.insertEdge(a, b);
                }
                w = w.getNext();
            }
            u = u.getNext();
        }

        return isIsomorfo(grafo, complementario);
    }

    // ------------ EJERCICIO 9: GRAFO DIRIGIDO - GraphListEdgeListaEnlazada ------------

    // ISOMORFO: compara dos grafos si tienen mismos vértices y conexiones (por nombre)
    public static <V, E> boolean isIsomorfo(GraphListEdgeListaEnlazada<V, E> g1, GraphListEdgeListaEnlazada<V, E> g2) {
        if (g1.secVertex.length() != g2.secVertex.length() || g1.secEdge.length() != g2.secEdge.length())
            return false;

        Nodo<VertexObj<V, E>> v1 = g1.secVertex.getFirst();
        while (v1 != null) {
            if (!g2.searchVertex(v1.getData().info)) return false;
            v1 = v1.getNext();
        }

        Nodo<EdgeObj<V, E>> a1 = g1.secEdge.getFirst();
        while (a1 != null) {
            V from = a1.getData().endVertex1.info;
            V to = a1.getData().endVertex2.info;
            if (!g2.searchEdge(from, to)) return false;
            a1 = a1.getNext();
        }

        return true;
    }

    // PLANO: usa fórmula m <= 3n - 6 (n >= 3), se asume plano si se cumple
    public static <V, E> boolean esPlano(GraphListEdgeListaEnlazada<V, E> grafo) {
        int n = grafo.secVertex.length();
        int m = grafo.secEdge.length();
        return n < 3 || m <= 3 * n - 6;
    }

    // CONEXO: todos los vértices deben ser alcanzables desde el primero (DFS)
    public static <V, E> boolean esConexo(GraphListEdgeListaEnlazada<V, E> grafo) {
        ListaEnlazada<VertexObj<V, E>> visitados = new ListaEnlazada<>();
        VertexObj<V, E> inicio = grafo.secVertex.getFirst().getData();
        dfsVisit(grafo, inicio, visitados);
        return visitados.length() == grafo.secVertex.length();
    }

    private static <V, E> void dfsVisit(GraphListEdgeListaEnlazada<V, E> grafo, VertexObj<V, E> v, ListaEnlazada<VertexObj<V, E>> visitados) {
        visitados.insertLast(v);

        Nodo<EdgeObj<V, E>> arista = grafo.secEdge.getFirst();
        while (arista != null) {
            EdgeObj<V, E> e = arista.getData();
            VertexObj<V, E> vecino = null;

            if (e.endVertex1.equals(v)) {
                vecino = e.endVertex2;
            }

            if (vecino != null && visitados.search(vecino) == -1) {
                dfsVisit(grafo, vecino, visitados);
            }

            arista = arista.getNext();
        }
    }

    // AUTO COMPLEMENTARIO: construir grafo complementario y comparar isomorfía
    public static <V, E> boolean esAutoComplementario(GraphListEdgeListaEnlazada<V, E> grafo) {
        GraphListEdgeListaEnlazada<V, E> complementario = new GraphListEdgeListaEnlazada<>();

        // Copiar vértices
        Nodo<VertexObj<V, E>> v = grafo.secVertex.getFirst();
        while (v != null) {
            complementario.insertVertex(v.getData().info);
            v = v.getNext();
        }

        // Insertar solo las aristas que NO están
        Nodo<VertexObj<V, E>> u = grafo.secVertex.getFirst();
        while (u != null) {
            Nodo<VertexObj<V, E>> w = grafo.secVertex.getFirst();
            while (w != null) {
                V a = u.getData().info;
                V b = w.getData().info;

                if (!a.equals(b) && !grafo.searchEdge(a, b)) {
                    complementario.insertEdge(a, b);
                }
                w = w.getNext();
            }
            u = u.getNext();
        }

        return isIsomorfo(grafo, complementario);
    }

}
