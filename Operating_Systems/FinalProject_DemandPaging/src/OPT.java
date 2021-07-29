/* 
 * Filename: DemandPaging.java
 * Author: Allison McDonald
 * Date: 3/8/20
 * Purpose: CMSC 412 Final Project
*/

import java.util.Scanner;

public class OPT extends Algorithm {
    private int refStringNumber;
    private int physicalFrameCount;
    private int faults = 0;
    private int faultIdentifier = 1;
    private int[] optimalArray;
    
    // Constructor
    public OPT(StringBuffer reference, int frames) {
        super(reference, frames);
    }
    
    // Calculate OPT
    public void calculateOPT() {
        Scanner scanIn = new Scanner(System.in);
        boolean flagContinue = true;
        physicalFrameCount = getPhysicalFrameCount();
        optimalArray = new int[referenceString.size()];
        for(int k = 0; k < referenceString.size(); k++) {
            optimalArray[k] = getReferenceStringNumber(k);
        }
        if(flagContinue) {
            for(int i = 0; i < referenceString.size(); i++) {
                pageFault.add(i, null);
                victimFrame.add(i, null);
                refStringNumber = getReferenceStringNumber(i);
                boolean inFrame = checkPhysicalFrame(refStringNumber);
                if(inFrame) {
                    // print
                } else {
                    int physicalFrameSize = getPhysicalFrameSize();
                    if(physicalFrameCount > physicalFrameSize) {
                        physicalFrame.addFirst(refStringNumber);
                        // print table
                    } else {
                        faults++;
                        pageFault.set(i, faultIdentifier);
                        int removeIndex = getOptimal(i);
                        victimFrame.add(i, physicalFrame.get(removeIndex));
                        physicalFrame.set(removeIndex, refStringNumber);
                        // print table
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
    
    // Determine optimal victim frame
    public int getOptimal(int i) {
        int optimalIndex = i + 1;
        int current = 0;
        int remove = 0;
        for(int j = 0; j < physicalFrameCount; j++) {
            for(int m = i + 1; m < referenceString.size(); m++) {
                current = physicalFrame.get(j).compareTo(referenceString.lastIndexOf(m));
                if(current > optimalIndex) {
                    optimalIndex = current;
                    remove = j;
                }
            }
        }
        return remove;
    }
}
