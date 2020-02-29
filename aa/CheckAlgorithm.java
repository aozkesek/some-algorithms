package aa;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

class CheckAlgorithm<T extends Comparable<T>> extends RecursiveTask<Boolean> {

        private static final long serialVersionUID = 0L;

        private List<T> ld;
        private int lo, hi;
        private String msg;

        /**
         * we want only isSorted method can access an create this recursive task
         * */
        private CheckAlgorithm(List<T> ld, int lo, int hi, String msg) {
                if (lo > hi) {
                        throw new InvalidSortAlgorithmImplementationException(msg);
                }

                this.ld = ld;
                this.lo = lo;
                this.hi = hi;
                this.msg = msg;
        }

        /**
         * go through on elements in a simple loop when element size is 
         * less than or equal 16
         * */
        private static <T extends Comparable<T>> void isSorted_16(List<T> numbers, String msg) {
                final int n = numbers.size() - 1;
                
                for (int i = 1; i <= n; i++) {
                        if (numbers.get(i-1).compareTo(numbers.get(i)) > 0) {
                                throw new InvalidSortAlgorithmImplementationException(msg);
                        }
                }

        }

        /**
         * go through on a bunch of four elements in a simple loop when element
         * size is less than or equal 64 but greater than 16
         * */
        private static <T extends Comparable<T>> void isSorted_64(List<T> numbers, String msg) {
                final int n = numbers.size() - 1;
                int i = 1;

                // divide it four chunks, check four items of them in one cycle
                int p = n / 4;
                while (i < p) {
                        boolean checked = numbers.get(i - 1).compareTo(numbers.get(i)) < 1
                                        && numbers.get(p + i - 1).compareTo(numbers.get(p + i)) < 1
                                        && numbers.get(2 * p + i - 1).compareTo(numbers.get(2 * p + i)) < 1
                                        && numbers.get(3 * p + i - 1).compareTo(numbers.get(3 * p + i)) < 1;
                        if (!checked) {
                                throw new InvalidSortAlgorithmImplementationException(msg);
                        }
                        i++;
                }
        }

        static <T extends Comparable<T>> void isSorted(List<T> numbers, String msg) {
                final int n = numbers.size() - 1;
                
                if (n < 17) {
                        isSorted_16(numbers, msg);
                        return;
                } else if (n < 65) {
                        isSorted_64(numbers, msg);
                        return;
                }

                // better do this with a fork/join executor
                ForkJoinPool pool = new ForkJoinPool();
                Future<Boolean> sorted = pool.submit(new CheckAlgorithm<>(numbers, 0, n, msg));

                try {
                        if (sorted.get()) { return; }
                        throw new InvalidSortAlgorithmImplementationException(msg);
                } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                } finally {
                        pool.shutdown();
                }
                
        }

        @Override
        protected Boolean compute() {
                int p = (hi - lo) / 2;
                
                if (p <= 1) {
                        return ld.get(lo).compareTo(ld.get(hi)) < 1;
                }

                ForkJoinTask<Boolean> forked = new CheckAlgorithm<>(ld, lo, lo + p, msg).fork();
                return new CheckAlgorithm<>(ld, lo + p, hi, msg).compute() && forked.join();
        }
}