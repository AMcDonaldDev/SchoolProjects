/*
* File: HeadPhone.java
* Author: Allison McDonald
* Date: February 24, 2019
* Purpose: This program creates the headphone class which provides the blueprint for 
* a headphone object/instance
* This class includes the following headphone information: Volume level,  
* Plugged In, Manufacturer, Color, Model
*/

// Import statements
import java.awt.Color;
import utils.ColorUtils;

public class HeadPhone { 
    // Class fields
    public static final int LOW = 1;
    public static final int MEDIUM = 2;
    public static final int HIGH = 3;
    private static int numberOfHeadPhones = 0;
    // Instance fields
    private int volume = MEDIUM;
    private boolean pluggedIn = false;
    private String manufacturer = "Unknown";
    private Color headPhoneColor = Color.black;
    private String headPhoneModel = "Unknown";
    private int headPhoneId;
    
    // Default constructor
    public HeadPhone() {
        this(MEDIUM, false, "Unknown", Color.black, "Unknown");
    }
   
    // Constructor
    public HeadPhone(int volume, boolean pluggedIn, String manufacturer,
            Color headPhoneColor, String headPhoneModel) { 
	this.volume = volume;
	this.pluggedIn = pluggedIn;
        this.manufacturer = manufacturer;
        this.headPhoneColor = headPhoneColor;
        this.headPhoneModel = headPhoneModel;
        // Increment number of headphones
        ++numberOfHeadPhones;
        // Create headphone id
        headPhoneId = numberOfHeadPhones;
    }
    
    // Setter methods
    // Set volume
    public void setVolume(int volume) {
	this.volume = volume;
    }
    // Set headphone plugged in
    public void setPluggedIn(boolean pluggedIn) {	
        this.pluggedIn = pluggedIn;
    }
    // Set headphone manufacturer
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    // Set headphone color
    public void setHeadPhoneColor(Color headPhoneColor) {
        this.headPhoneColor = headPhoneColor;
    }
    // Set headphone model
    public void setHeadPhoneModel(String headPhoneModel) {
        this.headPhoneModel = headPhoneModel;
    }
    
    // Getter methods
    // Get volume
    public int getVolume() {
	return volume;
    }    
    // Get headphone plugged in
    public boolean getPluggedIn() {
	return pluggedIn;
    }
    // Get headphone manufacturer
    public String getManufacturer() {
        return manufacturer;
    }
    // Get headphone color
    public String getHeadPhoneColor() {
        return ColorUtils.colorName(headPhoneColor);
    }
    // Get headphone model
    public String getHeadPhoneModel() {
        return headPhoneModel;
    }
    // Get headphone id
    public int getHeadPhoneId() {
        return headPhoneId;
    }
    
    // Method that changes the volume of the headphone to a value passed into the method
    public void changeVolume(int volume){
        setVolume(volume);
    }
    
    // Method that returns the headphone volume, plugged in, manufacturer, color and model
    public String toString() {
        String headPhoneInfo = "Headphone " + headPhoneId + " Information: Volume Level - "
                + volume + ", Plugged in - " + pluggedIn + ", Manufacturer - "
                + manufacturer + ", Color - " + ColorUtils.colorName(headPhoneColor)
                + ", Model - " + headPhoneModel;
        return headPhoneInfo;
    }

}