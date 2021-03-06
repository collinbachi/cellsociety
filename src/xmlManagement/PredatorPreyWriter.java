package xmlManagement;

import simulations.PredatorPrey;


public class PredatorPreyWriter extends SimWriter {

    private final double FISH_REPRODUCTION_TIME = 7;
    private final double SHARK_REPRODUCTION_TIME = 7;
    private final double FISH_ENERGY = 3;
    private final double UNIT_ENERGY = 1;

    PredatorPreyWriter () {
        super("XML/PredatorPrey.xml", "Predator-Prey", "Wator-world", "Brenna Milligan", 25, 25, 3);
    }

    @Override
    public void populateParameterMap () {
        super.getParameterMap().put(PredatorPrey.FISH_REPRODUCTION_TIME, FISH_REPRODUCTION_TIME);
        super.getParameterMap().put(PredatorPrey.SHARK_REPRODUCTION_TIME, SHARK_REPRODUCTION_TIME);
        super.getParameterMap().put(PredatorPrey.FISH_ENERGY, FISH_ENERGY);
        super.getParameterMap().put(PredatorPrey.UNIT_ENERGY, UNIT_ENERGY);

    }

}
