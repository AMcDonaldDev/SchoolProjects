/*
 * File: Square.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Square class. This class inherits from TwoDimensionalShape class.
 */

public class Square extends TwoDimensionalShape {
    
    public Square(double w, double h) {
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
