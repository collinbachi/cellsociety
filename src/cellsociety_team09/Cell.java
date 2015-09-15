package cellsociety_team09;

import javafx.scene.paint.Color;


public class Cell {
    private static final int NO_LIVES = 0;

    private int myCurrentState;
    private int myNextState;
    private Color myColor;
    private Cell[] myNeighbors;
    private int myLives;

    public Cell (int state, Color color) {
        myCurrentState = state;
        myNextState = myCurrentState;
        myColor = color;
        myLives = 0;
    }

    public int getMyLives () {
        return myLives;
    }

    public void setMyLives (int myLives) {
        this.myLives = myLives;
    }
    
    public void resetLives() {
        myLives = NO_LIVES;
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

    public void setMyNextState (int nextState) {
        myNextState = nextState;
    }

}
