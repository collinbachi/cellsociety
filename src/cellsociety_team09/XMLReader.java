package cellsociety_team09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLReader {
	
	public static final String FILENAME="XML/Conway.xml";
	private String title;
	private String name;
	private String author;
	private int gridWidth;
	private int gridHeight;
	private int numberOfStates;

	
	public void parseFile(String filename) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory fac=DocumentBuilderFactory.newInstance();
		DocumentBuilder build=fac.newDocumentBuilder();
		 
		File testFile=new File(FILENAME);
		Document doc=build.parse(testFile);
		
		doc.getDocumentElement().normalize();
		NodeList list=doc.getElementsByTagName("simulation");
		Element simDetails=(Element) list.item(0);
		getHeaderData(simDetails);
		
		
		NodeList cellList=simDetails.getElementsByTagName("cell");
		int cellArray[][]=new int[gridWidth][gridHeight];
		
	}
	public void populateInitialStates(int[][] cellArray,NodeList cellList)
	{
		for(int pos=0;pos<cellList.getLength();pos++)
		{
			//int xPos=cellList.item(pos).
		}
	}
	
	public void getHeaderData(Element simDetails)
	{
		name=simDetails.getElementsByTagName("simName").item(0).getTextContent();
		 title=simDetails.getElementsByTagName("title").item(0).getTextContent();
		 author=simDetails.getElementsByTagName("author").item(0).getTextContent();
		 gridWidth=Integer.parseInt(simDetails.getElementsByTagName("gridWidth").item(0).getTextContent());
		 gridHeight=Integer.parseInt(simDetails.getElementsByTagName("gridHeight").item(0).getTextContent());
		 numberOfStates=Integer.parseInt(simDetails.getElementsByTagName("numberOfStates").item(0).getTextContent());
	}
}
