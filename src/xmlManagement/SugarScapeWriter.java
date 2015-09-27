package xmlManagement;

import simulations.SugarScape;


public class SugarScapeWriter extends SimWriter {

    private final double myMaxVision = 10;
    private final double mySugarGrowBackInterval = 2;
    private final double mySugarGrowBackRate=1;
    private final double MAX_METABOLISM = 6;
    private final double MIN_METABOLISM = 1;
    private final double MIN_VISION = 1;
    private final double MAX_INIT_SUGAR = 25;
    private final double MIN_INIT_SUGAR = 5;

    public SugarScapeWriter () {
        super("XML/SugarScapeTwo.xml", "SugarScape One", "SugarScape One", "Brenna Milligan", 25, 25,
              5);
    }

    @Override
    public void populateParameterMap () {

        super.getParameterMap().put(SugarScape.MAX_VISION, myMaxVision);
        super.getParameterMap().put(SugarScape.SUGAR_GROW_BACK_INTERVAL, mySugarGrowBackInterval);
        super.getParameterMap().put(SugarScape.MAX_METABOLISM, MAX_METABOLISM);
        super.getParameterMap().put(SugarScape.MIN_METABOLISM, MIN_METABOLISM);
        super.getParameterMap().put(SugarScape.MIN_VISION, MIN_VISION);
        super.getParameterMap().put(SugarScape.MAX_INIT_SUGAR, MAX_INIT_SUGAR);
        super.getParameterMap().put(SugarScape.MIN_INIT_SUGAR, MIN_INIT_SUGAR);
        super.getParameterMap().put(SugarScape.SUGAR_GROW_BACK_RATE, mySugarGrowBackRate);

    }

}
