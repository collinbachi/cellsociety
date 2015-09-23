package simulations;

import java.util.HashMap;
import cells.Cell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ForagingAnts extends Simulation {
    public static final int TOTAL_STATES = 3;
    private static final Paint[] COLORS = { Color.WHITE, Color.LIGHTGREEN, Color.DIMGREY };
    public static final String ANT_LIFE = "ANT_LIFE";
    public static final String MAX_ANTS = "MAX_ANTS";
    public static final String ANTS_BORN_PER_TIME = "ANTS_BORN_PER_TIME";
    public static final String MIN_PHEROMONE = "MIN_PHEROMONE";
    public static final String MAX_PHEROMONE = "MAX_PHEROMONE";
    public static final String EVAPORATION_RATIO = "EVAPORATION_RATIO";
    public static final String DIFFUSION_RATIO = "DIFFUSION_RATIO";
    public static final String K = "K";
    public static final String N = "N";
    
    
    //For Jasper to add to XML
    private int myAntLife = 500;
    private int myMaxAnts = 10;
    private int myAntsBornPerTime = 2;
    private int myMinPheromone = 0;
    private int myMaxPheromone = 1000;
    private double myEvaporationRatio = .1;
    private double myDiffusionRatio = .1;
    private double myK = .001;
    private double myN = 10;
    
    

    public ForagingAnts () {
        super(TOTAL_STATES, COLORS);        
    }

    @Override
    public void checkRules (Cell cell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateParameters () {
        // TODO Auto-generated method stub

    }

    @Override
    public void setParameters (HashMap<String, Double> parameterMap) {
        myAntLife = parameterMap.get(ANT_LIFE).intValue();
        myMaxAnts = parameterMap.get(MAX_ANTS).intValue();
        myAntsBornPerTime = parameterMap.get(ANTS_BORN_PER_TIME).intValue();
        myMinPheromone = parameterMap.get(MIN_PHEROMONE).intValue();
        myMaxPheromone = parameterMap.get(MAX_PHEROMONE).intValue();
        myEvaporationRatio = parameterMap.get(EVAPORATION_RATIO);
        myDiffusionRatio = parameterMap.get(DIFFUSION_RATIO);
        myK = parameterMap.get(K);
        myN = parameterMap.get(N);
    }

}
