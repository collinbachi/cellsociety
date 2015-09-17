package xmlManagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLReader {

	private String myFileName;
	private String myTitle;
	private String myName;
	private String myAuthor;
	private int myGridWidth;
	private int myGridHeight;
	private int myNumberOfStates;
	private int[][] myCellArray;
	private HashMap<String,Double> myParameterMap=new HashMap<String,Double>();



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
		getParameterMap(myParameterMap,parameterList);
		
		NodeList cellList = simDetails.getElementsByTagName("cell");
		myCellArray = new int[myGridWidth][myGridHeight];

		populateInitialStates(myCellArray, cellList);
		
		

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
		myName = simDetails.getElementsByTagName("simName").item(0).getTextContent();
		myTitle = simDetails.getElementsByTagName("title").item(0).getTextContent();
		myAuthor = simDetails.getElementsByTagName("author").item(0).getTextContent();
		myGridWidth = Integer.parseInt(simDetails.getElementsByTagName("gridWidth").item(0).getTextContent());
		myGridHeight = Integer.parseInt(simDetails.getElementsByTagName("gridHeight").item(0).getTextContent());
		myNumberOfStates = Integer.parseInt(simDetails.getElementsByTagName("numberOfStates").item(0).getTextContent());
	}

	public String getTitle() {
		return myTitle;
	}

	public String getName() {
		return myName;
	}

	public String getAuthor() {
		return myAuthor;
	}

	public int getGridWidth() {
		return myGridWidth;
	}

	public int getGridHeight() {
		return myGridHeight;
	}

	public int getNumberOfStates() {
		return myNumberOfStates;
	}

	public int[][] getCellArray() {
		return myCellArray;
	}
	public HashMap<String, Double> getParameterMap() {
		return myParameterMap;
	}

}
