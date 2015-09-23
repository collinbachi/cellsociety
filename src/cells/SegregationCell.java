package cells;

import javafx.scene.paint.Color;
import simulations.Segregation;


/**
 * Cells that contain the specific functionality used
 * for the Segregation Simulation
 *
 * @author Brenna Milligan
 */

public class SegregationCell extends Cell {

    public SegregationCell (int state, Color color) {
        super(state, color);
    }

    private SegregationCell () {
        super(0, null);
    }

    static {
        CellFactory.registerCell(Segregation.SEGREGATION, new SegregationCell());
    }

    @Override
    public Cell createCell (int state, Color color) {
        return new SegregationCell(state, color);
    }

}
