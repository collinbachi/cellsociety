package cellsociety_team09;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class Segregation extends Simulation {
    // TODO write in XML commands
    private static final Color[] COLORS = {Color.WHITE, Color.BLUE, Color.RED};
    private static final int SIMILAR_THRESHOLD = 30;
    private static final int BLANK = 0;
    private static final int AGENTX = 1;
    private static final int AGENTY = 2;
    private static final int TOTAL_STATES = 3;

    private int[] collectNeighborInfo(Cell cell) {
        int[] countNeighbors = new int[TOTAL_STATES];
        Cell[] neighbors = cell.getMyNeighbors();
        for(int i = 0; i < neighbors.length; i++) {
            countNeighbors[neighbors[i].getMyCurrentState()]++;
        }
        return countNeighbors;
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
            int percentSimilarNeighbors = neighborInfo[cell.getMyCurrentState()] / (neighborInfo[AGENTX] + neighborInfo[AGENTY]);
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
        // TODO write a depth first search method.... :(
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

    @Override
    public void updateCell (Cell cell) {
        cell.setMyCurrentState(cell.getMyNextState());
        cell.setMyColor(COLORS[cell.getMyCurrentState()]);
    }

}
