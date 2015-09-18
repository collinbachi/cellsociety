package xmlManagement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConwayWriter extends XMLWriter{
	//Local Hard-coded values that get passed to super methods
	
	
	ConwayWriter()
	{		
		super("XML/Conway.xml", "Conway's Game of Life", "Conway's Game of Life", "Brenna Milligan", 100, 100, 2);
		
		super.writeFile();
	}

	@Override
	public void populateParameterMap() {

		//The Conway simulation is the only one that does not require specific parameters
	
	}

	public static void Main(String args[])
	{
		ConwayWriter writer=new ConwayWriter();
		writer.writeFile();
	}
	
	

}
