/* 
 * Filename: DemandPaging.java
 * Author: Allison McDonald
 * Date: 3/8/20
 * Purpose: CMSC 412 Final Project
*/

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class DemandPaging {
    static boolean flagContinue;
    static StringBuffer referenceString;
    static int numberPhysicalFrames;
    
    public static void main(String[] args) {
        if(args[0] == null) {
            System.out.println("Please enter the number of physical frames (0-7) in the command line.");
            System.exit(0);
        }
        String n = args[0];
        try {
            numberPhysicalFrames = Integer.parseInt(n);
            if((numberPhysicalFrames < 0) || (numberPhysicalFrames > 8)) {
                System.out.println("Invailed number of physcial frames entered.  Must be between 0 to 7.");
                System.exit(0);
            }
        } catch(NumberFormatException e) {
            System.out.println("Invaild number of physical frames entered.");
            System.exit(0);
        }
        Scanner scanIn = new Scanner(System.in);
        flagContinue = true;
        while(flagContinue) {
            displayTextMenu();
            try {
                int userSelection = scanIn.nextInt();
                runUserSelection(userSelection);
            } catch(InputMismatchException e) {
                System.out.println("Invaild selection entered.");
                scanIn.next();
            } catch(NullPointerException e) {
                System.out.println("You must enter or generate a reference " +
                        "string before selecting this option.");
            }

        }
    }
    
    // To display text menu
    private static void displayTextMenu() {
        System.out.println();
        System.out.println("Please select from the below menu:");
        System.out.println();
        System.out.println("0 - Exit");
        System.out.println("1 - Read reference string");
        System.out.println("2 - Generate reference string");
        System.out.println("3 - Display current reference string");
        System.out.println("4 - Simulate FIFO");
        System.out.println("5 - Simulate OPT");
        System.out.println("6 - Simulate LRU");
        System.out.println("7 - Simulate LFU");
        System.out.println();
        System.out.print("Enter your selection: ");
    }
    
    // To run option based on user selection
    private static void runUserSelection(int userSelection) {
        switch(userSelection) {
            case 0:
                System.out.println("You have selected to exit.");
                flagContinue = false;
                break;
            case 1:
                obtainRefString();
                break;
            case 2:
                generateReferenceString();
                break;
            case 3:
                displayRefString(referenceString);
                break;
            case 4:
                simulateFIFO(referenceString, numberPhysicalFrames);
                break;
            case 5:
                simulateOPT(referenceString, numberPhysicalFrames);
                break;
            case 6:
                simulateLRU(referenceString, numberPhysicalFrames);
                break;
            case 7:
                simulateLFU(referenceString, numberPhysicalFrames);
                break;
            default:
                break;
        }
        if((userSelection < 0) || (userSelection > 7)) {
            System.out.println("Invaild selection entered.");
        }
    }
    
    // To read user entered reference string
    private static void obtainRefString() {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter your reference string below. Only the numbers from 0 to 9 " +
                "are allowed. The numbers do not need to be separated, but " +
                "they can be separated by a space or a comma. ");
        String referenceInput = scanIn.nextLine();
        String reference = checkRefString(referenceInput);
        referenceString = new StringBuffer(reference);
    }
    
    // To check and format user entered reference string
    private static String checkRefString(String referenceInput) {
        String str = referenceInput.replaceAll("[\\s\\,]", "");
        String[] strArray = str.split("");
        int number;
        for(int i = 0; i < strArray.length; i++) {
            try {
                number = Integer.parseInt(strArray[i]);
                if((number < 0) || (number > 9)) {
                    System.out.println("Invalid reference string entered. " +
                            "Only numbers from 0 to 9 are allowed.");
                    break;
                }
            } catch(NumberFormatException e) {
                System.out.println("Invalid reference string entered." +
                        "Only numbers from 0 to 9 are allowed.");
                break;
            }
        }
        return str;
    }
    
    // To generate a random reference string with length determined by user
    private static void generateReferenceString() {
        Scanner scanIn = new Scanner(System.in);
        int refStringLength;
        System.out.println("Enter the length of the reference string you would " +
                "like to generate: ");
        try {
            refStringLength = scanIn.nextInt();
            if(refStringLength <= 0) {
                System.out.println("The length must be greather than 0.");
            } else {
                String randomNumbers = "";
                Random numbers;
                for(int i = 0; i < refStringLength; i++) {
                    numbers = new Random();
                    int randNumbers = numbers.nextInt(10);
                    randomNumbers += Integer.toString(randNumbers);
                    }
                referenceString = new StringBuffer(randomNumbers);
            }
        } catch(InputMismatchException e) {
            System.out.println("Invaild length entered.");
        }
    }
    
    // To display current reference string
    private static void displayRefString(StringBuffer referenceString) {
        if(haveRefString(referenceString)) {
            haveRefString(referenceString);
            System.out.println("Current Reference String: ");
            System.out.println();
            for(int i = 0; i < referenceString.length(); i++) {
                String refPrint = "";
                refPrint += referenceString.charAt(i) + " ";
                System.out.print(refPrint);
        }  
        }
    }
    
    // To determine if reference string exists
    private static boolean haveRefString(StringBuffer referenceString) {
        boolean flagHaveRef = true;
        if(referenceString == null) {
            flagHaveRef = false;
            System.out.println("You must enter or generate a reference " +
                        "string before selecting this option.");
        }
        return flagHaveRef;
    }
    
    // To simulate FIFO
    private static void simulateFIFO(StringBuffer referenceString, int numberPhysicalFrames) {
        if(haveRefString(referenceString)) {
            FIFO fifo = new FIFO(referenceString, numberPhysicalFrames);
            fifo.calculateFIFO();
        }
    }
    
    // To simulate OPT
    private static void simulateOPT(StringBuffer referenceString, int numberPhysicalFrames) {
        if(haveRefString(referenceString)) {
            OPT opt = new OPT(referenceString, numberPhysicalFrames);
            opt.calculateOPT();
        }
    }
    
    // To simulate LRU
    private static void simulateLRU(StringBuffer referenceString, int numberPhysicalFrames) {
        if(haveRefString(referenceString)) {
            LRU lru = new LRU(referenceString, numberPhysicalFrames);
            lru.calculateLRU();
        }
    }
    
    // To simulate LFU
    private static void simulateLFU(StringBuffer referenceString, int numberPhysicalFrames) {
        if(haveRefString(referenceString)) {
            LFU lfu = new LFU(referenceString, numberPhysicalFrames);
            lfu.calculateLFU();
        }
    }
}
