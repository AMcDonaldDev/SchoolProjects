/*
 * File: Cube.java
 * Author: Allison McDonald
 * Date: 7/9/2020
 * Purpose: Defines Cube class. This class inherits from ThreeDimensionalShape class
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Cube extends ThreeDimensionalShape {
    private double width;
    private double length;
    private double height;
    
    public Cube(double w) {
        super();
        width = w;
        length = w;
        height = w;
    }
    
    @Override
    public double volume() {
        double v = width * length * height;
        return v;
    }
    
    // draw shape
    public void drawCube(Graphics g) throws IOException {
        Image cubeImage;
        File cubeFile = new File("Cube.jpg");
        cubeImage = ImageIO.read(cubeFile);
        g.drawImage(cubeImage, 275, 275, (int) width, (int) height, null, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawCube(g);
        } catch(IOException e) {
            JOptionPane frame = new JOptionPane();
            JOptionPane.showMessageDialog(frame, "Cannot load image file.");
        }
    }
    
}
