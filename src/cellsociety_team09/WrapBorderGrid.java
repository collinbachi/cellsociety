package cellsociety_team09;

import java.util.ArrayList;
import java.util.HashMap;
import cells.*;
import simulations.*;

public class WrapBorderGrid extends Grid{

	@Override
	protected void initNeighbors(){
        int[] xCoords = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] yCoords = { -1, 0, 1, -1, 1, -1, 0, 1 };
        if (isHex){
        	int[] xCoords2 = {-1, 0, 1, 1, 0, -1};
        	int[] yCoords2 = {-1, -1, -1, 0, 1, 0};
        	xCoords = xCoords2;
        	yCoords = yCoords2;
        }
		for (int i=0; i<myRows.size(); i++){
			ArrayList<Cell> row = myRows.get(i);
			for (int j=0; j<row.size(); j++){
				Cell[] neighbors = new Cell[xCoords.length];
                for (int k = 0; k < xCoords.length; k++) {
                    neighbors[k] = wrapIndex(i + xCoords[k], j + yCoords[k]);
                            
                }
				myRows.get(i).get(j).setMyNeighbors(neighbors);
			}
		}
	}
	
	protected Cell wrapIndex(int i, int j){
		if (i >= myRows.size()) i = 0;
		if (i < 0) i = myRows.size()-1;
		if (j >= myRows.get(i).size()) j = 0;
		if (j < 0) j = myRows.get(i).size()-1;
		return myRows.get(i).get(j);
	}

}