/*
* File: TestGuitar.java
* Author: Allison McDonald
* Date: February 11, 2019
* Purpose: This program tests the Guitar class by constructing objects/instances
* and using its methods
*/

import java.awt.Color;

public class TestGuitar {	
    public static void main(String[] args)  { 
        
        // Construct a default guitar
        Guitar g1 = new Guitar();
        
        // Construct a guitar
        Guitar g2 = new Guitar(8, 25, "Fender", Color.black);
        
        // Construct a guitar
        Guitar g3 = new Guitar(10, 27, "Maestro", Color.blue);
        
        // Print toString for guitar one
        System.out.println(g1.toString());
        // Print playGuitar for guitar one
        System.out.println("Play these notes: " + g1.playGuitar() + "\n");
        
        // Print toString for guitar two
        System.out.println(g2.toString());
        // Print playGuitar for guitar two
        System.out.println("Play these notes: " + g2.playGuitar() + "\n");
        
        // Print toString for guitar three
        System.out.println(g3.toString());
        // Print playGuitar for guitar three
        System.out.println("Play these notes: " + g3.playGuitar() + "\n");
        
        // Use get method for guitar one
        System.out.println("Guitar " + g1.getGuitarId() + " has " + g1.getNumStrings()
            + " strings, measures " + g1.getGuitarLength() + " in length and is manufactured by "
            + g1.getGuitarManufacturer() + " in the color " + g1.getGuitarColor() + ".\n");
        
        // Use get method for guitar two
        System.out.println("Guitar " + g2.getGuitarId() + " has " + g2.getNumStrings()
            + " strings, measures " + g2.getGuitarLength() + " in length and is manufactured by "
            + g2.getGuitarManufacturer() + " in the color " + g2.getGuitarColor() + ".\n");
        
        // Use get meth for guitar three
        System.out.println("Guitar " + g3.getGuitarId() + " has " + g3.getNumStrings()
            + " strings, measures " + g3.getGuitarLength() + " in length and is manufactured by "
            + g3.getGuitarManufacturer() + " in the color " + g3.getGuitarColor() + ".\n");
    }

}