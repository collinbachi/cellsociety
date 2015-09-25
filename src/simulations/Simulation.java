package simulations;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import cells.Cell;
import javafx.scene.paint.Paint;


/**
 * Simulation superclass to be used as an abstract object
 * for the simulations used in the UI classes
 *
 * @author Brenna Milligan
 */

public abstract class Simulation {

    private int myTotalStates;
    private Paint[] myPaints;

    public Simulation (int totalStates, Paint[] paints) {
        myTotalStates = totalStates;
        myPaints = paints;
    }

    public abstract void checkRules (Cell cell);
    
    public abstract void setParameters(Map<String, Double> parameterMap);

    public int getMyTotalStates(){
        return myTotalStates;
    }
    
    public void initializeCellWithState(Cell cell, int state) {
        cell.initializeWithState(state);
        updateCell(cell);
    }

    public void updateCell (Cell cell) {
        cell.updateCurrentState();
        cell.setmyPaint(myPaints[cell.getMyCurrentState()]);
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

    protected Cell getRandomNeighbor (Cell[] neighbors, int state) {
        ArrayList<Cell> randomNeighbors = new ArrayList<>();
        for (Cell cell : neighbors) {
            if (cell != null && cell.getMyNextState() == state &&
                cell.getMyCurrentState() == state) {
                randomNeighbors.add(cell);
            }
        }
        if (!randomNeighbors.isEmpty()) {
            int randomNeighbor = randomNum(randomNeighbors.size());
            return (Cell) randomNeighbors.get(randomNeighbor);
        }
        else
            return null;
    }

    protected int randomNum (int bound) {
        Random rand = new Random();
        return rand.nextInt(bound);
    }
}
