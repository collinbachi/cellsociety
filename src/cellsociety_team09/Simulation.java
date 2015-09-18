package cellsociety_team09;

import java.util.HashMap;
import javafx.scene.paint.Color;

public abstract class Simulation {

    private int myTotalStates;
    private Color[] myColors;
    protected HashMap<String, Double> myParameterMap;

    public Simulation(int totalStates, Color[] colors) {
        System.out.println("DEBUG");
        myTotalStates = totalStates;
        myColors = colors;
    }
    
    public void setMyParameterMap(HashMap<String, Double> parameterMap) {
        myParameterMap = parameterMap;
    }

    public abstract void checkRules(Cell cell);

    public void updateCell(Cell cell) {
        cell.updateCurrentState();
        cell.setMyColor(myColors[cell.getMyCurrentState()]);
    }

    protected int[] collectNeighborInfo(Cell cell) {
        int[] countNeighbors = new int[myTotalStates];
        for (int i=0; i<countNeighbors.length; i++){
            countNeighbors[i]=0;
        }
        Cell[] neighbors = cell.getMyNeighbors();
        System.out.println(myTotalStates); //debug
        for(int i = 0; i < neighbors.length; i++) {
            if (neighbors[i]!=null) {
                countNeighbors[neighbors[i].getMyCurrentState()]++;
            }
        }
        return countNeighbors;
    }
}
