/*
 * File: ThreeDimensionalShape.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines ThreeDimensionalShape Class. This class inherits from Shape class.
 */

public abstract class ThreeDimensionalShape extends Shape {
    private int numberOfDimensions;
    
    public ThreeDimensionalShape() {
        numberOfDimensions = 3;
    }
    
    public abstract double volume();
    
}
