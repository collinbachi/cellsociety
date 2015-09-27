package cellsociety_team09;

import javafx.scene.shape.*;
import javafx.geometry.Bounds;


public class TriangleView extends GridView {

    public TriangleView (Grid g, Bounds bounds) {
        super(g, bounds);
    }

    public Shape generateShape (int row, int col) {
        if (col % 2 == 0) {
            return new Polygon(myBounds.getMinX() + (col + 0.0) * cellWidth,
                               myBounds.getMinY() + row * cellHeight,
                               myBounds.getMinX() + (col + 2) * cellWidth,
                               myBounds.getMinY() + row * cellHeight,
                               myBounds.getMinX() + (col + 1) * cellWidth,
                               myBounds.getMinY() + (row + 1) * cellHeight);
        }
        else {
            return new Polygon(myBounds.getMinX() + (col + 0.0) * cellWidth,
                               myBounds.getMinY() + (row + 1) * cellHeight,
                               myBounds.getMinX() + (col + 2.0) * cellWidth,
                               myBounds.getMinY() + (row + 1) * cellHeight,
                               myBounds.getMinX() + (col + 1) * cellWidth,
                               myBounds.getMinY() + row * cellHeight);
        }
    }
}
