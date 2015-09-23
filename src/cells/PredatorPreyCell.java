package cells;

import javafx.scene.paint.Color;
import simulations.PredatorPrey;


/**
 * Cells that contain the specific functionality used
 * for the Predator Prey Simulation
 *
 * @author Brenna Milligan
 */

public class PredatorPreyCell extends Cell {
    private int myLives;
    private int myEnergy;

    public PredatorPreyCell (int state, Color color) {
        super(state, color);
        myLives = 0;
        myEnergy = 0;
    }

    private PredatorPreyCell () {
        super(0, null);
    }

    static {
        CellFactory.registerCell(PredatorPrey.PREDATORPREY, new PredatorPreyCell());
    }

    @Override
    public Cell createCell (int state, Color color) {
        return new PredatorPreyCell(state, color);
    }

    public int getMyEnergy () {
        return myEnergy;
    }

    public void setMyEnergy (int myEnergy) {
        this.myEnergy = myEnergy;
    }

    public void setMyLives (int lives) {
        myLives = lives;
    }

    public int getMyLives () {
        return myLives;
    }

    public boolean checkMyLives (int numberToCheck) {
        return myLives == numberToCheck;
    }

    public void incrementLives () {
        myLives++;
    }

    public void resetLives () {
        myLives = 0;
    }
    
    public void addToCurrentState (int toAdd) {
        this.myCurrentState += toAdd;
    }

}
