package simulations;

import java.util.HashMap;
import java.util.Random;
import cells.Cell;
import javafx.scene.paint.Color;


/**
 * Simulation superclass to be used as an abstract object
 * for the simulations used in the UI classes
 *
 * @author Brenna Milligan
 */

public abstract class Simulation {

    private int myTotalStates;
    private Color[] myColors;
    protected HashMap<String, Double> myParameterMap;

    public Simulation (int totalStates, Color[] colors, HashMap<String, Double> parameterMap) {
        myTotalStates = totalStates;
        myColors = colors;
        myParameterMap = parameterMap;
    }

    public abstract Simulation createSimulation (HashMap<String, Double> parameterMap);

    public abstract void checkRules (Cell cell);

    public void setMyParameterMap (HashMap<String, Double> parameterMap) {
        myParameterMap = parameterMap;
    }

    public void updateCell (Cell cell) {
        cell.updateCurrentState();
        cell.setMyColor(myColors[cell.getMyCurrentState()]);
    }

    protected int[] collectNeighborInfo (Cell[] neighbors) {
        int[] countNeighbors = new int[myTotalStates];
        for (int i = 0; i < countNeighbors.length; i++) {
            countNeighbors[i] = 0;
        }
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] != null) {
                countNeighbors[neighbors[i].getMyCurrentState()]++;
            }
        }
        return countNeighbors;
    }

    protected int randomNum (int bound) {
        Random rand = new Random();
        return rand.nextInt(bound);
    }

    public abstract void updateParameters ();
}
