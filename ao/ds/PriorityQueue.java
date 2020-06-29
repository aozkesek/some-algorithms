package ao.ds;

import java.util.Comparator;
import java.util.function.Consumer;

public class PriorityQueue<T extends Comparable<T>> {

    public static class Item<E extends Comparable<E>> 
        implements Comparable<Item<E>>, Comparator<Item<E>> 
    {
        private E value;
        private double priority = 0.0;
        private Consumer<Item<E>> onPriorityChange = null;

        /**
         * @param e item to be added
         * @param p item's priority
         * */
        public Item(E e, double p) {
            value = e;
            priority = p;
        }

        public double getPriority() {
            return priority;
        }

        public void setPriority(double p) {
            priority = p;
            if (onPriorityChange != null) {
                onPriorityChange.accept(this);
            }
        }

        public void onPriorityChange(Consumer<Item<E>> action) {
            onPriorityChange = action;
        }

        public E getValue() {
            return value;
        }

        @Override
        public int compareTo(Item<E> e) {
            return compare(this, e);
        }
        
        @Override
        public int compare(Item<E> e1, Item<E> e2) {
            if (e1.priority == e2.priority) {
                return e1.compareTo(e2);
            }
            return e1.priority < e2.priority ? -1 : 1;
        }
    }

    private Object[] items = new Object[16];
    private int head = -1;
    private int tail = -1;

    


}