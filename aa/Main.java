package aa;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

        public static void main(String[] args) {
                int[] n = new int[] { 15, 48, 1024, 1024*8, 1024*64, 
                                      1024*256, 1024*1024*32 };

                for (int i : n) {
                        System.out.format("vvvvv Runing for: %12d%n", i);
                        List<Double> numbers = generate(i);
                
                        run(Sorting::jdkSort, List.copyOf(numbers));
                        
                        if (i < 8000) {
                                run(Sorting::selection, List.copyOf(numbers));
                                run(Sorting::insertion, List.copyOf(numbers));
                                run(Sorting::bubble, List.copyOf(numbers));
                        }
                        
                        run(Sorting::quick, List.copyOf(numbers));
                        run(Sorting::merge, List.copyOf(numbers));

                        
                }

                
                //run(Searching::min, n[4]);
                //run(Searching::get, n[4]);

                //for(int i = 0; i < n[4]; i++) {
                //        run(Searching::binary, n[4]);
                //}                

        }

        static void run(Consumer<List<Double>> sort, List<Double> numbers) {
                run(sort, numbers, false);
        }

        static void run(Consumer<List<Double>> sort, List<Double> numbers, boolean p) {

                List<Double> copyOf = new ArrayList<>();
                numbers.forEach(t -> copyOf.add(t));
                sort.accept(copyOf);
                if (p) {
                        System.out.println(copyOf);
                }
        }
        
        static List<Double> generate(int n) {
                return Stream
                        .generate(Math::random)
                        .limit(n)
                        .collect(Collectors.toList());
        }
}


