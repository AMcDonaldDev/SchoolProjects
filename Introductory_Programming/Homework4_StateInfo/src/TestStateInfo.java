/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Import statements
import java.util.Scanner;

public class TestStateInfo {
    public static void main(String[] args){
        boolean flag = true;
        Scanner scannerIn = new Scanner(System.in);
        
        while (flag) {
            System.out.println("Please enter a state to learn the state's bird and flower"
            + " or enter none to exit");
            String state = scannerIn.nextLine();
            
            if (state.trim().equalsIgnoreCase("none")) {
                flag = false;
            }
            else {
                int matchIndex = StateInfo.compareState(state.trim());
                if (matchIndex == -1) {
                    System.out.println("Invaild State Entered");
                    continue;
                }
                StateInfo stateInfo = new StateInfo();
                stateInfo = 
                System.out.println(StateInfo.getState() + "'s state bird is the "
                    + StateInfo.getBird() + " and the state flower is the "
                    + StateInfo.getFlower() + ".");
            }
        }
        System.out.println("You requested information for " + StateInfo.getNumberOfStates()
            + " states. Please see your summary below:");
//       for (int i = 1; i <= StateInfo.getStateId(); i++) {
//           String stateName = StateInfo.getState();
//           String stateBird = StateInfo.getBird();
//           String stateFlower = StateInfo.getFlower();
//           String stateSummary = ("State: " + stateName + " Bird: " + stateBird
//                   + " Flower: " + stateFlower + "\n");
//           String summary = "";
//           summary += stateSummary;
            System.out.println(StateInfo.searchSummary());
            
        }
    }
    
