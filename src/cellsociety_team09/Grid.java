package cellsociety_team09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.paint.Color;
/** 
 * Class for holding the 2d array of Cells that compose the Grid, along with
 * the current Simulation.
 *
 * @author D. Collin Bachi 
 */


public class Grid{
	private ArrayList<ArrayList<Cell>> myRows;
	private Simulation mySim;

	public static final long delay = 10000;
	public static long interval = 50000;

	//remove this later
	private GridView myGridView;

	public void Grid(){
		//do nothing
	}

	public void init(int[][] rows, String sim, HashMap<String, Double> parameterMap){
		myRows = new ArrayList<ArrayList<Cell>>();
		for (int[] row : rows){
			ArrayList<Cell> cellRow = new ArrayList<Cell>();
			for (int state : row){
				cellRow.add(new Cell(state, Color.BLACK));
			}
			myRows.add(cellRow);
		}

		initNeighbors();

		try{
			mySim = (Simulation) Class.forName("cellsociety_team09." + sim).getConstructor(HashMap.class).newInstance(parameterMap);
		}catch(Exception e){ 
 			System.out.println("There was a problem instantiating the class" +
 							   " by name in Grid.java");
		}

		
		
		// If we want Simulation classes to use a constructor
		/*Class<?> temp = Class.forName(sim);
		Constructor<?> constructor = temp.getConstructor(String.class, Integer.class);
		Object instance = constructor.newInstance("stringparam", 42);*/
		
		
		
		/*TimerTask task = new TimerTask(){
			@Override
			public void run(){ step(); }
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, delay, interval);*/
	}

	private void initNeighbors(){
		for (int i=0; i<myRows.size(); i++){
			ArrayList<Cell> row = myRows.get(i);
			for (int j=0; j<row.size(); j++){
				Cell[] neighbors = new Cell[8];
				neighbors[0] = safeIndex(i-1, j-1) ? myRows.get(i-1).get(j-1) : null;
				neighbors[1] = safeIndex(i-1, j) ? myRows.get(i-1).get(j) : null;
				neighbors[2] = safeIndex(i-1, j+1) ? myRows.get(i-1).get(j+1) : null;
				neighbors[3] = safeIndex(i, j-1) ? myRows.get(i).get(j-1) : null;
				neighbors[4] = safeIndex(i, j+1) ? myRows.get(i).get(j+1) : null;
				neighbors[5] = safeIndex(i+1, j-1) ? myRows.get(i+1).get(j-1) : null;
				neighbors[6] = safeIndex(i+1, j) ? myRows.get(i+1).get(j) : null;
				neighbors[7] = safeIndex(i+1, j+1) ? myRows.get(i+1).get(j+1) : null;
				myRows.get(i).get(j).setMyNeighbors(neighbors);
			}
		}
	}

	private boolean safeIndex(int i, int j){
		try{
			Cell c = myRows.get(i).get(j);
			return true;
		}catch(Exception e){
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

	public void step(){
		for (ArrayList<Cell> row : myRows){
			for (Cell c : row){
				mySim.checkRules(c);
			}
		}
		for (ArrayList<Cell> row : myRows){
			for (Cell c : row){
				mySim.updateCell(c);
			}
		}

		if (myGridView!=null){
			myGridView.updateCells();
		}
	}

	public void setGridView(GridView g){
		myGridView = g;
	}
}