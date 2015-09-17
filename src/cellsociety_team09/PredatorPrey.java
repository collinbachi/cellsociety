package cellsociety_team09;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import xmlManagement.XMLReader;


public class PredatorPrey extends Simulation {
    private static final Color[] COLORS = { Color.WHITE, Color.LIGHTGREEN, Color.CORNFLOWERBLUE };
    private static final int[] VALID_NEIGHBORS = { 1, 3, 4, 6 };
    private static final int TOTAL_STATES = 3;
    private static final int BLANK = 0;
    private static final int FISH = 1;
    private static final int SHARK = -1;
    private static final int SHARK_COLOR = 2;
    public static final String FISH_REPRODUCTION_TIME = "FISH_REPRODUCTION_TIME";
    public static final String SHARK_REPRODUCTION_TIME = "SHARK_REPRODUCTION_TIME";
    public static final String FISH_ENERGY = "SHARK_REPRODUCTION_TIME";
    public static final String UNIT_ENERGY = "UNIT_ENERGY";

    private int myFishReproductionTime;
    private int mySharkReproductionTime;
    private int myFishEnergy;
    private int myUnitEnergy;

    public PredatorPrey () {
        super(TOTAL_STATES, COLORS);
        XMLReader reader = new XMLReader();
        myFishReproductionTime = reader.getParameterMap().get(FISH_REPRODUCTION_TIME).intValue();
        mySharkReproductionTime = (int) reader.getParameterMap().get(SHARK_REPRODUCTION_TIME).intValue();
        myFishEnergy = (int) reader.getParameterMap().get(FISH_ENERGY).intValue();
        myUnitEnergy = (int) reader.getParameterMap().get(UNIT_ENERGY).intValue();
    }

    private boolean isFish (Cell cell) {
        return cell.getMyCurrentState() > 0;
    }

    private boolean isShark (Cell cell) {
        return cell.getMyCurrentState() < 0;
    }

    @Override
    public void checkRules (Cell cell) {
        if (isFish(cell)) {
            moveFish(cell);
        }
        else if (isShark(cell)) {
            sharkRules(cell);
        }

    }

    private void moveFish (Cell fish) {
        Cell[] neighbors = fish.getMyNeighbors();
        for (int i : VALID_NEIGHBORS) {
            if (neighbors[i] != null && neighbors[i].getMyNextState() == BLANK) {
                neighbors[i].setMyNextState(FISH);
                fishReproductionRules(fish, neighbors[i]);
                fish.resetLives();
                return;
            }
        }
        fish.incrementLives();
    }

    private void fishReproductionRules (Cell fish, Cell neighbor) {
        if (fish.checkMyLives(myFishReproductionTime)) {
            fish.setMyNextState(FISH);
            neighbor.resetLives();
        }
        else {
            fish.setMyNextState(BLANK);
            neighbor.incrementLives();
        }
    }

    private void sharkRules (Cell shark) {
        shark.addToCurrentState(myUnitEnergy);
        if(!sharkIsDead(shark)) {
            shark.incrementLives();
            Cell newSpot = findSpot(shark.getMyNeighbors());
            if (newSpot != null) {
                moveShark(shark, newSpot);
            }
        }
        else {
            shark.resetLives();
            shark.setMyNextState(BLANK);
        }
    }

    private Cell findSpot(Cell[] neighbors) {
        ArrayList<Integer> fishLocations = getLocations(neighbors, FISH);
        ArrayList<Integer> blankLocations = getLocations(neighbors, BLANK);
        int spot = 0;
        if (!fishLocations.isEmpty()) {
            spot = fishLocations.get(randomNum(fishLocations.size()));
            return neighbors[spot];
        }
        else if (!blankLocations.isEmpty()) {
            spot = blankLocations.get(randomNum(blankLocations.size()));
            return neighbors[spot];
        }
        return null;
    }

    private boolean sharkIsDead(Cell shark) {
        return shark.checkMyCurrentState(BLANK);
    }

    private ArrayList<Integer> getLocations (Cell[] neighbors, int status) {
        ArrayList<Integer> fishLocations = new ArrayList<>();
        for (int i : VALID_NEIGHBORS) {
            if (neighbors[i].checkMyCurrentState(status)) {
                fishLocations.add(i);
            }
        }
        return fishLocations;
    }

    private void moveShark (Cell shark, Cell newSpot) {
        if (newSpot.checkMyCurrentState(FISH)) {
            newSpot.setMyNextState(shark.getMyCurrentState() + myFishEnergy);
        }
        else {
            newSpot.setMyNextState(shark.getMyCurrentState());
        }
        sharkReproductionRules(shark);
        shark.resetLives();
    }

    private void sharkReproductionRules (Cell shark) {
        if(shark.checkMyLives(mySharkReproductionTime)) {
            shark.setMyNextState(SHARK);
        }
        else {
            shark.setMyNextState(BLANK);
        }
    }

    private int randomNum (int bound) {
        Random rand = new Random();
        return rand.nextInt(bound);
    }

    @Override
    public void updateCell(Cell cell) {
        cell.updateCurrentState();
        if(isShark(cell)) cell.setMyColor(COLORS[SHARK_COLOR]);
        else cell.setMyColor(COLORS[cell.getMyCurrentState()]);
    }

}
