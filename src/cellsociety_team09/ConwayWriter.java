
public class ConwayWriter extends XMLWriter{
	
	ConwayWriter()
	{
		/*
		super.DestinationFile = "XML/Conway.xml";
		super.nameToWrite = "Conway's Game of Life";
		super.titleToWrite = "Celluar Automaton";
		super.authorToWrite = "Jasper Hancock";
		super.gridHeightToWrite = 100;
		super.gridWidthToWrite = 100;
		super.numberOfStatesToWrite = 2;
		?
		*/
	}

	public static void main(String args[])
	{
		ConwayWriter writer=new ConwayWriter();
		writer.writeFile();
	}
	
	

}
