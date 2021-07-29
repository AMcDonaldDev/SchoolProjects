/*
 * File: RightTriangle.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines RightTriangle class. This class inherits from TwoDimensionalShapes class.
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class RightTriangle extends TwoDimensionalShape {
    private double base;
    private double height;
    
    public RightTriangle(double b, double h) {
        super();
        base = b;
        height = h;
    }
    
    @Override
    public double area() {
        double a = (base * height) / 2;
        return a;
    }
    
    // draw shape
    public void drawRightTriangle(Graphics g) throws IOException {
        Image rightTriangleImage;
        File rightTriangleFile = new File("RightTriangle.jpg");
        rightTriangleImage = ImageIO.read(rightTriangleFile);
        g.drawImage(rightTriangleImage, 275, 275, (int) base, (int) height, null, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawRightTriangle(g);
        } catch(IOException e) {
            JOptionPane frame = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Cannot load image file.");
        }
    }
    
}
