
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
/** 
 * Class for holding the 2d array of Cells that compose the Grid, along with
 * the current Simulation.
 *
 * @author D. Collin Bachi 
 */


public class GridView extends Group{
	private Grid myGrid;
	private Bounds myBounds;
	private double cellWidth;
	private double cellHeight;
	private ArrayList<ArrayList<Rectangle>> myRows = new ArrayList<ArrayList<Rectangle>>();

	public GridView(Grid g, Bounds bounds){
		myGrid = g;
		myBounds = bounds;
		cellWidth = bounds.getWidth() * 1.0 / myGrid.getWidth();
		cellHeight = bounds.getHeight() * 1.0 / myGrid.getHeight();
		init();
	}

	private void init(){
		for(int i = 0; i < myGrid.getHeight(); i++){
			ArrayList<Rectangle> row = new ArrayList<Rectangle>();
			for(int j = 0; j < myGrid.getWidth(); j++){
				Rectangle c = new Rectangle(myBounds.getMinX() + i * cellWidth, 
							  myBounds.getMinY() + i * cellHeight, 
							  cellWidth, cellHeight);
				c.setFill(myGrid.getCell(j,i).getMyColor());
				row.add(c);
			}
		}
	}
}