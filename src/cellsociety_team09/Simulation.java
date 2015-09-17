package cellsociety_team09;

import javafx.scene.paint.Color;

public abstract class Simulation {
    
    private int myTotalStates;
    private Color[] myColors;
    
    public Simulation(int totalStates, Color[] colors) {
        myTotalStates = totalStates;
        myColors = colors;
    }
    
    public abstract void checkRules(Cell cell);
    
    public void updateCell(Cell cell) {
        cell.updateCurrentState();
        cell.setMyColor(myColors[cell.getMyCurrentState()]);
    }
    
    protected int[] collectNeighborInfo(Cell cell) {
        int[] countNeighbors = new int[myTotalStates];
        Cell[] neighbors = cell.getMyNeighbors();
        for(int i = 0; i < neighbors.length; i++) {
            countNeighbors[neighbors[i].getMyCurrentState()]++;
        }
        return countNeighbors;
    }
}
