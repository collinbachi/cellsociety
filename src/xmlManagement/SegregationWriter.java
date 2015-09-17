package xmlManagement;

import cellsociety_team09.Segregation;

public class SegregationWriter extends XMLWriter {

	private static final double SIMILAR_THRESHOLD = 0.3;

	SegregationWriter() {

		super("XML/Segregation.xml", "Segregation", "Simulating Segregation", "Brenna Milligan", 100, 100, 3);
		populateParameterMap();
	}

	@Override
	public void populateParameterMap() {
		super.getParameterMap().put(Segregation.SIMILAR_THRESHOLD, SIMILAR_THRESHOLD);

	}


}
