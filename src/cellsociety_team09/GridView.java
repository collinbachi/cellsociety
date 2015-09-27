package cellsociety_team09;

import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;


/** 
 * Class for holding the 2d array of Cells that compose the Grid, along with
 * the current Simulation.
 *
 * @author D. Collin Bachi 
 */



public abstract class GridView extends Group{
	protected Grid myGrid;
	protected Bounds myBounds;
	protected double cellWidth;
	protected double cellHeight;
	protected ArrayList<ArrayList<Shape>> myRows = new ArrayList<ArrayList<Shape>>();

	public GridView(Grid g, Bounds bounds){
		myGrid = g;
		myBounds = bounds;
		cellWidth = bounds.getWidth() * 1.0 / myGrid.getWidth();
		cellHeight = bounds.getHeight() * 1.0 / myGrid.getHeight();
		g.setGridView(this);
		init();
	}

	private void init(){
		updateCells();
	}

	public void updateCells(){
		this.getChildren().clear();
		for(int i = 0; i < myGrid.getHeight(); i++){
			ArrayList<Shape> row = new ArrayList<Shape>();
			for(int j = 0; j < myGrid.getWidth(); j++){
				Shape c = generateShape(i,j);
				c.setFill(myGrid.getCell(i,j).getMyPaint());
				final int ii = i; // Because the nested class
				final int jj = j; // wants a final int...
				c.setOnMousePressed(new EventHandler<MouseEvent>() {
   					public void handle(MouseEvent me) {
        				//System.out.println("Mouse pressed");
        				myGrid.incrementState(ii, jj);
    			}});
				row.add(c);
				this.getChildren().add(c);
			}
		}
	}

	public abstract Shape generateShape(int row, int col);

}
