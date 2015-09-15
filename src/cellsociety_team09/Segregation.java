
import javafx.scene.paint.Color;

public class Segregation extends Simulation {

    // TODO write in XML commands
    private static final Color[] COLORS = {Color.WHITE, Color.BLUE, Color.RED};
    private static final int SIMILAR_THRESHOLD = 30;
    private static final int BLANK = 0;
    private static final int AGENTX = 1;
    private static final int AGENTY = 2;
    private static final int TOTAL_STATES = 3;
    
    public Segregation () {
        super(TOTAL_STATES, COLORS);
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
            int percentSimilarNeighbors = neighborInfo[cell.getMyCurrentState()] / (neighborInfo[AGENTX] + neighborInfo[AGENTY]) * 100;
            if (percentSimilarNeighbors >= SIMILAR_THRESHOLD) {
                cell.setMyNextState(cell.getMyCurrentState());
            }
            else {
                cell.setMyNextState(BLANK);
                moveAgent(cell, cell, false);
            }
        }
    }

    private void moveAgent(Cell agent, Cell cell, boolean moved) {
        for(Cell neighbor : agent.getMyNeighbors()) {
            if (!moved && neighbor != null) {
                if(neighbor.getMyNextState()==BLANK ) {
                    neighbor.setMyNextState(agent.getMyCurrentState());
                    agent.setMyNextState(BLANK);
                    moved = true;
                    return;
                }
                moveAgent(agent, neighbor, moved);
            }
        }

    }

}
