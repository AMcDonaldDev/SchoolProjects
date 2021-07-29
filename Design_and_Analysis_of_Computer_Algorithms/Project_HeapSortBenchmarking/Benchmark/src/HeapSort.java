/*
 * File: HeapSort.java
 * Author: Allison McDonald
 * Date: 11/25/2020
 * Purpose: CMSC 451 Project 1 - Heap sort class that implements SortInterface
 * with both recursive and iterative algorithms
 * Critical Operations are comparisons
 */

public class HeapSort implements SortInterface {
    // Declare variables
    private int count;
    private long time;
    
    @Override
    // Recursive Heap Sort
    // Code from: https://www.geeksforgeeks.org/heap-sort/
    public void recursiveSort(int[] arr) throws UnsortedException {
        // Setting the count and time to zero
        // The count will increase by one everytime heapify is called
        count = 0;
        time = 0;
        long timeStart = System.nanoTime();
        
        int n = arr.length;
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            // Call heapify on the reduced heap
            heapify(arr, i, 0);
        }
        
        // Stop time and set time
        long timeEnd = System.nanoTime();
        time = timeEnd - timeStart;
        
        if(!isSorted(arr)) {
            throw new UnsortedException("Caution: Recursive sort did not properly sort the array.");
        } 
    }
    
    // Recursive Heap Sort
    // Code from: https://www.geeksforgeeks.org/heap-sort/
    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is the size of heap
    void heapify(int[] arr, int n, int i) {
        count++;
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // Left = 2*i+1
        int r = 2 * i + 2; // Right = 2*i+2
        
        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;
        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;
        // If larges is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
    
    @Override
    // Iterative Heap Sort
    // Code from: https://www.geeksforgeeks.org/iterative-heap-sort/?ref=rp
    public void iterativeSort(int[] arr) throws UnsortedException {
        // Setting the count and time to zero
        // The count will increase by one for every loop in for, while, or do
        count = 0;
        time = 0;
        long timeStart = System.nanoTime();
        
        int size = arr.length;
        buildMaxHeap(arr, size);
        for(int i = size - 1; i > 0; i--) {

            // Swap value of first indexed with last indexed
            swap(arr, 0, i);
            // Maintaing heap property after each swapping
            int j = 0;
            int index;
            do {
                count++;
                index = (2 * j + 1);
                // If left child is smaller than right child point index variable
                // to right child
                if(index < (i - 1) && arr[index] < arr[index + 1]) {
                    index++;
                }
                // If parent is smaller than child then swapping parent with child
                // having higher value
                if(index < i && arr[j] < arr[index]) {
                    swap(arr, j, index);
                }
                j = index;
            } while (index < i);
        }
        
        // Stop time and set time
        long timeEnd = System.nanoTime();
        time = timeEnd - timeStart;
        
        // Check if list is not sorted and throw exception
        if(!isSorted(arr)) {
            throw new UnsortedException("Caution: Iterative sort did not properly sort the array.");
        }
    }
    
    // Code from: https://www.geeksforgeeks.org/iterative-heap-sort/?ref=rp
    // Function build Max Heap where value of each child is always smaller
    // than value of their parent
    private void buildMaxHeap(int[] arr, int n) {
        for(int i = 1; i < n; i++) {
            count++;
            // If child is bigger than parent
            if(arr[i] > arr[(i - 1) / 2]) {
                int j = i;
                // Swap child and parent until parent is smaller
                while(arr[j] > arr[(j - 1) / 2]) {

                    swap(arr, j, (j - 1) / 2);
                    j = (j - 1) / 2;
                }
            }
        }
    }
    
    // Code from: https://www.geeksforgeeks.org/iterative-heap-sort/?ref=rp
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    @Override
    public int getCount() {
        return count;
    }
    
    @Override
    public long getTime() {
        return time;
    }
    
    // Check to see if list is sorted
    private boolean isSorted(int[] list) {
        boolean sorted = true;
        for(int i = 0; i < list.length - 1; i++) {
            if(list[i] <= list[i + 1]) {
                sorted = true;
            } else {
                sorted = false;
                return sorted;
            }
        }
        return sorted;
    }
}
