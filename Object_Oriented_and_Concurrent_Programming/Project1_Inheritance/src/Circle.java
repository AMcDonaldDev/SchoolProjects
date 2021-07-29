/*
 * File: Circle.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Circle class. This class inherits from TwoDimensionalShape class.
 */

public class Circle extends TwoDimensionalShape {
    
    public Circle(double radius) {
        super();
        innerRadius = radius;
    }
    
    @Override
    public double area() {
        double a = pi * Math.pow(innerRadius, 2);
        return a;
    }
    
}
