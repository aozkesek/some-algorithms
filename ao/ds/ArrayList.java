package ao.ds;

import java.util.Arrays;

public class ArrayList<T> {
    private Object[] items = new Object[16];
    private int head = -1;
    private int tail = -1;
    private int current = -1;

    public T get() {
        if (current == -1) {
            return null;
        }
        return (T)items[current];
    }

    public T get(int pos) {
        if (head == -1) {
            return null;
        }
        int size = head - tail + 1;
        if (pos < 0 || pos > size) {
            return null;
        }
        return (T)items[pos];
    }

    private void extend() {
        if (head > items.length - 8) {
            if (tail > 16) {
                items = Arrays.copyOfRange(items, tail, head);
                current -= tail;
                tail = 0;
                head = items.length - 1;
            } else {
                items = Arrays.copyOf(items, items.length + 16);
            }
        }
    }

    public boolean add(T item) {
        extend();
        items[++head] = item;
        return true;
    }

    public boolean add(T item, int pos) {
        int size = head - tail + 1;
        if (pos < 0 || pos > size) {
            return false;
        }
        
        return true;
    }

}