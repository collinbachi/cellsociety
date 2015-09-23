package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import cells.Cell;
import cells.PredatorPreyCell;
import javafx.scene.paint.Color;


/**
 * Class that controls the rules and
 * functionality of the Predator Prey Simulation
 *
 * @author Brenna Milligan
 */

public class PredatorPrey extends Simulation {
    public static final String PREDATORPREY = "PredatorPrey";
    private static final Color[] COLORS = { Color.WHITE, Color.LIGHTGREEN, Color.DIMGREY };
    private static final int[] VALID_NEIGHBORS = { 1, 3, 4, 6 };
    private static final int TOTAL_STATES = 3;
    private static final int BLANK = 0;
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

    public PredatorPrey (HashMap<String, Double> parameterMap) {
        super(TOTAL_STATES, COLORS, parameterMap);
        myFishReproductionTime = myParameterMap.get(FISH_REPRODUCTION_TIME).intValue();
        mySharkReproductionTime = (int) myParameterMap.get(SHARK_REPRODUCTION_TIME).intValue();
        myFishEnergy = (int) myParameterMap.get(FISH_ENERGY).intValue();
        myUnitEnergy = (int) myParameterMap.get(UNIT_ENERGY).intValue();
    }

    private PredatorPrey () {
        super(0, null, null);
    }

    static {
        SimulationFactory.registerSimulation(PREDATORPREY, new PredatorPrey());
    }

    private boolean isFish (Cell cell) {
        return cell.getMyNextState() == FISH && cell.getMyCurrentState() == FISH;
    }

    private boolean isShark (Cell cell) {
        return cell.getMyNextState() == SHARK && cell.getMyCurrentState() == SHARK;
    }

    @Override
    public void checkRules (Cell cell) {
        if (isFish(cell)) {
            moveFish((PredatorPreyCell) cell);
        }
        else if (isShark(cell)) {
            sharkRules((PredatorPreyCell) cell);
        }
    }

    private void moveFish (PredatorPreyCell fish) {
        Cell[] totalNeighbors = fish.getMyNeighbors();
        Cell[] neighbors = new PredatorPreyCell[VALID_NEIGHBORS.length];
        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = totalNeighbors[VALID_NEIGHBORS[i]];
        }
        PredatorPreyCell randomBlankNeighbor = getRandomNeighbor(neighbors, BLANK);
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
            fish.setMyNextState(BLANK);
        }
    }

    private void sharkRules (PredatorPreyCell shark) {
        if (!sharkIsDead(shark)) {
            shark.incrementLives();
            shark.setMyEnergy(shark.getMyEnergy() - myUnitEnergy);
            moveShark(shark, shark.getMyNeighbors());
        }
        else {
            shark.resetLives();
            shark.setMyNextState(BLANK);
        }
    }

    private void moveShark (PredatorPreyCell shark, Cell[] neighbors) {
        PredatorPreyCell fishToEat = getRandomNeighbor(neighbors, FISH);
        PredatorPreyCell spaceToMove = getRandomNeighbor(neighbors, BLANK);
        if (fishToEat != null) {
            fishToEat.setMyNextState(SHARK);
            fishToEat.setMyEnergy(shark.getMyEnergy() + myFishEnergy);
            sharkReproductionRules(shark);
            fishToEat.setMyLives(shark.getMyLives());
            shark.resetLives();
        }
        else if (spaceToMove != null) {
            spaceToMove.setMyNextState(SHARK);
            spaceToMove.setMyEnergy(shark.getMyEnergy());
            sharkReproductionRules(shark);
            spaceToMove.setMyLives(shark.getMyLives());
            shark.resetLives();
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
            shark.setMyNextState(BLANK);
        }
    }

    private PredatorPreyCell getRandomNeighbor (Cell[] neighbors, int state) {
        ArrayList<Cell> randomNeighbors = new ArrayList<>();
        for (Cell cell : neighbors) {
            if (cell != null && cell.getMyNextState() == state &&
                cell.getMyCurrentState() == state) {
                randomNeighbors.add(cell);
            }
        }
        if (!randomNeighbors.isEmpty()) {
            int randomNeighbor = randomNum(randomNeighbors.size());
            return (PredatorPreyCell) randomNeighbors.get(randomNeighbor);
        }
        else
            return null;
    }

    @Override
    public Simulation createSimulation (HashMap<String, Double> parameterMap) {
        return new PredatorPrey(parameterMap);
    }

    @Override
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

}
