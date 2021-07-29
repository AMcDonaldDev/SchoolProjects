/*
 * File: Master.java
 * Author: Allison McDonald
 * Date: 2/2/2020
 * Purpose: This program creates the Master class and main thread
 */

public class Master {
    
    public static void main(String[] args) throws InterruptedException {
        Thread first_child = new Thread(new Worker());
        first_child.start();
        try {
            Thread.currentThread().sleep(1);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e.toString());
        }
        
        Thread second_child = new Thread(new Worker());
        second_child.start();
        try {
            Thread.currentThread().sleep(1);
        } catch(InterruptedException e) {
            System.out.println("Exception: " + e.toString());
        }
        
        Thread third_child = new Thread(new Worker());
        third_child.start();
       try {
           Thread.currentThread().sleep(1);
       } catch(InterruptedException e) {
           System.out.println("Exception: " + e.toString());
       }
    }
}