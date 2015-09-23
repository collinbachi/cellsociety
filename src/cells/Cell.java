package cells;

import javafx.scene.paint.Color;


/**
 * Cell superclass to be used as an abstract object
 * for the cells used in the UI classes
 *
 * @author Brenna Milligan
 */

public abstract class Cell {
    protected int myCurrentState;
    private int myNextState;
    private Color myColor;
    private Cell[] myNeighbors;

    public Cell (int state, Color color) {
        myCurrentState = state;
        myNextState = myCurrentState;
        myColor = color;
    }

    public abstract Cell createCell (int state, Color color);

    public void updateCurrentState () {
        myCurrentState = myNextState;
    }

    public boolean checkMyCurrentState (int numberToCheck) {
        return myCurrentState == numberToCheck;
    }

    public Color getMyColor () {
        return myColor;
    }

    public int getMyCurrentState () {
        return myCurrentState;
    }

    public void setMyColor (Color myColor) {
        this.myColor = myColor;
    }

    public int getMyNextState () {
        return myNextState;
    }

    public Cell[] getMyNeighbors () {
        return myNeighbors;
    }

    public void setMyNeighbors (Cell[] myNeighbors) {
        this.myNeighbors = myNeighbors;
    }

    public void setMyNextState (int nextState) {
        myNextState = nextState;
    }

}
