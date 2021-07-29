/*
 * File: RightTriangle.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines RightTriangle class. This class inherits from TwoDimensionalShapes class.
 */

public class RightTriangle extends TwoDimensionalShape {
    
    public RightTriangle(double b, double h) {
        super();
        base = b;
        height = h;
    }
    
    @Override
    public double area() {
        double a = (base * height) / 2;
        return a;
    }
    
}
