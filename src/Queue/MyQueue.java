package Queue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyQueue <T> implements Iterable<T>{
    private List<T> queueList = new LinkedList<>();

    public void enqueue(T element){
        queueList.add(element);
    }

    public T dequeue(){
        if (isEmpty())
            throw new RuntimeException("Empty Queue");

        T element = queueList.get(0);
        queueList.remove(element);
        return element;
    }

    public T peak(){
        if (isEmpty())
            throw new RuntimeException("Empty Queue");

        return queueList.get(0);
    }

    public boolean isEmpty(){
        return queueList.isEmpty();
    }

    public int size(){
        return queueList.size();
    }

    public boolean contain(T element){
        for (T item : queueList) {
            if (item.equals(element))
                return true;
        }
        return false;
    }

    public void print(){
        for (T t : queueList) {
            System.out.print(t + " ");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return queueList.iterator();
    }
}
