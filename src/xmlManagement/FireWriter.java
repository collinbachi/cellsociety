package xmlManagement;

import simulations.Fire;


public class FireWriter extends SimWriter {

    private final double PROB_CATCH = 0.75;

    public FireWriter () {
        super("XML/Fire.xml", "Fire", "Simulation of fire", "Brenna Milligan", 25, 25, 3);

    }

    @Override
    public void populateParameterMap () {

        super.getParameterMap().put(Fire.PROB_CATCH, PROB_CATCH);
    }

}
