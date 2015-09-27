package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cells.Cell;
import cells.SlimeMoldCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class SlimeMold extends Simulation {
    public static final String ID = "SlimeMold";
    private static final int TOTAL_STATES = 5;
    private static final Paint[] COLORS =
            { Color.WHITE, Color.BLUE, Color.rgb(178, 0, 0),
              Color.rgb(237, 10, 10), Color.rgb(255, 160, 160) };
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

    private double myWiggleBias;
    private double myWiggleAngle;
    private double mySniffThreshold;
    private double mySniffAngle;
    private double myCampDrop;
    private double myEvaporationRate;
    private double myDiffusionRate;

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
        if (slime.getMyPatchAmount() > 0) {
            slime.diffuse(myDiffusionRate);
            slime.evaporate(myEvaporationRate);
        }
        if (cell.getMyNextState() != AMOEBE) {
            setNextCampState((SlimeMoldCell) cell);
        }
    }

    private void setForwardNeighbors (SlimeMoldCell slime) {
        int orientation = slime.getMyOrientation();
        if (mySniffAngle == 0) {
            slime.updateForwardLocations(orientation);
        }
        else if (mySniffAngle % 180 < 45) {
            slime.updateForwardLocations(orientation + 1);
        }
        else if (mySniffAngle % 180 < 90) {
            slime.updateForwardLocations(orientation + 2);
        }
        else if (mySniffAngle % 180 < 135) {
            slime.updateForwardLocations(orientation + 3);
        }
    }

    private void wiggleCell (SlimeMoldCell slime, int locationToMove) {
        if (locationToMove >= 0) {
            if (myWiggleAngle == 0) {
                locationToMove += 0 * myWiggleBias;
            }
            else if (myWiggleAngle % 180 < 45) {
                locationToMove += 1 * myWiggleBias;
            }
            else if (myWiggleAngle % 180 < 90) {
                locationToMove += 2 * myWiggleBias;
            }
            else if (myWiggleAngle % 180 < 135) {
                locationToMove += 3 * myWiggleBias;
            }
            locationToMove = slime.wrapAroundNeighbors(locationToMove);
            move(slime, locationToMove);
        }
        else {
            locationToMove = randomNum(slime.getMyNeighbors().length);
            while (slime.getMyNeighbors()[locationToMove] == null ||
                   slime.getMyNeighbors()[locationToMove].getMyCurrentState() == AMOEBE) {
                locationToMove = randomNum(slime.getMyNeighbors().length);
            }
            move(slime, locationToMove);
        }
    }

    private int orientToMostCamp (SlimeMoldCell slime) {
        setForwardNeighbors(slime);
        Cell[] cells = slime.getMyNeighbors();
        int[] forwardView = slime.getMyForwardLocations();
        int location = -1;
        int max = 0;
        for (int i : forwardView) {
            if (cells[i] != null && cells[i].getMyCurrentState() != AMOEBE) {
                SlimeMoldCell neighbor = (SlimeMoldCell) cells[i];
                if (neighbor.getMyPatchAmount() > max &&
                    neighbor.getMyPatchAmount() > mySniffThreshold) {
                    location = i;
                }
            }
        }
        return location;
    }

    private void move (SlimeMoldCell slime, int locationToMove) {
        if (locationToMove >= 0) {
            SlimeMoldCell newSlime = (SlimeMoldCell) slime.getMyNeighbors()[locationToMove];
            newSlime.setMyNextState(AMOEBE);
            slime.setMyNextState(EMPTY);
        }
    }

    private void setNextCampState (SlimeMoldCell cell) {
        int state = EMPTY;
        if (cell.getMyPatchAmount() == 0) {
            state = EMPTY;
        }
        else if (cell.getMyPatchAmount() < 5) {
            state = LOW_CAMP;
        }
        else if (cell.getMyPatchAmount() < 8) {
            state = MEDIUM_CAMP;
        }
        else if (cell.getMyPatchAmount() >= 8) {
            state = HIGH_CAMP;
        }
        cell.setMyNextState(state);
    }

    private void dropCamp (SlimeMoldCell patch) {
        patch.addToCamp(myCampDrop);
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

    @Override
    public void initializeCells (List<ArrayList<Cell>> rows) {
        for (List<Cell> row : rows) {
            for (Cell cell : row) {
                if (!cell.checkMyCurrentState(AMOEBE)) {
                    cell.setMyNextState(EMPTY);
                    updateCell(cell);
                }
            }
        }
    }

}
