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
    
    public SlimeMold () {
        super(TOTAL_STATES, COLORS);
    }

    @Override
    public void checkRules (Cell cell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setParameters (Map<String, Double> parameterMap) {
        // TODO Auto-generated method stub

    }

}
