package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cells.Cell;
import cells.ForagingAntsCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class ForagingAnts extends SimulationWithPatch {
    public static final int TOTAL_STATES = 5;
    public static final int EMPTY = 0;
    public static final int LOW_PHEROMONES = 1;
    public static final int MEDIUM_PHEROMONES = 2;
    public static final int HIGH_PHEROMONES = 3;
    public static final int ANT = 4;
    private static final Paint[] COLORS =
            { Color.WHITE, Color.color(255 / 255, 168 / 255, 168 / 255),
              Color.color(255 / 255, 20 / 255, 20 / 255),
              Color.color(141 / 255, 0 / 255, 0 / 255), Color.BLACK };
    public static final String ANT_LIFE = "ANT_LIFE";
    public static final String MAX_ANTS = "MAX_ANTS";
    public static final String ANTS_BORN_PER_TIME = "ANTS_BORN_PER_TIME";
    public static final String MIN_PHEROMONE = "MIN_PHEROMONE";
    public static final String MAX_PHEROMONE = "MAX_PHEROMONE";
    public static final String EVAPORATION_RATE = "EVAPORATION_RATE";
    public static final String DIFFUSION_RATE = "DIFFUSION_RATE";
    public static final String INIT_ANTS = "INIT_ANTS";
    public static final String K = "K";
    public static final String N = "N";
    public static final int CONSTANT = 2;

    private int myMaxAnts;
    private int myAntsBornPerTime;
    private int myMaxPheromones;
    private double myK;
    private double myN;
    private int myInitAnts;

    public ForagingAnts () {
        super(TOTAL_STATES, COLORS);
    }

    @Override
    public void update (List<ArrayList<Cell>> rows) {
        for (int i = 0; i < rows.size(); i++) {
            List<Cell> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                ForagingAntsCell cell = (ForagingAntsCell) row.get(j);
                if (i == 70 && j == 70) {
                    cell.addToNumberOfAnts(myAntsBornPerTime);
                }
                checkRules(cell);
            }
        }
        for (List<Cell> row : rows) {
            for (Cell c : row) {
                updateCell(c);
            }
        }
    }

    @Override
    public void initializeCells (List<ArrayList<Cell>> rows) {
        for (int i = 0; i < rows.size(); i++) {
            List<Cell> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                ForagingAntsCell cell = (ForagingAntsCell) row.get(j);
                if (i == 70 && j == 70) {
                    cell.setMyNestPheromones(myMaxPheromones);
                    cell.addToNumberOfAnts(myInitAnts);
                    cell.setIsNest(true);
                }
                else if (i == 20 && j == 20) {
                    cell.setMyFoodPheromones(myMaxPheromones);
                    cell.setIsFood(true);
                }
                setNextState(cell);
                updateCell(cell);
            }
        }
    }

    @Override
    public void checkRules (Cell cell) {
        ForagingAntsCell antCell = (ForagingAntsCell) cell;
        int numberOfAntsOnCell = antCell.getMyNumberOfAnts();
        for (int i = 0; i < numberOfAntsOnCell; i++) {
            findNestOrFood(antCell);
        }
        setNextState(antCell);
        if (antCell.getMyCurrentState() != EMPTY) {
            antCell.diffuse(myDiffusionRate);
            antCell.evaporate(myEvaporationRate);
        }
    }

    private void setNextState (ForagingAntsCell cell) {
        int state = EMPTY;
        if (cell.getMyNumberOfAnts() > 0) {
            state = ANT;
        }
        else if (cell.getTotalPheromones() == 0) {
            state = EMPTY;
        }
        else if (cell.getTotalPheromones() <= myMaxPheromones / 3) {
            state = LOW_PHEROMONES;
        }
        else if (cell.getTotalPheromones() <= myMaxPheromones * 2 / 3) {
            state = MEDIUM_PHEROMONES;
        }
        else if (cell.getTotalPheromones() <= myMaxPheromones) {
            state = HIGH_PHEROMONES;
        }
        cell.setMyNextState(state);
    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        myMaxAnts = parameterMap.get(MAX_ANTS).intValue();
        myAntsBornPerTime = parameterMap.get(ANTS_BORN_PER_TIME).intValue();
        myMaxPheromones = parameterMap.get(MAX_PHEROMONE).intValue();
        myEvaporationRate = parameterMap.get(EVAPORATION_RATE);
        myDiffusionRate = parameterMap.get(DIFFUSION_RATE);
        myK = parameterMap.get(K);
        myN = parameterMap.get(N);
        myInitAnts = parameterMap.get(INIT_ANTS).intValue();
    }

    private void findNestOrFood (ForagingAntsCell ant) {
        if (ant.isFood() || ant.isNest()) {
            ant.updateForwardLocations(setOrientation(ant));
        }
        int locationToMove = selectLocation((ForagingAntsCell[]) ant.getMyNeighbors(),
                                            ant.getMyForwardLocations(), ant.hasFoodItem());
        if (locationToMove == -1) {
            locationToMove = selectLocation((ForagingAntsCell[]) ant
                    .getMyNeighbors(), ant.getMyNeighborLocations(), ant.hasFoodItem());
        }
        if (locationToMove != -1) {
            dropPheromones(ant);
            ant.updateForwardLocations(locationToMove);
            ForagingAntsCell neighborToMoveTo =
                    (ForagingAntsCell) ant.getMyNeighbors()[locationToMove];
            neighborToMoveTo.incrementMyNumberOfAnts();
            ant.decrementMyNumberOfAnts();
            neighborToMoveTo.setMyFoodItem(ant.hasFoodItem());
            ant.setMyFoodItem(false);
            if (neighborToMoveTo.isNest()) {
                neighborToMoveTo.setMyFoodItem(false);
            }
            else if (neighborToMoveTo.isFood()) {
                neighborToMoveTo.setMyFoodItem(true);
            }
        }
    }

    private int setOrientation (ForagingAntsCell ant) {
        ForagingAntsCell[] neighbors = (ForagingAntsCell[]) ant.getMyNeighbors();
        int orientation = randomNum(neighbors.length);
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] != null) {
                if (ant.hasFoodItem()) {
                    if (neighbors[i].getMyNestPheromones() > neighbors[orientation]
                            .getMyNestPheromones()) {
                        orientation = i;
                    }
                }
                else if (!ant.hasFoodItem() &&
                         neighbors[i].getMyFoodPheromones() > neighbors[orientation]
                                 .getMyFoodPheromones()) {
                    orientation = i;
                }
            }
        }
        return orientation;
    }

    private int selectLocation (ForagingAntsCell[] neighbors,
                                List<Integer> locationSet,
                                boolean hasFood) {
        int location = -1;
        for (int i : locationSet) {
            if (neighbors[i] == null)
                locationSet.remove(i);
            if (neighbors[i].getMyNumberOfAnts() >= myMaxAnts) {
                locationSet.remove(i);
            }
        }
        if (!locationSet.isEmpty()) {
            int max = 0;
            for (int i : locationSet) {
                if (hasFood && neighbors[i].getMyNestPheromones() > max) {
                    max = neighbors[i].getMyNestPheromones();
                    location = i;
                }
                else if (!hasFood &&
                         Math.pow(myK + neighbors[i].getMyFoodPheromones(), myN) > max) {
                    max = (int) Math.pow(myK + neighbors[i].getMyFoodPheromones(), myN);
                    location = i;
                }
            }
        }
        return location;
    }

    private void dropPheromones (ForagingAntsCell ant) {
        if (ant.isFood()) {
            ant.setMyFoodPheromones(myMaxPheromones);
        }
        else if (ant.isNest()) {
            ant.setMyNestPheromones(myMaxPheromones);
        }
        else {
            int max = 0;
            for (ForagingAntsCell neighbor : (ForagingAntsCell[]) ant.getMyNeighbors()) {
                if (neighbor != null) {
                    int neighborsPheromones =
                            ant.hasFoodItem() ? neighbor.getMyFoodPheromones()
                                              : neighbor.getMyNestPheromones();
                    if (neighborsPheromones > max)
                        max = neighborsPheromones;
                }
            }
            int desired = max - CONSTANT;
            if (ant.hasFoodItem() && desired > ant.getMyFoodPheromones()) {
                ant.setMyFoodPheromones(desired);
            }
            else if (!ant.hasFoodItem() && desired > ant.getMyNestPheromones()) {
                ant.setMyNestPheromones(desired);
            }
        }
    }

}
