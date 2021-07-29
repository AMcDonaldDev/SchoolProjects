/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Import statements
import java.util.Arrays;

public class StateInfo {
    // Class fields
    private static int numberOfStates = 0;
    // Instance fields
    private String state;
    private String bird;
    private String flower;
    private int stateId;
    
    // Constructor
    public StateInfo(String state, String bird, String flower) {
        this.state = state;
        this.bird = bird;
        this.flower = flower;
        // Increment number of states
        ++numberOfStates;
        // Create state Id
        stateId = numberOfStates;
    }
    
    // Default constructor
    public StateInfo() {
    }

    // State information array
    private static String[][] stateArray = {{"Alabama", "Yellowhammer", "Camellia"}, 
            {"Alaska", "Willow Ptarmigan", "Forget Me Not"}, 
            {"Arizona", "Cactus Wren", "Saguaro Cactus Blossom"}, 
            {"Arkansas", "Mockingbird", "Apple Blossom"}, 
            {"California", "California Quail", "Golden Poppy"}, 
            {"Colorado", "Lark Bunting", "Rocky Mountain columbine"}, 
            {"Connecticut", "American Robin", "Mountain Laurel"}, 
            {"Delware", "Blue Hen Chicken", "Peach Blossom"}, 
            {"District of Columbia", "Wood Thrush", "American Beauty Rose"}, 
            {"Florida", "Mockingbird", "Orange Blossom"}, 
            {"Georgia", "Brown Thrasher", "Cherokee Rose"}, 
            {"Hawaii", "Nene", "Hibiscus"}, 
            {"Idaho", "Mountain Bluebird", "Syringa"}, 
            {"Illinois", "Cardinal", "Violet"}, 
            {"Indiana", "Cardinal", "Peony"}, 
            {"Iowa", "American Goldfinch", "Wild Rose"}, 
            {"Kansas", "Western Meadowlark", "Sunflower"}, 
            {"Kentucky", "Cardinal", "Goldenrod"}, 
            {"Louisiana", "Brown Pelican", "Magnolia"}, 
            {"Maine", "Black-caped Chickadee", "White Pine Cone and Tassel"}, 
            {"Maryland", "Baltimore Oriole", "Black-eyed Susan"}, 
            {"Massachusetts", "Black-caped Chickadee", "Mayflower"}, 
            {"Michigan", "American Robin", "Apple Blossom"}, 
            {"Minnesota", "Common Loon", "Lady Slipper"}, 
            {"Mississippi", "Mockingbird", "Magnolia"}, 
            {"Missouri", "Eastern Bluebird", "Hawthorn"}, 
            {"Montana", "Western Meadowlark", "Bitterroot"}, 
            {"Nebraska", "Western Meadowlark", "Goldenrod"}, 
            {"Nevada", "Mountain Bluebird", "Sagebrush"}, 
            {"New Hampshire", "Purple Finch", "Purple Lilac"}, 
            {"New Jersey", "American Goldfinch", "Purple Violet"}, 
            {"New Mexio", "Roadrunner", "Yucca"}, 
            {"New York", "Eastern Bluebird", "Rose"}, 
            {"North Carolina", "Cardinal", "Dogwood"}, 
            {"North Dakota", "Western Meadowlark", "Wild Prairie Rose"}, 
            {"Ohio", "Cardinal", "Scarlet Carnation"}, 
            {"Oklahoma", "Sissor-tailed Flycatcher", "Mistletoe"}, 
            {"Oregon", "Western Meadowlark", "Oregon Grape"}, 
            {"Pennsylvania", "Ruffed Grouse", "Mountain Laurel"}, 
            {"Rhode Island", "Rhode Island Red", "Violet"}, 
            {"South Carolina", "Carolina Wren", "Carolina Yellow Jessamine"}, 
            {"South Dakota", "Ring-necked Pheasant", "American Pasque Flower"}, 
            {"Tennessee", "Mockingbird", "Iris"}, 
            {"Texas", "Mockingbird", "Bluebonnet"}, 
            {"Utah", "California Gull", "Sego Lily"}, 
            {"Vermont", "Hermit Thrush", "Red Clover"}, 
            {"Virginia", "Cardinal", "American Dogwood"}, 
            {"Washington", "American Goldfinch", "Coast Rhododendron"}, 
            {"West Virginia", "Cardinal", "Rhododendron"}, 
            {"Wisconsin", "American Robin", "Wood Violet"}, 
            {"Wyoming", "Western Meadowlark", "Indian Paintbrush"}};
    
    // Getter methods
    public String getState() {
        return state;
    }
    public String getBird() {
        return bird;
    }
    public String getFlower() {
        return flower;
    }
    public static int getNumberOfStates() {
        return numberOfStates;
    }
    public int getStateId() {
        return stateId;
    }
    
    // Compare user input to state array
    public static int compareState(String state) {
        int matchIndex = -1;
        for (int index = 0; index < stateArray.length; index++) {
            if (stateArray[index][0].equalsIgnoreCase(state)) {
                matchIndex = index;
                StateInfo stateInfo = new StateInfo(stateArray[index][0], stateArray[index][1], stateArray[index][2]);
                break;
            }
        }
        return matchIndex;
    }
    
    public static String searchResults(int matchIndex) {
        StateInfo stateInfo = new StateInfo(stateArray[matchIndex][0], stateArray[matchIndex][1], stateArray[matchIndex][2]);
    }
    public String searchSummary() {
        String summary = "";
        for (int i = 0; i < stateId; i++) {
//           String stateName = state; 
//           String stateBird = bird;
//           String stateFlower = flower;
           String stateSummary = ("State: " + state + ", Bird: " + bird
                    + ", Flower: " + flower + "\n");
            summary += stateSummary;
        }
        return summary;
    }
    
}
