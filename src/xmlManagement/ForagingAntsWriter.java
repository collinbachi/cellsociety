package xmlManagement;

import simulations.ForagingAnts;


public class ForagingAntsWriter extends SimWriter {

    private final double myAntLife = 500;
    private final double myMaxAnts = 10;
    private final double myAntsBornPerTime = 2;
    private final double myMinPheromones = 0;
    private final double myMaxPheromones = 1000;
    private final double myEvaporationRate = .1;
    private final double myDiffusionRatio = .1;
    private final double myK = .001;
    private final double myN = 10;
    private final double myInitAnts = 10;

    public ForagingAntsWriter () {
        super("XML/ForagingAnts.xml", "Foraging Ants", "Foraging Ants", "Brenna Milligan", 100, 100,
              5);

    }

    @Override
    public void populateParameterMap () {

        super.getParameterMap().put(ForagingAnts.ANT_LIFE, myAntLife);
        System.out.println(super.getParameterMap().get(ForagingAnts.ANT_LIFE));
        super.getParameterMap().put(ForagingAnts.ANTS_BORN_PER_TIME, myAntsBornPerTime);
        super.getParameterMap().put(ForagingAnts.MAX_ANTS, myMaxAnts);
        super.getParameterMap().put(ForagingAnts.MIN_PHEROMONE, myMinPheromones);
        super.getParameterMap().put(ForagingAnts.MAX_PHEROMONE, myMaxPheromones);
        super.getParameterMap().put(ForagingAnts.EVAPORATION_RATE, myEvaporationRate);
        super.getParameterMap().put(ForagingAnts.DIFFUSION_RATE, myDiffusionRatio);
        super.getParameterMap().put(ForagingAnts.K, myK);
        super.getParameterMap().put(ForagingAnts.N, myN);
        super.getParameterMap().put(ForagingAnts.INIT_ANTS, myInitAnts);

    }

}
