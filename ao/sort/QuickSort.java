package ao.sort;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

import ao.InvalidAlgorithmImplementationException;

public class QuickSort<T extends Comparable<T>> extends RecursiveAction {
    private static final long serialVersionUID = 0L;

    private int lo, hi;
    private T[] items;

    private QuickSort(T[] arr, int l, int h) {
        if (lo > hi) {
            throw new InvalidAlgorithmImplementationException("QuickSort");
        }
        
        items = arr;
        lo = l;
        hi = h;
    }

    @Override
    protected void compute() {
            
        if (lo >= hi) {
            return;
        }

        int p = partition(items, lo, hi);
        ForkJoinTask<?> left = new QuickSort<>(items, lo, p - 1).fork();
        new QuickSort<>(items, p + 1, hi).compute();
        left.join();        
    }

    public static <E extends Comparable<E>> void sort(E[] items) {
        final int n = items.length - 1;
        
        ForkJoinPool executor = new ForkJoinPool();
        Future<?> task = executor.submit(new QuickSort<>(items, 0, n));

        try {
            task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
            
        executor.shutdown();
    }
    
    private int partition(T[] numbers, int l, int h) {
        // select middle element as pivot
        T p = numbers[(h + l) / 2];
        int i = l;
        int j = h;
        while (true) {
            while (numbers[i].compareTo(p) < 0)
                i++;
            while (numbers[j].compareTo(p) > 0)
                j--;
            if (i >= j) {
                break;
            }

            T temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
        return j;
    }


}