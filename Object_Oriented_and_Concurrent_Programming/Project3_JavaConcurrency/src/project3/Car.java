/*
 * File: Car.java
 * Author: Allison McDonald
 * Date: 8/8/2020
 * Purpose: CMSC 335 Project 3(Final) - This class defines the car.
 */

package project3;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Car implements Runnable {
    private static int numberOfCars = 0;
    
    private int carID;
    private String carName;
    private volatile int x;
    private int y;
    private Color color;
    private int height;
    private int width;
    private int currentSpeed;
    private TrafficLight trafficLight;
    private volatile int trafficLightColorValue;
    private boolean run = true;
    private int intersectionDistance;
    public Thread t;
    
    private Color[] colorArray = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                    Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK};
    
    // Constructor
    public Car(TrafficLight lightColor, int interDistance) {
        ++numberOfCars;
        carID = numberOfCars;
        carName = Integer.toString(carID);
        x = generateRandomX();
        y = 120;
        currentSpeed = generateRandomSpeed();
        color = colorArray[generateRandomColor()];
        height = 15;
        width = 25;
        trafficLight = lightColor;
        trafficLightColorValue = lightColor.getColorValue();
        intersectionDistance = interDistance;
        t = new Thread(this);
    }
    
    private int generateRandomSpeed() {
        int randomSpeed = ThreadLocalRandom.current().nextInt(6, 20);
        return randomSpeed;
    }
    
    private int generateRandomColor() {
        int randomColor = ThreadLocalRandom.current().nextInt(colorArray.length);
        return randomColor;
    }
    
    private int generateRandomX() {
        int randomX = ThreadLocalRandom.current().nextInt(0, 120);
        return randomX;
    }
    
    private int getLightValue() {
        return trafficLightColorValue = trafficLight.getColorValue();
    }
    
    private synchronized void setX(int newX) {
        x = newX;
    }
    
    public String getName() {
        return carName;
    }
    
    public Color getColor() {
        return color;
    }
    
    public int getCarID() {
        return carID;
    }
    
    public String getCurrentSpeed() {
        return Integer.toString(currentSpeed);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public String getLocation() {
        String xS = Integer.toString(x);
        String yS = Integer.toString(y);
        return "(" + xS + ", " + yS + ")";
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
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
        t = new Thread(this);
        t.start();
    }

    // Still need to address a car not at an intersection - attempted but unable to complete
    @Override
    public void run() {
        while(run) {
            int lightValue = getLightValue();
            if(x < 900){
                if(lightValue == 2 || lightValue == 3 && x < 906) {
                setX(x + currentSpeed);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
