/*
 * File: Sphere.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Sphere class. This class inherits from ThreeDimensionalShape class
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Sphere extends ThreeDimensionalShape {
    private double radius;
    private final double PI = Math.PI;
    
    public Sphere(double r) {
        super();
        radius = r;
    }
    
    @Override
    public double volume() {
        double v = (Math.pow(radius, 3)/3) * PI * 4;
        return v;
    }
    
    // draw shape
    public void drawSphere(Graphics g) throws IOException {
        Image sphereImage;
        File sphereFile = new File("Sphere.jpg");
        sphereImage = ImageIO.read(sphereFile);
        g.drawImage(sphereImage, 275, 275, (int) radius * 2, (int) radius * 2, null, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawSphere(g);
        } catch(IOException e) {
            JOptionPane frame = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Cannot load image file.");
        }
    }
    
}
