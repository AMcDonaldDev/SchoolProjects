/* 
 * Filename: DemandPaging.java
 * Author: Allison McDonald
 * Date: 3/8/20
 * Purpose: CMSC 412 Final Project
*/

import java.util.ArrayList;
import java.util.LinkedList;

public class Algorithm {
    // Instance variables
    public ArrayList<Integer> referenceString;
    public int numberPhysicalFrames;
    public LinkedList<Integer> physicalFrame;
    public ArrayList<Integer> pageFault;
    public ArrayList<Integer> victimFrame;
    
    // Constructor
    public Algorithm(StringBuffer reference, int frames) {
        int arrayListSize = reference.length();
        numberPhysicalFrames = frames;
        referenceString = new ArrayList<>(arrayListSize);
        for(int i = 0; i < arrayListSize; i++) {
            int number = Integer.parseInt(String.valueOf(reference.charAt(i)));
            referenceString.add(number);
        }
        physicalFrame = new LinkedList<>();
        pageFault = new ArrayList<>();
        victimFrame = new ArrayList<>();
    }
    
    // Get current reference string number
    public int getReferenceStringNumber(int i) {
        return referenceString.get(i);
    }
    
    // Get physical frames number
    public int getPhysicalFrameCount() {
        return numberPhysicalFrames;
    }
    
    
    // Get physical frame number array list
    public int getPhysicalFrameSize() {
        return physicalFrame.size();
    }
    
    // Check physical frames for current reference string number
    public boolean checkPhysicalFrame(int refStringNum) {
        boolean isContained = true;
        if(physicalFrame.contains(refStringNum)) {
            return isContained;
        } else {
            isContained = false;
        }
        return isContained;
    }
}
