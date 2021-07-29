/*
* File: Guitar.java
* Author: Allison McDonald
* Date: February 11, 2019
* Purpose: This program creates the Guitar class which provides the blueprint for 
* a Guitar object/instance
* This class includes the following guitar information: Number of Strings,  
* Guitar Length, Guitar Manufacturer, Guitar Color
*/

// Import statements
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import utils.ColorUtils;

public class Guitar { 
    // Class fields
    private static int numberOfGuitars = 0;
    // Instance fields
    private int numStrings;
    private double guitarLength;
    private String guitarManufacturer;
    private Color guitarColor;
    private int guitarId;
   
    // Constructor
    public Guitar(int numStrings, double guitarLength, String guitarManufacturer,
            Color guitarColor) { 
	this.numStrings = numStrings;
	this.guitarLength = guitarLength;
        this.guitarManufacturer = guitarManufacturer;
        this.guitarColor = guitarColor;
        // Increment number of guitars
        ++numberOfGuitars;
        // Create guitar id
        guitarId = numberOfGuitars;
    }
    
    // Default constructor
    public Guitar() {		
	numStrings = 6;
	guitarLength = 28.2;
        guitarManufacturer = "Gibson";
        guitarColor = Color.red;
        // Increment number of pets
        ++numberOfGuitars;
        // Create pet id
        guitarId = numberOfGuitars;
    }
    
    // Set methods
    // Set number of strings
    public void setNumStrings(int numStrings) {
	this.numStrings = numStrings;
    }
    // Set guitar length
    public void setGuitarLength(double guitarLength) {	
        this.guitarLength = guitarLength;
    }
    // Set guitar manufacturer
    public void setGuitarManufacturer(String guitarManufacturer) {
        this.guitarManufacturer = guitarManufacturer;
    }
    // Set guitar color
    public void setGuitarColor(Color guitarColor) {
        this.guitarColor = guitarColor;
    }
    
    // Get methods
    // Get number of strings
    public int getNumStrings() {
	return numStrings;
    }    
    // Get guitar length
    public double getGuitarLength() {
	return guitarLength;
    }
    // Get guitar manufacturer
    public String getGuitarManufacturer() {
        return guitarManufacturer;
    }
    // Get guitar color
    public String getGuitarColor() {
        return ColorUtils.colorName(guitarColor);
    }
    // Get number of guitars entered
    public static int getNumberOfGuitars() {
        return numberOfGuitars;
    }
    // Get guitar id
    public int getGuitarId() {
        return guitarId;
    }
    
    /* Method that returns 16 randomly selected musical notes of random duration
    * Vaild musical notes are: A, B, C, D, E, F, G
    * Vaild durations are: 0.25, 0.5, 1, 2, 4
    */
    // Declare song variable for return of playGuitar method
    private String song = "";
    public String playGuitar() {
        // Create array list of musical notes
        ArrayList<String> musicalNote = new ArrayList<String>(6);
        musicalNote.add("A");
        musicalNote.add("B");
        musicalNote.add("C");
        musicalNote.add("D");
        musicalNote.add("E");
        musicalNote.add("F");
        musicalNote.add("G");
        // Create array list of durations
        ArrayList<String> duration = new ArrayList<String>(4);
        duration.add("0.25");
        duration.add("0.5");
        duration.add("1");
        duration.add("2");
        duration.add("4");
        // Create random objects for musical notes and duration
        Random randomNote = new Random();
        Random randomDuration = new Random();
        // For loop to generate random notes and duration
        for(int i = 0; i < 16; i++) {
            // Generate random notes and durations
            String notes = musicalNote.get(randomNote.nextInt(6));
            String durations = duration.get(randomDuration.nextInt(4));
            // Combine random notes and durations
            String notesDurations = notes + "(" + durations + ") ";
            // Add combined notes and durations to song string
            song += notesDurations;            
        }
        return song;
    }
    
    /* Method that returns the number of strings, length, manufacturer and color
    * of the guitar
    */
    public String toString() {
        String guitarInfo = "Guitar " + guitarId + " Information: String Number - "
                + numStrings + ", Length - " + guitarLength + ", Manufacturer - "
                + guitarManufacturer + ", Color - " + ColorUtils.colorName(guitarColor);
        return guitarInfo;
    }

}