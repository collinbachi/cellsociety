package simulations;

import java.util.HashMap;
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
    private static final int[] VALID_NEIGHBORS = { 1, 3, 4, 6 };
    public static final String PROB_CATCH = "PROB_CATCH";

    private double myProbCatch;

    public Fire () {
        super(TOTAL_STATES, COLORS);
    }
    
    @Override
    public void checkRules (Cell cell) {
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
        for (int i : VALID_NEIGHBORS) {
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
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

    @Override
    public void setParameters (HashMap<String, Double> parameterMap) {
        myProbCatch = parameterMap.get(PROB_CATCH);
    }

}
