import org.sorting.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // Create an array of 1000 random elements to sort
        int originalArray[] = new int[1000];
        Random random = new Random();
        for (int i = 0; i < originalArray.length; i++) {
            originalArray[i] = random.nextInt(100);
        }

        // Create an array of sorting methods
        String[] sortingMethods = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Heap Sort"};
        long[] durations = new long[sortingMethods.length];

        // Create an executor service to run the sorting methods
        ExecutorService executorService = Executors.newFixedThreadPool(sortingMethods.length);

        // Create a list of tasks to run on the executor service
        List<Callable<Long>> tasks = new ArrayList<>();

        // Bubble Sort
        tasks.add(() -> {
            // Create a copy of the original array
            int[] bubbleSortArray = Arrays.copyOf(originalArray, originalArray.length);
            BubbleSorting bubbleSorting = new BubbleSorting();
            // Start the timer
            long startTime = System.nanoTime();
            // Sort the array
            bubbleSorting.bubbleSorting(bubbleSortArray);
            // Stop the timer
            long endTime = System.nanoTime();
            // Return the duration
            return endTime - startTime;
        });

        // Selection Sort
        tasks.add(() -> {
            int[] selectionSortArray = Arrays.copyOf(originalArray, originalArray.length);
            SelectionSort selectionSort = new SelectionSort();
            long startTime = System.nanoTime();
            selectionSort.selectionSort(selectionSortArray);
            long endTime = System.nanoTime();
            return endTime - startTime;
        });

        // Insertion Sort
        tasks.add(() -> {
            int[] insertionSortArray = Arrays.copyOf(originalArray, originalArray.length);
            InsertionSort insertionSort = new InsertionSort();
            long startTime = System.nanoTime();
            insertionSort.insertionSort(insertionSortArray);
            long endTime = System.nanoTime();
            return endTime - startTime;
        });

        // Merge Sort
        tasks.add(() -> {
            int[] mergeSortArray = Arrays.copyOf(originalArray, originalArray.length);
            MergeSort mergeSort = new MergeSort();
            long startTime = System.nanoTime();
            mergeSort.mergeSort(mergeSortArray, 0, mergeSortArray.length - 1);
            long endTime = System.nanoTime();
            return endTime - startTime;
        });

        // Quick Sort
        tasks.add(() -> {
            int[] quickSortArray = Arrays.copyOf(originalArray, originalArray.length);
            QuickSorting quickSorting = new QuickSorting();
            long startTime = System.nanoTime();
            quickSorting.quickSorting(quickSortArray, 0, quickSortArray.length - 1);
            long endTime = System.nanoTime();
            return endTime - startTime;
        });

        // Heap Sort
        tasks.add(() -> {
            int[] heapSortArray = Arrays.copyOf(originalArray, originalArray.length);
            HeapSorting heapSorting = new HeapSorting();
            long startTime = System.nanoTime();
            heapSorting.heapSorting(heapSortArray);
            long endTime = System.nanoTime();
            return endTime - startTime;
        });

        // Run the tasks and get the durations
        List<Future<Long>> futures = executorService.invokeAll(tasks);

        // Shutdown the executor service

        int i = 0;
        for (Future<Long> future : futures) {
            durations[i++] = future.get();
        }

        executorService.shutdown();

        // Create a list of sorting methods and durations
        List<String> sortedSortingMethods = new ArrayList<>();
        List<Long> sortedDurations = new ArrayList<>();

        // Sort the sorting methods and durations
        for (int j = 0; j < durations.length - 1; j++) {
            for (int k = 0; k < durations.length - 1 - j; k++) {
                if (durations[k] > durations[k + 1]) {
                    long tempDuration = durations[k];
                    durations[k] = durations[k + 1];
                    durations[k + 1] = tempDuration;

                    String tempMethod = sortingMethods[k];
                    sortingMethods[k] = sortingMethods[k + 1];
                    sortingMethods[k + 1] = tempMethod;
                }
            }
        }

        // Add the sorted sorting methods and durations to the lists

        System.out.println("Sorting methods by speed:");
        for (int j = 0; j < durations.length; j++) {
            sortedSortingMethods.add(sortingMethods[j]);
            sortedDurations.add(durations[j]);
            System.out.println(sortingMethods[j] + ": " + durations[j] + " ns");
        }

        // Print the sorted sorting methods and durations
        System.out.println("Sorting methods by speed:");
        for (int j = 0; j < sortedSortingMethods.size(); j++) {
            System.out.println(sortedSortingMethods.get(j) + ": " + sortedDurations.get(j) + " ns");
        }
    }

    /**
     * Sorts the given array using Bubble Sort
     * @param array the array to sort
     * @return the sorted array
     */
    public static int[] bubbleSort(int[] array) {
        int[] bubbleSortArray = Arrays.copyOf(array, array.length);
        BubbleSorting bubbleSorting = new BubbleSorting();
        bubbleSorting.bubbleSorting(bubbleSortArray);
        return bubbleSortArray;
    }

    /**
     * Sorts the given array using Selection Sort
     * @param array the array to sort
     * @return the sorted array
     */
    public static int[] selectionSort(int[] array) {
        int[] selectionSortArray = Arrays.copyOf(array, array.length);
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.selectionSort(selectionSortArray);
        return selectionSortArray;
    }

    /**
     * Sorts the given array using Insertion Sort
     * @param array the array to sort
     * @return the sorted array
     */
    public static int[] insertionSort(int[] array) {
        int[] insertionSortArray = Arrays.copyOf(array, array.length);
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.insertionSort(insertionSortArray);
        return insertionSortArray;
    }

    /**
     * Sorts the given array using Merge Sort
     * @param array the array to sort
     * @return the sorted array
     */
    public static int[] mergeSort(int[] array) {
        int[] mergeSortArray = Arrays.copyOf(array, array.length);
        MergeSort mergeSort = new MergeSort();
        mergeSort.mergeSort(mergeSortArray, 0, mergeSortArray.length - 1);
        return mergeSortArray;
    }

    /**
     * Sorts the given array using Quick Sort
     * @param array the array to sort
     * @return the sorted array
     */
    public static int[] quickSort(int[] array) {
        int[] quickSortArray = Arrays.copyOf(array, array.length);
        QuickSorting quickSorting = new QuickSorting();
        quickSorting.quickSorting(quickSortArray, 0, quickSortArray.length - 1);
        return quickSortArray;
    }

    /**
     * Sorts the given array using Heap Sort
     * @param array the array to sort
     * @return the sorted array
     */
    public static int[] heapSort(int[] array) {
        int[] heapSortArray = Arrays.copyOf(array, array.length);
        HeapSorting heapSorting = new HeapSorting();
        heapSorting.heapSorting(heapSortArray);
        return heapSortArray;
    }
}
