package cellsociety_team09;

import java.util.ArrayList;
import java.util.HashMap;
import cells.*;
import javafx.scene.control.ScrollPane;
import simulations.*;

public class InfiniteBorderGrid extends Grid{
	
	/*private ArrayList<Tuple<Cell, Integer>> leftEdges = new ArrayList<Tuple<Cell, Integer>>();
	private ArrayList<Tuple<Cell, Integer>> rightEdges = new ArrayList<Tuple<Cell, Integer>>();
	private ArrayList<Tuple<Cell, Integer>> topEdges = new ArrayList<Tuple<Cell, Integer>>();
	private ArrayList<Tuple<Cell, Integer>> bottomEdges = new ArrayList<Tuple<Cell, Integer>>();
	*/
	

	@Override
	protected void initNeighbors(){
		System.out.println("infinite border grid");
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
                    neighbors[k] = safeIndex(i + xCoords[k], j + yCoords[k]) ? myRows.get(i + xCoords[k]).get(j + yCoords[k]) : null;
                            
                }
				myRows.get(i).get(j).setMyNeighbors(neighbors);
			}
		}	
	}
	
	
	private Cell createEdgeCell(int i, int j){
		/*Cell c = createEmptyCell();
		if (!((i<getWidth() && i>=0) || (j<getHeight() && j>=0))) return null;
        if (i == getWidth()) rightEdges.add(new Tuple<Cell, Integer>(c, i));
        if (i < 0) leftEdges.add(new Tuple<Cell, Integer>(c,i));
        if (j == getHeight()) bottomEdges.add(new Tuple<Cell, Integer>(c,j));
        if (j < 0) topEdges.add(new Tuple<Cell, Integer>(c,j));
        return c;*/
		return null;
	}
	
	private Cell createEmptyCell(){
		CellFactory cellFactory = new CellFactory();
        Cell c = cellFactory.createCell(mySim.getClass().getSimpleName());
        mySim.initializeCellWithState(c, 0);
        return c;
	}
	
	/*private class Tuple<Cell, Integer>{
		public final cells.Cell first;
		public final int last;
		public Tuple(cells.Cell c, Integer i){
			first = c;
			last = (int) i;
		}
	}*/
	
	private ArrayList<Cell> newEmptyRow(){
		ArrayList<Cell> ret = new ArrayList<Cell>();
		for(int i = 0; i < this.getWidth(); i++){
			ret.add(createEmptyCell());
		}
		return ret;
	}
	
	public void step(){
		mySim.update(myRows);
		/*for (Tuple tup : topEdges){
			Cell c = tup.first;
			int x = tup.last;
			if (c.getMyCurrentState() != 0){
				myRows.add(0, newCellList(c,x,getWidth()));
			}
		}
		for (Tuple tup : bottomEdges){
			Cell c = tup.first;
			int x = tup.last;
			if (c.getMyCurrentState() != 0){
				myRows.add(newCellList(c,x,getWidth()));
			}
		}
		for (Tuple tup : leftEdges){
			Cell c = tup.first;
			int x = tup.last;
			if (c.getMyCurrentState() != 0){
				for (int i=0; i<getHeight(); i++){
					ArrayList<Cell> row = myRows.get(i);
					row.add(0, i==x ? c : createEmptyCell());
				}
			}
		}
		for (Tuple tup : rightEdges){
			System.out.println("right");
			Cell c = tup.first;
			int x = tup.last;
			if (c.getMyCurrentState() != 0){
				for (int i=0; i<getHeight(); i++){
					ArrayList<Cell> row = myRows.get(i);
					row.add(i==x ? c : createEmptyCell());
				}
			}
		}*/
		for (Cell c : myRows.get(0)){
			if (c.getMyCurrentState()!=0) {
				myRows.add(0, this.newEmptyRow());
				this.initNeighbors();
				break;
			}
		}
		for (Cell c : myRows.get(myRows.size()-1)){
			if (c.getMyCurrentState()!=0) {
				myRows.add(this.newEmptyRow());
				this.initNeighbors();
				break;
			}
		}
		for (int i=0; i<getHeight(); i++){
			if (myRows.get(i).get(0).getMyCurrentState()!=0){
				this.addNewLeftCol();
				this.initNeighbors();
				break;
			}
		}
		for (int i=0; i<getHeight(); i++){
			if (myRows.get(i).get(getWidth()-1).getMyCurrentState()!=0){
				this.addNewRightCol();
				this.initNeighbors();
				break;
			}
		}
		if (myGridView!=null){
			double tempV = sp.getVvalue();
			double tempH = sp.getHvalue();
			myGridView.updateCells();
			sp.setVvalue(tempV);
			sp.setHvalue(tempH);
		}
	}
	
	
	
	private void addNewLeftCol(){
		for (ArrayList<Cell> row : myRows){
			row.add(0, this.createEmptyCell());
		}
	}
	
	private void addNewRightCol(){
		for (ArrayList<Cell> row: myRows){
			row.add(this.createEmptyCell());
		}
	}


}