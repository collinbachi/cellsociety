package cellsociety_team09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import cells.*;
import javafx.scene.paint.Color;
import simulations.*;


/**
 * Class for holding the 2d array of Cells that compose the Grid, along with
 * the current Simulation.
 *
 * @author D. Collin Bachi
 */

public class Grid {
    private static final int NUMBER_OF_NEIGHBORS = 8;
    private ArrayList<ArrayList<Cell>> myRows;
    private Simulation mySim;

    public static final long delay = 10000;
    public static long interval = 50000;

    // remove this later
    private GridView myGridView;

    public void Grid () {
        // do nothing
    }

    static {
        try {
            String[] simNames =
                    { Conway.CONWAY, Fire.FIRE, Segregation.SEGREGATION,
                      PredatorPrey.PREDATORPREY };
            for (String simName : simNames) {
                Class.forName(String.format("simulations.%s", simName));
                Class.forName(String.format("cells.%sCell", simName));
            }
        }
        catch (ClassNotFoundException any) {
            any.printStackTrace();
        }
    }

    public void init (int[][] rows, String sim, HashMap<String, Double> parameterMap) {
        SimulationFactory simulationFactory = new SimulationFactory();
        mySim = simulationFactory.createSimulation(sim, parameterMap);
        myRows = new ArrayList<ArrayList<Cell>>();
        CellFactory cellFactory = new CellFactory();
        for (int[] row : rows) {
            ArrayList<Cell> cellRow = new ArrayList<Cell>();
            for (int state : row) {
                Cell cellToAdd = cellFactory.createCell(sim, state, Color.BLACK);
                cellRow.add(cellToAdd);
            }
            myRows.add(cellRow);
        }

        initNeighbors();

        /*
         * TimerTask task = new TimerTask(){
         * 
         * @Override
         * public void run(){ step(); }
         * };
         * Timer timer = new Timer();
         * timer.scheduleAtFixedRate(task, delay, interval);
         */
    }

    private void initNeighbors () {
        int[] xCoords = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] yCoords = { -1, 0, 1, -1, 1, -1, 0, 1 };
        for (int i = 0; i < myRows.size(); i++) {
            ArrayList<Cell> row = myRows.get(i);
            for (int j = 0; j < row.size(); j++) {
                Cell[] neighbors = new Cell[8];
                for (int k = 0; k < xCoords.length; k++) {
                    neighbors[k] =
                            safeIndex(i + xCoords[k], j + yCoords[k]) ? myRows.get(i + xCoords[k])
                                    .get(j + yCoords[k]) : null;
                }
                myRows.get(i).get(j).setMyNeighbors(neighbors);
            }
        }
    }

    private boolean safeIndex (int i, int j) {
        try {
            Cell c = myRows.get(i).get(j);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public int getWidth () {
        return myRows.get(0).size();
    }

    public int getHeight () {
        return myRows.size();
    }

    public Cell getCell (int row, int col) {
        ArrayList<Cell> temp = myRows.get(row);
        return temp.get(col);
    }

    public void step () {
        for (ArrayList<Cell> row : myRows) {
            for (Cell c : row) {
                mySim.checkRules(c);
            }
        }
        for (ArrayList<Cell> row : myRows) {
            for (Cell c : row) {
                mySim.updateCell(c);
            }
        }

        if (myGridView != null) {
            myGridView.updateCells();
        }
    }

    public void setGridView (GridView g) {
        myGridView = g;
    }
}