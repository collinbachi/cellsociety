package cells;

public abstract class CellWithPatch extends Cell {
    
    protected int[] myForwardLocations;
    protected int myOrientation;
    protected int myPatchAmount;
    
    public int getMyPatchAmount () {
        return myPatchAmount;
    }
    
    public int[] getMyForwardLocations () {
        return myForwardLocations;
    }

    public void setMyPatchAmount (int d) {
        this.myPatchAmount = d;
    }

    public CellWithPatch() {
        myOrientation = 1;
        myForwardLocations = new int[3];
    }

    @Override
    public abstract void initializeWithState (int state);

    public void updateForwardLocations(int orientation) {
        myOrientation = orientation;
        myForwardLocations[0] = orientation - 1;
        myForwardLocations[1] = orientation;
        myForwardLocations[2] = orientation + 1;
        if (orientation == 0) {
            myForwardLocations[0] = getMyNeighbors().length - 1;
        }
        else if (orientation == myNeighbors.length - 1) {
            myForwardLocations[2] = 0;
        }
    }
    
    public int getMyOrientation () {
        return myOrientation;
    }
    
    public int wrapAroundNeighbors (int number) {
        if (number > myNeighbors.length) {
            number -= myNeighbors.length;
        }
        else if (number < -myNeighbors.length) {
            number += myNeighbors.length;
        }
        return number;
    }
    
    public void evaporate(double evaporationRate) {
        myPatchAmount = (int) (myPatchAmount * (1-evaporationRate));
    }
    
    public void diffuse(double diffusionRate) {
        for (Cell cell : myNeighbors) {
            CellWithPatch neighbor = (CellWithPatch) cell;
            if (neighbor != null) {
                int currentNeighborPatch = neighbor.getMyPatchAmount();
                int patchToAdd = (int) (myPatchAmount*diffusionRate);
                neighbor.setMyPatchAmount(currentNeighborPatch + patchToAdd);
            }
        }
    }
}
