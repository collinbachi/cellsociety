package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cells.Cell;
import cells.SugarScapeCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public abstract class SugarScape extends SimulationWithPatch {

    public static final int TOTAL_STATES = 5;
    public static final Paint[] COLORS =
            { Color.WHITE, Color.DARKBLUE, Color.PEACHPUFF, Color.LIGHTCORAL, Color.DARKORANGE };
    public static final String MAX_METABOLISM = "MAX_METABOLISM";
    public static final String MIN_METABOLISM = "MIN_METABOLISM";
    public static final String MAX_VISION = "MAX_VISION";
    public static final String MIN_VISION = "MIN_VISION";
    public static final String MAX_INIT_SUGAR = "MAX_INIT_SUGAR";
    public static final String MIN_INIT_SUGAR = "MIN_INIT_SUGAR";
    public static final String SUGAR_GROW_BACK_RATE = "SUGAR_GROW_BACK_RATE";
    public static final String SUGAR_GROW_BACK_INTERVAL = "SUGAR_GROW_BACK_INTERVAL";
    private static final int EMPTY = 0;
    private static final int AGENT = 1;
    private static final int LOW_SUGAR = 2;
    private static final int MEDIUM_SUGAR = 3;
    private static final int HIGH_SUGAR = 4;

    protected int myMaxMetabolism;
    protected int myMinMetabolism;
    protected int myMaxVision;
    protected int myMinVision;
    protected int myMaxInitSugar;
    protected int myMinInitSugar;
    protected int mySugarGrowBackRate;
    protected int mySugarGrowBackInterval;
    protected int[] myCardinalNeighbors = new int[4];
    private int myTicks;

    public SugarScape () {
        super(TOTAL_STATES, COLORS);
    }

    @Override
    public void update (List<ArrayList<Cell>> rows) {
        List<SugarScapeCell> agents = new ArrayList<>();
        List<SugarScapeCell> otherCells = new ArrayList<>();
        for (List<Cell> row : rows) {
            for (Cell cell : row) {
                if (cell.checkMyCurrentState(AGENT)) {
                    agents.add((SugarScapeCell) cell);
                }
            }
        }
        while (!agents.isEmpty()) {
            SugarScapeCell cell = agents.get(randomNum(agents.size()));
            checkRules(cell);
            this.updateCell(cell);
            agents.remove(cell);
        }
        while (!otherCells.isEmpty()) {
            SugarScapeCell cell = otherCells.get(randomNum(otherCells.size()));
            checkRules(cell);
            this.updateCell(cell);
            otherCells.remove(cell);
        }

    }
    
    @Override
    public void initializeCells(List<ArrayList<Cell>> rows) {
        for (List<Cell> row : rows) {
            for (Cell cell : row) {
                SugarScapeCell sugarCell = (SugarScapeCell) cell;
                sugarCell.setMyMaxSugar(randomNum(myMaxInitSugar) + myMinInitSugar);
                sugarCell.setMySugar(sugarCell.getMyMaxSugar());
                if (sugarCell.checkMyCurrentState(AGENT)) {
                    sugarCell.setMySugarMetabolism(randomNum(myMaxMetabolism) + myMinMetabolism);
                    sugarCell.setMyVision(randomNum(myMaxVision) + myMinVision);
                }
                
            }
        }
    }

    public void checkRules (Cell cell) {
        myCardinalNeighbors = initializeCardinalNeighbors(cell.getMyNeighbors().length);
        growBackSugar((SugarScapeCell) cell);
        if (cell.checkMyCurrentState(AGENT)) {
            List<SugarScapeCell> seenCells = cellsAgentSees((SugarScapeCell) cell);
            SugarScapeCell cellToMoveTo = cellWithMostSugar(seenCells);
            moveToPatch((SugarScapeCell) cell, cellToMoveTo);
            subtractMetabolism(cellToMoveTo);
        }

    }

    public void setParameters (Map<String, Double> parameterMap) {
        myMaxMetabolism = parameterMap.get(MAX_METABOLISM).intValue();
        myMinMetabolism = parameterMap.get(MIN_METABOLISM).intValue();
        myMaxVision = parameterMap.get(MAX_VISION).intValue();
        myMinVision = parameterMap.get(MIN_VISION).intValue();
        myMaxInitSugar = parameterMap.get(MAX_INIT_SUGAR).intValue();
        myMinInitSugar = parameterMap.get(MIN_INIT_SUGAR).intValue();
        mySugarGrowBackRate = parameterMap.get(SUGAR_GROW_BACK_RATE).intValue();
        mySugarGrowBackInterval = parameterMap.get(SUGAR_GROW_BACK_INTERVAL).intValue();
    }

    private void growBackSugar (SugarScapeCell cell) {
        if (myTicks >= mySugarGrowBackInterval) {
            cell.setMySugar(cell.getMySugar() + mySugarGrowBackRate);
            if (cell.getMySugar() > cell.getMyMaxSugar()) {
                cell.setMySugar(cell.getMyMaxSugar());
            }
            myTicks = 0;
        }
        else
            myTicks++;
    }

    private List<SugarScapeCell> cellsAgentSees (SugarScapeCell agent) {
        List<SugarScapeCell> seenCells = new ArrayList<>();
        SugarScapeCell currentCell = agent;
        for (int j = 0; j < agent.getMyVision(); j++) {
            for (int i : myCardinalNeighbors) {
                int k = j;
                currentCell = agent;
                while (k >= 0) {
                    currentCell = (SugarScapeCell) currentCell.getMyNeighbors()[i];
                    k--;
                }
                if (currentCell != null)
                    seenCells.add(currentCell);
            }
        }
        return seenCells;
    }

    private SugarScapeCell cellWithMostSugar (List<SugarScapeCell> cellsToChoose) {
        if (cellsToChoose.isEmpty())
            return null;
        SugarScapeCell cellToChoose = cellsToChoose.get(0);
        for (int i = cellsToChoose.size(); i > 0; i--) {
            SugarScapeCell cell = cellsToChoose.get(i);
            if (cell.getMySugar() > cellToChoose.getMySugar()) {
                cellToChoose = cell;
            }
        }
        return cellToChoose;
    }

    private void moveToPatch (SugarScapeCell agent, SugarScapeCell cellToMoveTo) {
        if (cellToMoveTo != null) {
            cellToMoveTo.setMyAgentsSugar(agent.getMyAgentsSugar() + cellToMoveTo.getMySugar());
            cellToMoveTo.setMySugar(0);
            cellToMoveTo.setMyNextState(AGENT);
            setSugarStates(agent);
        }
    }

    private void subtractMetabolism (SugarScapeCell agent) {
        agent.setMyAgentsSugar(agent.getMySugar() - agent.getMySugarMetabolism());
        if (agent.getMyAgentsSugar() <= 0) {
            setSugarStates(agent);
        }
    }

    private void setSugarStates (SugarScapeCell cell) {
        if (cell.getMySugar() <= 0) {
            cell.setMyNextState(EMPTY);
        }
        else if (cell.getMySugar() < cell.getMyMaxSugar() / 2) {
            cell.setMyNextState(LOW_SUGAR);
        }
        else if (cell.getMySugar() < cell.getMyMaxSugar()) {
            cell.setMyNextState(MEDIUM_SUGAR);
        }
        else if (cell.getMySugar() == cell.getMyMaxSugar()) {
            cell.setMyNextState(HIGH_SUGAR);
        }
        cell.setMyAgentsSugar(0);
    }

}
