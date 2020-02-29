package aa;

import static java.lang.System.out;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

class Sorting {

        private static void printDuration(LocalTime ltime, String msg) {
                out.format("%16s: %,12d%n", msg, 
                           Duration.between(ltime, LocalTime.now()).toNanos() / 1000);
        }

        private static <T extends Comparable<T>> void sort(String msg, 
                                     BiConsumer<List<T>, Comparator<T>> alg, 
                                     List<T> terms, Comparator<T> comp) {
                LocalTime start = LocalTime.now();

                alg.accept(terms, comp);

                printDuration(start, msg);

                CheckAlgorithm.isSorted(terms, msg); 

        }

        private static <T extends Comparable<T>> void sort(String msg, 
                                     BiConsumer<List<T>, Comparator<T>> alg, 
                                     List<T> terms) {
                sort(msg, alg, terms, null);
        }

        static void jdkSort(List<Double> numbers) {

                sort("jdk-sort", (t, c) -> t.sort(c), numbers, Double::compare);

        }

        static void merge(List<Double> numbers) {

                sort("merge-sort", (t,c) -> MergeSort.sort(t), numbers);
                          
        }

        static void quick(List<Double> numbers) {
                
                sort("quick-sort", (t,c) -> QuickSort.sort(t), numbers);
                
        }

        

        /**
         * 
         * @param numbers
         */
        static void bubble(List<Double> numbers) {

                int n = numbers.size() - 1;
                boolean isChanged;
                LocalTime start = LocalTime.now();

                do {
                        isChanged = false;
                        for (int i = 0; i < n; i++) {
                                // compare :
                                if (numbers.get(i) <= numbers.get(i + 1))
                                        continue;

                                swap(numbers, i, i + 1);
                                isChanged = true;
                        }
                        n--;
                } while (isChanged);

                printDuration(start, "bubble-sort");;
                
                CheckAlgorithm.isSorted(numbers, "bubble-sort");

        }

        /**
         * Simple Sort
         */
        static void insertion(List<Double> numbers) {
                int n = numbers.size() - 1;
                LocalTime start = LocalTime.now();

                for (int i = 1; i <= n; i++) {
                        for (int j = i; j > 0; j--) {
                                // compare
                                if (numbers.get(j) >= numbers.get(j - 1)) {
                                        continue;
                                }

                                swap(numbers, j, j - 1);
                        }
                }

                printDuration(start, "insertion-sort");;
                
                CheckAlgorithm.isSorted(numbers, "insertion-sort");
        }

        /**
         * Simple Sort it has Quadratic Time Complexity O(n2)
         */
        static void selection(List<Double> numbers) {

                int n = numbers.size() - 1;
                LocalTime start = LocalTime.now();
                
                for (int i = n; i > 0; i--) {
                        int k = 0;
                        for (int j = 1; j <= i; j++) {
                                // compare
                                if (numbers.get(k) < numbers.get(j)) {
                                        k = j;
                                }
                        }

                        swap(numbers, k, i);
                }

                printDuration(start, "selection-sort");;
                
                CheckAlgorithm.isSorted(numbers, "selection-sort");

        }

        /**
         * swap function
         * */
        private static void swap(List<Double> numbers, int l, int r) {
                Double temp = numbers.get(l);
                numbers.set(l, numbers.get(r));
                numbers.set(r, temp);
        }
}