/*
 * File: AMProject1.java
 * Author: Allison McDonald with code from CMSC405P1Template.java
 * Date: 6/2/2020
 */

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;

public class AMProject1 extends JPanel {
    
    private int frameNumber;
    private long elapsedTimeMillis;
    private float pixelSize;
    static int translateX = 0;
    static int translateY = 0;
    static double rotation = 0.0;
    static double scaleX = 1.0;
    static double scaleY = 1.0;
    SquareImage square = new SquareImage();
    LetterEImage letterE = new LetterEImage();
    PlusImage plusSign = new PlusImage();
    BufferedImage sImage = square.getImage(SquareImage.square);
    BufferedImage lImage = letterE.getImage(LetterEImage.letterE);
    BufferedImage pImage = plusSign.getImage(PlusImage.plusSign);
    
    // Constructor
    public AMProject1() {
        setPreferredSize(new Dimension(800, 600));
    }
    
    // Method from CMSC405P1Template.java Code
    // Adjustments to code were to customize for images
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        applyWindowToViewportTransformation(g2, -150, 150, -150, 150, true);
        AffineTransform savedTransform = g2.getTransform();
        switch (frameNumber) {
            case 1:
                translateX = 0;
                translateY = 0;
                scaleX = 1.0;
                scaleY = 1.0;
                rotation = 0;
                break;
            case 2:
                translateX = -5;
                translateY = 7;
                break;
            case 3:
                translateX = -5;
                translateY = 7;
                rotation = 45 * Math.PI / 180.0;
                break;
            case 4:
                translateX = -5;
                translateY = 7;
                rotation = (45 - 90) * Math.PI / 180.0;
                break;
            case 5:
                translateX = -5;
                translateY = 7;
                rotation = (45 - 90) * Math.PI / 180.0;
                scaleX = scaleX * 2.0;
                scaleY = scaleY * 0.5;
                break;
            default:
                break;
        }
        // Add square
        g2.translate(translateX, translateY);
        g2.rotate(rotation);
        g2.scale(scaleX, scaleY);
        g2.drawImage(sImage, 0, 0, this);
        g2.setTransform(savedTransform);
        
        // Add Letter E
        g2.translate(translateX, translateY);
        g2.translate(-50, 50);
        g2.rotate(rotation);
        g2.scale(scaleX, scaleY);
        g2.drawImage(lImage, 0, 0, this);
        g2.setTransform(savedTransform);
        
        // Add plus sign
        g2.translate(translateX, translateY);
        g2.translate(-100, 100);
        g2.rotate(rotation);
        g2.scale(scaleX, scaleY);
        g2.drawImage(pImage, 0, 0, this);
        g2.setTransform(savedTransform);
    }
    
    // Method from CMSC405P1Template.java Code
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth();
        int height = getHeight();
        if (preserveAspect) {
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }
    
    // Method from CMSC405P1Template.java code
    public static void main(String[] args) {
        // Taken from CMSC405P1Template
        JFrame window = new JFrame("McDonald - Project 1");
        AMProject1 panel = new AMProject1();
        window.setContentPane(panel);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(
                (screen.width - window.getWidth()) / 2,
                (screen.height - window.getHeight()) /2);
        Timer animationTimer;
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(1600, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (panel.frameNumber > 5) {
                    panel.frameNumber = 0;
                } else {
                    panel.frameNumber++;
                }
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        window.setVisible(true);
        animationTimer.start();
    }
    
}
