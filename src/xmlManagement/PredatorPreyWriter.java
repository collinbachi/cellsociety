package xmlManagement;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class PredatorPreyWriter extends XMLWriter {

	private final double FISH_REPRODUCTION_TIME=3;
	PredatorPreyWriter() {
		super("XML/PredatorPrey.xml", "Predator-Prey", "Wator-world", "Collin Bachi", 100, 100, 3);
	}

	@Override
	public void populateParameterMap() {

		super.getParameterMap().put("FISH_REPRODUCTION_TIME", FISH_REPRODUCTION_TIME);
		super.getParameterMap().put("SHARK_REPRODUCTION_TIME", 3.0);
		super.getParameterMap().put("FISH_ENERGY", 1.0);
		super.getParameterMap().put("UNIT_ENERGY", 1.0);
		
	}


}
