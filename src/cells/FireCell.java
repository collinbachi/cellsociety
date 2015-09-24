package cells;


/**
 * Cells that contain the specific functionality used
 * for the Fire Simulation
 *
 * @author Brenna Milligan
 */

public class FireCell extends Cell {

    @Override
    public void initializeWithState (int state) {
        setMyNextState(state);
    }

}
