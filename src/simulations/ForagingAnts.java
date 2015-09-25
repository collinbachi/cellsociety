package simulations;

import java.util.List;
import java.util.Map;
import cells.Cell;
import cells.ForagingAntsCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class ForagingAnts extends Simulation {
    public static final int TOTAL_STATES = 2;
    public static final int BLANK = 0;
    public static final int LOW_PHEROMONES = 1;
    public static final int MEDIUM_PHEROMONES = 2;
    public static final int HIGH_PHEROMONES = 3;
    public static final int ANT = 4;
    private static final Paint[] COLORS =
            { Color.WHITE, Color.color(255, 168, 168), Color.color(255, 20, 20),
              Color.color(141, 0, 0), Color.BLACK };
    public static final String ANT_LIFE = "ANT_LIFE";
    public static final String MAX_ANTS = "MAX_ANTS";
    public static final String ANTS_BORN_PER_TIME = "ANTS_BORN_PER_TIME";
    public static final String MIN_PHEROMONE = "MIN_PHEROMONE";
    public static final String MAX_PHEROMONE = "MAX_PHEROMONE";
    public static final String EVAPORATION_RATIO = "EVAPORATION_RATIO";
    public static final String DIFFUSION_RATIO = "DIFFUSION_RATIO";
    public static final String K = "K";
    public static final String N = "N";
    public static final int CONSTANT = 2;

    // For Jasper to add to XML
    private int myAntLife = 500;
    private int myMaxAnts = 10;
    private int myAntsBornPerTime = 2;
    private int myMinPheromones = 0;
    private int myMaxPheromones = 1000;
    private double myEvaporationRatio = .1;
    private double myDiffusionRatio = .1;
    private double myK = .001;
    private double myN = 10;

    public ForagingAnts () {
        super(TOTAL_STATES, COLORS);
    }

    @Override
    public void checkRules (Cell cell) {
        int numberOfAntsOnCell = ((ForagingAntsCell) cell).getMyNumberOfAnts();
        if (numberOfAntsOnCell > 0) {
            for (int i = 0; i < numberOfAntsOnCell; i++) {
                if (((ForagingAntsCell) cell).getMyLives() == myAntLife) {
                    kill((ForagingAntsCell) cell);
                }
                else {
                    antForage((ForagingAntsCell) cell);
                }
            }
        }
        ((ForagingAntsCell) cell).incrementLives();
        setNextState((ForagingAntsCell) cell);
    }

    public void setNextState (ForagingAntsCell cell) {
        int state = BLANK;
        if (cell.getMyNumberOfAnts() > 0) {
            state = ANT;
        }
        else if (cell.getTotalPheromones() == 0) {
            state = BLANK;
        }
        else if (cell.getTotalPheromones() <= myMaxPheromones/3) {
            state = LOW_PHEROMONES;
        }
        else if (cell.getTotalPheromones() <= myMaxPheromones*2/3) {
            state = MEDIUM_PHEROMONES;
        }
        else if (cell.getTotalPheromones() <= myMaxPheromones) {
            state = HIGH_PHEROMONES;
        }
        cell.setMyNextState(state);
    }

    private void kill (ForagingAntsCell cell) {
        cell.setMyFoodItem(false);
        cell.decrementMyNumberOfAnts();
    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        myAntLife = parameterMap.get(ANT_LIFE).intValue();
        myMaxAnts = parameterMap.get(MAX_ANTS).intValue();
        myAntsBornPerTime = parameterMap.get(ANTS_BORN_PER_TIME).intValue();
        myMinPheromones = parameterMap.get(MIN_PHEROMONE).intValue();
        myMaxPheromones = parameterMap.get(MAX_PHEROMONE).intValue();
        myEvaporationRatio = parameterMap.get(EVAPORATION_RATIO);
        myDiffusionRatio = parameterMap.get(DIFFUSION_RATIO);
        myK = parameterMap.get(K);
        myN = parameterMap.get(N);
    }

    private void antForage (ForagingAntsCell ant) {
        if (ant.hasFoodItem()) {
            returnToNest(ant);
        }
        else {
            findFoodSource(ant);
        }
    }

    private boolean atFoodSource (ForagingAntsCell ant) {
        return ant.getMyFoodPheromones() == myMaxPheromones;
    }

    private boolean atNest (ForagingAntsCell ant) {
        return ant.getMyNestPheromones() == myMaxPheromones;
    }

    private void returnToNest (ForagingAntsCell ant) {
        if (atFoodSource(ant)) {
            ant.setMyOrientation(setOrientationNest(ant));
        }
        int locationToMove =
                selectLocationNest((ForagingAntsCell[]) ant.getMyNeighbors(),
                                   ant.getMyForwardLocations());
        if (locationToMove == -1) {
            locationToMove =
                    selectLocationNest((ForagingAntsCell[]) ant.getMyNeighbors(),
                                       ant.getMyNeighborLocations());
        }
        if (locationToMove != -1) {
            dropFoodPheromones(ant);
            ant.setMyOrientation(locationToMove);
            ForagingAntsCell neighborToMoveTo =
                    (ForagingAntsCell) ant.getMyNeighbors()[locationToMove];
            neighborToMoveTo.incrementMyNumberOfAnts();
            ant.decrementMyNumberOfAnts();
            if (atNest(neighborToMoveTo)) {
                neighborToMoveTo.setMyFoodItem(false);
            }
        }
    }

    private void findFoodSource (ForagingAntsCell ant) {
        if (atNest(ant)) {
            ant.setMyOrientation(setOrientationFood(ant));
        }
        int locationToMove =
                selectLocationFood((ForagingAntsCell[]) ant.getMyNeighbors(),
                                   ant.getMyForwardLocations());
        if (locationToMove == -1) {
            locationToMove =
                    selectLocationFood((ForagingAntsCell[]) ant.getMyNeighbors(),
                                       ant.getMyNeighborLocations());
        }
        if (locationToMove != -1) {
            dropHomePheromones(ant);
            ant.setMyOrientation(locationToMove);
            ForagingAntsCell neighborToMoveTo =
                    (ForagingAntsCell) ant.getMyNeighbors()[locationToMove];
            neighborToMoveTo.incrementMyNumberOfAnts();
            ant.decrementMyNumberOfAnts();
            if (atFoodSource(neighborToMoveTo)) {
                neighborToMoveTo.setMyFoodItem(true);
            }
        }
    }

    private int setOrientationFood (ForagingAntsCell ant) {
        int orientation = -1;
        ForagingAntsCell[] neighbors = (ForagingAntsCell[]) ant.getMyNeighbors();
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] != null) {
                if (neighbors[i].getMyFoodPheromones() > neighbors[orientation]
                        .getMyFoodPheromones()) {
                    orientation = i;
                }
            }
        }
        return orientation;
    }

    private int setOrientationNest (ForagingAntsCell ant) {
        int orientation = -1;
        ForagingAntsCell[] neighbors = (ForagingAntsCell[]) ant.getMyNeighbors();
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] != null) {
                if (neighbors[i].getMyNestPheromones() > neighbors[orientation]
                        .getMyNestPheromones()) {
                    orientation = i;
                }
            }
        }
        return orientation;
    }

    private int selectLocationNest (ForagingAntsCell[] neighbors, List<Integer> locationSet) {
        int location = -1;
        for (int i : locationSet) {
            if (neighbors[i] != null)
                locationSet.remove(i);
            if (neighbors[i].isAnObstacle() || neighbors[i].getMyNumberOfAnts() >= myMaxAnts) {
                locationSet.remove(i);
            }
        }
        if (!locationSet.isEmpty()) {
            int max = 0;
            for (int i : locationSet) {
                if (neighbors[i].getMyNestPheromones() > max) {
                    location = i;
                }
            }
        }
        return location;
    }

    private int selectLocationFood (ForagingAntsCell[] neighbors, List<Integer> locationSet) {
        int location = -1;
        for (int i : locationSet) {
            if (neighbors[i] != null)
                locationSet.remove(i);
            if (neighbors[i].isAnObstacle() || neighbors[i].getMyNumberOfAnts() >= myMaxAnts) {
                locationSet.remove(i);
            }
        }
        if (!locationSet.isEmpty()) {
            int maxProb = 0;
            for (int i : locationSet)
                if (Math.pow(myK + neighbors[i].getMyFoodPheromones(), myN) > maxProb)
                    location = i;
        }
        return location;
    }

    private void dropHomePheromones (ForagingAntsCell ant) {
        if (atNest(ant)) {
            ant.setMyNestPheromones(myMaxPheromones);
        }
        else {
            int max = 0;
            for (ForagingAntsCell neighbor : (ForagingAntsCell[]) ant.getMyNeighbors()) {
                int neighborsNestPheromones = neighbor.getMyNestPheromones();
                if (neighborsNestPheromones > max)
                    max = neighborsNestPheromones;
            }
            int desired = max - CONSTANT;
            if (desired > ant.getMyNestPheromones()) {
                ant.setMyNestPheromones(desired);
            }
        }
    }

    private void dropFoodPheromones (ForagingAntsCell ant) {
        if (atFoodSource(ant)) {
            ant.setMyFoodPheromones(myMaxPheromones);
        }
        else {
            int max = 0;
            for (ForagingAntsCell neighbor : (ForagingAntsCell[]) ant.getMyNeighbors()) {
                if (neighbor != null) {
                    int neighborsFoodPheromones = neighbor.getMyFoodPheromones();
                    if (neighborsFoodPheromones > max)
                        max = neighborsFoodPheromones;
                }
            }
            int desired = max - CONSTANT;
            if (desired > ant.getMyFoodPheromones()) {
                ant.setMyFoodPheromones(desired);
            }
        }
    }

}
