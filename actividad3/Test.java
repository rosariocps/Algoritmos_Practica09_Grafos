package actividad3;

import actividad1.ExceptionIsEmpty;

public class Test {
    public static void main(String[] args) {

        System.out.println("Cola de prioridad: Nombres (String) con prioridad (Integer)");

        // Creamos una cola de prioridad para Strings, con prioridad tipo Integer
        PriorityQueue<String, Integer> colaNombres = new PriorityQueueLinkSort<>();

        // Insertamos elementos con distintas prioridades
        colaNombres.enqueue("Ana", 3);
        colaNombres.enqueue("Luis", 1);
        colaNombres.enqueue("Carlos", 2);
        colaNombres.enqueue("Bea", 4);

        // Mostramos el contenido completo de la cola (ordenada por prioridad)
        System.out.println("\nContenido de la cola:");
        System.out.println(colaNombres);

        try {
            // Mostramos el primer y último elemento de la cola
            System.out.println("Elemento con mayor prioridad -> front: " + colaNombres.front());
            System.out.println("Elemento con menor prioridad -> back: " + colaNombres.back());

            // Probamos buscar un elemento existente y uno que no está
            System.out.println("\nBuscamos 'Carlos': " + colaNombres.buscarElemento("Carlos"));
            System.out.println("Buscamos 'Pedro': " + colaNombres.buscarElemento("Pedro"));

            // Eliminamos el elemento con mayor prioridad
            System.out.println("\nElemento eliminado -> dequeue: " + colaNombres.dequeue());

            // Mostramos la cola después de eliminar
            System.out.println("Contenido después del dequeue:");
            System.out.println(colaNombres);

        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: " + e.getMessage());
        }


        System.out.println("\nCola de prioridad: Números (Integer) con prioridad Double");

        // Cola de enteros, priorizados con números decimales
        PriorityQueue<Integer, Double> colaNumeros = new PriorityQueueLinkSort<>();

        // Insertamos varios elementos
        colaNumeros.enqueue(10, 5.2);
        colaNumeros.enqueue(20, 1.9);
        colaNumeros.enqueue(30, 7.8);
        colaNumeros.enqueue(40, 3.3);

        // Mostramos el contenido de la cola
        System.out.println("\nContenido de la cola:");
        System.out.println(colaNumeros);

        try {
            // Mostramos el primer y último elemento
            System.out.println("Elemento con mayor prioridad -> front: " + colaNumeros.front());
            System.out.println("Elemento con menor prioridad -> back: " + colaNumeros.back());

            // Probamos la búsqueda
            System.out.println("\nBuscando 30: " + colaNumeros.buscarElemento(30));

            // Eliminamos todos los elementos uno por uno
            while (!colaNumeros.isEmpty()) {
                System.out.println("Elemento eliminado -> dequeue: " + colaNumeros.dequeue());
            }

            // Verificamos si quedó vacía
            System.out.println("¿Está vacía?: " + colaNumeros.isEmpty());

        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nCola vacía: Comprobando excepciones");

        // Creamos una cola vacía para probar errores
        PriorityQueue<String, Integer> colaVacia = new PriorityQueueLinkSort<>();

        try {
            // Intentamos acceder al primer elemento en una cola vacía
            colaVacia.front();
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error al acceder a front(): " + e.getMessage());
        }

        try {
            // Intentamos acceder al último elemento en una cola vacía
            colaVacia.back();
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error al acceder a back(): " + e.getMessage());
        }

        try {
            // Intentamos eliminar de una cola vacía
            colaVacia.dequeue();
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error al hacer dequeue(): " + e.getMessage());
        }
    }    
}
