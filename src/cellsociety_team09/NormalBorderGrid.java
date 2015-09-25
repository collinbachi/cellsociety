package cellsociety_team09;

import java.util.ArrayList;
import java.util.HashMap;
import cells.*;
import simulations.*;

public class NormalBorderGrid extends Grid{

	@Override
	protected void initNeighbors(){
        int[] xCoords = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] yCoords = { -1, 0, 1, -1, 1, -1, 0, 1 };
		for (int i=0; i<myRows.size(); i++){
			ArrayList<Cell> row = myRows.get(i);
			for (int j=0; j<row.size(); j++){
				Cell[] neighbors = new Cell[8];
                for (int k = 0; k < xCoords.length; k++) {
                    neighbors[k] = safeIndex(i + xCoords[k], j + yCoords[k]) ? myRows.get(i + xCoords[k]).get(j + yCoords[k]) : null;
                            
                }
				myRows.get(i).get(j).setMyNeighbors(neighbors);
			}
		}
	}

}