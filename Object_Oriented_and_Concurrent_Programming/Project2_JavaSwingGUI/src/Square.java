/*
 * File: Square.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Square class. This class inherits from TwoDimensionalShape class.
 */

import java.awt.Color;
import java.awt.Graphics;

public class Square extends TwoDimensionalShape {
    private double length;
    
    public Square(double l) {
        super();
        length = l;
    }
    
    @Override
    public double area() {
        double a = Math.pow(length, 2);
        return a;
    }
    
    // draw shape
    public void drawSquare(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(275, 275, (int) length, (int) length);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSquare(g);
    }
    
}
