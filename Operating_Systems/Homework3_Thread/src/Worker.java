/*
 * File: Worker.java
 * Author: Allison McDonald
 * Date: 2/2/2020
 * Purpose: This program creates the Worker class
 */

public class Worker implements Runnable {
    // Class fields
    private static int numberOfWorkers = 0;
    // Instance variables
    private int workerID;
    private String workerName;
    
    // Default constructor
    Worker() {
        // Increment number of workers
        ++numberOfWorkers;
        // Create worker id
        workerID = numberOfWorkers;
        workerName = "Thread";
    }
    
    public void run() {
        for(int i = 1; i < 6; i++) {
            try {
                Thread.currentThread().sleep(4);
                System.out.println(workerName + " " + workerID +
                        ", iteration: " + i);
            } catch(InterruptedException e) {
                System.out.println("Exception: " + e.toString());
            }
        }
    }
}