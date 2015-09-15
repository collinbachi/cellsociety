package cellsociety_team09;

import javafx.scene.paint.Color;

public class Cell {
    
    private int myCurrentState;
    private int myNextState;
    private Color myColor;
    private Cell[] myNeighbors;
    
    public Cell(int state) {
        myCurrentState = state;
        myNextState = myCurrentState;
        myColor = Color.WHITE;
    }
    
    public Color getMyColor () {
        return myColor;
    }

    public void setMyColor (Color myColor) {
        this.myColor = myColor;
    }

    public int getMyNextState () {
        return myNextState;
    }

    public int getMyCurrentState () {
        return myCurrentState;
    }

    public void setMyCurrentState (int myCurrentState) {
        this.myCurrentState = myCurrentState;
    }

    public Cell[] getMyNeighbors () {
        return myNeighbors;
    }

    public void setMyNeighbors (Cell[] myNeighbors) {
        this.myNeighbors = myNeighbors;
    }

    public void setMyNextState(int nextState) {
        myNextState = nextState;
    }

}
