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
            neighbor.setMyNextState(FISH);
            neighbor.resetLives();
        }
        else {
            neighbor.setMyNextState(FISH);
            fish.setMyNextState(BLANK);
            neighbor.incrementLives();
        }
    }

    private void sharkRules (Cell shark) {
        shark.setMyCurrentState(shark.getMyCurrentState() + myUnitEnergy);
        if(!sharkIsDead(shark)) {
            Cell[] neighbors = shark.getMyNeighbors();
            ArrayList<Integer> fishLocations = getFishLocations(neighbors);
            ArrayList<Integer> blankLocations = getBlankLocations(neighbors);
            if (!fishLocations.isEmpty()) {
                eatFish(shark, fishLocations, neighbors);
            }
            else if (!blankLocations.isEmpty()) {
                moveShark(shark, blankLocations, neighbors);
            }
        }
        else {
            shark.resetLives();
            shark.setMyNextState(BLANK);
        }
    }
    
    private boolean sharkIsDead(Cell shark) {
        return shark.getMyCurrentState() == BLANK;
    }

    private ArrayList<Integer> getFishLocations (Cell[] neighbors) {
        ArrayList<Integer> fishLocations = new ArrayList<>();
        for (int i : VALID_NEIGHBORS) {
            if (neighbors[i].getMyCurrentState() == FISH) {
                fishLocations.add(i);
            }
        }
        return fishLocations;
    }

    private ArrayList<Integer> getBlankLocations (Cell[] neighbors) {
        ArrayList<Integer> blankLocations = new ArrayList<>();
        for (int i : VALID_NEIGHBORS) {
            if (neighbors[i].getMyCurrentState() == BLANK) {
                blankLocations.add(i);
            }
        }
        return blankLocations;
    }

    private void eatFish (Cell shark, ArrayList<Integer> fishLocations, Cell[] neighbors) {
        int fishToEat = fishLocations.get(randomNum(fishLocations.size()));
        sharkReproductionRules(shark);
        neighbors[fishToEat].setMyNextState(shark.getMyCurrentState() - myFishEnergy);
        neighbors[fishToEat].incrementLives();
        shark.resetLives();
    }

    private void moveShark (Cell shark, ArrayList<Integer> blankLocations, Cell[] neighbors) {
        int placeToMove = blankLocations.get(randomNum(blankLocations.size()));
        sharkReproductionRules(shark);
        neighbors[placeToMove].setMyNextState(shark.getMyCurrentState());
        neighbors[placeToMove].incrementLives();
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
        cell.setMyCurrentState(cell.getMyNextState());
        if(isShark(cell)) cell.setMyColor(COLORS[SHARK_COLOR]);
        else cell.setMyColor(COLORS[cell.getMyCurrentState()]);
    }

}
