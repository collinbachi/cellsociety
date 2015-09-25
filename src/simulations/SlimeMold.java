package simulations;

import java.util.Map;
import cells.Cell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class SlimeMold extends Simulation {
    public static final String ID = "SlimeMold";
    private static final int TOTAL_STATES = 5;
    private static final Paint[] COLORS =
            { Color.WHITE, Color.BLUE, Color.BLACK, Color.ORANGE, Color.PURPLE };
    private static final int EMPTY = 0;
    public static final String WIGGLE_BIAS = "WIGGLE_BIAS";
    public static final String WIGGLE_ANGLE = "WIGGLE_ANGLE";
    public static final String SNIFF_THRESHOLD = "SNIFF_THRESHOLD";
    public static final String SNIFF_ANGLE = "SNIFF_ANGLE";
    
    private double myWiggleBias;
    private double myWiggleAngle;
    private double mySniffThreshold;
    private double mySniffAngle;
    
    public SlimeMold () {
        super(TOTAL_STATES, COLORS);
    }

    @Override
    public void checkRules (Cell cell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        myWiggleBias = parameterMap.get(WIGGLE_BIAS);
        myWiggleAngle = parameterMap.get(WIGGLE_ANGLE);
        mySniffThreshold = parameterMap.get(SNIFF_THRESHOLD);
        mySniffAngle = parameterMap.get(SNIFF_ANGLE);
    }

}
