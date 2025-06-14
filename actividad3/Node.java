package actividad3;

public class Node<T> {
    T data;
    Node<T> next;

    public Node(T data){
        this.data = data;
        this.next = null;
    }

    public void setData(T data){
        this.data = data;
    }

    public void setNext(Node<T> next){
        this.next = next;
    }

    public T getData(){
        return data;
    }

    public Node<T> getNext(){
        return next;
    }
}