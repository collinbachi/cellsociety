package cells;

import java.util.List;


public class ForagingAntsCell extends CellWithPatch {

    private boolean myFoodItem;
    private int myNestPheromones;
    private int myFoodPheromones;
    private int myLives;
    private List<Integer> myNeighborLocations;
    private int myNumberOfAnts;

    public ForagingAntsCell () {
        myFoodItem = false;
        myNestPheromones = 0;
        myFoodPheromones = 0;
        myNumberOfAnts = 0;
        myLives = 0;
    }

    public int getMyLives () {
        return myLives;
    }

    public void incrementLives () {
        myLives++;
    }

    public void resetLives () {
        myLives = 0;
    }

    public void addToNumberOfAnts (int toAdd) {
        myNumberOfAnts += toAdd;
    }

    public void incrementMyNumberOfAnts () {
        myNumberOfAnts++;
    }

    public void decrementMyNumberOfAnts () {
        myNumberOfAnts--;
    }

    public void setMyNeighborLocations () {
        for (int i = 0; i < getMyNeighbors().length; i++) {
            myNeighborLocations.add(i);
        }
    }

    public boolean hasFoodItem () {
        return myFoodItem;
    }

    public int getMyNestPheromones () {
        return myNestPheromones;
    }

    public int getMyFoodPheromones () {
        return myFoodPheromones;
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

    public void setMyNestPheromones (int myNestPheromones) {
        this.myNestPheromones = myNestPheromones;
    }

    public void setMyFoodPheromones (int myFoodPheromones) {
        this.myFoodPheromones = myFoodPheromones;
    }

    public void setMyFoodItem (boolean myFoodItem) {
        this.myFoodItem = myFoodItem;
    }

    public int getTotalPheromones () {
        return myFoodPheromones + myNestPheromones;
    }

    @Override
    public void initializeWithState (int state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void evaporate (double evaporationRate) {
        myNestPheromones = (int) (-myNestPheromones * evaporationRate);
        myFoodPheromones = (int) (-myFoodPheromones * evaporationRate);
    }
    
    @Override
    public void diffuse(double diffusionRate) {
        for (ForagingAntsCell neighbor : (ForagingAntsCell[]) myNeighbors) {
            if (neighbor != null) {
                int currentNeighborPatch = neighbor.getMyNestPheromones();
                int patchToAdd = (int) (myNestPheromones*diffusionRate);
                neighbor.setMyNestPheromones(currentNeighborPatch + patchToAdd);
                
                currentNeighborPatch = neighbor.getMyFoodPheromones();
                patchToAdd = (int) (myFoodPheromones*diffusionRate);
                neighbor.setMyFoodPheromones(currentNeighborPatch + patchToAdd);
            }
        }
    }

}
