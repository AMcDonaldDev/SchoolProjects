/*
 * File: Cone.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Cone class. This class inherits from ThreeDimensionalShape class.
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Cone extends ThreeDimensionalShape {
    private double radius;
    private double height;
    private final double PI = Math.PI;
    
    public Cone(double r, double h) {
        super();
        radius = r;
        height = h;
    }
    
    @Override
    public double volume() {
        double v = (height/3) * PI * Math.pow(radius, 2);
        return v;
    }
    
    // draw shape
    public void drawCone(Graphics g) throws IOException {
        Image coneImage;
        File coneFile = new File("Cone.jpg");
        coneImage = ImageIO.read(coneFile);
        g.drawImage(coneImage, 275, 275, (int) radius * 2, (int) height, null, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawCone(g);
        } catch(IOException e) {
            JOptionPane frame = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Cannot load image file.");
        }
    }
    
}
