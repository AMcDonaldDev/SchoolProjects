/*
 * File: TrafficLight.java
 * Author: Allison McDonald
 * Date: 8/8/2020
 * Purpose: CMSC 335 Project 3(Final) - This class defines the traffic light.
 * Color Value 1 = Red, Color Value 2 = Green, Color Value 3 = Yellow
 */

package project3;

import java.awt.Color;
import java.awt.Graphics;

public class TrafficLight implements Runnable {
    private volatile Color color;
    private Graphics g;
    private volatile String colorName;
    private volatile int colorValue;
    private boolean run = true;
    private int test = 1;
    public Thread t;
    
    public TrafficLight() {
        color = Color.RED;
        colorName = "Red";
        colorValue = 1;
        t = new Thread(this);
    }
    
    private void changeColor() {
        switch(colorValue) {
            case 1:
                colorValue = 2;
                color = Color.GREEN;
                colorName = "Green";
                break;
            case 2:
                colorValue = 3;
                color = Color.YELLOW;
                colorName = "Yellow";
                break;
            case 3:
                colorValue = 1;
                color = Color.RED;
                colorName = "Red";
                break;
        }
    }
    
    public int getColorValue() {
        return colorValue;
    }
    
    public String getColorName() {
        return colorName;
    }
    
    public Color getColor() {
        return color;
    }
    
    // Use when you want to end; Cannot restart
    public void cancel() {
        run = false;
    }
    
    // Use when you want to pause; Can restart
    public void pause() {
        run = false;
    }
    
    // Use when you want to restart
    public void restart() {
        run = true;
        t = new Thread();
        t.start();
    }

    @Override
    public void run() {
        while(run) {
            color = getColor();
            try {
                switch(colorValue) {
                    case 1:
                        Thread.sleep(7000);
                        break;
                    case 2:
                        Thread.sleep(10000);
                        break;
                    case 3:
                        Thread.sleep(3000);
                        break;
                }
            } catch(InterruptedException exc) {
                System.out.println(exc);
            }
            changeColor();
        }
    }
}
