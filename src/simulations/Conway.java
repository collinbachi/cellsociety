package simulations;

import java.util.HashMap;
import cells.Cell;
import javafx.scene.paint.Color;


/**
 * Class that controls the rules and
 * functionality of the Conway Simulation
 *
 * @author Brenna Milligan
 */

public class Conway extends Simulation {
    public static final String CONWAY = "Conway";
    private static final Color[] COLORS = { Color.WHITE, Color.BLACK };
    private static final int TOTAL_STATES = 2;
    private static final int OFF = 0;
    private static final int ON = 1;

    public Conway (HashMap<String, Double> parameterMap) {
        super(TOTAL_STATES, COLORS, parameterMap);
    }

    private Conway () {
        super(0, null, null);
    }

    static {
        SimulationFactory.registerSimulation(CONWAY, new Conway());
    }

    @Override
    public void checkRules (Cell cell) {
        int[] neighborInfo = collectNeighborInfo(cell.getMyNeighbors());
        if (neighborInfo[ON] < 2) {
            cell.setMyNextState(OFF);
        }
        else if (neighborInfo[ON] == 3 && cell.getMyCurrentState() == OFF) {
            cell.setMyNextState(ON);
        }
        else if (neighborInfo[ON] > 3) {
            cell.setMyNextState(OFF);
        }
        else if (neighborInfo[ON] <= 3) {
            // cell remains unchanged
        }
    }

    @Override
    public Simulation createSimulation (HashMap<String, Double> parameterMap) {
        return new Conway(parameterMap);
    }

    @Override
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

}
