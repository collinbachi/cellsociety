package cellsociety_team09;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
/** 
 * Class for holding the 2d array of Cells that compose the Grid, along with
 * the current Simulation.
 *
 * @author D. Collin Bachi 
 */


public class Grid{
	private ArrayList<ArrayList> myRows;
	private Simulation mySim;

	public static final long delay = 0;
	public static long interval = 1000;

	public void Grid(ArrayList<ArrayList> rows, String sim){
		myRows = rows;

		try{
			mySim = (Simulation) Class.forName(sim).newInstance();
		}catch(Exception e){ return; }

		// If we want Simulation classes to use a constructor
		/*Class<?> temp = Class.forName(sim);
		Constructor<?> constructor = temp.getConstructor(String.class, Integer.class);
		Object instance = constructor.newInstance("stringparam", 42);*/
		TimerTask task = new TimerTask(){
			@Override
			public void run(){ step(); }
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, delay, interval);
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
	}
}