/*
 * File: BenchmarkReportGUI.java
 * Author: Allison McDonald
 * Date: 11/25/2020
 * Purpose: CMSC 451 Project 1 - This is the GUI that reads a text file that is
 * produced by the BenchmarkSorts.java program and creates a report
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BenchmarkReportGUI extends JFrame {
    // Declare variables for showing the report
    private JFileChooser fileChooser;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JTable table;
    private JOptionPane frame = new JOptionPane();
    private DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
    // Declare and instantiate variables for creating the report
    private ArrayList<String[]> benchmarkData = new ArrayList<>();
    private ArrayList<String> dataSize = new ArrayList<>();
    private ArrayList<Double[]> dataCount = new ArrayList<>();
    private ArrayList<Double[]> dataTime = new ArrayList<>();
    private Double[] averageCount = new Double[10];
    private Double[] averageTime = new Double[10];
    private Double[] coefficientCount = new Double[10];
    private Double[] coefficientTime = new Double[10];
    private String[][] rowData = new String[10][5];
    private NumberFormat nf = NumberFormat.getInstance();
    private String[] columnNames = new String[]{"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
    
    // Constructor
    public BenchmarkReportGUI() {
        setFrame();
    }
    
    // Creates the UI
    private void setFrame() {
        // Initialize variables
        fileChooser = new JFileChooser();
        panel = new JPanel();
        scrollPane = new JScrollPane(table);
        table = new JTable();
        
        // Set default upon closing the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Add Window Listener so File Chooser opens when window opens
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        
        // Set frame layout
        setTitle("Benchmark Report");
        setLayout(new BorderLayout());
        setSize(820, 620);
        setLocationRelativeTo(null);
        
        // Set table layout
        centerRender.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRender);
        table.setDefaultEditor(Object.class, null);
        table.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        scrollPane.setViewportView(table);
        tablePanel.add(scrollPane);
        table.setOpaque(true);
        add(tablePanel, BorderLayout.CENTER);
        
        // Set file chooser layout
        fileChooser.setApproveButtonText("Open");
        fileChooser.setDialogTitle("Choose File");
        
        pack();
    }
    
    // Set visibility
    public void display() {
        setVisible(true);
    }
    
    // Function for Window Listener
    // When file is choosen this function calls other functions to read the data,
    // parse the data, calculate the data, and create the data for the table
    private void formWindowOpened(WindowEvent evt) {
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            readFile(file);
            parseBenchmarkData(benchmarkData);
            calculateAverage(dataCount, averageCount);
            calculateAverage(dataTime, averageTime);
            calculateCoefficientOfVariance(dataCount, averageCount, coefficientCount);
            calculateCoefficientOfVariance(dataTime, averageTime, coefficientTime);
            populateTable(dataSize, averageCount, coefficientCount, averageTime, coefficientTime);
        }
    }
    
    // Function to read in file and create data array list
    private void readFile(File fileName) {
        BufferedReader inputStream = null;
        String dataLine;
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            while((dataLine = inputStream.readLine()) != null) {
                String[] lineData = dataLine.split(",");
                benchmarkData.add(lineData);
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(frame, "File Did Not Open");
        }
    }

    // Function to parse data into arrays for data size, data count,
    // and data time
    private void parseBenchmarkData(ArrayList<String[]> list) {
        int index = 0;
        for(String[] lineData : list) {
            Double[] count = new Double[50];
            Double[] time = new Double[50];
            int countIndex = 0;
            int timeIndex = 0;
            for(int i = 0; i < lineData.length; i++) {
                if(i == 0) {
                    dataSize.add(lineData[i]);
                } else if(i % 2 == 0 && i != 0) {
                    time[timeIndex] = Double.parseDouble(lineData[i]);
                    timeIndex++;
                } else {
                    count[countIndex] = Double.parseDouble(lineData[i]);
                    countIndex++;
                }
            }
            dataTime.add(index, time);
            dataCount.add(index, count);
            index++;
        }
    }
    
    // Function to calculate the average/mean of the data sets
    private void calculateAverage(ArrayList<Double[]> list, Double[] array) {
        double sum = 0;
        int index = 0;
        for(Double[] element : list) {
            for(int i = 0; i < element.length; i++) {
                double num = element[i];
                sum += num;
            }
            double average = (sum / element.length);
            array[index] = average;
            index++;
        }
    }
    
    // Function to calculate the coefficient of the data sets
    private void calculateCoefficientOfVariance(ArrayList<Double[]> list, Double[] avArray,
            Double[] coArray) {
        int index = 0;
        for(Double[] element : list) {
            double deviation = 0;
            double standardDeviation = 0;
            for(int i = 0; i < element.length; i++) {
                double num = element[i];
                deviation += Math.pow((num - avArray[index]), 2);
            }
            standardDeviation = Math.sqrt(deviation / list.get(index).length);
            double coefficientVar = ((standardDeviation / avArray[index]) * 100);
            coArray[index] = coefficientVar;
            index++;
        }
    }
    
    // Function to add data to the table
    private void populateTable(ArrayList<String> dataSize, Double[] averageCount, Double[] coefficientCount,
            Double[] averageTime, Double[] coefficientTime) {
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
            for(int i = 0; i < dataSize.size(); i++) {
                for(int j = 0; j < averageCount.length; j++) {
                    if(j == 0) {
                        rowData[i][j] = dataSize.get(i);
                    } else if(j == 1) {
                        String avgC = nf.format(averageCount[i]);
                        rowData[i][j] = avgC;
                    } else if(j == 2) {
                        String coC = (nf.format(coefficientCount[i]) + "%");
                        rowData[i][j] =  coC;
                    } else if(j == 3) {
                        String avgT = nf.format(averageTime[i]);
                        rowData[i][j] = avgT;
                    } else if(j == 4) {
                        String coT = (nf.format(coefficientTime[i]) + "%");
                        rowData[i][j] = coT;
                    }
                }
            }
            table.setModel(new DefaultTableModel(rowData, columnNames));
    }
    
    public static void main(String[] args) {
        BenchmarkReportGUI window = new BenchmarkReportGUI();
        window.display();
    }
}