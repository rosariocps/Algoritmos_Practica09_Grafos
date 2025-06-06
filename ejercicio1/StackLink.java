package ejercicio1;

import actividad1.ExceptionIsEmpty;
import actividad1.Stack;

public class StackLink<E> implements Stack<E> {
    //puntero al nodo que esta en el tope de la pila
    private Node<E> tope;
    //constructor: creamos una pila vacia
    public StackLink() {
        this.tope = null;
    }

    //para agregar un elemento en el tope de la pila
    @Override
    public void push(E x) {
        //creamos un nuevo nodo que tendra el valor x
        Node<E> nuevoNodo = new Node<>(x);
        // el nuevoNodo apunta al tope 
        nuevoNodo.next = tope;
        // y luego tope apunta al nuevoNodo
        tope = nuevoNodo;
    }

    //eliminar o desapilar el ultimo elemento de la pila
    @Override
    public E pop() throws ExceptionIsEmpty {
        //verificamos si esta vacia la pila
        if (isEmpty()) {
            //lanzamos una excepecion personalizada
            throw new ExceptionIsEmpty("Pila vacia");
        }
        //creamos esta variable generica
        //y guardamos el dato del tope para devolverlo despues
        E valor = tope.data;
        //movemos el tope al siguiente nodo y asi el anterior se quita de la pila
        tope = tope.next;
        return valor;//retornamos el valor que tenia tope antes
    }

    //retonar el elemento tope sin eliminarlo
    @Override
    public E top() throws ExceptionIsEmpty {
        //verificamos si esta vacia la pila
        if (isEmpty()) {
            //lanzamos una excepecion personalizada
            throw new ExceptionIsEmpty("Pila vacia");
        }
        //solo retornamos el dato de tope, pero no lo quitamos
        return tope.data;
    }

    //para ver si la pila esta vacia
    @Override
    public boolean isEmpty() {
        //retorna true si apunta a null, osea que no hay nodos
        return tope == null;
    }

    //para "eliminar" la pila
    @Override
    public void destroyStack() {
        tope = null; //hacemos que tope apunte a null
    }

    @Override
    public String toString() {
        //verificamos si la pila esta vacia
        if (isEmpty()) {
            return "Pila vacía";
        }
        /// usamos StringBuilder porque es mas eficiente para juntar varios textos,
        // especialmente cuando estamos dentro de un bucle y asi evitamos crear muchas cadenas nuevas en memoria.
        StringBuilder sb = new StringBuilder("Pila: ["); // el comienzo de la cadena
        //creamos un nodo para recorrer la pila desde el tope
        Node<E> current = tope;
        //mientras no llegue al final de la pila
        while (current != null) {
            //aqui agregamos el dato del nodo actual al texto
            sb.append(current.data);
            //verificamos que el siguiente nodo no sea null
            if (current.next != null) {
                // si hay otro nodo despues, agregamos una coma
                sb.append(", ");
            }
            //pasamos al siguiente nodo
            current = current.next;
        }
        // cerramos la cadena con ]
        sb.append("]");
        //retonamos el texto que hemos construido :)
        return sb.toString();
    }
}
