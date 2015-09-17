package cellsociety_team09;

import javafx.scene.paint.Color;

public abstract class Simulation {
    
    private int myTotalStates;
    private Color[] myColors;
    
    public Simulation(int totalStates, Color[] colors) {
        System.out.println("DEBUG");
        myTotalStates = totalStates;
        myColors = colors;
    }
    
    public abstract void checkRules(Cell cell);
    
    public void updateCell(Cell cell) {
        cell.setMyCurrentState(cell.getMyNextState());
        cell.setMyColor(myColors[cell.getMyCurrentState()]);
    }
    
    protected int[] collectNeighborInfo(Cell cell) {
        int[] countNeighbors = new int[myTotalStates+1];
        for (int i=0; i<countNeighbors.length; i++){
            countNeighbors[i]=0;
        }
        Cell[] neighbors = cell.getMyNeighbors();
        //System.out.println(myTotalStates); //debug
        for(int i = 0; i < neighbors.length; i++) {
            if (neighbors[i]==null) continue;
            countNeighbors[neighbors[i].getMyCurrentState()] = countNeighbors[neighbors[i].getMyCurrentState()]+1;
        }
        return countNeighbors;
    }
}
