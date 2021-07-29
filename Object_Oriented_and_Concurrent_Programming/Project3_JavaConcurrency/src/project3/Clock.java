/*
 * File: Clock.java
 * Author: Allison McDonald
 * Date: 8/8/2020
 * Purpose: CMSC 335 Project 3(Final) - This class defines the clock.
 */

package project3;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

public class Clock implements Runnable {
    private JTextField timeTextField;
    private Date date;
    private boolean run = true;
    public Thread t;
    
    public Clock(JTextField textfield) {
        timeTextField = textfield;
        t = new Thread(this);
        date = new Date();
    }
    
    private synchronized void setDate(Date newDate) {
        date = newDate;
    }
    
    public String getDateText() {
        return date.toString();
    }
    
    // Use when you want to end; Cannot restart
    public void cancel() {
        run = false;
    }
    
    // Use when you want to pause; Can restart
    public void pause() {
        run = false;
    }
    
    // Use when you want to restart
    public void restart() {
        run = true;
        t = new Thread();
        t.start();
    }

    @Override
    public void run() {
        while(run) {
            Date d = new Date();
            setDate(d);
            timeTextField.setText(getDateText());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
