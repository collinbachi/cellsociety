package xmlManagement;

import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javafx.scene.paint.Color;


public abstract class StyleWriter extends Writer {
    protected final String myDestinationFile;
    protected final String myGridShape;
    protected final String myGridEdge;
    protected final boolean myScrollable;
    protected final boolean myOutlined;
    protected final HashMap<Integer, Color> myColorMap = new HashMap<Integer, Color>();
    protected final HashMap<Integer, String> myShapeMap = new HashMap<Integer, String>();
    protected final String myNeighborsToConsider;

    public StyleWriter (String fileName,
                        String gridShape,
                        String gridEdge,
                        boolean scrollable,
                        boolean outlined,
                        String neighbors) {
        myDestinationFile = fileName;
        myGridShape = gridShape;
        myGridEdge = gridEdge;
        myScrollable = scrollable;
        myOutlined = outlined;
        myNeighborsToConsider = neighbors;
        populateColorMap();
        populateImageMap();
    }

    public abstract void populateColorMap ();

    public abstract void populateImageMap ();

    public void writeStyleSheet () {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dB = docFactory.newDocumentBuilder();
            Document newFile = dB.newDocument();

            Element root = newFile.createElement("StyleFile");
            newFile.appendChild(root);

            Element style = newFile.createElement("style");
            root.appendChild(style);

            addNodeToElement(newFile, style, StyleTags.GRID_SHAPE_TAG, myGridShape);

            addNodeToElement(newFile, style, StyleTags.GRID_EDGE_TAG, myGridEdge);

            addNodeToElement(newFile, style, StyleTags.SCROLLABLE_TAG,
                             Boolean.toString(myScrollable));
            addNodeToElement(newFile, style, StyleTags.OUTLINED_TAG, Boolean.toString(myOutlined));
            addNodeToElement(newFile, style, StyleTags.NEIGHBORS_TO_CONSIDER_TAG,
                             myNeighborsToConsider);

            createFile(newFile, "XML/Styles/" + myDestinationFile);

        }
        catch (ParserConfigurationException | TransformerFactoryConfigurationError
                | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
