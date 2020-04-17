package ao.sort;

public class Sorting {

    /**
     * Bubble Sort
     * @param items array to be sorted 
     */
    public static <T extends Comparable<T>> void bubble(T[] items) {

        int n = items.length - 1;
        boolean isChanged;

        do {
            isChanged = false;
            // start from beginning to the end
            for (int i = 0; i < n; i++) {
                if (items[i].compareTo(items[i + 1]) <= 0)
                    continue;
                // move biggest element to the end
                swap(items, i, i + 1);
                isChanged = true;
            }
            // lower end index, we already put the max there
            n--;
            //repeat till there is nothing changed in last iteration
        } while (isChanged);

    }

    /**
     * Simple Sort
     * @param items array to be sorted
     */
    public static <T extends Comparable<T>> void insertion(T[] items) {
        int n = items.length - 1;

        for (int i = 1; i <= n; i++) {
            for (int j = i; j > 0; j--) {
                // compare
                if (items[j].compareTo(items[j - 1]) >= 0) {
                    continue;
                }

                swap(items, j, j - 1);
            }
        }

    }
    
    /**
     * Selection Sort
     * @param items array to be sorted
     */
    public static <T extends Comparable<T>> void selection(T[] items) {

        int n = items.length - 1;
        
        for (int i = n; i > 0; i--) {
            int k = 0;
            for (int j = 1; j <= i; j++) {
                // compare
                if (items[k].compareTo(items[j]) < 0) {
                    k = j;
                }
            }

            swap(items, k, i);
        }

    }

    /**
     * swap function
     * */
    private static <T extends Comparable<T>> void swap(T[] items, int l, int r) {
        T temp = items[l];
        items[l] = items[r];
        items[r] = temp;
    }
}