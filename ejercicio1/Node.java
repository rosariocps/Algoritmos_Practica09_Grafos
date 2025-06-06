package ejercicio1;

public class Node<E> {
    public E data; //dato del nodo
    public Node<E> next; // referencia al siguiente nodo

    public Node(E data) {
        this.data = data; //le asigamos el dato que recibamos de parametro
        this.next = null; //nulo xq por defecto esta vacio
    }
}
