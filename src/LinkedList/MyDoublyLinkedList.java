package LinkedList;

import java.util.*;

public class MyDoublyLinkedList<T> implements Iterable<Node<T>> {
    //nulls mean empty linked list
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public void clear() {
        Node<T> traversal = head;
        while (traversal.next != null) {
            Node<T> nextToTraversal = traversal.next;
            traversal.previous = traversal.next = null;
            traversal.value = null;
            traversal.next = nextToTraversal;
        }
        head = tail = traversal = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    //element means value
    public void addLast(T element) {
        Node<T> addedNode = new Node(element, null, null);

        if (isEmpty())
            head = tail = addedNode;

        else {
            addedNode.previous = tail;
            tail.next = addedNode;
            tail = addedNode;
        }

        size++;

    }

    public void addFirst(T element) {
        Node<T> addedNode = new Node(element, null, null);

        if (isEmpty())
            head = tail = addedNode;

        else {
            addedNode.next = head;
            head.previous = addedNode;
            head = addedNode;
        }

        size++;

    }

    public T peakFirst() {
        if (isEmpty())
            throw new RuntimeException("Empty List");

        return head.value;
    }

    public T peakLast() {
        if (isEmpty())
            throw new RuntimeException("Empty List");

        return tail.value;
    }

    public T removeFirst() {
        if (isEmpty())
            throw new RuntimeException("Empty List");

        T value = head.value;
        head = head.next;
        --size;

        //if it becomes empty after deleting the last node
        if (isEmpty())
            tail = null;
            //there one node at least
        else
            head.previous = null;

        return value;
    }

    public T removeLast() {
        if (isEmpty())
            throw new RuntimeException("Empty List");

        T value = tail.value;
        tail = tail.previous;
        --size;

        //if it becomes empty after deleting the last node
        if (isEmpty())
            head = null;
            //there one node at least
        else
            tail.next = null;

        return value;
    }

    public boolean remove(T element) {
        if (isEmpty())
            throw new RuntimeException("Empty List");

        if (size() == 1 && element == head) {
            tail = head = null;
            size--;
            return true;
        }

        for (Node<T> item : this) {
            if (item.value.equals(element)) {

                if (item.value.equals(tail.value))
                    removeLast();

                else if (item.value.equals(head.value))
                    removeFirst();

                else {
                    Node<T> previousToRemoved = item.previous;
                    Node<T> nextToRemoved = item.next;
                    nextToRemoved.previous = previousToRemoved;
                    previousToRemoved.next = nextToRemoved;
                    size--;
                }


                return true;
            }
        }

        return false;

    }

    public void print() {
        if (this.isEmpty()) {
            System.out.println("[]");
            return;
        }
        for (Node<T> node : this) {
            System.out.print(node.value + " ");
        }
        System.out.print("\n----------");
    }


    @Override
    public Iterator<Node<T>> iterator() {
        return new Iterator<>() {
            private Node<T> traversal = head;

            @Override
            public boolean hasNext() {
                return traversal != null;
            }

            @Override
            public Node<T> next() {
                Node<T> currentNode = traversal;
                T value = traversal.value;
                traversal = traversal.next;
                return currentNode;
            }
        };
    }
}

class Node<T> {
    T value;
    Node<T> previous, next;


    public Node(T value, Node<T> previous, Node<T> next) {
        this.value = value;
        this.previous = previous;
        this.next = next;
    }
}