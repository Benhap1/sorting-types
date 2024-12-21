import org.sorting.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // Create an array of 1000 random elements to sort
        int[] originalArray = new int[1000];
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

        // Add sorting tasks using a loop to reduce repetition
        tasks.add(createSortingTask(originalArray, new BubbleSorting()));
        tasks.add(createSortingTask(originalArray, new SelectionSort()));
        tasks.add(createSortingTask(originalArray, new InsertionSort()));
        tasks.add(createSortingTask(originalArray, new MergeSort()));
        tasks.add(createSortingTask(originalArray, new QuickSorting()));
        tasks.add(createSortingTask(originalArray, new HeapSorting()));

        // Run the tasks and get the durations
        List<Future<Long>> futures = executorService.invokeAll(tasks);

        int i = 0;
        for (Future<Long> future : futures) {
            durations[i++] = future.get();
        }

        executorService.shutdown();

        // Sort the sorting methods and durations
        sortDurations(sortingMethods, durations);

        // Print the results
        System.out.println("Sorting methods by speed:");
        for (int j = 0; j < durations.length; j++) {
            System.out.println(sortingMethods[j] + ": " + durations[j] + " ns");
        }
    }

    // Helper method to create sorting tasks
    private static Callable<Long> createSortingTask(int[] originalArray, Object sorter) {
        return () -> {
            int[] arrayCopy = Arrays.copyOf(originalArray, originalArray.length);
            long startTime = System.nanoTime();
            if (sorter instanceof BubbleSorting) {
                ((BubbleSorting) sorter).bubbleSorting(arrayCopy);
            } else if (sorter instanceof SelectionSort) {
                ((SelectionSort) sorter).selectionSort(arrayCopy);
            } else if (sorter instanceof InsertionSort) {
                ((InsertionSort) sorter).insertionSort(arrayCopy);
            } else if (sorter instanceof MergeSort) {
                ((MergeSort) sorter).mergeSort(arrayCopy, 0, arrayCopy.length - 1);
            } else if (sorter instanceof QuickSorting) {
                ((QuickSorting) sorter).quickSorting(arrayCopy, 0, arrayCopy.length - 1);
            } else if (sorter instanceof HeapSorting) {
                ((HeapSorting) sorter).heapSorting(arrayCopy);
            }
            long endTime = System.nanoTime();
            return endTime - startTime;
        };
    }

    // Helper method to sort durations and their associated sorting methods
    private static void sortDurations(String[] sortingMethods, long[] durations) {
        for (int i = 0; i < durations.length - 1; i++) {
            for (int j = 0; j < durations.length - 1 - i; j++) {
                if (durations[j] > durations[j + 1]) {
                    long tempDuration = durations[j];
                    durations[j] = durations[j + 1];
                    durations[j + 1] = tempDuration;

                    String tempMethod = sortingMethods[j];
                    sortingMethods[j] = sortingMethods[j + 1];
                    sortingMethods[j + 1] = tempMethod;
                }
            }
        }
    }
}
