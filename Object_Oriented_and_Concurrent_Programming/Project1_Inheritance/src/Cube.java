/*
 * File: Cube.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Cube class. This class inherits from ThreeDimensionalShape class
 */

public class Cube extends ThreeDimensionalShape {
    
    public Cube(double w) {
        super();
        side = w;
    }
    
    @Override
    public double volume() {
        double v = Math.pow(side, 3);
        return v;
    }
    
}
