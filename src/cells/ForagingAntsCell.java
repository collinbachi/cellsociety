package cells;

public class ForagingAntsCell extends CellWithPatch {

    private boolean myFoodItem;
    private int myNestPheromones;
    private int myFoodPheromones;
    private int myLives;
    private int[] myNeighborLocations;
    private int myNumberOfAnts;
    private boolean isNest;
    private boolean isFood;

    public ForagingAntsCell () {
        myFoodItem = false;
        myNestPheromones = 0;
        myFoodPheromones = 0;
        myNumberOfAnts = 0;
        myLives = 0;
        isNest = false;
        isFood = false;
    }
    
    public void setIsNest(boolean isnest) {
        isNest = isnest;
    }
    
    public void setIsFood(boolean isfood) {
        isFood = isfood;
    }
    
    public boolean isNest() {
        return isNest;
    }
    
    public boolean isFood() {
        return isFood;
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
        myNeighborLocations = new int[myNeighbors.length];
        for (int i = 0; i < getMyNeighbors().length; i++) {
            myNeighborLocations[i] = i;
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

    public int[] getMyNeighborLocations () {
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
        myNextState = state;
        myFoodItem = false;
        myNestPheromones = 0;
        myFoodPheromones = 0;
        myLives = 0;
    }

    @Override
    public void evaporate (double evaporationRate) {
        myNestPheromones = (int) (-myNestPheromones * evaporationRate);
        myFoodPheromones = (int) (-myFoodPheromones * evaporationRate);
    }
    
    @Override
    public void diffuse(double diffusionRate) {
        for (Cell cell : myNeighbors) {
            ForagingAntsCell neighbor = (ForagingAntsCell) cell;
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
