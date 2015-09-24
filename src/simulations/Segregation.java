package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import cells.Cell;
import cells.SegregationCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * Class that controls the rules and
 * functionality of the Segregation Simulation
 *
 * @author Brenna Milligan
 */

public class Segregation extends Simulation {

    public static final String ID = "Segregation";
    private static final Paint[] COLORS = { Color.WHITE, Color.BLUE, Color.RED };
    private static final int BLANK = 0;
    private static final int AGENTX = 1;
    private static final int AGENTY = 2;
    private static final int TOTAL_STATES = 3;
    public static final String SIMILAR_THRESHOLD = "SIMILAR_THRESHOLD";

    private double mySimilarThreshold;

    public Segregation () {
        super(TOTAL_STATES, COLORS);
    }
    
    @Override
    public void checkRules (Cell cell) {
        int[] neighborInfo = collectNeighborInfo(cell.getMyNeighbors());
        satisfiedSurroundingsRule(cell, neighborInfo);
    }

    private boolean isNotBlank (Cell cell) {
        return cell.getMyCurrentState() == AGENTX || cell.getMyCurrentState() == AGENTY;
    }

    private void satisfiedSurroundingsRule (Cell cell, int[] neighborInfo) {
        if (isNotBlank(cell) && (neighborInfo[AGENTX] != 0 || neighborInfo[AGENTY] != 0)) {
            double percentSimilarNeighbors =
                    neighborInfo[cell.getMyCurrentState()] /
                                             (neighborInfo[AGENTX] + neighborInfo[AGENTY]);
            if (percentSimilarNeighbors >= mySimilarThreshold) {
                cell.setMyNextState(cell.getMyCurrentState());
            }
            else {
                cell.setMyNextState(BLANK);
                moveSegregationCell(cell);
            }
        }
    }

    private void moveSegregationCell (Cell cell) {
        SegregationCell randomBlankNeighbor = getRandomNeighbor(cell.getMyNeighbors(), BLANK);
        if (randomBlankNeighbor != null) {
            randomBlankNeighbor.setMyNextState(cell.getMyCurrentState());
            cell.setMyNextState(BLANK);
        }

    }

    private SegregationCell getRandomNeighbor (Cell[] neighbors, int state) {
        ArrayList<Cell> randomNeighbors = new ArrayList<>();
        for (Cell cell : neighbors) {
            if (cell != null && cell.getMyNextState() == state &&
                cell.getMyCurrentState() == state) {
                randomNeighbors.add(cell);
            }
        }
        if (!randomNeighbors.isEmpty()) {
            int randomNeighbor = randomNum(randomNeighbors.size());
            return (SegregationCell) randomNeighbors.get(randomNeighbor);
        }
        else
            return null;
    }

    @Override
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

    @Override
    public void setParameters (HashMap<String, Double> parameterMap) {
        mySimilarThreshold = parameterMap.get(SIMILAR_THRESHOLD);
    }

}