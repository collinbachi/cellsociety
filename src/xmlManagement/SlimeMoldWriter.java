package xmlManagement;

import simulations.SlimeMold;

public class SlimeMoldWriter extends SimWriter {
    
    private final double myWiggleBias = 0;
    private final double myWiggleAngle = 0;
    private final double mySniffThreshold = 0;
    private final double mySniffAngle = 0;
    private final double myCampDrop = 1;
    private final double myEvaporationRate = .4;
    private final double myDiffusionRate = .1;

    public SlimeMoldWriter () {
        super("XML/SlimeMold.xml", "Slime Mold", "Slimes", "Brenna Milligan", 25, 25, 5);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void populateParameterMap () {

        super.getParameterMap().put(SlimeMold.WIGGLE_BIAS, myWiggleBias);
        super.getParameterMap().put(SlimeMold.WIGGLE_ANGLE, myWiggleAngle);
        super.getParameterMap().put(SlimeMold.SNIFF_THRESHOLD, mySniffThreshold);
        super.getParameterMap().put(SlimeMold.SNIFF_ANGLE, mySniffAngle);
        super.getParameterMap().put(SlimeMold.CAMP_DROP, myCampDrop);
        super.getParameterMap().put(SlimeMold.EVAPORATION_RATE, myEvaporationRate);
        super.getParameterMap().put(SlimeMold.DIFFUSION_RATE, myDiffusionRate);

        
    }

}
