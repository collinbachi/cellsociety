package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import cells.Cell;
import cells.SegregationCell;
import javafx.scene.paint.Color;


/**
 * Class that controls the rules and
 * functionality of the Segregation Simulation
 *
 * @author Brenna Milligan
 */

public class Segregation extends Simulation {

    public static final String SEGREGATION = "Segregation";
    private static final Color[] COLORS = { Color.WHITE, Color.BLUE, Color.RED };
    private static final int BLANK = 0;
    private static final int AGENTX = 1;
    private static final int AGENTY = 2;
    private static final int TOTAL_STATES = 3;
    public static final String SIMILAR_THRESHOLD = "SIMILAR_THRESHOLD";

    private double mySimilarThreshold;

    public Segregation (HashMap<String, Double> parameterMap) {
        super(TOTAL_STATES, COLORS, parameterMap);
        mySimilarThreshold = myParameterMap.get(SIMILAR_THRESHOLD);
    }

    private Segregation () {
        super(0, null, null);
    }

    static {
        SimulationFactory.registerSimulation(SEGREGATION, new Segregation());
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
    public Simulation createSimulation (HashMap<String, Double> parameterMap) {
        return new Segregation(parameterMap);
    }

    @Override
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

}
