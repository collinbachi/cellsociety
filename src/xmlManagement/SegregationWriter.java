package xmlManagement;

import simulations.Segregation;


public class SegregationWriter extends SimWriter {

    private static final double SIMILAR_THRESHOLD = 0.3;

    SegregationWriter () {

        super("XML/Segregation.xml", "Segregation", "Simulating Segregation", "Brenna Milligan", 25,
              25, 3);
        populateParameterMap();
    }

    @Override
    public void populateParameterMap () {
        super.getParameterMap().put(Segregation.SIMILAR_THRESHOLD, SIMILAR_THRESHOLD);

    }

}
