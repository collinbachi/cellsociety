package xmlManagement;

import simulations.ForagingAnts;

public class ForagingAntsWriter extends SimWriter {
    
    private double myAntLife = 500;
    private double myMaxAnts = 10;
    private double myAntsBornPerTime = 2;
    private double myMinPheromones = 0;
    private double myMaxPheromones = 1000;
    private double myEvaporationRatio = .1;
    private double myDiffusionRatio = .1;
    private double myK = .001;
    private double myN = 10;

    public ForagingAntsWriter () {
        super("XML/ForagingAnts.xml", "Watch the little people run", "Walk on the wild side", "Brenna Milligan", 25, 25, 2);

    }

    @Override
    public void populateParameterMap () {
        
        super.getParameterMap().put(ForagingAnts.ANT_LIFE, myAntLife);
        super.getParameterMap().put(ForagingAnts.ANTS_BORN_PER_TIME,myAntsBornPerTime);
        super.getParameterMap().put(ForagingAnts.MAX_ANTS, myMaxAnts);
        super.getParameterMap().put(ForagingAnts.MIN_PHEROMONE, myMinPheromones);
        super.getParameterMap().put(ForagingAnts.MAX_PHEROMONE,myMaxPheromones);
        super.getParameterMap().put(ForagingAnts.EVAPORATION_RATIO, myEvaporationRatio);
        super.getParameterMap().put(ForagingAnts.DIFFUSION_RATIO, myDiffusionRatio);
        super.getParameterMap().put(ForagingAnts.K, myK);
        super.getParameterMap().put(ForagingAnts.N, myN);
        
        
    }
    

}
