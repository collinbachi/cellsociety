package cells;

import java.util.ArrayList;
import java.util.List;

public class ForagingAntsCell extends Cell {
    public static final int NUMBER_FORWARD_LOCATIONS = 3;
    
    private boolean myFoodItem;
    private int myNestPheromones;
    private int myFoodPheromones;
    private int myLives;
    private int myOrientation;
    private List<Integer> myForwardLocations;
    private List<Integer> myNeighborLocations;
    private boolean myObstacle;
    private int myNumberOfAnts;
    
    public ForagingAntsCell() {
        myFoodItem = false;
        myNestPheromones = 0;
        myFoodPheromones = 0;
        myOrientation = 1;
        myForwardLocations = new ArrayList<Integer>();
        updateForwardLocations();
        myNumberOfAnts = 0;
        setMyNeighborLocations();
        myLives = 0;
    }
    
    public int getMyLives () {
        return myLives;
    }

    public void incrementLives () {
        myLives++;
    }
    
    public void resetLives() {
        myLives = 0;
    }

    public void incrementMyNumberOfAnts () {
        myNumberOfAnts++;
    }
    
    public void decrementMyNumberOfAnts () {
        myNumberOfAnts--;
    }

    private void setMyNeighborLocations() {
        for (int i = 0; i < getMyNeighbors().length; i++) {
            myNeighborLocations.add(i);
        }
    }
    
    public boolean isAnObstacle() {
        return myObstacle;
    }
    
    public boolean hasFoodItem() {
        return myFoodItem;
    }

    public int getMyNestPheromones () {
        return myNestPheromones;
    }

    public int getMyFoodPheromones () {
        return myFoodPheromones;
    }
    
    public int getMyOrientation () {
        return myOrientation;
    }
    
    public List<Integer> getMyForwardLocations () {
        return myForwardLocations;
    }
    
    public List<Integer> getMyNeighborLocations () {
        return myNeighborLocations;
    }
    
    public int getMyNumberOfAnts () {
        return myNumberOfAnts;
    }
    
    public void setMyOrientation (int myOrientation) {
        this.myOrientation = myOrientation;
        updateForwardLocations();
    }
    
    private void updateForwardLocations() {
        myForwardLocations.add(myOrientation - 1);
        myForwardLocations.add(myOrientation);
        myForwardLocations.add(myOrientation + 1);
        if (myOrientation == 0) {
            myForwardLocations.set(0, getMyNeighbors().length - 1);
        }
        else if (myOrientation == getMyNeighbors().length - 1) {
            myForwardLocations.set(2, 0);
        }
    }

    public void setMyNestPheromones (int myNestPheromones) {
        this.myNestPheromones = myNestPheromones;
    }

    public void setMyFoodPheromones (int myFoodPheromones) {
        this.myFoodPheromones = myFoodPheromones;
    }
    
    public void setMyFoodItem (boolean myFoodItem) {
        this.myFoodItem = myFoodItem;
    }

    public int getTotalPheromones() {
        return myFoodPheromones + myNestPheromones;
    }
    @Override
    public void initializeWithState (int state) {
        // TODO Auto-generated method stub
        
    }

}
