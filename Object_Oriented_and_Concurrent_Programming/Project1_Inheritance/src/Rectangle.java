/*
 * File: Rectangle.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Rectangle class. This class inherits from TwoDimensionalShape class.
 */

public class Rectangle extends TwoDimensionalShape {
    
    public Rectangle(double w, double h) {
        super();
        width = w;
        height = h;
    }
    
    @Override
    public double area() {
        double a = width * height;
        return a;
    }
    
}
