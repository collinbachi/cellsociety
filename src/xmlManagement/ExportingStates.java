package xmlManagement;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import cellsociety_team09.Grid;
import cellsociety_team09.UIView;
import configurations.ListConfiguration;

public class ExportingStates extends Writer {
    
    
    public void modifyXMLFile(SimReader myXMLReader,Grid myGrid)
    {
        String filePath="XML/"+myXMLReader.getMyFileName()+".xml";
        DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            Document doc;
            doc = docBuilder.parse(filePath);

        
        NodeList cellList=doc.getElementsByTagName(XMLTags.CELL_TAG_TITLE);
        
        ListConfiguration listConfig=new ListConfiguration();
        for( int index=0;index<cellList.getLength();index++)
        {
            Node cell=cellList.item(index);
            int cellX=listConfig.getCellX(cellList, index);
            int cellY=listConfig.getCellY(cellList, index);
            Node cellState=cell.getChildNodes().item(2);
            
            cellState.setTextContent(Integer.toString(myGrid.getCell(cellX, cellY).getMyCurrentState()));
            
        }
        
        super.createFile(doc, filePath);
        }
        catch (ParserConfigurationException |SAXException | IOException |TransformerException e) {
            UIView popup=new UIView();
            popup.createErrorMessage("Invalid input", "Please enter valid inputs");
        
        }
       
    }
}
