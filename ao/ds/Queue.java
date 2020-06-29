package ao.ds;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * First In, Firts Out
 * 
 */
public class Queue<T extends Comparable<T>> {

    private Object[] items = new Object[16];

    private int head = -1;
    private int tail = -1;

    public synchronized boolean contains(T t) {
        for (int i = tail; i <= head; i++) {
            if (t.compareTo((T)items[i]) == 0) {
                return true;
            }
        }
        return false;
    }

    public synchronized T get(int i) {
        if (tail + i < head) {
            return (T)items[tail + i];
        }
        return null;
    }

    public synchronized Queue<T> push(T t) {
        if (head > items.length - 8) {
            if (tail > 16) {
                items = Arrays.copyOfRange(items, tail, head);
                tail = 0;
                head = items.length - 1;
            } else {
                items = Arrays.copyOf(items, items.length + 16);
            }
        }
        items[++head] = t;
        if (tail == -1) {
            tail = 0;
        };

        return this;
    }

    public synchronized Queue<T> pushAll(T[] arr) {
        for (T t : arr) {
            push(t);
        }
        return this;
    }

    public synchronized Queue<T> pushAll(Iterable<T> col) {
        Iterator<T> it = col.iterator();
        while(it.hasNext()) {
            push(it.next());
        }
        return this;
    }

    public synchronized T pop() {
        if (tail == -1) {
            return null;
        }
        
        T t = (T)items[tail++];

        if (tail > head) {
            tail = head = -1;
            return t;
        }

        return t; 
    }

    public synchronized int popAll(T[] arr, int sz) {
        if (arr == null || arr.length < sz) {
            return -1;
        }

        for (int i = 0; i < sz && tail != -1; i++) {
            arr[i] = pop();
        }

        return arr.length;
    }

    public synchronized int popAll(Collection<T> col) {
        if (col == null) {
            return -1;
        }

        while (tail != -1) {
            col.add(pop());
        }

        return col.size();
    }

    public void forEach(Consumer<T> consumer) {
        while (head >= 0) {
            consumer.accept(pop());
        }
    }
}