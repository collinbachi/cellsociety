package cellsociety_team09;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class Segregation extends Simulation {

    // TODO write in XML commands
    private static final Color[] COLORS = {Color.WHITE, Color.BLUE, Color.RED};
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

    @Override
    public void checkRules (Cell cell) {
        int[] neighborInfo = collectNeighborInfo(cell.getMyNeighbors());
        satisfiedSurroundingsRule(cell, neighborInfo);
    }

    private boolean isNotBlank(Cell cell) {
        return cell.getMyCurrentState()==AGENTX || cell.getMyCurrentState()==AGENTY;
    }

    private void satisfiedSurroundingsRule(Cell cell, int[] neighborInfo) {
        if(isNotBlank(cell) && (neighborInfo[AGENTX] != 0 || neighborInfo[AGENTY] != 0)) {
            double percentSimilarNeighbors = neighborInfo[cell.getMyCurrentState()] / (neighborInfo[AGENTX] + neighborInfo[AGENTY]);
            if (percentSimilarNeighbors >= mySimilarThreshold) {
                cell.setMyNextState(cell.getMyCurrentState());
            }
            else {
                cell.setMyNextState(BLANK);
                moveCell(cell);
            }
        }
    }
    
    private void moveCell(Cell cell) {
        Cell randomBlankNeighbor = getRandomNeighbor(cell.getMyNeighbors(), BLANK);
        if (randomBlankNeighbor != null) {
            randomBlankNeighbor.setMyNextState(cell.getMyCurrentState());
            cell.setMyNextState(BLANK);
        }

    }

}
