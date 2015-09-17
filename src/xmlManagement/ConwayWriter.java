package xmlManagement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConwayWriter extends XMLWriter{
	//Local Hard-coded values that get passed to super methods
	
	private static final String fileName = "XML/Conway.xml";
	private static final String simName = "Conway's Game of Life";
	private static final String simTitle = "Celluar Automaton";
	private static final String simAuthor = "Jasper Hancock";
	private static final int height = 25;
	private static final int width= 25;
	private static final int possibleStates = 2;
	
	public ConwayWriter()
	{		
		super(fileName, simName, simTitle, simAuthor, height, width, possibleStates);
		
		super.writeFile();
	}

	@Override
	public void populateParameterMap() {

		//The Conway simulation is the only one that does not require specific parameters
	
	}

	
	

}
