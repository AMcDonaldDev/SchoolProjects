/*
 * File: ThreeDimensionalShape.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines ThreeDimensionalShape Class. This class inherits from Shape class.
 */

public abstract class ThreeDimensionalShape extends Shape {
    
    public ThreeDimensionalShape() {
        numberOfDimensions = 3;
    }
    
    public abstract double volume();
    
}
