package simulations;

import java.util.Map;
import cells.Cell;
import javafx.scene.paint.Paint;

public abstract class SimulationWithPatch extends Simulation {

    public SimulationWithPatch (int totalStates, Paint[] paints) {
        super(totalStates, paints);
    }

    @Override
    public abstract void checkRules (Cell cell);

    @Override
    public abstract void setParameters (Map<String, Double> parameterMap);

}
