package simulations;

import java.util.List;
import java.util.Map;
import cells.Cell;
import cells.SlimeMoldCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class SlimeMold extends SimulationWithPatch {
    public static final String ID = "SlimeMold";
    private static final int TOTAL_STATES = 5;
    private static final Paint[] COLORS =
            { Color.WHITE, Color.BLUE, Color.BLACK, Color.ORANGE, Color.PURPLE };
    private static final int EMPTY = 0;
    private static final int AMOEBE = 1;
    private static final int LOW_CAMP = 2;
    private static final int MEDIUM_CAMP = 3;
    private static final int HIGH_CAMP = 4;
    public static final String WIGGLE_BIAS = "WIGGLE_BIAS";
    public static final String WIGGLE_ANGLE = "WIGGLE_ANGLE";
    public static final String SNIFF_THRESHOLD = "SNIFF_THRESHOLD";
    public static final String SNIFF_ANGLE = "SNIFF_ANGLE";
    public static final String CAMP_DROP = "CAMP_DROP";
    public static final String EVAPORATION_RATE = "EVAPORATION_RATE";
    public static final String DIFFUSION_RATE = "DIFFUSION_RATE";
    
    // For Jasper
    private double myWiggleBias = 0;
    private double myWiggleAngle = 0;
    private double mySniffThreshold = 0;
    private double mySniffAngle = 0;
    private double myCampDrop = 1;
//    private double myEvaporationRate = .4;
//    private double myDiffusionRate = .1;
    
    public SlimeMold () {
        super(TOTAL_STATES, COLORS);
    }

    @Override
    public void checkRules (Cell cell) {
        SlimeMoldCell slime = (SlimeMoldCell) cell;
        if (cell.checkMyCurrentState(AMOEBE)) {
            int locationToMove = orientToMostCamp(slime);
            wiggleCell(slime, locationToMove);
            dropCamp(slime);
        }
        if (slime.getMyCampAmount() > 0) {
            slime.diffuse(myDiffusionRate);
            slime.evaporate(myEvaporationRate);
        }
        if (cell.getMyNextState() != AMOEBE) {
            setNextCampState((SlimeMoldCell) cell);
        }
    }
    
    private void setForwardNeighbors(SlimeMoldCell slime) {
        int orientation = slime.getMyOrientation();
        int posOrNeg = 1;
        if (mySniffAngle < 0) posOrNeg = -1;
        if (mySniffAngle % 180 < 45) {
            slime.updateForwardLocations(orientation + posOrNeg * 1);
        }
        else if (mySniffAngle % 180 < 90) {
            slime.updateForwardLocations(orientation + posOrNeg * 2);
        }
        else if (mySniffAngle % 180 < 135) {
            slime.updateForwardLocations(orientation + posOrNeg * 3);
        }
    }
    
    private void wiggleCell (SlimeMoldCell slime, int locationToMove) {
        if (locationToMove > 0) {
            int posOrNeg = 1;
            if (myWiggleAngle < 0) posOrNeg = -1;
            if (myWiggleAngle % 180 < 45) {
                locationToMove += posOrNeg * 1 * myWiggleBias;
            }
            else if (myWiggleAngle % 180 < 90) {
                locationToMove += posOrNeg * 2 * myWiggleBias;
            }
            else if (myWiggleAngle % 180 < 135) {
                locationToMove += posOrNeg * 3 * myWiggleBias;
            }
            locationToMove = slime.wrapAroundNeighbors(locationToMove);
        }
        move(slime, locationToMove);
    }
    
    private int orientToMostCamp (SlimeMoldCell slime) {
        setForwardNeighbors(slime);
        SlimeMoldCell[] neighbors = (SlimeMoldCell[]) slime.getMyNeighbors();
        List<Integer> forwardView = slime.getMyForwardLocations();
        int location = -1;
        for (int i : forwardView) {
            if (neighbors[i] == null) {
                forwardView.remove(i);
            }
        }
        if (!forwardView.isEmpty()) {
            int max = 0;
            for (int i : forwardView) {
                if (neighbors[i].getMyCampAmount() > max && neighbors[i].getMyCampAmount() > mySniffThreshold) {
                    location = i;
                }
            }
        }
        return location;
    }
    
    private void move(SlimeMoldCell slime, int locationToMove) {
        if (locationToMove > 0) {
            SlimeMoldCell newSlime = (SlimeMoldCell) slime.getMyNeighbors()[locationToMove];
            newSlime.setMyNextState(AMOEBE);
            setNextCampState(slime);
        }
    }
    
    private void setNextCampState (SlimeMoldCell cell) {
        int state = EMPTY;
        if (cell.getMyCampAmount() < 50) {
            state = LOW_CAMP;
        }
        else if (cell.getMyCampAmount() < 150) {
            state = MEDIUM_CAMP;
        }
        else if (cell.getMyCampAmount() < 300) {
            state = HIGH_CAMP;
        }
        cell.setMyNextState(state);
    }
    
    private void dropCamp (SlimeMoldCell patch) {
        patch.addToCamp (myCampDrop);
    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        myWiggleBias = parameterMap.get(WIGGLE_BIAS);
        myWiggleAngle = parameterMap.get(WIGGLE_ANGLE);
        mySniffThreshold = parameterMap.get(SNIFF_THRESHOLD);
        mySniffAngle = parameterMap.get(SNIFF_ANGLE);
        myCampDrop = parameterMap.get(CAMP_DROP);
        myEvaporationRate = parameterMap.get(EVAPORATION_RATE);
        myDiffusionRate = parameterMap.get(DIFFUSION_RATE);
    }

}
