package ao.ds;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * First In, Last Out 
 * 
 * */
public class Stack<T> {

    private Object[] items = new Object[16];
    private int head = -1;

    public synchronized Stack<T> push(T t) {
        if (head > items.length - 8) {
            items = Arrays.copyOf(items, items.length + 16);
        }
        items[++head] = t;
        return this;
    }

    public synchronized Stack<T> pushAll(T[] arr) {
        for (T t : arr) {
            push(t);
        }
        return this;
    }

    public synchronized Stack<T> pushAll(Iterable<T> col) {
        Iterator<T> it = col.iterator();
        while(it.hasNext()) {
            push(it.next());
        }
        return this;
    }

    public synchronized T pop() {
        if (head < 0) {
            return null;
        }
        return (T)items[head--]; 
    }

    public synchronized int popAll(T[] arr, int sz) {
        if (arr == null || arr.length < sz) {
            return -1;
        }

        for (int i = 0; i < sz && head >= 0; i++) {
            arr[i] = pop();
        }

        return arr.length;
    }

    public synchronized int popAll(Collection<T> col) {
        if (col == null) {
            return -1;
        }

        while (head >= 0) {
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