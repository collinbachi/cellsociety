package xmlManagement;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import cellsociety_team09.PredatorPrey;

public class PredatorPreyWriter extends XMLWriter {
	private final double FISH_REPRODUCTION_TIME = 3;
	private final double SHARK_REPRODUCTION_TIME = 3;
	private final double FISH_ENERGY = 3;
	private final double UNIT_ENERGY = 3;

	PredatorPreyWriter() {
		super("XML/PredatorPrey.xml", "Predator-Prey", "Wator-world", "Collin Bachi", 100, 100, 3);
	}

	@Override
	public void populateParameterMap() {
		super.getParameterMap().put(PredatorPrey.FISH_REPRODUCTION_TIME, FISH_REPRODUCTION_TIME);
		super.getParameterMap().put(PredatorPrey.SHARK_REPRODUCTION_TIME, SHARK_REPRODUCTION_TIME);
		super.getParameterMap().put(PredatorPrey.FISH_ENERGY, FISH_ENERGY);
		super.getParameterMap().put(PredatorPrey.UNIT_ENERGY, UNIT_ENERGY);

	}

}
