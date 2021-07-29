/*
 * File: TwoDimensionalShape.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines TwoDimensionalShape class. This class inherits from Shape class.
 */

public abstract class TwoDimensionalShape extends Shape {
    
    public TwoDimensionalShape() {
        numberOfDimensions = 2;
    }
    
    public abstract double area();
    
}
