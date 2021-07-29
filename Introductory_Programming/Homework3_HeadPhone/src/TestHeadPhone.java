/*
* File: TestHeadPhone.java
* Author: Allison McDonald
* Date: February 24, 2019
* Purpose: This program tests the headphone class by constructing objects/instances
* and using its methods
*/

import java.awt.Color;

public class TestHeadPhone {	
    public static void main(String[] args)  { 
        
        // Construct a default headphone
        HeadPhone h1 = new HeadPhone();
        
        // Construct a headphone
        HeadPhone h2 = new HeadPhone(1, true, "Bose", Color.red, "UMB27");
        
        // Construct a headphone
        HeadPhone h3 = new HeadPhone(3, false, "Beats", Color.blue, "LTR95");
        
        // Print toString for headphone one
        System.out.println(h1.toString());
        
        // Print toString for headphone two
        System.out.println(h2.toString());
        
        // Print toString for headphone three
        System.out.println(h3.toString());
        
        // Use change volume method for headphone one
        h1.changeVolume(3);
        
        // Use setter methods for headphone one to set manufacturer and model
        h1.setManufacturer("Acme");
        h1.setHeadPhoneModel("ABC123");
        
        // Print toString for headphone one
        System.out.println(h1.toString());
        
    }

}