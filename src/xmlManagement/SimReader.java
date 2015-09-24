package xmlManagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import cellsociety_team09.Grid;


/*
 * This class is responsible for parsing the xml files produced by XMLWriter and storing the data
 * from the
 * XML file in its private fields. It has get methods for each field, as this data is important for
 * other
 * classes
 * 
 * @author Jasper Hancock
 */

public class SimReader {



	private String myFileName;
	private String myTitle;
	private String myName;
	private String myAuthor;
	private int myGridWidth;
	private int myGridHeight;
	private int myNumberOfStates;
	private int[][] myCellArray;
	private Map<String, Double> myParameterMap = new HashMap<String, Double>();
	private XMLTags xmlTags = new XMLTags();
	private final double DEFAULT_VALUE = 1;

	public void parseFile(File testFile, Grid grid) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		DocumentBuilder build = fac.newDocumentBuilder();
		myFileName=testFile.getName().substring(0,testFile.getName().indexOf(".xml"));
		Document doc = build.parse(testFile);
		doc.getDocumentElement().normalize();

		NodeList headerList = doc.getElementsByTagName(xmlTags.ROOT_TAG_TITLE);
		Element simDetails = (Element) headerList.item(0);
		getHeaderData(simDetails);

		NodeList parameterList = doc.getElementsByTagName(xmlTags.PARAMETER_TAG_TITLE);
		populateParameterMap(myParameterMap, parameterList);

		myCellArray = new int[myGridWidth][myGridHeight];

		ConfigurationFactory gridConfig=new ConfigurationFactory();
		Configuration config=gridConfig.createConfiguration("Random");
		grid.init(config.populateGrid(myCellArray, simDetails, myNumberOfStates),
				myFileName, myParameterMap);

	}

	private void populateParameterMap(Map<String, Double> parameterMap, NodeList parameterList) {
		for (int pos = 0; pos < parameterList.getLength(); pos++) {
			Node newParameter = parameterList.item(pos).getChildNodes().item(0);

			try {
				parameterMap.put(newParameter.getNodeName(), Double.parseDouble(newParameter.getTextContent()));
			} catch (NumberFormatException e) {
				parameterMap.put(newParameter.getNodeName(), DEFAULT_VALUE);
			}
		}

	}

	private void getHeaderData(Element simDetails) {

		myName = simDetails.getElementsByTagName(xmlTags.NAME_TAG_TITLE).item(0).getTextContent();
		myTitle = simDetails.getElementsByTagName(xmlTags.TITLE_TAG_TITLE).item(0).getTextContent();
		myAuthor = simDetails.getElementsByTagName(xmlTags.AUTHOR_TAG_TITLE).item(0).getTextContent();
		myGridWidth = Integer
				.parseInt(simDetails.getElementsByTagName(xmlTags.WIDTH_TAG_TITLE).item(0).getTextContent());
		myGridHeight = Integer
				.parseInt(simDetails.getElementsByTagName(xmlTags.HEIGHT_TAG_TITLE).item(0).getTextContent());
		myNumberOfStates = Integer
				.parseInt(simDetails.getElementsByTagName(xmlTags.STATE_TAG_TITLE).item(0).getTextContent());

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

	public Map<String, Double> populateParameterMap() {
		return myParameterMap;
	}

	public String getMyFileName() {
		return myFileName;
	}
}
