package cells;


/**
 * Cells that contain the specific functionality used
 * for the Conway Simulation
 *
 * @author Brenna Milligan
 */

public class ConwayCell extends Cell {

    @Override
    public void initializeWithState (int state) {
        myNextState = state;
        myCurrentState = state;
    }

}
