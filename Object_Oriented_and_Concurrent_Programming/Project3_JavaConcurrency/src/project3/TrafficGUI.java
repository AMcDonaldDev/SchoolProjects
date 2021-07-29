/*
 * File: TrafficGUI.java
 * Author: Allison McDonald
 * Date: 8/11/2020
 * Purpose: CMSC 335 Project 3(Final) - Displays time, traffic signal and other
 * information for traffic analysts; Defines the GUI and contains the main method.
 */

package project3;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;

public class TrafficGUI extends JPanel {
    private ArrayList<Car> listOfCars = new ArrayList(3);
    private final int trafficPanelWidth = 906;
    private int numberOfIntersections = 4; // This will give 3 intersections
    private int intersectionDistance = (trafficPanelWidth / numberOfIntersections);
    private int numberOfCars = 3;
    private int carWidth;
    private int carHeight;
    private TrafficLight trafficLight = new TrafficLight();
    private Clock clock;
    private Car car;
    private Color carColor;
    private int carX;
    private int carY;
    private javax.swing.JButton addCarButton;
    private javax.swing.JLabel addElementsLabel;
    private javax.swing.JButton addIntersectionButton;
    private javax.swing.JLabel analysisControlLabel;
    private javax.swing.JLabel carDataLabel;
    private javax.swing.JLabel carDataNoteLabel;
    private javax.swing.JPanel carDataPanel;
    private javax.swing.JLabel carIDLabel1;
    private javax.swing.JLabel carIDLabel2;
    private javax.swing.JLabel carIDLabel3;
    private javax.swing.JLabel carIDLabel4;
    private javax.swing.JLabel carIDLabel5;
    private javax.swing.JTextField carIDTextField1;
    private javax.swing.JTextField carIDTextField2;
    private javax.swing.JTextField carIDTextField3;
    private javax.swing.JTextField carIDTextField4;
    private javax.swing.JTextField carIDTextField5;
    private javax.swing.JButton continueButton;
    private javax.swing.JLabel locationLabel1;
    private javax.swing.JLabel locationLabel2;
    private javax.swing.JLabel locationLabel3;
    private javax.swing.JLabel locationLabel4;
    private javax.swing.JLabel locationLabel5;
    private javax.swing.JTextField locationTextField1;
    private javax.swing.JTextField locationTextField2;
    private javax.swing.JTextField locationTextField3;
    private javax.swing.JTextField locationTextField4;
    private javax.swing.JTextField locationTextField5;
    private javax.swing.JButton pauseButton;
    private javax.swing.JLabel speedLabel1;
    private javax.swing.JLabel speedLabel2;
    private javax.swing.JLabel speedLabel3;
    private javax.swing.JLabel speedLabel4;
    private javax.swing.JLabel speedLabel5;
    private javax.swing.JTextField speedTextField1;
    private javax.swing.JTextField speedTextField2;
    private javax.swing.JTextField speedTextField3;
    private javax.swing.JTextField speedTextField4;
    private javax.swing.JTextField speedTextField5;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JPanel timeButtonPanel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JTextField timeTextField;
    private javax.swing.JPanel trafficDisplayPanel;
    private javax.swing.JFrame frame;

    public TrafficGUI() {
        for(int i = 0; i < numberOfCars; i++) {
                    car = new Car(trafficLight, intersectionDistance);
                    listOfCars.add(car);
                }
        paintComponent();
    }
                        
