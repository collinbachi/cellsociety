package cells;

import javafx.scene.paint.Color;
import simulations.Fire;


/**
 * Cells that contain the specific functionality used
 * for the Fire Simulation
 *
 * @author Brenna Milligan
 */

public class FireCell extends Cell {

    public FireCell (int state, Color color) {
        super(state, color);
    }

    private FireCell () {
        super(0, null);
    }

    static {
        CellFactory.registerCell(Fire.FIRE, new FireCell());
    }

    @Override
    public Cell createCell (int state, Color color) {
        return new FireCell(state, color);
    }

}
