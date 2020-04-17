package ao.sort;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

import ao.InvalidAlgorithmImplementationException;

public class MergeSort<T extends Comparable<T>> extends RecursiveAction {

    private static final long serialVersionUID = 0L;

    private int lo, hi;
    private T[] items;

    // we do not want someone create this
    private MergeSort(T[] arr, int l, int h) {
        if (lo > hi) {
            throw new InvalidAlgorithmImplementationException("MergeSort");
        }

        items = arr;
        lo = l;
        hi = h;

    }

    public static <E extends Comparable<E>> void sort(E[] items) {
        int n = items.length - 1;
        ForkJoinPool executor = new ForkJoinPool();

        Future<?> task = executor.submit(new MergeSort<E>(items, 0, n));

        try {
            task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();        
    }

    @Override
    protected void compute() {    
            
        if (lo == hi) { 
            return; 
        }
        
        int pvt = (hi + lo) / 2;
        if (pvt > lo) {
            ForkJoinTask<?> left = new MergeSort<>(items, lo, pvt).fork();
            new MergeSort<>(items, pvt + 1, hi).compute();
            left.join();  
        }  

        merge(lo, pvt, hi);
        
    }
    
    private void merge(int l, int p, int h) {

        Object[] temp = new Object[h - l + 1];

        int i = l, 
            j = p + 1,
            k = 0;

        // sort both sides in a temp list
        while(i <= p && j <= h) {

            if (items[j].compareTo(items[i]) < 0) {
                temp[k++] = items[j++]; 
                continue;                               
            } 
            
            if (items[j].compareTo(items[i]) > 0) {
                temp[k++] = items[i++];
                continue;
            } 
            
            // equal, 
            temp[k++] = items[i++];
            temp[k++] = items[j++];
                
        }

        // is still a term left out on the both side?
        // only one side can be left over
        for (; i <= p; i++) {
            temp[k++] = items[i]; 
        }
        for (; j <= h; j++) {
            temp[k++] = items[j]; 
        }

        // put sorted ones back their stand
        for(i = 0; i < temp.length; i++) {
            items[l + i] = (T)temp[i];
        }
            
    }

}