package aa;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

class MergeSort extends RecursiveAction {

        private static final long serialVersionUID = 0L;

        private int lo, hi;
        private List<Double> numbers;

        private MergeSort(List<Double> dl, int l, int h) {
                if (lo > hi) {
                        throw new InvalidSortAlgorithmImplementationException("MergeSort");
                }

                numbers = dl;
                lo = l;
                hi = h;
        }

        static void sort(List<Double> numbers) {
                final int n = numbers.size() - 1;
                
                // better do this with a fork/join executor
                ForkJoinPool pool = new ForkJoinPool();
                pool.submit(new MergeSort(numbers, 0, n));
                pool.shutdown();

                try {
                        pool.awaitTermination(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                } 
                
        }

        private void merge(int l, int p, int h) {

                Double[] dl = new Double[h - l + 1];

                int i = l, 
                    j = p + 1,
                    k = 0;

                // sort both sides in a temp list
                while(i <= p && j <= h) {

                        if (numbers.get(j) < numbers.get(i)) {
                                dl[k++] = numbers.get(j++); 
                                continue;                               
                        } 
                        
                        if (numbers.get(i) < numbers.get(j)) {
                                dl[k++] = numbers.get(i++);
                                continue;
                        } 
                        
                        // equal, 
                        dl[k++] = numbers.get(i++);
                        dl[k++] = numbers.get(j++);
                        
                }

                // is still a term left out on the both side?
                // only one side can be left over
                for (; i <= p; i++) {
                        dl[k++] = numbers.get(i); 
                }
                for (; j <= h; j++) {
                        dl[k++] = numbers.get(j); 
                }

                // put sorted ones back their stand
                for(i = 0; i < dl.length; i++) {
                        numbers.set(l + i, dl[i]);
                }

                
        }

        @Override
        protected void compute() {    
                
                if (lo == hi) { return; }
                
                int p = (hi + lo) / 2;
                if (p > lo) {
                        ForkJoinTask<?> left = new MergeSort(numbers, lo, p).fork();
                        new MergeSort(numbers, p + 1, hi).compute();
                        left.join();  
                }  

                merge(lo, p, hi);
                
        }

}