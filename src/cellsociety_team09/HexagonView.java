package cellsociety_team09;

import javafx.scene.shape.*;
import javafx.geometry.Bounds;

public class HexagonView extends GridView {

	public HexagonView(Grid g, Bounds bounds){
		super(g, bounds);
	}
	
	
	private final double xconst = 0.225708;
	private final double sconst = 0.548584;
	
	public Shape generateShape(int row, int col){
		double ydelta = 0;
		if(col % 2 != 0){
			ydelta = -cellHeight/2;
		}
			double temp = cellWidth;
			cellWidth *= 1+xconst;
			double x1 = myBounds.getMinX() + col * cellWidth * (1-xconst);
			double y1 = myBounds.getMinY() + row * cellHeight + cellHeight/2 + ydelta;
			double x2 = x1 + xconst*cellWidth;
			double y2 = y1 - cellHeight/2;
			double x3 = x2 + sconst*cellWidth;
			double y3 = y2;
			double x4 = x1 + cellWidth;
			double y4 = y1;
			double x5 = x3;
			double y5 = y1 + cellHeight/2;
			double x6 = x2;
			double y6 = y5;
			cellWidth = temp;
			return new Polygon(x1,y1,x2,y2,x3,y3,x4,y4,x5,y5,x6,y6);
	}
}