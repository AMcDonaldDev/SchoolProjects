/*
 * File: Cylinder.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Cylinder class. This class inherits from ThreeDimensionalShape class
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Cylinder extends ThreeDimensionalShape {
    private double radius;
    private double height;
    private final double PI = Math.PI;
    
    public Cylinder(double r, double h) {
        super();
        radius = r;
        height = h;
    }
    
    @Override
    public double volume() {
        double v = PI * Math.pow(radius, 2) * height;
        return v;
    }
    
    // draw shape
    public void drawCylinder(Graphics g) throws IOException {
        Image cylinderImage;
        File cylinderFile = new File("Cylinder.jpg");
        cylinderImage = ImageIO.read(cylinderFile);
        g.drawImage(cylinderImage, 275, 275, (int) radius * 2, (int) height, null, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawCylinder(g);
        } catch(IOException e) {
            JOptionPane frame = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Cannot load image file.");
        }
    }
    
}