    private void paintComponent() {

        frame = new javax.swing.JFrame();
        trafficDisplayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int initialXVerticalLine = intersectionDistance;
                for(int i = 1; i < numberOfIntersections; i++) {
                    drawVerticalLines(g, initialXVerticalLine);
                    drawTrafficLight(g, initialXVerticalLine, trafficLight.getColor());
                    initialXVerticalLine += intersectionDistance;
                }
                drawHorizontalLines(g);
                drawCars(g);
                repaint();
            }
        };
        carDataPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                updateCarData();
                repaint();
            }
        };
        carDataLabel = new javax.swing.JLabel();
        carIDLabel1 = new javax.swing.JLabel();
        speedLabel1 = new javax.swing.JLabel();
        carDataNoteLabel = new javax.swing.JLabel();
        locationLabel1 = new javax.swing.JLabel();
        locationTextField1 = new javax.swing.JTextField();
        carIDTextField1 = new javax.swing.JTextField();
        speedTextField1 = new javax.swing.JTextField();
        carIDLabel2 = new javax.swing.JLabel();
        carIDTextField2 = new javax.swing.JTextField();
        speedLabel2 = new javax.swing.JLabel();
        speedTextField2 = new javax.swing.JTextField();
        locationLabel2 = new javax.swing.JLabel();
        locationTextField2 = new javax.swing.JTextField();
        carIDLabel3 = new javax.swing.JLabel();
        carIDTextField3 = new javax.swing.JTextField();
        speedLabel3 = new javax.swing.JLabel();
        speedTextField3 = new javax.swing.JTextField();
        locationLabel3 = new javax.swing.JLabel();
        locationTextField3 = new javax.swing.JTextField();
        carIDLabel4 = new javax.swing.JLabel();
        carIDTextField4 = new javax.swing.JTextField();
        speedLabel4 = new javax.swing.JLabel();
        speedTextField4 = new javax.swing.JTextField();
        locationLabel4 = new javax.swing.JLabel();
        locationTextField4 = new javax.swing.JTextField();
        carIDTextField5 = new javax.swing.JTextField();
        speedLabel5 = new javax.swing.JLabel();
        locationLabel5 = new javax.swing.JLabel();
        carIDLabel5 = new javax.swing.JLabel();
        speedTextField5 = new javax.swing.JTextField();
        locationTextField5 = new javax.swing.JTextField();
        timeButtonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        timeLabel = new javax.swing.JLabel();
        timeTextField = new javax.swing.JTextField();
        clock = new Clock(timeTextField);
        analysisControlLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        continueButton = new javax.swing.JButton();
        addElementsLabel = new javax.swing.JLabel();
        addIntersectionButton = new javax.swing.JButton();
        addCarButton = new javax.swing.JButton();

        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Traffic Congestion Analysis");

        trafficDisplayPanel.setToolTipText("Traffic Display");

        javax.swing.GroupLayout trafficDisplayPanelLayout = new javax.swing.GroupLayout(trafficDisplayPanel);
        trafficDisplayPanel.setLayout(trafficDisplayPanelLayout);
        trafficDisplayPanelLayout.setHorizontalGroup(
            trafficDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 906, Short.MAX_VALUE)
        );
        trafficDisplayPanelLayout.setVerticalGroup(
            trafficDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        carDataPanel.setToolTipText("Car Data Display");

        carDataLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        carDataLabel.setText("Car Data");
        carDataLabel.setToolTipText("Car Data Label");

        carIDLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDLabel1.setText("Car ID");
        carIDLabel1.setToolTipText("Car Id Label");

        speedLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedLabel1.setText("Speed");
        speedLabel1.setToolTipText("Speed Label");

        carDataNoteLabel.setText("* Speed measured as pixels per 1/2 second. Location reported as (x, y) values. *");
        carDataNoteLabel.setToolTipText("Note: Speed measured as pixels per 1/2 second. Location reported as (x, y) values.");

        locationLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationLabel1.setText("Location");
        locationLabel1.setToolTipText("Location Label");

        locationTextField1.setEditable(false);
        locationTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        locationTextField1.setToolTipText("Location text field");
        locationTextField1.setMinimumSize(new java.awt.Dimension(80, 21));
        locationTextField1.setName(""); // NOI18N
        locationTextField1.setPreferredSize(new java.awt.Dimension(80, 21));

        carIDTextField1.setEditable(false);
        carIDTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        carIDTextField1.setToolTipText("Car ID text field");
        carIDTextField1.setMinimumSize(new java.awt.Dimension(21, 21));
        carIDTextField1.setPreferredSize(new java.awt.Dimension(21, 21));

        speedTextField1.setEditable(false);
        speedTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        speedTextField1.setToolTipText("Speed text field");
        speedTextField1.setMinimumSize(new java.awt.Dimension(30, 21));
        speedTextField1.setPreferredSize(new java.awt.Dimension(30, 21));

        carIDLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDLabel2.setText("Car ID");
        carIDLabel2.setToolTipText("Car Id Label");

        carIDTextField2.setEditable(false);
        carIDTextField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        carIDTextField2.setToolTipText("Car ID text field");
        carIDTextField2.setMinimumSize(new java.awt.Dimension(21, 21));
        carIDTextField2.setPreferredSize(new java.awt.Dimension(21, 21));

        speedLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedLabel2.setText("Speed");
        speedLabel2.setToolTipText("Speed Label");

        speedTextField2.setEditable(false);
        speedTextField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        speedTextField2.setToolTipText("Speed text field");
        speedTextField2.setMinimumSize(new java.awt.Dimension(30, 21));
        speedTextField2.setPreferredSize(new java.awt.Dimension(30, 21));

        locationLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationLabel2.setText("Location");
        locationLabel2.setToolTipText("Location Label");

        locationTextField2.setEditable(false);
        locationTextField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        locationTextField2.setToolTipText("Location text field");
        locationTextField2.setMinimumSize(new java.awt.Dimension(80, 21));
        locationTextField2.setName(""); // NOI18N
        locationTextField2.setPreferredSize(new java.awt.Dimension(80, 21));

        carIDLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDLabel3.setText("Car ID");
        carIDLabel3.setToolTipText("Car Id Label");

        carIDTextField3.setEditable(false);
        carIDTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        carIDTextField3.setToolTipText("Car ID text field");
        carIDTextField3.setMinimumSize(new java.awt.Dimension(21, 21));
        carIDTextField3.setPreferredSize(new java.awt.Dimension(21, 21));

        speedLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedLabel3.setText("Speed");
        speedLabel3.setToolTipText("Speed Label");

        speedTextField3.setEditable(false);
        speedTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        speedTextField3.setToolTipText("Speed text field");
        speedTextField3.setMinimumSize(new java.awt.Dimension(30, 21));
        speedTextField3.setPreferredSize(new java.awt.Dimension(30, 21));

        locationLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationLabel3.setText("Location");
        locationLabel3.setToolTipText("Location Label");

        locationTextField3.setEditable(false);
        locationTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        locationTextField3.setToolTipText("Location text field");
        locationTextField3.setMinimumSize(new java.awt.Dimension(80, 21));
        locationTextField3.setName(""); // NOI18N
        locationTextField3.setPreferredSize(new java.awt.Dimension(80, 21));

        carIDLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDLabel4.setText("Car ID");
        carIDLabel4.setToolTipText("Car Id Label");

        carIDTextField4.setEditable(false);
        carIDTextField4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        carIDTextField4.setToolTipText("Car ID text field");
        carIDTextField4.setMinimumSize(new java.awt.Dimension(21, 21));
        carIDTextField4.setPreferredSize(new java.awt.Dimension(21, 21));

        speedLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedLabel4.setText("Speed");
        speedLabel4.setToolTipText("Speed Label");

        speedTextField4.setEditable(false);
        speedTextField4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        speedTextField4.setToolTipText("Speed text field");
        speedTextField4.setMinimumSize(new java.awt.Dimension(30, 21));
        speedTextField4.setPreferredSize(new java.awt.Dimension(30, 21));

        locationLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationLabel4.setText("Location");
        locationLabel4.setToolTipText("Location Label");

        locationTextField4.setEditable(false);
        locationTextField4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        locationTextField4.setToolTipText("Location text field");
        locationTextField4.setMinimumSize(new java.awt.Dimension(80, 21));
        locationTextField4.setName(""); // NOI18N
        locationTextField4.setPreferredSize(new java.awt.Dimension(80, 21));

        carIDTextField5.setEditable(false);
        carIDTextField5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        carIDTextField5.setToolTipText("Car ID text field");
        carIDTextField5.setMinimumSize(new java.awt.Dimension(21, 21));
        carIDTextField5.setPreferredSize(new java.awt.Dimension(21, 21));

        speedLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedLabel5.setText("Speed");
        speedLabel5.setToolTipText("Speed Label");

        locationLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationLabel5.setText("Location");
        locationLabel5.setToolTipText("Location Label");

        carIDLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        carIDLabel5.setText("Car ID");
        carIDLabel5.setToolTipText("Car Id Label");

        speedTextField5.setEditable(false);
        speedTextField5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        speedTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        speedTextField5.setToolTipText("Speed text field");
        speedTextField5.setMinimumSize(new java.awt.Dimension(30, 21));
        speedTextField5.setPreferredSize(new java.awt.Dimension(30, 21));

        locationTextField5.setEditable(false);
        locationTextField5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        locationTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        locationTextField5.setToolTipText("Location text field");
        locationTextField5.setMinimumSize(new java.awt.Dimension(80, 21));
        locationTextField5.setName(""); // NOI18N
        locationTextField5.setPreferredSize(new java.awt.Dimension(80, 21));

        javax.swing.GroupLayout carDataPanelLayout = new javax.swing.GroupLayout(carDataPanel);
        carDataPanel.setLayout(carDataPanelLayout);
        carDataPanelLayout.setHorizontalGroup(
            carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(carDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(carDataLabel)
                    .addComponent(carDataNoteLabel)
                    .addGroup(carDataPanelLayout.createSequentialGroup()
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addComponent(locationLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(locationTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDLabel1)
                                    .addComponent(speedLabel1))
                                .addGap(28, 28, 28)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(speedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDLabel2)
                                    .addComponent(speedLabel2))
                                .addGap(28, 28, 28)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(speedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addComponent(locationLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(locationTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDLabel3)
                                    .addComponent(speedLabel3))
                                .addGap(28, 28, 28)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(speedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addComponent(locationLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(locationTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDLabel4)
                                    .addComponent(speedLabel4))
                                .addGap(28, 28, 28)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(speedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addComponent(locationLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(locationTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(speedLabel5)
                                    .addComponent(carIDLabel5))
                                .addGap(28, 28, 28)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(carIDTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(speedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addComponent(locationLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(locationTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        carDataPanelLayout.setVerticalGroup(
            carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(carDataPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(carDataPanelLayout.createSequentialGroup()
                        .addComponent(carDataLabel)
                        .addGap(5, 5, 5)
                        .addComponent(carDataNoteLabel)
                        .addGap(18, 18, 18)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(carIDLabel1)
                                    .addComponent(carIDTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(speedLabel1)
                                    .addComponent(speedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(locationLabel1)
                                    .addComponent(locationTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(carIDLabel2)
                                    .addComponent(carIDTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(speedLabel2)
                                    .addComponent(speedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(locationLabel2)
                                    .addComponent(locationTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(carDataPanelLayout.createSequentialGroup()
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(carIDLabel3)
                                    .addComponent(carIDTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(speedLabel3)
                                    .addComponent(speedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(locationLabel3)
                                    .addComponent(locationTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(carDataPanelLayout.createSequentialGroup()
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(carIDLabel4)
                            .addComponent(carIDTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(speedLabel4)
                            .addComponent(speedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(locationLabel4)
                            .addComponent(locationTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(carDataPanelLayout.createSequentialGroup()
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(carIDLabel5)
                            .addComponent(carIDTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(speedLabel5)
                            .addComponent(speedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(carDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(locationLabel5)
                            .addComponent(locationTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        timeButtonPanel.setToolTipText("Time and Button Panel");

        timeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeLabel.setText("Current Time");

        timeTextField.setEditable(false);
        timeTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeTextField.setToolTipText("Current Date & Time");

        analysisControlLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        analysisControlLabel.setText("Analysis Controls");

        startButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        startButton.setText("Start");
        startButton.setToolTipText("Press to start analysis");
        startButton.setMaximumSize(new java.awt.Dimension(87, 25));
        startButton.setMinimumSize(new java.awt.Dimension(87, 25));
        startButton.setPreferredSize(new java.awt.Dimension(87, 25));
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        stopButton.setText("Stop");
        stopButton.setToolTipText("Press to stop analysis");
        stopButton.setMaximumSize(new java.awt.Dimension(87, 25));
        stopButton.setMinimumSize(new java.awt.Dimension(87, 25));
        stopButton.setPreferredSize(new java.awt.Dimension(87, 25));
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        pauseButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pauseButton.setText("Pause");
        pauseButton.setToolTipText("Press to pause analysis");
        pauseButton.setMaximumSize(new java.awt.Dimension(87, 25));
        pauseButton.setMinimumSize(new java.awt.Dimension(87, 25));
        pauseButton.setPreferredSize(new java.awt.Dimension(87, 25));
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        continueButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        continueButton.setText("Continue");
        continueButton.setToolTipText("Press to continue analysis after pausing");
        continueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueButtonActionPerformed(evt);
            }
        });

        addElementsLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addElementsLabel.setText("Add Elements");
        addElementsLabel.setToolTipText("Add Elements Label");

        addIntersectionButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addIntersectionButton.setText("Add Intersection");
        addIntersectionButton.setToolTipText("Press to add intersection");
        addIntersectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIntersectionButtonActionPerformed(evt);
            }
        });

        addCarButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addCarButton.setText("Add Car");
        addCarButton.setToolTipText("Press to add a car");
        addCarButton.setMaximumSize(new java.awt.Dimension(133, 25));
        addCarButton.setMinimumSize(new java.awt.Dimension(133, 25));
        addCarButton.setPreferredSize(new java.awt.Dimension(133, 25));
        addCarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout timeButtonPanelLayout = new javax.swing.GroupLayout(timeButtonPanel);
        timeButtonPanel.setLayout(timeButtonPanelLayout);
        timeButtonPanelLayout.setHorizontalGroup(
            timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timeButtonPanelLayout.createSequentialGroup()
                .addGroup(timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timeButtonPanelLayout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(timeLabel)
                        .addGap(18, 18, 18)
                        .addComponent(timeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(timeButtonPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addIntersectionButton)
                        .addGap(18, 18, 18)
                        .addComponent(addCarButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 292, Short.MAX_VALUE))
            .addGroup(timeButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timeButtonPanelLayout.createSequentialGroup()
                        .addGroup(timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(analysisControlLabel)
                            .addComponent(addElementsLabel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(timeButtonPanelLayout.createSequentialGroup()
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(continueButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        timeButtonPanelLayout.setVerticalGroup(
            timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timeButtonPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel)
                    .addComponent(timeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(analysisControlLabel)
                .addGap(18, 18, 18)
                .addGroup(timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(continueButton))
                .addGap(18, 18, 18)
                .addComponent(addElementsLabel)
                .addGap(18, 18, 18)
                .addGroup(timeButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addIntersectionButton)
                    .addComponent(addCarButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(trafficDisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(carDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(trafficDisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        repaint();
    }// </editor-fold>                        

    private void addCarButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        numberOfCars++;
        car = new Car(trafficLight, intersectionDistance);
        listOfCars.add(car);
        repaint();
    }                                            

    private void addIntersectionButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        numberOfIntersections++;
        intersectionDistance = (trafficPanelWidth / numberOfIntersections);
        repaint();
    }                                                     

    private void continueButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        clock.restart();
        trafficLight.restart();
        for(int i = 0; i < listOfCars.size(); i++) {
            listOfCars.get(i).restart();
        }
        repaint();
    }                                              

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        clock.pause();
        trafficLight.pause();
        for(int i = 0; i < listOfCars.size(); i++) {
            listOfCars.get(i).pause();
        }
        repaint();
    }                                           

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        clock.cancel();
        trafficLight.cancel();
        for(int i = 0; i < listOfCars.size(); i++) {
            listOfCars.get(i).cancel();
        }
        System.exit(0);
    }                                          

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        clock.t.start();
        for(int i = 0; i < listOfCars.size(); i++) {
            listOfCars.get(i).t.start();
            repaint();
        }
        trafficLight.t.start();
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrafficGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrafficGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrafficGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrafficGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrafficGUI().setVisible(true);
            }
        });;
    }
    
    private void drawCars(Graphics g) {
        for(int i = 0; i < listOfCars.size(); i++) {
                    carColor = listOfCars.get(i).getColor();
                    carX = listOfCars.get(i).getX();
                    carY = listOfCars.get(i).getY();
                    carWidth = listOfCars.get(i).getWidth();
                    carHeight = listOfCars.get(i).getHeight();
                    g.setColor(carColor);
                    g.fillRect(carX, carY, carWidth, carHeight);
                }
    }
    
    private void drawHorizontalLines(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawLine(0, 100, 906, 100);
        g.setColor(Color.YELLOW);
        g.drawLine(0, 155, 906, 155);
    }
    
    private void drawVerticalLines(Graphics g, int startingX) {
        g.setColor(Color.YELLOW);
        g.drawLine(startingX, 80, startingX, 175);
        g.setColor(Color.YELLOW);
        g.drawLine((startingX + 25), 80, (startingX + 25), 175);
    }
    
    private void drawTrafficLight(Graphics g, int startingX, Color light) {
        g.setColor(Color.BLACK);
        g.drawRect((startingX - 2), 50, 30, 30);
        g.setColor(light);
        g.fillOval(startingX, 52, 25, 25);
    }
    
    private void updateCarData() {
        carIDTextField1.setText(listOfCars.get(0).getName());
        carIDTextField2.setText(listOfCars.get(1).getName());
        carIDTextField3.setText(listOfCars.get(2).getName());
        speedTextField1.setText(listOfCars.get(0).getCurrentSpeed());
        speedTextField2.setText(listOfCars.get(1).getCurrentSpeed());
        speedTextField3.setText(listOfCars.get(2).getCurrentSpeed());
        locationTextField1.setText(listOfCars.get(0).getLocation());
        locationTextField2.setText(listOfCars.get(1).getLocation());
        locationTextField3.setText(listOfCars.get(2).getLocation());
        if(listOfCars.size() >= 4) {
            carIDTextField4.setText(listOfCars.get(3).getName());
            speedTextField4.setText(listOfCars.get(3).getCurrentSpeed());
            locationTextField4.setText(listOfCars.get(3).getLocation());
        }
        if(listOfCars.size() >= 5) {
            locationTextField5.setText(listOfCars.get(4).getLocation());
            speedTextField5.setText(listOfCars.get(4).getCurrentSpeed());
            carIDTextField5.setText(listOfCars.get(4).getName());
        }
    }         
}
