package cells;


/**
 * Cells that contain the specific functionality used
 * for the Predator Prey Simulation
 *
 * @author Brenna Milligan
 */

public class PredatorPreyCell extends Cell {
    private int myLives;
    private int myEnergy;

    public PredatorPreyCell () {
        myLives = 0;
        myEnergy = 0;
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

    @Override
    public void initializeWithState (int state) {
        setMyNextState(state);
        myLives = 0;
        myEnergy = 0;        
    }

}
