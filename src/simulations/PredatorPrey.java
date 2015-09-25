package simulations;

import java.util.Map;
import cells.Cell;
import cells.PredatorPreyCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * Class that controls the rules and
 * functionality of the Predator Prey Simulation
 *
 * @author Brenna Milligan
 */

public class PredatorPrey extends Simulation {
    public static final String ID = "PredatorPrey";
    private static final Paint[] COLORS = { Color.WHITE, Color.LIGHTGREEN, Color.DIMGREY };
    private static final int TOTAL_STATES = 3;
    private static final int EMPTY = 0;
    private static final int FISH = 1;
    private static final int SHARK = 2;
    private static final int SHARK_ENERGY = 3;
    public static final String FISH_REPRODUCTION_TIME = "FISH_REPRODUCTION_TIME";
    public static final String SHARK_REPRODUCTION_TIME = "SHARK_REPRODUCTION_TIME";
    public static final String FISH_ENERGY = "FISH_ENERGY";
    public static final String UNIT_ENERGY = "UNIT_ENERGY";

    private int myFishReproductionTime;
    private int mySharkReproductionTime;
    private int myFishEnergy;
    private int myUnitEnergy;
    private int[] myCardinalNeighbors = new int[4];

    public PredatorPrey () {
        super(TOTAL_STATES, COLORS);
    }

    private boolean isFish (Cell cell) {
        return cell.getMyNextState() == FISH && cell.getMyCurrentState() == FISH;
    }

    private boolean isShark (Cell cell) {
        return cell.getMyNextState() == SHARK && cell.getMyCurrentState() == SHARK;
    }

    @Override
    public void checkRules (Cell cell) {
        myCardinalNeighbors = initializeCardinalNeighbors(cell.getMyNeighbors().length);
        if (isFish(cell)) {
            moveFish((PredatorPreyCell) cell);
        }
        else if (isShark(cell)) {
            sharkRules((PredatorPreyCell) cell);
        }
    }

    private void moveFish (PredatorPreyCell fish) {
        Cell[] totalNeighbors = fish.getMyNeighbors();
        Cell[] neighbors = new PredatorPreyCell[myCardinalNeighbors.length];
        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = totalNeighbors[myCardinalNeighbors[i]];
        }
        PredatorPreyCell randomBlankNeighbor = (PredatorPreyCell) getRandomNeighbor(neighbors, EMPTY);
        if (randomBlankNeighbor != null) {
            randomBlankNeighbor.setMyNextState(FISH);
            fishReproductionRules(fish);
            randomBlankNeighbor.setMyLives(fish.getMyLives() + 1);
            fish.resetLives();
        }
        fish.incrementLives();
    }

    private void fishReproductionRules (PredatorPreyCell fish) {
        if (fish.getMyLives() >= myFishReproductionTime) {
            fish.setMyNextState(FISH);
            fish.resetLives();
        }
        else {
            fish.setMyNextState(EMPTY);
        }
    }

    private void sharkRules (PredatorPreyCell shark) {
        shark.setMyEnergy(shark.getMyEnergy() - myUnitEnergy);
        if (!sharkIsDead(shark)) {
            shark.incrementLives();
            moveShark(shark, shark.getMyNeighbors());
        }
        else {
            shark.resetLives();
            shark.setMyEnergy(0);
            shark.setMyNextState(EMPTY);
        }
    }

    private void moveShark (PredatorPreyCell shark, Cell[] neighbors) {
        PredatorPreyCell fishToEat = (PredatorPreyCell) getRandomNeighbor(neighbors, FISH);
        PredatorPreyCell spaceToMove = (PredatorPreyCell) getRandomNeighbor(neighbors, EMPTY);
        if (fishToEat != null) {
            fishToEat.setMyNextState(SHARK);
            fishToEat.setMyEnergy(shark.getMyEnergy() + myFishEnergy);
            sharkReproductionRules(shark);
            fishToEat.setMyLives(shark.getMyLives());
            shark.resetLives();
            shark.setMyEnergy(0);
        }
        else if (spaceToMove != null) {
            spaceToMove.setMyNextState(SHARK);
            spaceToMove.setMyEnergy(shark.getMyEnergy());
            sharkReproductionRules(shark);
            spaceToMove.setMyLives(shark.getMyLives());
            shark.resetLives();
            shark.setMyEnergy(0);
        }
        else {
            shark.setMyNextState(SHARK);
        }
    }

    private boolean sharkIsDead (PredatorPreyCell shark) {
        return shark.getMyEnergy() <= -SHARK_ENERGY;
    }

    private void sharkReproductionRules (PredatorPreyCell shark) {
        if (shark.getMyLives() >= mySharkReproductionTime) {
            shark.setMyNextState(SHARK);
        }
        else {
            shark.setMyNextState(EMPTY);
        }
    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        myFishReproductionTime = parameterMap.get(FISH_REPRODUCTION_TIME).intValue();
        mySharkReproductionTime = parameterMap.get(SHARK_REPRODUCTION_TIME).intValue();
        myFishEnergy = parameterMap.get(FISH_ENERGY).intValue();
        myUnitEnergy = parameterMap.get(UNIT_ENERGY).intValue();
    }

}
