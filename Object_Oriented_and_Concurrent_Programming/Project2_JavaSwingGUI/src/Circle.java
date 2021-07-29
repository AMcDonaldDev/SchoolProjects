/*
 * File: Circle.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Circle class. This class inherits from TwoDimensionalShape class.
 */

import java.awt.Graphics;
import java.awt.Color;

public class Circle extends TwoDimensionalShape {
    private double radius;
    private final double PI = Math.PI;
    
    public Circle(double rad) {
        super();
        radius = rad;
    }
    
    @Override
    public double area() {
        double a = PI * Math.pow(radius, 2);
        return a;
    }
    
    // draw shape
    public void drawCircle(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(275, 275, (int)radius * 2, (int)radius * 2);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(g);
    }
    
}
