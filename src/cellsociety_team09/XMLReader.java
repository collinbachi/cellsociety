package cellsociety_team09;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLReader {

	private String fileName;
	private String title;
	private String name;
	private String author;
	private int gridWidth;
	private int gridHeight;
	private int numberOfStates;
	private int[][] cellArray;
	private HashMap<String,Double> parameterMap=new HashMap<String,Double>();

	XMLReader(String fileName) throws ParserConfigurationException, SAXException, IOException {
		parseFile(fileName);
	}

	public void parseFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		DocumentBuilder build = fac.newDocumentBuilder();

		File testFile = new File(fileName);
		Document doc = build.parse(testFile);
		doc.getDocumentElement().normalize();

		NodeList headerList = doc.getElementsByTagName("simulation");
		Element simDetails = (Element) headerList.item(0);
		getHeaderData(simDetails);

		NodeList parameterList=doc.getElementsByTagName("parameter");
		getParameterMap(parameterMap,parameterList);
		
		NodeList cellList = simDetails.getElementsByTagName("cell");
		cellArray = new int[gridWidth][gridHeight];

		populateInitialStates(cellArray, cellList);
		
		

	}

	public void populateInitialStates(int[][] cellArray, NodeList cellList) {
		for (int pos = 0; pos < cellList.getLength(); pos++) {
			int xPos = Integer.parseInt(cellList.item(pos).getChildNodes().item(0).getTextContent());
			int yPos = Integer.parseInt(cellList.item(pos).getChildNodes().item(1).getTextContent());
			int state = Integer.parseInt(cellList.item(pos).getChildNodes().item(2).getTextContent());
			cellArray[xPos][yPos] = state;
		}
	}

	public void getParameterMap(HashMap<String,Double> parameterMap,NodeList parameterList)
	{
		for(int pos=0; pos< parameterList.getLength();pos++)
		{
			Node newParameter=parameterList.item(pos).getChildNodes().item(0);
			parameterMap.put(newParameter.getNodeName(),Double.parseDouble(newParameter.getTextContent()));
		}
		
	}
	public void getHeaderData(Element simDetails) {
		name = simDetails.getElementsByTagName("simName").item(0).getTextContent();
		title = simDetails.getElementsByTagName("title").item(0).getTextContent();
		author = simDetails.getElementsByTagName("author").item(0).getTextContent();
		gridWidth = Integer.parseInt(simDetails.getElementsByTagName("gridWidth").item(0).getTextContent());
		gridHeight = Integer.parseInt(simDetails.getElementsByTagName("gridHeight").item(0).getTextContent());
		numberOfStates = Integer.parseInt(simDetails.getElementsByTagName("numberOfStates").item(0).getTextContent());
	}

	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public int getNumberOfStates() {
		return numberOfStates;
	}

	public int[][] getCellArray() {
		return cellArray;
	}
	public HashMap<String, Double> getParameterMap() {
		return parameterMap;
	}

}
