
import javafx.scene.paint.Color;
import xmlManagement.XMLReader;

public class Segregation extends Simulation {

    // TODO write in XML commands
    private static final Color[] COLORS = {Color.WHITE, Color.BLUE, Color.RED};
    private static final int BLANK = 0;
    private static final int AGENTX = 1;
    private static final int AGENTY = 2;
    private static final int TOTAL_STATES = 3;
    public static final String SIMILAR_THRESHOLD = "SIMILAR_THRESHOLD";
    
    private double mySimilarThreshold;
    
    public Segregation () {
        super(TOTAL_STATES, COLORS);
        XMLReader reader = new XMLReader();
        mySimilarThreshold = reader.getParameterMap().get(SIMILAR_THRESHOLD);
    }

    @Override
    public void checkRules (Cell cell) {
        int[] neighborInfo = collectNeighborInfo(cell);
        satisfiedSurroundingsRule(cell, neighborInfo);
    }

    private boolean isNotBlank(Cell cell) {
        return cell.getMyCurrentState()==AGENTX || cell.getMyCurrentState()==AGENTY;
    }

    private void satisfiedSurroundingsRule(Cell cell, int[] neighborInfo) {
        if(isNotBlank(cell)) {
            double percentSimilarNeighbors = neighborInfo[cell.getMyCurrentState()] / (neighborInfo[AGENTX] + neighborInfo[AGENTY]);
            if (percentSimilarNeighbors >= mySimilarThreshold) {
                cell.setMyNextState(cell.getMyCurrentState());
            }
            else {
                cell.setMyNextState(BLANK);
                this.moveCell(cell, cell, false);
            }
        }
    }
    
    private void moveCell(Cell cellToMove, Cell neighborCell, boolean moved) {
        if (moved) return;
        for(Cell neighbor : neighborCell.getMyNeighbors()) {
            if (neighbor != null) {
                if(neighbor.getMyNextState()==BLANK) {
                    neighbor.setMyNextState(cellToMove.getMyCurrentState());
                    cellToMove.setMyNextState(BLANK);
                    moved = true;
                    return;
                }
                moveCell(cellToMove, neighbor, moved);
            }
        }

    }

}
