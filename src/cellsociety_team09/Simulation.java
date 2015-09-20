package cellsociety_team09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.paint.Color;

public abstract class Simulation {

    private int myTotalStates;
    private Color[] myColors;
    protected HashMap<String, Double> myParameterMap;

    public Simulation(int totalStates, Color[] colors, HashMap<String, Double> parameterMap) {
        myTotalStates = totalStates;
        myColors = colors;
        myParameterMap = parameterMap;
    }
    
    public void setMyParameterMap(HashMap<String, Double> parameterMap) {
        myParameterMap = parameterMap;
    }

    public abstract void checkRules(Cell cell);

    public void updateCell(Cell cell) {
        cell.updateCurrentState();
        cell.setMyColor(myColors[cell.getMyCurrentState()]);
    }

    protected int[] collectNeighborInfo(Cell[] neighbors) {
        int[] countNeighbors = new int[myTotalStates];
        for (int i=0; i<countNeighbors.length; i++){
            countNeighbors[i]=0;
        }
        for(int i = 0; i < neighbors.length; i++) {
            if (neighbors[i]!=null) {
                countNeighbors[neighbors[i].getMyCurrentState()]++;
            }
        }
        return countNeighbors;
    }
    

    protected Cell getRandomNeighbor(Cell[] neighbors, int state) {
        ArrayList<Cell> randomNeighbors = new ArrayList<>();
        for(Cell cell : neighbors) {
            if (cell != null && cell.getMyNextState() == state) {
                randomNeighbors.add(cell);
            }
        }
        if (!randomNeighbors.isEmpty()) {
            int randomNeighbor = randomNum(randomNeighbors.size());
            return randomNeighbors.get(randomNeighbor);
        }
        else return null;
    }
    
    protected int randomNum (int bound) {
        Random rand = new Random();
        return rand.nextInt(bound);
    }
}
