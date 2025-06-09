package actividad3;

import actividad1.ExceptionIsEmpty;

public class PriorityQueueLinkSort<E, N extends Comparable<N>> implements PriorityQueue<E, N> {
    //clase interna
    class EntryNode {
        E data; // Dato
        N priority; // Prioridad asociada al dato

        //Contructor
        EntryNode(E data, N priority) { 
            this.data = data;
            this.priority = priority;
        }
    }

    private Node<EntryNode> first; // Referencia al primer nodo de la cola
    private Node<EntryNode> last;  // Referencia al último nodo de la cola

    //Constructor
    public PriorityQueueLinkSort() { // No hay parametros para inicializar la cola vacía
        this.first = null; // first y last son null para representar una cola vacía
        this.last = null;
    }

    // Inserta un elemento según su prioridad
    @Override
    public void enqueue(E x, N pr) { //dato x y prioridad pr
        //objeto nuevo EntryNode 
        EntryNode entradaNueva = new EntryNode(x, pr);
        //Nuevo nodo de tipo EntryNode que contiene como dato a entradaNueva
        Node<EntryNode> nodoNuevo = new Node<>(entradaNueva);

        // 1° Caso: Si la cola está vacía
        if (first == null) {
            first = last = nodoNuevo;
            return; // El nuevo nodo es el único, se vuelve first y last
        } // 2° Caso: Si se inserta al inicio
        
        if (pr.compareTo(first.data.priority) < 0) { // Si el nuevo nodo tiene mayor prioridad que el primero
            nodoNuevo.next = first; // Entonces el nuevo nodo apunta al nodo que era el primero 
            first = nodoNuevo; // El nuevo nodo se convierte en el primero de la cola
            return;
        } // 3° Caso: Si se inserta al medio o al final
        
        Node<EntryNode> nodoCurrent = first;

        while(nodoCurrent.next != null && pr.compareTo(nodoCurrent.next.data.priority)>=0){
            nodoCurrent = nodoCurrent.next;
        }

        nodoNuevo.next = nodoCurrent.next;
        nodoCurrent.next = nodoNuevo;

        if(nodoNuevo.next == null){
            last = nodoNuevo;
        }

    }

    // Elimina y retorna el elemento con mayor prioridad
    @Override
    public E dequeue() throws ExceptionIsEmpty {
        // Verificamos si la cola está vacía
        if (isEmpty())
            // Si está vacía, lanzamos la excepción personalizada
            throw new ExceptionIsEmpty("Cannot remove from an empty queue");

        // Creamos una variable aux de tipo genérico que guarda el dato con mayor prioridad (el que está al inicio)
        E valor = this.first.getData().data;
        // Actualizamos el puntero first para que ahora apunte al siguiente nodo de la lista
        this.first = this.first.getNext();
        // Verificamos si después de eliminar el nodo, la lista quedó vacía
        if (this.first == null)
            // Si quedó vacía, también actualizamos last a null para mantener la consistencia
            this.last = null;
        // Retornamos el dato que fue eliminado
        return valor;
    }
    
    // Retorna el dato del elemento con menor prioridad
    @Override
    public E back() throws ExceptionIsEmpty {
        if (isEmpty()) 
            throw new ExceptionIsEmpty("La cola está vacía");
        return last.data.data;
    }

    // Retorna el dato del elemento con mayor prioridad
    @Override
    public E front() throws ExceptionIsEmpty {
        if (isEmpty()) 
            throw new ExceptionIsEmpty("La cola está vacía");
        return first.data.data;
    }

    // Retorna true si la cola está vacía, false en caso contrario
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    // Método para buscar un elemento específico en la cola
    @Override
    public String buscarElemento(E elemento) {
        
        Node<EntryNode> actual = first; // Creamos un nodo auxiliar que apunta al primer nodo
        int posicion = 0; // Variable que nos ayudará a llevar la posición actual del recorrido

        // Recorremos la lista mientras no lleguemos al final
        while (actual != null) {
            // Si encontramos un nodo cuyo dato coincide con el buscado
            if (actual.getData().data.equals(elemento)) {
                // Retornamos el mensaje indicando su posición y su prioridad
                return "\"" + elemento + "\" está en la posición " + posicion +
                    " y tiene prioridad " + actual.getData().priority + ".";
            }
            // Pasamos al siguiente nodo
            actual = actual.getNext();
            // Aumentamos la posición
            posicion++;
        }
        // Si llegamos al final sin encontrarlo, retornamos mensaje indicándolo
        return "\"" + elemento + "\" no se encontró en la cola de prioridad.";
    }

    @Override
    public String toString() {
        // Si la cola está vacía, lo indicamos
        if (isEmpty()) return "Cola vacía";

        // Creamos un StringBuilder para construir el texto más eficientemente
        StringBuilder sb = new StringBuilder();
        // Cabecera de la tabla
        sb.append(String.format("%-10s | %s\n", "Dato", "Prioridad"));
        sb.append("----------------------\n");
        
        Node<EntryNode> actual = first; // Creamos un nodo auxiliar que recorra desde el primer nodo

        // Mientras no lleguemos al final de la lista
        while (actual != null) {
            // Agregamos el dato y su prioridad con formato de tabla
            sb.append(String.format("%-10s | %s\n", actual.data.data.toString(),
                    actual.data.priority.toString()));
            // Pasamos al siguiente nodo
            actual = actual.next;
        }
        // Retornamos el texto final con todos los elementos y sus prioridades
        return sb.toString();
    }
}
