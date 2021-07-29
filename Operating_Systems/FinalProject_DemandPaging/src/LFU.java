/* 
 * Filename: DemandPaging.java
 * Author: Allison McDonald
 * Date: 3/8/20
 * Purpose: CMSC 412 Final Project
*/

import java.util.HashMap;
import java.util.Scanner;

public class LFU extends Algorithm {
    private int refStringNumber;
    private int physicalFrameCount;
    private int faults = 0;
    private int faultIdentifier = 1;
    private HashMap<Integer, Integer> usedLeast = new HashMap<>();
    
    // Constructor
    public LFU(StringBuffer reference, int frames) {
        super(reference, frames);
    }
    
    // calculate LFU
    public void calculateLFU() {
        Scanner scanIn = new Scanner(System.in);
        boolean flagContinue = true;
        physicalFrameCount = getPhysicalFrameCount();
        if(flagContinue) {
            for(int i = 0; i < referenceString.size(); i++) {
                pageFault.add(i, null);
                victimFrame.add(i, null);
                refStringNumber = getReferenceStringNumber(i);
                if(!usedLeast.containsKey(refStringNumber)) {
                    usedLeast.put(refStringNumber, 1);
                } else {
                    usedLeast.put(refStringNumber, usedLeast.get(refStringNumber++));
                }
                boolean inFrame = checkPhysicalFrame(refStringNumber);
                if(inFrame) {
                    // print table
                } else {
                    int physicalFrameSize = getPhysicalFrameSize();
                    if(physicalFrameCount > physicalFrameSize) {
                        physicalFrame.addFirst(refStringNumber);
                        // print table
                    } else {
                        faults++;
                        pageFault.set(i, faultIdentifier);
                        int removeIndex = getLeastUsed();
                        victimFrame.add(i, physicalFrame.get(removeIndex));
                        physicalFrame.set(removeIndex, refStringNumber);
                        // print
                    }
                }
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
    
    // Determine least used
    public int getLeastUsed() {
        int timesUsed = 0;
        int index = 0;
        int leader = 0;
        for(int i = 0; i < physicalFrameCount; i++) {
            timesUsed = usedLeast.get(physicalFrame.get(i));
            if(timesUsed > leader) {
                leader = timesUsed;
                index = i;
            }
        }
        return index;
    }
}
