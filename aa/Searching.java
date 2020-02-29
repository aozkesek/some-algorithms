package aa;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Searching {

        private static AtomicInteger sum = new AtomicInteger(0);

        /**
         * Logarithmic Time Complexity O(log n)
         * 
         * */
        static void binary(List<Double> numbers) {
                int n = numbers.size() - 1;

                // we do not interested if its sorted or not
                numbers.sort((a,b) -> (b > a) ? -1 : ((b < a) ? 1 : 0));
                
                sum.set(0);

                // select a random element int the list
                Double key = numbers.get((int) (Math.random() * n - 1));

                int i = 0, j = n, m = -1;

                while (i <= j) {
                        sum.incrementAndGet();
                        m = (i + j) / 2;
                        if      (key > numbers.get(m)) { i = m + 1; }
                        else if (key < numbers.get(m)) { j = m - 1; }
                        else     break;
                }
                
                // not found, so possible place index should be
                if (numbers.get(m) != key) { m = -j; }

                System.out.println(key + ":" + numbers.get(m) + " found in round of " + sum.get());


        } 

        /**
         * search an item in an unsorted array;
         * - the time complexity is Linear O(n)
         * 
         * */
        static void min(List<Double> numbers) {

                int n = numbers.size() - 1;
                double value = numbers.get(0);
                
                for (int i = 1; i < n; i++) {
                        if (value < numbers.get(i)) {
                                continue; 
                        }
                        value = numbers.get(i);
                } 

                System.out.println("min=" + value);

        } 

        /**
         * accessing an item with a given index in an array;
         * - the time complecity is Constant O(1)
         * */
        static void get(List<Double> numbers) {

                int n = numbers.size() - 1;
                int i = (int)(Math.random() * n);
                double value = numbers.get(i);
                System.out.println("get("+i+")="+value);

        }
}