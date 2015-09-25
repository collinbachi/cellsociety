package simulations;

import java.util.Map;
import cells.Cell;
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
        Cell randomBlankNeighbor = getRandomNeighbor(cell.getMyNeighbors(), BLANK);
        if (randomBlankNeighbor != null) {
            randomBlankNeighbor.setMyNextState(cell.getMyCurrentState());
            cell.setMyNextState(BLANK);
        }

    }

    @Override
<<<<<<< HEAD
=======
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

    @Override
>>>>>>> 87571b78f1cfe2485bc06d0c5692ca6fe7e45b8a
    public void setParameters (Map<String, Double> parameterMap) {
        mySimilarThreshold = parameterMap.get(SIMILAR_THRESHOLD);
    }

}
