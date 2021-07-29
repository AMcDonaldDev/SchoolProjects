/*
 * File: Resource
 * Author: Allison McDonald
 * Date: 2/10/20
 * Purpose: This program creates the Resource class
 */

public class Resource implements Cloneable {
    // Class fields
    private static int numberOfResources = 0;
    // Instance variables
    private int resourceID;
    private int resourceAvailable;
    
    // Constructor
    Resource(int resourceAvailable) {
        this.resourceAvailable = resourceAvailable;
        ++numberOfResources;
        resourceID = numberOfResources;
    }
    
    // Set Resource Count
    public void setResourceAvailable(int resourceAvailable) {
        this.resourceAvailable = resourceAvailable;
    }
    
    // Get Resource Count
    public int getResourceAvailable() {
        return resourceAvailable;
    }
    
    // Get resource id
    public int getResourceID() {
        return resourceID;
    }
}
