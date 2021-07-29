/*
 * File: Bankers.java
 * Author: Allison McDonald
 * Date: 2/19/20
 * Purpose: This program implements the Banker's algorithm
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class Bankers {

    public static void main(String[] args) throws IOException {
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Process> processes = new ArrayList<>();
        Resource r = null;
        Process p = null;
        BufferedReader inputStream = null;
        Scanner scannerIn = new Scanner(System.in);
        String file;
        String fileLine;
        System.out.println("Please enter file name: ");
        file = scannerIn.nextLine();
        
        // import file
        try {
            inputStream = new BufferedReader(new FileReader(file));
            while((fileLine = inputStream.readLine()) != null) {
                String[] rData = new String[9];
                String[] pLineDataMax = new String[9];
                String[] pLineDataAllocated = new String[9];
                String[] pLineDataNeeded = new String[9];
                if(fileLine.startsWith("R")) {
                    String[] rLineData = StringUtils.substringsBetween(fileLine, "[", "]");
                    rData = rLineData[0].split(", ");
                    for (String rData1 : rData) {
                        r = new Resource(Integer.parseInt(rData1));
                        resources.add(r);
                    }
                } if(fileLine.startsWith("P")) {
                    String[] pLineData = StringUtils.substringsBetween(fileLine, "[", "]");
                    pLineDataMax = pLineData[0].split(", ");
                    pLineDataAllocated = pLineData[1].split(", ");
                    pLineDataNeeded = pLineData[2].split(", ");
                    p = new Process(convertToIntArray(pLineDataMax), 
                            convertToIntArray(pLineDataAllocated),
                            convertToIntArray(pLineDataNeeded));
                    processes.add(p);
                }
            }
        } catch(IOException io) {
            System.out.println("File IO exception" + io.getMessage());
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch(IOException io) {
                System.out.println("Issue closing the files " + io.getMessage());
            }
        }
        System.out.println();
        System.out.println("List of all safe solutions: ");
        
        // create copy of processes array
        ArrayList<Process> currProcesses = new ArrayList<>(processes);
        
        // calculate initial availabe resources
        ArrayList<Resource> currResources = new ArrayList<>(resources);
       for(int i = 0; i < resources.size(); i++) {
           int totalAllocated = 0;
           int begResource = 0;
           int totalAvailable = 0;
           for(int j = 0; j < processes.size(); j++) {
               totalAllocated += processes.get(j).getAllocatedResources(i);
           }
           begResource = resources.get(i).getResourceAvailable();
           totalAvailable = begResource - totalAllocated;
           currResources.get(i).setResourceAvailable(totalAvailable);
       }
       
        // create empty array list for reviewed processes
        ArrayList<Process> review = new ArrayList<>();
        
        // process data
        recursiveProcessing(currProcesses, currResources, review, processes);
    }
    
    public static int[] convertToIntArray(String[] array) {
        int[] intArray = new int[9];
        for(int i = 0; i < array.length; i++) {
            intArray[i] = Integer.parseInt(array[i]);
        }
        return intArray;
    }
    
    public static void calculateResources(ArrayList<Resource> newCurrResources,
            ArrayList<Process> processes) {
        for(int i = 0; i < newCurrResources.size(); i++) {
                int totalAllocated = 0;
                int begResource = 0;
                int totalAvailable = 0;
                for(int j = 0; j < processes.size(); j++) {
                    totalAllocated += processes.get(i).getAllocatedResources(i);
                }
                begResource = newCurrResources.get(i).getResourceAvailable();
                totalAvailable = begResource + totalAllocated;
                newCurrResources.get(i).setResourceAvailable(totalAvailable);
            }
    }
    
    // recursive processing to find safe processing sequence
    public static void recursiveProcessing(ArrayList<Process> currProcesses,
            ArrayList<Resource> currResources, ArrayList<Process> review,
            ArrayList<Process> processes) {
        for(Process process : currProcesses) {
            if(currProcesses.size() > 1) {
                if(process.checkAvailableResources(currResources)) {
                    ArrayList<Process> reviewed = new ArrayList<>(review);
                    reviewed.add(process);
                    ArrayList<Process> newCurrProcesses = new ArrayList<>(currProcesses);
                    newCurrProcesses.remove(process);
                    ArrayList<Resource> newCurrResources = new ArrayList<>(currResources);
                    calculateResources(newCurrResources, processes);
                    recursiveProcessing(newCurrProcesses, newCurrResources, reviewed, processes);
                }
            } else if(process.checkAvailableResources(currResources)) {
                review.add(process);
                for(Process pro : review) {
                    System.out.print("Process " + Integer.toString(pro.getProcessID()) + ", ");
                }
                System.out.print("Processes completed.");
                System.out.println();
            }
        } 
    }
}
