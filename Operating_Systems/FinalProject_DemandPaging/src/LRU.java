/* 
 * Filename: DemandPaging.java
 * Author: Allison McDonald
 * Date: 3/8/20
 * Purpose: CMSC 412 Final Project
*/

import java.util.HashMap;
import java.util.Scanner;

public class LRU extends Algorithm {
    private int refStringNumber;
    private int physicalFrameCount;
    private int faults = 0;
    private int faultIdentifier = 1;
    private HashMap<Integer, Integer> lastRecentlyUsed = new HashMap<>();
    
    // Constructor
    public LRU(StringBuffer reference, int frames) {
        super(reference, frames);
    }
    
    // Calculate LRU
    public void calculateLRU() {
        Scanner scanIn = new Scanner(System.in);
        boolean flagContinue = true;
        physicalFrameCount = getPhysicalFrameCount();
        if(flagContinue) {
            for(int i = 0; i < referenceString.size(); i++) {
                pageFault.add(i, null);
                victimFrame.add(i, null);
                refStringNumber = getReferenceStringNumber(i);
                boolean inFrame = checkPhysicalFrame(refStringNumber);
                if(inFrame) {
                    // print table
                } else {
                    int physicalFrameSize = getPhysicalFrameSize();
                    if(physicalFrameCount > physicalFrameSize) {
                        physicalFrame.addFirst(refStringNumber);
                        // print
                    } else {
                        faults++;
                        pageFault.set(i, faultIdentifier);
                        int removeIndex = getLastRecentlyUsed(i);
                        victimFrame.add(i, physicalFrame.get(removeIndex));
                        physicalFrame.set(removeIndex, refStringNumber);
                        // print table
                    }
                }
                lastRecentlyUsed.put(refStringNumber, i + 1);
                System.out.println("Enter 1 to Continue or 2 to Exit: ");
                int userInput = scanIn.nextInt();
                if(userInput ==1) {
                    flagContinue = true;
                } else {
                    flagContinue = false;
                    break;
                }
            }
        }
        // print table
    }
    
    // Determine last recently used
    public int getLastRecentlyUsed(int i) {
        int max = i;
        int min = 1;
        for(int j = 0; j < physicalFrameCount; j++) {
            int last = lastRecentlyUsed.get(j);
            if(last <= max) {
                max = last;
                min = j;
            }
        }
        return min;
    }
    
}
