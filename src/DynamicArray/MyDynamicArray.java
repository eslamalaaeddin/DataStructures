package DynamicArray;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class MyDynamicArray<T> implements Iterable<T> {
    private T[] array = (T[]) new Object[0];

    public void add(T item) {
        T[] newArray = (T[]) new Object[array.length + 1];
        for (int i = 0; i < newArray.length - 1; i++) {
            newArray[i] = array[i];
        }
        newArray[newArray.length - 1] = item;
        array = newArray;
    }

    public void remove(T item) {
        if (array.length == 0)
            return;

        if (array.length == 1) {
            if (item.equals(array[0])) {
                array = (T[]) new Object[0];
                return;
            }
            return;
        }

        T[] newArray = (T[]) new Object[array.length - 1];
        for (int i = 0, j = 0; j < newArray.length && i < array.length; i++) {
            if (item != array[i]) {
                newArray[j] = array[i];
                j++;
            }
        }
        array = newArray;
    }

    public void clear() {
        for (T item : array) {
            remove(item);
        }
    }

    public int size() {
        return array.length;
    }

    public void removeAt(int index) {

        if (index < 0 || index >= array.length)
            throw new IndexOutOfBoundsException();

        remove(array[index]);
    }

    public int indexOf(T item) {
        for (int i = 0; i < array.length; i++) {
            if (item.equals(array[i]))
                return i;
        }
        return -1;
    }

    public boolean contain(T item) {
        for (T element : array) {
            if (element.equals(item))
                return true;
        }
        return false;
    }


    public void printArray() {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1)
                System.out.print(array[i]);
            else
                System.out.print(array[i] + ", ");
        }
        System.out.println("]");
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public T next() {
                return array[index++];
            }
        };
    }
}
