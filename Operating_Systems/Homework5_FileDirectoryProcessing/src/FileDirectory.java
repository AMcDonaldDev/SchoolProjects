/*
 * Filename: FileDirectory.java
 * Author: Allison McDonald
 * Date: 3/7/20
 * Purpose:CMSC 412 Homework 5
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileDirectory {
    static File directory;
    static Scanner scannerIn = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        
        boolean flag = true;
        while(flag) {
            displayTextMenu();
            int userInput = scannerIn.nextInt();
            System.out.println();
            switch(userInput) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    obtainDirectory();
                    break;
                case 2:
                    listDirectoryContent(directory);
                    break;
                case 3:
                    listAllDirectoryContent(directory);
                    break;
                case 4:
                    deleteFile(directory);
                    break;
                case 5:
                    displayFile(directory);
                    break;
                case 6:
                    encryptFile(directory);
                    break;
                case 7:
                    decryptFile(directory);
                    break;
            }
        }
    }
    
    public static void displayTextMenu() {
        System.out.println();
        System.out.println("Please select from the below Menu:");
        System.out.println();
        System.out.println("0 - Exit");
        System.out.println("1 - Select directory");
        System.out.println("2 - List directory content (first level)");
        System.out.println("3 - List directory content (all levels)");
        System.out.println("4 - Delete file");
        System.out.println("5 - Display file (hexadecimal view)");
        System.out.println("6 - Encrypt file (XOR with password)");
        System.out.println("7 - Decrypt file (XOR with password)");
        System.out.println();
        System.out.print("Enter your selection: ");
    }
    
    // To get user input for directory path
    public static void obtainDirectory() {
        System.out.println("Enter the absolute directory path: ");
        String userInput = scannerIn.next();
        directory = new File(userInput);
        System.out.println();
        if(directory.isDirectory()) {
            System.out.println("Selected directory: " + directory);
        } else {
            System.out.println("Invalid directory entered.");
        }
    }
    
    // To list first level of directory content
    public static void listDirectoryContent(File directory) {
        if(directory == null) {
            System.out.println("You must enter a directory before selecting this option.");
        } else {
            File[] directoryContent = directory.listFiles();
            for(File content : directoryContent) {
                if(content.isDirectory()) {
                    System.out.println("Directory: " + content);
                } else {
                    System.out.println("File: " + content);
                }
            }
        }
    }
    
    // To list all levels of directory content
    public static void listAllDirectoryContent(File directory) {
        if(directory == null) {
            System.out.println("You must enter a directory before selecting this option.");
        } else {
            File[] allDirectoryContent = directory.listFiles();
            for(File allContent : allDirectoryContent) {
                if(allContent.isDirectory()) {
                    System.out.println("Directory: " + allContent);
                    listAllDirectoryContent(allContent);
                } else {
                    System.out.println("File: " + allContent);
                }
            }
        }
    }
    
    // To delete file from user input
    public static void deleteFile(File directory) throws IOException {
        Scanner scan = new Scanner(System.in);
        if(directory == null) {
            System.out.println("You must enter a directory before selecting this option.");
        } else {
            System.out.print("Enter the filename you want to delete: ");
            String userInput = scan.nextLine();
            System.out.println();
            String filePath = directory.getAbsolutePath() + "\\" + userInput;
            File file = new File(filePath);
            if(file.exists()) {
                if(file.isDirectory()) {
                    System.out.println("Unable to delete a directory.  You must enter a filename.");
                } else {
                    file.delete();
                }
            } else {
                System.out.println("Invalid filename entered.");
            }
        }
    }
    
    // To display a file in hexadecimal view
    public static void displayFile(File directory) throws IOException {
        Scanner scan = new Scanner(System.in);
        FileInputStream inputStream = null;
        ArrayList<String> hex = new ArrayList<>();
        if(directory == null) {
            System.out.println("You must enter a directory before selecting this option.");
        } else {
            System.out.print("Enter the filename you want to view: ");
            String userFileInput = scan.nextLine();
            String filePath = directory.getAbsolutePath() + "\\" + userFileInput;
            File file = new File(filePath);
            if(file.exists()) {
                if(file.isDirectory()) {
                    System.out.println("Unable to display a directory.  You must enter a filename.");
                } else {
                    byte[] byteFile = new byte[(int)file.length()];
                    try {
                        inputStream = new FileInputStream(file);
                        while(inputStream.read() != -1) {
                           inputStream.read(byteFile);
                           for(int i = 0; i < byteFile.length; i++) {
                               String fByte = String.format("%02X", byteFile[i]);
                               hex.add(fByte);
                           } 
                        }
                        printHex(hex);
                    } catch(FileNotFoundException error) {
                        System.out.println(error);
                    }
                }
            } else {
                System.out.println("Invalid filename entered.");
            }
        }
    }
    
    // To print the file's hex string
    public static void printHex(ArrayList<String> hex) {
        System.out.println();
        System.out.println("Hexadecimal view of file:");
        int j = 0;
        for(int i = 0; i < hex.size(); i++) {
            String hexPrint = "";
            hexPrint += hex.get(i) + " ";
            j++;
            if(j > 16) {
                System.out.print("\n");
                j = 1;
            }
            System.out.print(hexPrint);
        }
    }
    
    // To encrypt file with XOR password
    public static void encryptFile(File directory) throws IOException {
        Scanner scanIn = new Scanner(System.in);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        if(directory == null) {
            System.out.println("You must enter a directory before selecting this option.");
        } else {
            System.out.print("Enter the password: ");
            String userPassword = scanIn.nextLine();
            if(userPassword.getBytes().length > 256) {
                System.out.println("Password bytes length exceeded.  Max bytes allowed is 256.");
            }
            byte[] passwordArray = userPassword.getBytes();
            System.out.print("Enter the filename you want to encrypt: ");
            String userInput = scanIn.nextLine();
            String filename = directory.getAbsolutePath() + "\\" + userInput;
            File file = new File(filename);
            if(file.exists()) {
                if(file.isDirectory()) {
                    System.out.println("Unable to encrypt a directory.  You must enter a filename.");
                } else {
                    byte[] byteFile = new byte[(int) file.length()];
                    try {
                        inputStream = new FileInputStream(file);
                        while(inputStream.read() != -1) {
                            inputStream.read(byteFile);
                        }
                        int i = 0;
                        for(int j = 0; j < byteFile.length; j++) {
                            byteFile[j] = (byte) (byteFile[j] ^ passwordArray[i]);
                            i++;
                            if(i > passwordArray.length - 1) {
                                i = 0;
                            }         
                        }
                        outputStream = new FileOutputStream(directory.getAbsolutePath() + "\\Encrypted_" + file.getName());
                        outputStream.write(byteFile);
                        outputStream.close();
                    } catch(FileNotFoundException error) {
                        System.out.println(error);
                    }
                }
            } else {
                System.out.println("Invalid filename entered.");
            }
        }
    }
    
    // To decrypt file from XOR password
    public static void decryptFile(File directory) throws IOException {
        Scanner scanIn = new Scanner(System.in);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        if(directory == null) {
            System.out.println("You must enter a directory before selecting this option.");
        } else {
            System.out.print("Enter the password: ");
            String userPassword = scanIn.nextLine();
            byte[] passwordArray = userPassword.getBytes();
            System.out.print("Enter the encrypted filename you want to decrypt: ");
            String userInput = scanIn.nextLine();
            String filename = directory.getAbsolutePath() + "\\" + userInput;
            File file = new File(filename);
            if(file.exists()) {
                if(file.isDirectory()) {
                    System.out.println("Unable to decrypt a directory.  You must enter an encrypted filename.");
                } else {
                    byte[] byteFile = new byte[(int) file.length()];
                    try {
                        inputStream = new FileInputStream(file);
                        while(inputStream.read() != -1) {
                            inputStream.read(byteFile);
                        }
                        int i = 0;
                        for(int j = 0; j < byteFile.length; j++) {
                            byteFile[j] = (byte) (passwordArray[i] ^ byteFile[j]);
                            i++;
                            if(i > passwordArray.length - 1) {
                                i = 0;
                            }         
                        }
                        outputStream = new FileOutputStream(directory.getAbsolutePath() + "\\Decrypted_" + file.getName());
                        outputStream.write(byteFile);
                        outputStream.close();
                    } catch(FileNotFoundException error) {
                        System.out.println(error);
                    }
                }
            } else {
                System.out.println("Invalid filename entered.");
            }
        }
    }
}
