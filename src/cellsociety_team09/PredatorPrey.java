package cellsociety_team09;

import java.util.HashMap;
import javafx.scene.paint.Color;


public class PredatorPrey extends Simulation {
    private static final Color[] COLORS = { Color.WHITE, Color.LIGHTGREEN, Color.DARKGREY};
    private static final int[] VALID_NEIGHBORS = { 1, 3, 4, 6 };
    private static final int TOTAL_STATES = 3;
    private static final int BLANK = 0;
    private static final int FISH = 1;
    private static final int SHARK = 2;
    public static final String FISH_REPRODUCTION_TIME = "FISH_REPRODUCTION_TIME";
    public static final String SHARK_REPRODUCTION_TIME = "SHARK_REPRODUCTION_TIME";
    public static final String FISH_ENERGY = "SHARK_REPRODUCTION_TIME";
    public static final String UNIT_ENERGY = "UNIT_ENERGY";

    private int myFishReproductionTime;
    private int mySharkReproductionTime;
    private int myFishEnergy;
    private int myUnitEnergy;

    public PredatorPrey (HashMap<String, Double> parameterMap) {
        super(TOTAL_STATES, COLORS, parameterMap);
        myFishReproductionTime = myParameterMap.get(FISH_REPRODUCTION_TIME).intValue();
        mySharkReproductionTime = (int) myParameterMap.get(SHARK_REPRODUCTION_TIME).intValue();
        myFishEnergy = (int) myParameterMap.get(FISH_ENERGY).intValue();
        myUnitEnergy = (int) myParameterMap.get(UNIT_ENERGY).intValue();
    }

    private boolean isFish (Cell cell) {
        return cell.getMyCurrentState() == FISH;
    }

    private boolean isShark (Cell cell) {
        return cell.getMyCurrentState() == SHARK;
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
        Cell[] totalNeighbors = fish.getMyNeighbors();
        Cell[] neighbors = new Cell[VALID_NEIGHBORS.length];
        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = totalNeighbors[VALID_NEIGHBORS[i]];
        }
        Cell randomBlankNeighbor = getRandomNeighbor(neighbors, BLANK);
        if (randomBlankNeighbor != null) {
            randomBlankNeighbor.setMyNextState(FISH);
            fishReproductionRules(fish, randomBlankNeighbor);
            randomBlankNeighbor.setMyLives(fish.getMyLives() + 1);
            fish.resetLives();
        }
        fish.incrementLives();
    }

    private void fishReproductionRules (Cell fish, Cell neighbor) {
        if (fish.checkMyLives(myFishReproductionTime)) {
            fish.setMyNextState(FISH);
        }
        else {
            fish.setMyNextState(BLANK);
        }
    }

    private void sharkRules (Cell shark) {
        if(!sharkIsDead(shark)) {
            shark.incrementLives();
            shark.setMyEnergy(shark.getMyEnergy() - myUnitEnergy);
            moveShark(shark, shark.getMyNeighbors());
        }
        else {
            shark.resetLives();
            shark.setMyNextState(BLANK);
        }
    }

    private void moveShark(Cell shark, Cell[] neighbors) {
        Cell fishToEat = getRandomNeighbor(neighbors, FISH);
        Cell spaceToMove = getRandomNeighbor(neighbors, BLANK);
        if (fishToEat != null) {
            fishToEat.setMyEnergy(shark.getMyEnergy() + myFishEnergy);
            sharkReproductionRules(shark);
            fishToEat.setMyLives(shark.getMyLives());
            shark.resetLives();
        }
        else if (spaceToMove != null) {
            spaceToMove.setMyEnergy(shark.getMyEnergy());
            sharkReproductionRules(shark);
            spaceToMove.setMyLives(shark.getMyLives());
            shark.resetLives();
        }
        else {
            shark.setMyNextState(SHARK);
        }
    }

    private boolean sharkIsDead(Cell shark) {
        return shark.checkMyCurrentState(BLANK);
    }

    private void sharkReproductionRules (Cell shark) {
        if(shark.getMyLives() >= mySharkReproductionTime) {
            shark.setMyNextState(SHARK);
        }
        else {
            shark.setMyNextState(BLANK);
        }
    }

}
