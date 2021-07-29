/*
 * File: Cylinder.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Cylinder class. This class inherits from ThreeDimensionalShape class
 */

public class Cylinder extends ThreeDimensionalShape {
    
    public Cylinder(double r, double h) {
        super();
        innerRadius = r;
        height = h;
    }
    
    @Override
    public double volume() {
        double v = pi * Math.pow(innerRadius, 2) * height;
        return v;
    }
    
}
