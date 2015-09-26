package xmlManagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javafx.scene.paint.Color;

public class StyleReader {
 
    private  String myFileName;
    private String myGridShape;
    private String myGridEdge;
    private boolean myScrollable;
    private boolean myOutlined;
    private HashMap<Integer,Color> myColorMap=new HashMap<Integer,Color>();
    private HashMap<Integer,String> myShapeMap=new HashMap<Integer,String>();
    private String myNeighborsToConsider;
    
    public void parseStyle(File styleFile) throws ParserConfigurationException, SAXException, IOException{
        
        DocumentBuilderFactory fac=DocumentBuilderFactory.newInstance();
        DocumentBuilder build=fac.newDocumentBuilder();
        myFileName=styleFile.getName();
        Document styleSheet=build.parse(styleFile);
        
        myGridShape=styleSheet.getElementsByTagName(StyleTags.GRID_SHAPE_TAG).item(0).getTextContent();
        myGridEdge=styleSheet.getElementsByTagName(StyleTags.GRID_EDGE_TAG).item(0).getTextContent();
        myScrollable=Boolean.parseBoolean(styleSheet.getElementsByTagName(StyleTags.SCROLLABLE_TAG).item(0).getTextContent());
        myOutlined=Boolean.parseBoolean(styleSheet.getElementsByTagName(StyleTags.OUTLINED_TAG).item(0).getTextContent());
        myNeighborsToConsider=styleSheet.getElementsByTagName(StyleTags.NEIGHBORS_TO_CONSIDER_TAG).item(0).getTextContent();

        
    }
    
    public String getMyFileName () {
        return myFileName;
    }

    public String getMyGridShape () {
        return myGridShape;
    }

    public String getMyGridEdge () {
        return myGridEdge;
    }

    public boolean isMyScrollable () {
        return myScrollable;
    }

    public boolean isMyOutlined () {
        return myOutlined;
    }

    public HashMap<Integer, Color> getMyColorMap () {
        return myColorMap;
    }

    public HashMap<Integer, String> getMyShapeMap () {
        return myShapeMap;
    }

    public String getMyNeighborsToConsider () {
        return myNeighborsToConsider;
    }

    

}
