package cells;

import javafx.scene.paint.Color;
import simulations.Conway;


/**
 * Cells that contain the specific functionality used
 * for the Conway Simulation
 *
 * @author Brenna Milligan
 */

public class ConwayCell extends Cell {

    public ConwayCell (int state, Color color) {
        super(state, color);
    }

    private ConwayCell () {
        super(0, null);
    }

    static {
        CellFactory.registerCell(Conway.CONWAY, new ConwayCell());
    }

    @Override
    public Cell createCell (int state, Color color) {
        return new ConwayCell(state, color);
    }

}
