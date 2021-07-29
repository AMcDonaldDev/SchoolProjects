/*
 * File: ShapeInheritance.java
 * Author: Allison McDonald
 * Date: 6/30/20
 * Purpose: CMSC 335 Project 1
 */

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShapeInheritance {
    static boolean flagContinue;
    
    public static void main(String[] args) {
        Scanner scanIn = new Scanner(System.in);
        flagContinue = true;
        System.out.println("-----Shape Inheritance Program-----");
        System.out.println("Hello there!");
        System.out.println("This program calculates the area of a 2D shape or the volume of a 3D shape.");
        System.out.println("Let's get started!");
        while(flagContinue) {
            displayTextMenu();
            try {
                int userSelection = scanIn.nextInt();
                runUserSelection(userSelection);
            } catch(InputMismatchException e) {
                System.out.println("Invalid number entered.");
                scanIn.next();
            }
        }
    }
    
    // To display text menu
    private static void displayTextMenu() {
        System.out.println();
        System.out.println("Please select which shape to build from the below menu: ");
        System.out.println();
        System.out.println("0 - Exit");
        System.out.println("1 - Circle");
        System.out.println("2 - Cone");
        System.out.println("3 - Cube");
        System.out.println("4 - Cylinder");
        System.out.println("5 - Rectangle");
        System.out.println("6 - Sphere");
        System.out.println("7 - Square");
        System.out.println("8 - Torus");
        System.out.println("9 - Right Triangle");
        System.out.println();
        System.out.print("Enter your selection: ");
    }
    
    // To run option based on user selection
    private static void runUserSelection(int userSelection) {
        try {
            switch(userSelection) {
            case 0:
                System.out.println("You have selected to exit.");
                System.out.println("Thank you for choosing the Shape Inheritance Program!");
                Date date = new Date();
                System.out.println("Current Date & Time: " + date);
                flagContinue = false;
                break;
            case 1:
                System.out.println("You have selected to build a circle.");
                double radiusCircle = getUserRadiusInput();
                Circle circle = new Circle(radiusCircle);
                System.out.println("The area for this circle is " + circle.area() + ".");
                break;
            case 2:
                System.out.println("You have selected to build a cone.");
                double radiusCone = getUserRadiusInput();
                double heightCone = getUserHeightInput();
                Cone cone = new Cone(radiusCone, heightCone);
                System.out.println("The volume for this cone is " + cone.volume() + ".");
                break;
            case 3:
                System.out.println("You have selected to build a cube.");
                double widthCube = getUserWidthInput();
                Cube cube = new Cube(widthCube);
                System.out.println("The volume for this cube is " + cube.volume() + ".");
                break;
            case 4:
                System.out.println("You have selected to build a cylinder.");
                double radiusCylinder = getUserRadiusInput();
                double heightCylinder = getUserHeightInput();
                Cylinder cylinder = new Cylinder(radiusCylinder, heightCylinder);
                System.out.println("The volume for this cylinder is " + cylinder.volume() + ".");
                break;
            case 5:
                System.out.println("You have selected to build a rectangle.");
                double widthRectangle = getUserWidthInput();
                double heightRectangle = getUserHeightInput();
                Rectangle rectangle = new Rectangle(widthRectangle, heightRectangle);
                System.out.println("The area for this rectangle is " + rectangle.area() + ".");
                break;
            case 6:
                System.out.println("You have selected to build a sphere.");
                double radiusSphere = getUserRadiusInput();
                Sphere sphere = new Sphere(radiusSphere);
                System.out.println("The volume for this sphere is " + sphere.volume() + ".");
                break;
            case 7:
                System.out.println("You have selected to build a square.");
                double widthSquare = getUserWidthInput();
                Square square = new Square(widthSquare, widthSquare);
                System.out.println("The area for this square is " + square.area() + ".");
                break;
            case 8:
                System.out.println("You have selected to build a torus.");
                double innerRadiusTorus = getUserInnerRadiusInput();
                double outerRadiusTorus = getUserOuterRadiusInput();
                Torus torus = new Torus(innerRadiusTorus, outerRadiusTorus);
                System.out.println("The volume for this torus is " + torus.volume() + ".");
                break;
            case 9:
                System.out.println("You have selected to build a right triangle.");
                double baseRightTriangle = getUserBaseInput();
                double heightRightTriangle = getUserHeightInput();
                RightTriangle rightTriangle = new RightTriangle(baseRightTriangle, heightRightTriangle);
                System.out.println("The area for this right triangle is " + rightTriangle.area() + ".");
                break;
            default:
                break;
            }
        } catch(InputMismatchException e) {
            System.out.println("Invalid number entered.");
        }
        if((userSelection < 0) || (userSelection > 9)) {
            System.out.println("Invalid selection entered.");
        }
    }
    
    // To get radius from user
    private static double getUserRadiusInput() throws InputMismatchException {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter the radius: ");
        double radius = scanIn.nextDouble();
        System.out.println();
        return radius;
    }
    
    // To get inner radius from user
    private static double getUserInnerRadiusInput() throws InputMismatchException {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter the inner radius: ");
        double innerRadius = scanIn.nextDouble();
        System.out.println();
        return innerRadius;
    }
    
    // To get outer radius from user
    private static double getUserOuterRadiusInput() throws InputMismatchException {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter the outer radius: ");
        double outerRadius = scanIn.nextDouble();
        System.out.println();
        return outerRadius;
    }
    
    // To get height from user
    private static double getUserHeightInput() throws InputMismatchException {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter the height: ");
        double height = scanIn.nextDouble();
        System.out.println();
        return height;
    }
    
    // To get width from user
    private static double getUserWidthInput() throws InputMismatchException {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter the width: ");
        double width = scanIn.nextDouble();
        System.out.println();
        return width;
    }
    
    // To get base from user
    private static double getUserBaseInput() throws InputMismatchException{
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter the base: ");
        double base = scanIn.nextDouble();
        System.out.println();
        return base;
    }
    
}
