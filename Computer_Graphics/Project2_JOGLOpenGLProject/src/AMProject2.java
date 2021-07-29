/*
 * File: AMProject2.java
 * Author: Allison McDonald & JoglStarter.java & UnlitCube.java
 * Date: 6/16/2020
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;

public class AMProject2 extends GLJPanel implements GLEventListener, KeyListener {
    
    public static void main(String[] args) {
        JFrame window = new JFrame("JOGL OpenGL Project");
        AMProject2 panel = new AMProject2();
        window.setContentPane(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
    }
    
    // Variables used to set color for cube panels; fb(front/back), lr(left/right, tb(top/bottom)
    private double fbPanelR = 0.0;
    private double fbPanelG = 0.0;
    private double fbPanelB = 0.0;
    private double lrPanelR = 0.0;
    private double lrPanelG = 0.0;
    private double lrPanelB = 0.0;
    private double tbPanelR = 0.0;
    private double tbPanelG = 0.0;
    private double tbPanelB = 0.0;
    
    // Constructor
    public AMProject2() {
        super(new GLCapabilities(null));
        setPreferredSize(new Dimension(720, 720));
        addGLEventListener(this);
        addKeyListener(this);
        setLayout(new BorderLayout());
    }
    
    // Variables used to rotate the graphics using the arrow keys
    double rotateX = 5;
    double rotateY = -5;
    double rotateZ = 0;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.3F, 0.3F, 0.3F, 1.0F);
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0,0,0,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-4, 4, -4, 4, -3, 3);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glLoadIdentity();
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(rotateX,1,0,0);
        plusSign(gl, 0.25, 0.25, 0.25);

        gl.glLoadIdentity();
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(rotateX,1,0,0);
        flower(gl, 0.25, 0.25, 0.25);
        
        gl.glLoadIdentity();
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(rotateX,1,0,0);
        hollowCube(gl, 0.25, 0.25, 0.25);
        
        // Fighter Jet
        gl.glLoadIdentity();
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(rotateX,1,0,0);
        fighterJet(gl, 0.25, 0.25, 0.25);
        
        // Pineapple
        gl.glLoadIdentity();
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(rotateX,1,0,0);
        pineapple(gl, 0.5, 0.5, 0.5);
        
        // Cube Pyramid
        gl.glLoadIdentity();
        gl.glRotated(rotateZ,0,0,1);
        gl.glRotated(rotateY,0,1,0);
        gl.glRotated(rotateX,1,0,0);
        cubePyramid(gl, 0.25, 0.25, 0.25);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int i, int i1, int i2, int i3) {
    }
    
    // Code from UnlitCube.java
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    // Code from UnlitCube.java
    @Override
    public void keyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();
        if ( key == KeyEvent.VK_LEFT )
            rotateY -= 15;
         else if ( key == KeyEvent.VK_RIGHT )
            rotateY += 15;
         else if ( key == KeyEvent.VK_DOWN)
            rotateX += 15;
         else if ( key == KeyEvent.VK_UP )
            rotateX -= 15;
         else if ( key == KeyEvent.VK_PAGE_UP )
            rotateZ += 15;
         else if ( key == KeyEvent.VK_PAGE_DOWN )
            rotateZ -= 15;
         else if ( key == KeyEvent.VK_HOME )
            rotateX = rotateY = rotateZ = 0;
        repaint();
    }
    
    // Code from UnlitCube.java
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    // Code modified from UnlitCube.java
    private void square(GL2 gl, double r, double g, double b) {
        gl.glColor3d(r, g, b);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex3d(-0.5,-0.5, 0.5);
        gl.glVertex3d(0.5, -0.5, 0.5);
        gl.glVertex3d(0.5, 0.5, 0.5);
        gl.glVertex3d(-0.5, 0.5, 0.5);
        gl.glEnd();
    }
    
    // Code modified from UnlitCube.java
    private void cube(GL2 gl, double xSize, double ySize, double zSize) {
        gl.glPushMatrix();
        gl.glScaled(xSize, ySize, zSize);
        // Front
        square(gl, fbPanelR, fbPanelG, fbPanelB);
        
        gl.glPushMatrix();
        gl.glRotated(90, 0, 1, 0);
        // Right
        square(gl, lrPanelR, lrPanelG, lrPanelB);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotated(-90, 1, 0, 0);
        // Top
        square(gl, tbPanelR, tbPanelG, tbPanelB);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotated(180, 0, 1, 0);
        // Back
        square(gl, fbPanelR, fbPanelG, fbPanelB);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotated(-90, 0, 1, 0);
        // Left
        square(gl, lrPanelR, lrPanelG, lrPanelB);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotated(90, 1, 0, 0);
        // Bottom
        square(gl, tbPanelR, tbPanelG, tbPanelB);
        gl.glPopMatrix();
        
        gl.glPopMatrix();
    }
    
    // Plus Sign Graphic
    private void plusSign(GL2 gl, double xSize, double ySize, double zSize) {
        setPanelColors(0.0, 0.878, 0.665, 0.0, 0.686, 0.665, 0.0, 0.686, 0.665);
        gl.glRotated(0, 0, 0, 1);
        gl.glRotated(-10, 0, 1, 0);
        gl.glRotated(10, 1, 0, 0);
        gl.glTranslated(-2.25, 2.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0.25, 0);
        cube(gl, xSize, ySize, zSize);
    }
    
    // Flower Graphic
    private void flower(GL2 gl, double xSize, double ySize, double zSize) {
        setPanelColors(1.0, 0.857, 1.0, 1.0, 0.744, 1.0, 1.0, 0.744, 1.0);
        gl.glRotated(-45, 0, 0, 1);
        gl.glRotated(15, 0, 1, 0);
        gl.glRotated(-15, 1, 0, 0);
        gl.glTranslated(-2.0, -1.25, -0.25);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, 0.5, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.5, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, -0.5, 0);
        cube(gl, xSize, ySize, zSize);
    }
    
    // Hollow Cube Graphic
    private void hollowCube(GL2 gl, double xSize, double ySize, double zSize) {
        setPanelColors(0.159, 0.410, 0.439, 0.159, 0.594, 0.439, 0.159, 0.703, 0.439);
        gl.glRotated(0, 0, 0, 1);
        gl.glRotated(-25, 0, 1, 0);
        gl.glRotated(10, 1, 0, 0);
        gl.glTranslated(-2.75, -0.5, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, 0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, 0.25, 0);
        cube(gl, xSize, ySize, zSize);
    }
    
    // Fighter Jet Graphic
    private void fighterJet(GL2 gl, double xSize, double ySize, double zSize) {
        setPanelColors(0.544, 0.502, 0.548, 0.544, 0.351, 0.548, 0.544, 0.146, 0.548);
        gl.glRotated(45, 0, 0, 1);
        gl.glRotated(-15, 0, 1, 0);
        gl.glRotated(-10, 1, 0, 0);
        gl.glTranslated(1.0, 1.75, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, 0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, 0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0, -0.75, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.5, 0.25, 0);
        gl.glRotated(180, 0, 0, 1);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-.125, 0.125, 0);
        cube(gl, xSize, ySize, zSize);
    }
    
    // Pineapple Graphic; Code modified UnlitCube.java
    private void pineapple(GL2 gl, double xSize, double ySize, double zSize) {
        
        double[][] vertexList = {{-0.25,1,0.125}, {-0.25,1,-0.125}, {-0.25,1.25,0.125},
            {-0.25,1.25,-0.125}, {-0.125,1,0.125}, {-0.125,1,-0.125}, {0,1.25,0.125},
            {0,1.25,-0.125}, {0.125,1,0.125}, {0.125,1,-0.125}, {0.25,1.25,0.125},
            {0.25,1.25,-0.125}, {0.25,1,0.125}, {0.25,1,-0.125} };
        
        int[][] faceList = {{0,1,3,2}, {0,2,4}, {1,3,5},
            {4,6,8}, {5,7,9}, {8,10,12}, {9,11,13}, {12,10,11,13}, {0,1,13,12} };
        
        setPanelColors(1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0);
        gl.glRotated(0, 0, 0, 1);
        gl.glRotated(10, 0, 1, 0);
        gl.glRotated(-10, 1, 0, 0);
        gl.glTranslated(-0.5, -1, 0);
        for (int i = 0; i < faceList.length; i++) {
            gl.glColor3f(0,1,0 );
            gl.glBegin(GL2.GL_TRIANGLE_FAN);
            for (int j = 0; j < faceList[i].length; j++) {
                int vertexNum = faceList[i][j];
                double[] vertexCoords = vertexList[vertexNum];
                gl.glVertex3dv( vertexCoords, 0 );
            }
            gl.glEnd();
        }
        gl.glTranslated(0, 0.75, 0);
        cube(gl, xSize, ySize, zSize);
    }
    
    // Cube Pyramid Graphic
    private void cubePyramid(GL2 gl, double xSize, double ySize, double zSize) {
        setPanelColors(0.853, 0.146, 0.548, 0.552, 0.146, 0.757, 0.335, 0.0, 0.619);
        gl.glRotated(0, 0, 0, 1);
        gl.glRotated(-25, 0, 1, 0);
        gl.glRotated(0, 1, 0, 0);
        gl.glTranslated(1.5, 1.5, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(0.25, -0.25, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
        gl.glTranslated(-0.25, 0, 0);
        cube(gl, xSize, ySize, zSize);
    }
    
    // Method to set the cube panel colors
    private void setPanelColors(double fbR, double fbG, double fbB, double lrR,
            double lrG, double lrB, double tbR, double tbG, double tbB) {
        fbPanelR = fbR;
        fbPanelG = fbG;
        fbPanelB = fbB;
        lrPanelR = lrR;
        lrPanelG = lrG;
        lrPanelB = lrB;
        tbPanelR = tbR;
        tbPanelG = tbG;
        tbPanelB = tbB;
    }
}
