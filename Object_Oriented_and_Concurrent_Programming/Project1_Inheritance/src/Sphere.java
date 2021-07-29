/*
 * File: Sphere.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Sphere class. This class inherits from ThreeDimensionalShape class
 */

public class Sphere extends ThreeDimensionalShape {
    
    public Sphere(double r) {
        super();
        innerRadius = r;
    }
    
    @Override
    public double volume() {
        double v = (Math.pow(innerRadius, 3)/3) * pi * 4;
        return v;
    }
    
}
