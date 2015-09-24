package cells;

import javafx.scene.paint.Paint;


/**
 * Cell superclass to be used as an abstract object
 * for the cells used in the UI classes
 *
 * @author Brenna Milligan
 */

public abstract class Cell {
    protected int myCurrentState;
    protected int myNextState;
    private Paint myPaint;
    private Cell[] myNeighbors;
    
    public abstract void initializeWithState(int state);

    public void updateCurrentState () {
        myCurrentState = myNextState;
    }

    public boolean checkMyCurrentState (int numberToCheck) {
        return myCurrentState == numberToCheck;
    }

    public Paint getMyPaint () {
        return myPaint;
    }

    public int getMyCurrentState () {
        return myCurrentState;
    }

    public void setmyPaint (Paint myPaint) {
        this.myPaint = myPaint;
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
