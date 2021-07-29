/*
 * File: Torus.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Torus class. This class inherits from ThreeDimensionalShape class
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Torus extends ThreeDimensionalShape {
    private double innerRadius;
    private double outerRadius;
    private final double PI = Math.PI;
    
    public Torus(double ir, double or) {
        super();
        innerRadius = ir;
        outerRadius = or;
    }
    
    @Override
    public double volume() {
        double v = 2 * Math.pow(PI, 2) * outerRadius * Math.pow(innerRadius, 2);
        return v;
    }
    
    // draw shape
    public void drawTorus(Graphics g) throws IOException {
        Image torusImage;
        File torusFile = new File("Torus.jpg");
        torusImage = ImageIO.read(torusFile);
        g.drawImage(torusImage, 275, 275, (int) innerRadius * 2, (int) outerRadius * 2, null, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawTorus(g);
        } catch(IOException e) {
            JOptionPane frame = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Cannot load image file.");
        }
    }
    
}
