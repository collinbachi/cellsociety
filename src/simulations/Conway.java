package simulations;

import java.util.Map;
import cells.Cell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * Class that controls the rules and
 * functionality of the Conway Simulation
 *
 * @author Brenna Milligan
 */

public class Conway extends Simulation {
    public static final String ID = "Conway";
    private static final Paint[] COLORS = { Color.WHITE, Color.BLACK };
    private static final int TOTAL_STATES = 2;
    private static final int OFF = 0;
    private static final int ON = 1;

    public Conway () {
        super(TOTAL_STATES, COLORS);
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
<<<<<<< HEAD
=======
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

    @Override
>>>>>>> 87571b78f1cfe2485bc06d0c5692ca6fe7e45b8a
    public void setParameters (Map<String, Double> parameterMap) {
        // No parameters to update
    }

}
