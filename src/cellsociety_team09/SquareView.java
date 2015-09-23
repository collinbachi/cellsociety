package cellsociety_team09;

import javafx.scene.shape.*;
import javafx.geometry.Bounds;

public class SquareView extends GridView {

	public SquareView(Grid g, Bounds bounds){
		super(g, bounds);
	}
	
	public Shape generateShape(int row, int col){
		return new Rectangle(myBounds.getMinX() + col * cellWidth, 
							 myBounds.getMinY() + row * cellHeight, 
							 cellWidth, cellHeight);
	}
}