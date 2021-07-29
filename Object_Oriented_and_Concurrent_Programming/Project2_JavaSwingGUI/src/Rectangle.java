/*
 * File: Rectangle.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Rectangle class. This class inherits from TwoDimensionalShape class.
 */

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends TwoDimensionalShape {
    private double width;
    private double length;
    
    public Rectangle(double w, double l) {
        super();
        width = w;
        length = l;
    }
    
    @Override
    public double area() {
        double a = width * length;
        return a;
    }
    
    // draw shape
    public void drawRectangle(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(275, 275, (int) width, (int) length);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRectangle(g);
    }
    
}
