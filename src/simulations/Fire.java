package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cells.Cell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * Class that controls the rules and
 * functionality of the Fire Simulation
 *
 * @author Brenna Milligan
 */

public class Fire extends Simulation {
    public static final String ID = "Fire";
    private static final int TOTAL_STATES = 3;
    private static final Paint[] COLORS = { Color.YELLOW, Color.DARKOLIVEGREEN, Color.DARKRED };
    private static final int EMPTY = 0;
    private static final int TREE = 1;
    private static final int BURNING = 2;
    public static final String PROB_CATCH = "PROB_CATCH";

    private double myProbCatch;
    private int[] myCardinalNeighbors = new int[4];

    public Fire () {
        super(TOTAL_STATES, COLORS);
    }
    
    
    
    @Override
    public void checkRules (Cell cell) {
        myCardinalNeighbors = initializeCardinalNeighbors(cell.getMyNeighbors().length);
        if (isBurning(cell)) {
            burnDown(cell);
        }
        else if (isTree(cell)) {
            mayCatchFire(cell);
        }

    }

    private boolean isTree (Cell cell) {
        return cell.getMyCurrentState() == TREE;
    }

    private boolean isBurning (Cell cell) {
        return cell.getMyCurrentState() == BURNING;
    }

    private void burnDown (Cell cell) {
        cell.setMyNextState(EMPTY);
    }

    private void mayCatchFire (Cell cell) {
        for (int i : myCardinalNeighbors) {
            if (cell.getMyNeighbors()[i] != null) {
                int neighborState = cell.getMyNeighbors()[i].getMyCurrentState();
                if (neighborState == BURNING && randomDouble() < myProbCatch) {
                    cell.setMyNextState(BURNING);
                }
            }
        }
    }

    protected double randomDouble () {
        Random rand = new Random();
        return rand.nextDouble();
    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        myProbCatch = parameterMap.get(PROB_CATCH);
    }
    
    @Override
    public void initializeCells (List<ArrayList<Cell>> rows) {
        // Do nothing
        
    }

}
