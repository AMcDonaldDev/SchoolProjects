/*
 * File: BenchmarkSorts.java
 * Author: Allison McDonald
 * Date: 11/25/2020
 * Purpose: CMSC 451 Project 1 - This is the program that benchmarks the heap sort
 * algorithm both recursive and iterative for 10 differently sized data sets.  
 * Each data set is ran 50 times with randomized data. The benchmarking data is
 * written to text file.
 * The choice of critical operation are comparisons.
 * For recursive data the file name is RecursiveBenchmark.txt
 * For iterative data the file name is IterativeBenchmark.txt
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkSorts {
    // Declare and instantiate variables
    private int dataSetSize;
    private static int numberOfRunsPerSet = 50;
    // Array of the differencly sized data sets
    private static int[] setSizes = new int[]{1000, 5000, 10000, 15000, 20000, 25000, 35000, 40000, 45000, 50000};
    private static BenchmarkSorts[] jvmWarmup = new BenchmarkSorts[setSizes.length];
    private static BenchmarkSorts[] benchmarkSort = new BenchmarkSorts[setSizes.length];
    private double[] recursiveHeapCount;
    private double[] recursiveHeapTime;
    private double[] iterativeHeapCount;
    private double[] iterativeHeapTime;
    private HeapSort heapSort = new HeapSort();
    private Random randomNumber = new Random();
    private static File file;
    private static FileWriter output;
    private static BufferedWriter outputStream;
    
    // Constructor
    public BenchmarkSorts(int setSize) {
        dataSetSize = setSize;
        recursiveHeapCount = new double[numberOfRunsPerSet];
        recursiveHeapTime = new double[numberOfRunsPerSet];
        iterativeHeapCount = new double[numberOfRunsPerSet];
        iterativeHeapTime = new double[numberOfRunsPerSet];
    }
    
    public static void main(String[] args) throws UnsortedException {
        // Array of JVM warmups
        for(int j = 0; j < 150; j++) {
            for(int i = 0; i < setSizes.length; i++) {
            BenchmarkSorts warmupBenchmarkData = new BenchmarkSorts(setSizes[i]);
            jvmWarmup[i] = warmupBenchmarkData;
            jvmWarmup[i].runBenchmarking(jvmWarmup, i);
            }
        }
        System.out.println("JVMWarmup complete");

        // Array of benchmark objects
        for(int i = 0; i < setSizes.length; i++) {
            BenchmarkSorts benchmarkData = new BenchmarkSorts(setSizes[i]);
            benchmarkSort[i] = benchmarkData;
            benchmarkSort[i].runBenchmarking(benchmarkSort, i);
        }
        System.out.println("Benchmarking complete");
        
        // Create and write benchmarking reports
        createRecursiveFile();
        System.out.println("RecursiveBenchmark.txt output file complete");
        createIterativeFile();
        System.out.println("IterativeBenchmark.txt output file complete");
    }
    
    // Create random number arrays for data sets
    // Run data sets with same randomized numbers through recurisive and iterative heap sort
    private void runBenchmarking(BenchmarkSorts[] array, int index) {
        try {
            int[] recursiveData = new int[array[index].dataSetSize];
            int[] iterativeData = new int[array[index].dataSetSize];
            for(int k = 0; k < numberOfRunsPerSet; k++) {
                for(int j = 0; j < array[index].dataSetSize; j++) {
                    int num = randomNumber.nextInt(1000);
                    recursiveData[j] = num;
                    iterativeData[j] = num;
                }
                heapSort.recursiveSort(recursiveData);
                array[index].recursiveHeapCount[k] = heapSort.getCount();
                array[index].recursiveHeapTime[k] = heapSort.getTime();
                heapSort.iterativeSort(iterativeData);
                array[index].iterativeHeapCount[k] = heapSort.getCount();
                array[index].iterativeHeapTime[k] = heapSort.getTime();
            }
        } catch(UnsortedException e) {
            System.out.println(e.message);
        }
    }
    
    // Function to get the data set size
    public static int getDataSetSize(BenchmarkSorts[] setArray, int i) {
        return setArray[i].dataSetSize;
    }
    
    // Function to get the heap recursive count
    public static double getRecursiveCount(BenchmarkSorts[] setArray, int i, int j) {
        return setArray[i].recursiveHeapCount[j];
    }
    
    // Function to get the heap recursive time
    public static double getRecursiveTime(BenchmarkSorts[] setArray, int i, int j) {
        return setArray[i].recursiveHeapTime[j];
    }
    
    // Function to get the heap iterative count
    public static double getIterativeCount(BenchmarkSorts[] setArray, int i, int j) {
        return setArray[i].iterativeHeapCount[j];
    }
    
    // Function to get the heap iterative time
    public static double getIterativeTime(BenchmarkSorts[] setArray, int i, int j) {
        return setArray[i].iterativeHeapTime[j];
    }
    
    // Function to create and write the recursive benchmark data
    private static void createRecursiveFile() {
        try {
            file = new File("RecursiveBenchmark.txt");
            output = new FileWriter(file.getAbsoluteFile());
            outputStream = new BufferedWriter(output);
            for(int i = 0; i < setSizes.length; i++) {
                // Print data set size
                String dataSize = Integer.toString(getDataSetSize(benchmarkSort, i));
                outputStream.write(dataSize);
                for(int j = 0; j < numberOfRunsPerSet; j++) {
                    // Print count and time
                    String dataCount = Double.toString(getRecursiveCount(benchmarkSort, i , j));
                    String dataTime = Double.toString(getRecursiveTime(benchmarkSort, i, j));
                    outputStream.write("," + dataCount + "," + dataTime);
                    if(j == (numberOfRunsPerSet - 1)) {
                        outputStream.write("\n");
                    }
                }
            }
            outputStream.close();
        } catch(IOException io) {
            System.out.println(io.getMessage());
        }
    }
    
    // Function to create and write the iterative benchmark data
    private static void createIterativeFile() {
        try {
            file = new File("IterativeBenchmark.txt");
            output = new FileWriter(file.getAbsoluteFile());
            outputStream = new BufferedWriter(output);
            for(int i = 0; i < setSizes.length; i++) {
                // Print data set size
                String dataSize = Integer.toString(getDataSetSize(benchmarkSort, i));
                outputStream.write(dataSize);
                for(int j = 0; j < numberOfRunsPerSet; j++) {
                    // Print count and time
                    String dataCount = Double.toString(getIterativeCount(benchmarkSort, i , j));
                    String dataTime = Double.toString(getIterativeTime(benchmarkSort, i, j));
                    outputStream.write("," + dataCount + "," + dataTime);
                    if(j == (numberOfRunsPerSet - 1)) {
                        outputStream.write("\n");
                    }
                }
            }
            outputStream.close();
        } catch(IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
