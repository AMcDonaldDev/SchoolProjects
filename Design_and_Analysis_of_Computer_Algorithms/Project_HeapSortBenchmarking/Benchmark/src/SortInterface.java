/*
 * File: SortInterface.java
 * Author: Allison McDonald
 * Date: 11/25/2020
 * Purpose: CMSC 451 Project 1 - SortInterface that is implemented by the HeapSort class
 */

public interface SortInterface {
    void recursiveSort(int[] list) throws UnsortedException;
    void iterativeSort(int[] list) throws UnsortedException;
    int getCount();
    long getTime();
}
