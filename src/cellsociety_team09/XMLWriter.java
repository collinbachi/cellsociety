package cellsociety_team09;

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
import java.util.concurrent.ThreadLocalRandom;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {

	public static final String DestinationFile = "XML/Conway.xml";

	private final static String nameToWrite = "Conway's Game of Life";
	private final static String titleToWrite = "Celluar Automaton";
	private final static String authorToWrite = "Jasper Hancock";
	private final static int gridHeightToWrite = 100;
	private final static int gridWidthToWrite = 100;
	private  int numberOfStatesToWrite = 2;

	public static void main(String args[])
	{
		XMLWriter writer=new XMLWriter();
		writer.writeFile();
	}
	public void writeFile() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		XMLFields fields = new XMLFields();

		try {
			DocumentBuilder dB = docFactory.newDocumentBuilder();

			Document newFile = dB.newDocument();

			Element root = newFile.createElement("SimFile");
			newFile.appendChild(root);

			Element sim = newFile.createElement("simulation");

			root.appendChild(sim);
			XMLWriter writer = new XMLWriter();
			writer.addNodeToSim(newFile, sim, fields.getNameFieldTitle(), nameToWrite);
			writer.addNodeToSim(newFile, sim, fields.getTitleFieldTitle(), titleToWrite);
			writer.addNodeToSim(newFile, sim, fields.getAuthorFieldTitle(), authorToWrite);
			writer.addNodeToSim(newFile, sim, fields.getHeightFieldTitle(), Integer.toString(gridHeightToWrite));
			writer.addNodeToSim(newFile, sim, fields.getWidthFieldTitle(), Integer.toString(gridWidthToWrite));
			writer.addNodeToSim(newFile, sim, fields.getStateFieldTitle(), Integer.toString(numberOfStatesToWrite));
			writer.configureInitialPositions(newFile, sim);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(newFile);
			StreamResult result = new StreamResult(new File("XML/Conway.xml"));

			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addNodeToSim(Document newFile, Element sim, String fieldName, String content) {
		Element newNode = newFile.createElement(fieldName);
		newNode.appendChild(newFile.createTextNode(content));
		sim.appendChild(newNode);
	}

	public void configureInitialPositions(Document newFile, Element sim) {
		Element cells = newFile.createElement("cells");
		for (int x = 0; x < gridWidthToWrite; x++) {
			for (int y = 0; y < gridHeightToWrite; y++) {
				Element newCell = newFile.createElement("cell");
				Element cellX = newFile.createElement("cellX");
				cellX.appendChild(newFile.createTextNode(Integer.toString(x)));

				Element cellY = newFile.createElement("cellY");
				cellY.appendChild(newFile.createTextNode(Integer.toString(y)));

				Element cellState = newFile.createElement("state");
				int randomState = ThreadLocalRandom.current().nextInt(0, numberOfStatesToWrite);
				cellState.appendChild(newFile.createTextNode(Integer.toString(randomState)));

				newCell.appendChild(cellX);
				newCell.appendChild(cellY);
				newCell.appendChild(cellState);
				cells.appendChild(newCell);
			}
		}
		sim.appendChild(cells);
	}

}
