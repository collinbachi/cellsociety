package xmlManagement;

import cellsociety_team09.Fire;

public class FireWriter extends XMLWriter {

	
	private final double PROB_CATCH=0.75;
	public FireWriter() {
		super("XML/Fire.xml", "Fire", "Simulation of fire", "Brenna Milligan", 100, 100, 3);
				
	}

	@Override
	public void populateParameterMap() {
		
		super.getParameterMap().put(Fire.PROB_CATCH, PROB_CATCH);
	}
	
	
	
}