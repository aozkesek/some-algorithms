package ao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ao.ds.Queue;
import ao.ds.Stack;
import ao.sort.MergeSort;
import ao.sort.QuickSort;
import ao.sort.Sorting;

public class Main {

    public static void main(String[] args) {
        int[] n = new int[] { 
            15, 48, 1024 , 1024*8
            , 1024*64, 1024*256 //, 1024*1024*32 
        };  

        for (int i : n) {
            double[] numbers = genNRandom(i);
            int k = numbers.length;
            
            System.out.println(new Stack<Double>()
                                        .pushAll(copyAsDouble(numbers))
                                        .popAll(new ArrayList<Double>()));

            System.out.println(new Queue<Double>()
                                        .pushAll(copyAsDouble(numbers))
                                        .popAll(new ArrayList<Double>()));


            if (k < 10000) {
                sortNCheck(copyAsDouble(numbers), Sorting::bubble, "buble");
                sortNCheck(copyAsDouble(numbers), Sorting::insertion, "insertion");
                sortNCheck(copyAsDouble(numbers), Sorting::selection, "selection");
            }

            sortNCheck(copyAsDouble(numbers), MergeSort::sort, "merge");
            sortNCheck(copyAsDouble(numbers), QuickSort::sort, "quick");
            sortNCheck(copyAsDouble(numbers), Arrays::sort, "java"); 
        }

        
    }

    static <T extends Comparable<T>> 
    void sortNCheck(T[] items, Consumer<T[]> sorter, String alg) {

        LocalTime start = LocalTime.now();

        sorter.accept(items);

        System.out.printf(
            "it has %,9d msec taken for sort of %,12d items array with %s%n"
            , Duration.between(start, LocalTime.now()).toMillis()
            , items.length, alg
        );

        for (int i = 0; i < items.length - 1; i++) {
            if (items[i].compareTo(items[i+1]) > 0) {
                System.out.println("Exception: This arrays is not sorted!");
            }
        }
    }

    static Double[] copyAsDouble(double[] arr) {
        Double[] orr = new Double[arr.length];
        for(int i = 0; i < arr.length; i++) {
            orr[i] = arr[i];
        }
        return orr;
    }

    static double[] genNRandom(int n) {
        return Stream.generate(Math::random).limit(n).mapToDouble(d->d).toArray();
    }

}


