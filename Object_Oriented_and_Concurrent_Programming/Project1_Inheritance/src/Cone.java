/*
 * File: Cone.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Cone class. This class inherits from ThreeDimensionalShape class.
 */

public class Cone extends ThreeDimensionalShape {
    
    public Cone(double r, double h) {
        super();
        innerRadius = r;
        height = h;
    }
    
    @Override
    public double volume() {
        double v = (height/3) * pi * Math.pow(innerRadius, 2);
        return v;
    }
    
}
