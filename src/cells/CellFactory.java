package cells;

import java.util.HashMap;
import javafx.scene.paint.Color;


/**
 * Factory class to generate the concrete cell class needed
 *
 * @author Brenna Milligan
 */

public class CellFactory {

    private static HashMap<String, Cell> myRegisteredCells = new HashMap<>();

    public static void registerCell (String cellID, Cell cell) {
        myRegisteredCells.put(cellID, cell);
    }

    public Cell createCell (String cellID, int state, Color color) {
        return myRegisteredCells.get(cellID).createCell(state, color);
    }

}
