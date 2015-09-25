package simulations;

import java.util.Map;
import cells.Cell;
import cells.SlimeMoldCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class SlimeMold extends Simulation {
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
    
    private double myWiggleBias;
    private double myWiggleAngle;
    private double mySniffThreshold;
    private double mySniffAngle;
    private double myCampDrop = 1;
    private double myEvaporationRate = .4;
    private double myDiffusionRate = .1;
    
    public SlimeMold () {
        super(TOTAL_STATES, COLORS);
    }

    @Override
    public void checkRules (Cell cell) {
        if (cell.checkMyCurrentState(AMOEBE)) {
            findNextCell((SlimeMoldCell) cell);
            moveToCell();
            dropCamp((SlimeMoldCell) cell);
        }
        else if (cell.getMyCurrentState() > AMOEBE) {
            diffuseCamp((SlimeMoldCell) cell);
            evaporateCamp((SlimeMoldCell) cell);
        }
    }
    
    private void findNextCell (SlimeMoldCell slime) {
        // Use sniff
    }
    
    private void moveToCell () {
        // With wiggle
    }
    
    private void dropCamp (SlimeMoldCell patch) {
        patch.ad
    }
    
    private void evaporateCamp(SlimeMoldCell patch) {
        patch.setMyCampAmount(-patch.getMyCampAmount()*myDiffusionRate);
    }
    
    private void diffuseCamp(SlimeMoldCell patch) {
        SlimeMoldCell[] neighbors = (SlimeMoldCell[]) patch.getMyNeighbors();
        for (SlimeMoldCell neighbor : neighbors) {
            if (neighbor != null) {
                double currentNeighborCamp = neighbor.getMyCampAmount();
                double campToAdd = patch.getMyCampAmount()*myDiffusionRate;
                neighbor.setMyCampAmount(currentNeighborCamp + campToAdd);
            }
        }
    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        myWiggleBias = parameterMap.get(WIGGLE_BIAS);
        myWiggleAngle = parameterMap.get(WIGGLE_ANGLE);
        mySniffThreshold = parameterMap.get(SNIFF_THRESHOLD);
        mySniffAngle = parameterMap.get(SNIFF_ANGLE);
    }

}
