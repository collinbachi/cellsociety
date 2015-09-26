package cells;

import java.util.ArrayList;
import java.util.List;

public abstract class CellWithPatch extends Cell {
    
    protected List<Integer> myForwardLocations;
    protected int myOrientation;
    
    public CellWithPatch() {
        myOrientation = 1;
        myForwardLocations = new ArrayList<Integer>();
    }

    @Override
    public abstract void initializeWithState (int state);

    public void updateForwardLocations(int orientation) {
        myOrientation = orientation;
        myForwardLocations.add(orientation - 1);
        myForwardLocations.add(orientation);
        myForwardLocations.add(orientation + 1);
        if (orientation == 0) {
            myForwardLocations.set(0, getMyNeighbors().length - 1);
        }
        else if (orientation == getMyNeighbors().length - 1) {
            myForwardLocations.set(2, 0);
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
}
