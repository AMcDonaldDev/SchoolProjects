/*
 * File: Process.java
 * Author: Allison McDonald
 * Date: 2/19/20
 * Purpose: This program creates the Process class
 */

import java.util.ArrayList;

public class Process {
    // Class fields
    private static int numberOfProcesses = 0;
    // Instance fields
    private int processID;
    private int[] maxResources;
    private int[] allocatedResources;
    private int[] neededResources;
    
    // Constructor
    Process(int[] maxResources, int[] allocatedResources, int[] neededResources) {
        this.maxResources = maxResources;
        this.allocatedResources = allocatedResources;
        this.neededResources = neededResources;
        ++numberOfProcesses;
        processID = numberOfProcesses;
    }
    
    // Get process id
    public int getProcessID() {
    return processID;
    }
    
    // Get max resources
    public int getMaxResources(int i) {
        return maxResources[i];
    }
    
    // Get allocated resources
    public int getAllocatedResources(int i) {
        return allocatedResources[i];
    }
    
    // Get needed resources
    public int getNeededResources(int i) {
        return neededResources[i];
    }
    
    // To check current available resources against needed resources
    public boolean checkAvailableResources(ArrayList<Resource> resourcesAvail) {
        for(int i = 0; i < resourcesAvail.size(); i++) {
            if(neededResources[i] <= resourcesAvail.get(i).getResourceAvailable()) {
                return true;
            }
        }
        return false;
    }
    
    
    //    public boolean checkForResources(ArrayList<Resource> resources) {
////        boolean check;
////        check = false;
//        for(int i = 0; i < numberOfProcesses; i++) {
//            for(int j = 0; j < resources.size(); j++) {
//                int need = neededResources[i];
//                int have = resources.get(j).getResourceAvailable();
//                if(need < have) {
//                    System.out.println(processID + " Needed: " + neededResources[i]);
//                    System.out.println(resources.get(j).getResourceID() + " Available: " + resources.get(j).getResourceAvailable());
//                }
////                if(getNeededResources(i) < resources.get(j).getResourceAvailable()) {
//  //                  System.out.println(processID + " Needed: " + getNeededResources(i));
////                    System.out.println(resources.get(j).getResourceID() + " Available: " + resources.get(j).getResourceAvailable());
////                    return true;
// //               }
//            }
////            int need = neededResources[i];
////            int have = resources.get(i).getResourceAvailable();
////            if(neededResources[i] < resources.get(i).getResourceAvailable()) {
////                System.out.println(neededResources[i]);
////                System.out.println(resources.get(i).getResourceAvailable());
// //               return true;
////            } else {
////                return false;
////            }
//        }
//        return false;
//    }
    
    
}
