package cellsociety_team09;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cells.Cell;
import cells.CellFactory;
import javafx.scene.control.ScrollPane;
import simulations.Simulation;
import simulations.SimulationFactory;


/** 
 * Class for holding the 2d array of Cells that compose the Grid, along with
 * the current Simulation.
 *
 * @author D. Collin Bachi 
 */

public abstract class Grid{
	protected List<ArrayList<Cell>> myRows;
	protected Simulation mySim;

	//remove this later
	protected GridView myGridView;
	protected boolean isHex = false;
	protected ScrollPane sp;

	public void init(int[][] rows, String sim, Map<String, Double> parameterMap){
        SimulationFactory simulationFactory = new SimulationFactory();
        mySim = simulationFactory.createSimulation(sim);
        mySim.setParameters(parameterMap);
		myRows = new ArrayList<ArrayList<Cell>>();
        CellFactory cellFactory = new CellFactory();
		for (int[] row : rows){
			ArrayList<Cell> cellRow = new ArrayList<Cell>();
			for (int state : row){
                Cell cellToAdd = cellFactory.createCell(sim);
                mySim.initializeCellWithState(cellToAdd, state);
                cellRow.add(cellToAdd);
			}
			myRows.add(cellRow);
		}
		initNeighbors();
		mySim.initializeCells(myRows);
	}

	protected abstract void initNeighbors();

	protected boolean safeIndex(int i, int j){
		try{
            myRows.get(i).get(j);
			return true;
        }
        catch (Exception e) {
			return false;
		}
	}

	public int getWidth(){
		return myRows.get(0).size();
	}

	public int getHeight(){
		return myRows.size();
	}

	public Cell getCell(int row, int col){
		ArrayList<Cell> temp = myRows.get(row);
		return temp.get(col);
	}

	public void incrementState(int row, int col){
		Cell c = myRows.get(row).get(col);
		//System.out.println(c.getMyCurrentState());
		mySim.initializeCellWithState(c, mySim.getMyTotalStates() > c.getMyCurrentState()+1 ? c.getMyCurrentState()+1 : 0);
		//System.out.println(c.getMyCurrentState());
		myGridView.updateCells();
	}

	public void step(){
		mySim.update(myRows);

		if (myGridView!=null){
			myGridView.updateCells();
		}
	}
	
	public void updateView(){
		myGridView.updateCells();
	}

	public void setGridView(GridView g){
		myGridView = g;
	}
	
	public void setParameterMap(Map<String,Double> newParameterMap)
	{
	    mySim.setParameters(newParameterMap);
	}
	
	public void setSP(ScrollPane s){
		sp = s;
	}
}