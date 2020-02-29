package aa;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

class QuickSort extends RecursiveAction {
        private static final long serialVersionUID = 0L;

        private int lo, hi;
        private List<Double> numbers;

        private QuickSort(List<Double> dl, int l, int h) {
                numbers = dl;
                lo = l;
                hi = h;
        }

        static void sort(List<Double> numbers) {
                final int n = numbers.size() - 1;
                
                if (n < 17) {
                        Sorting.selection(numbers);
                        return;
                } 

                // better do this with a fork/join executor
                ForkJoinPool pool = new ForkJoinPool();
                Future<?> sorted = pool.submit(new QuickSort(numbers, 0, n));

                try {
                        sorted.get();
                } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                } finally {
                        pool.shutdown();
                }
                
        }
        
        private static int partition(List<Double> numbers, int l, int h) {
                // select middle element as pivot
                Double p = numbers.get((h + l) / 2);
                int i = l;
                int j = h;
                while (true) {
                        while (numbers.get(i) < p)
                                i++;
                        while (numbers.get(j) > p)
                                j--;
                        if (i >= j) {
                                break;
                        }

                        Double temp = numbers.get(i);
                        numbers.set(i, numbers.get(j));
                        numbers.set(j, temp);
                }
                return j;
        }

        @Override
        protected void compute() {
                
                if (lo >= hi) {
                        return;
                }

                int p = partition(numbers, lo, hi);
                ForkJoinTask<?> left = new QuickSort(numbers, lo, p - 1).fork();
                new QuickSort(numbers, p + 1, hi).compute();
                left.join();        
        }

}