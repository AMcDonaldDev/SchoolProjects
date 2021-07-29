/*
 * File: Torus.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: Defines Torus class. This class inherits from ThreeDimensionalShape class
 */

public class Torus extends ThreeDimensionalShape {
    
    public Torus(double ir, double or) {
        super();
        innerRadius = ir;
        outerRadius = or;
    }
    
    @Override
    public double volume() {
        double v = 2 * Math.pow(pi, 2) * outerRadius * Math.pow(innerRadius, 2);
        return v;
    }
    
}
