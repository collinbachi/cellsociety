package cells;


/**
 * Cells that contain the specific functionality used
 * for the Segregation Simulation
 *
 * @author Brenna Milligan
 */

public class SegregationCell extends Cell {

    @Override
    public void initializeWithState (int state) {
        myCurrentState = state;
        myNextState = state;
    }

}
