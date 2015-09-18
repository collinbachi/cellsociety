package xmlManagement;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cellsociety_team09.Simulation;

/*
 * This abstract superclass easily handles writing the xml for each and every simulation, where the specific parameters for each 
 * simulation are defined in their respective sub classes
 * 
 * @author Jasper Hancock
 */
public abstract class XMLWriter {

	protected final String myDestinationFile;
	protected final String myName;
	protected final String myTitle;
	protected final String myAuthor;
	protected final int myGridHeight;
	protected final int myGridWidth;
	protected final int myPossibleStates;
	protected final HashMap<String, Double> parameterMap = new HashMap<String, Double>();
	protected Simulation specificParameterNames;

	// TODO Abstract createParameterMap
	XMLWriter(String fileName, String simName, String simTitle, String simAuthor, int height, int width,
			int possibleStates) {
		myDestinationFile = fileName;
		myName = simName;
		myTitle = simTitle;
		myAuthor = simAuthor;
		myGridHeight = height;
		myGridWidth = width;
		myPossibleStates = possibleStates;
		populateParameterMap();
	}

	public void writeFile() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		XMLTags fields = new XMLTags();

		try {
			DocumentBuilder dB = docFactory.newDocumentBuilder();

			Document newFile = dB.newDocument();

			Element root = newFile.createElement("SimFile");
			newFile.appendChild(root);
			Element sim = newFile.createElement("simulation");
			root.appendChild(sim);

			addHeaderData(fields, newFile, sim);
			addSpecificParameters(newFile, sim);
			configureInitialPositions(newFile, sim, myGridWidth, myGridHeight, myPossibleStates);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(newFile);
			StreamResult result = new StreamResult(new File(myDestinationFile));

			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addHeaderData(XMLTags fields, Document newFile, Element sim) {
		addNodeToElement(newFile, sim, fields.NAME_TAG_TITLE, myName);
		addNodeToElement(newFile, sim, fields.TITLE_TAG_TITLE, myTitle);
		addNodeToElement(newFile, sim, fields.AUTHOR_TAG_TITLE, myAuthor);
		addNodeToElement(newFile, sim, fields.HEIGHT_TAG_TITLE, Integer.toString(myGridHeight));
		addNodeToElement(newFile, sim, fields.WIDTH_TAG_TITLE, Integer.toString(myGridWidth));
		addNodeToElement(newFile, sim, fields.STATE_TAG_TITLE, Integer.toString(myPossibleStates));
	}

	private void addSpecificParameters(Document newFile, Element sim) {
		Element parameters = newFile.createElement("parameters");
		for (String fieldName : parameterMap.keySet()) {
			Element parameter = newFile.createElement("parameter");
			addNodeToElement(newFile, parameter, fieldName, Double.toString(parameterMap.get(fieldName)));
			parameters.appendChild(parameter);

		}
		sim.appendChild(parameters);
	}

	// sets the values of simulation specific parameters
	public abstract void populateParameterMap();

	public void addNodeToElement(Document newFile, Element element, String fieldName, String content) {
		Element newNode = newFile.createElement(fieldName);
		newNode.appendChild(newFile.createTextNode(content));
		element.appendChild(newNode);
	}

	public void configureInitialPositions(Document newFile, Element sim, int width, int height, int states) {
		Element cells = newFile.createElement("cells");
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Element newCell = newFile.createElement("cell");
				Element cellX = newFile.createElement("cellX");
				cellX.appendChild(newFile.createTextNode(Integer.toString(x)));

				Element cellY = newFile.createElement("cellY");
				cellY.appendChild(newFile.createTextNode(Integer.toString(y)));

				Element cellState = newFile.createElement("state");
				int randomState = ThreadLocalRandom.current().nextInt(0, states);
				cellState.appendChild(newFile.createTextNode(Integer.toString(randomState)));

				newCell.appendChild(cellX);
				newCell.appendChild(cellY);
				newCell.appendChild(cellState);
				cells.appendChild(newCell);
			}
		}
		sim.appendChild(cells);
	}

	public HashMap<String, Double> getParameterMap() {
		return parameterMap;
	}

}
