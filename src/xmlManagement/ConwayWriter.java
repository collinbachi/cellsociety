package xmlManagement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConwayWriter extends XMLWriter{
	

	public ConwayWriter()
	{		
		super("XML/Conway.xml", "Conway's Game of Life", "Conway's Game of Life", "Brenna Milligan", 25, 25, 2);
		
		super.writeFile();
	}

	@Override
	public void populateParameterMap() {

		//The Conway simulation is the only one that does not require specific parameters
	
	}



}
