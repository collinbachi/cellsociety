package cellsociety_team09;

import java.util.ArrayList;
import java.util.HashMap;
import cells.*;
import simulations.*;

public class InfiniteBorderGrid extends Grid{
	
	private ArrayList<Cell> leftEdges = new ArrayList<Cell>();
	private ArrayList<Cell> rightEdges = new ArrayList<Cell>();
	private ArrayList<Cell> topEdges = new ArrayList<Cell>();
	private ArrayList<Cell> bottomEdges = new ArrayList<Cell>();

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
                    neighbors[k] = safeIndex(i + xCoords[k], j + yCoords[k]) ? myRows.get(i + xCoords[k]).get(j + yCoords[k]) : createEdgeCell(i,j);
                            
                }
				myRows.get(i).get(j).setMyNeighbors(neighbors);
			}
		}	
	}
	
	private Cell createEdgeCell(int i, int j){
		CellFactory cellFactory = new CellFactory();
        Cell c = cellFactory.createCell(mySim.getClass().getSimpleName());
        mySim.initializeCellWithState(c, 0);
        if (i == getWidth()) rightEdges.add(c);
        if (i < 0) leftEdges.add(c);
        if (j == getHeight()) bottomEdges.add(c);
        if (j < 0) topEdges.add(c);
        return c;
	}
	
	public void step(){
		mySim.update(myRows);
		for (Cell c : topEdges){
			if (c.getMyCurrentState() != 0){
				
			}
		}
		if (myGridView!=null){
			myGridView.updateCells();
		}
	}

}