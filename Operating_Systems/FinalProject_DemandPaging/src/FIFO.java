/* 
 * Filename: DemandPaging.java
 * Author: Allison McDonald
 * Date: 3/8/20
 * Purpose: CMSC 412 Final Project
*/

import java.util.Scanner;

public class FIFO extends Algorithm {
    private int refStringNumber;
    private int physicalFrameCount;
    private int faults = 0;
    private int faultIdentifier = 1;
    
    // Constructor
    public FIFO(StringBuffer reference, int frames) {
        super(reference, frames);
    }
    
    // Calculate FIFO
    public void calculateFIFO() {
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
                        victimFrame.add(i, physicalFrame.getLast());
                        physicalFrame.removeLast();
                        physicalFrame.addFirst(refStringNumber);
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
    
    // Create table not completed
    public void createTable() {
        System.out.println(super.referenceString);
        for(int i = 0; i < numberPhysicalFrames; i++){
            for(int j = 0; j < referenceString.size(); j++)
            System.out.println("PF " + i + " " + physicalFrame.get(i));
        }
    }
}
