package xmlManagement;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Writer {

    protected void addNodeToElement (Document newFile,
                                     Element element,
                                     String fieldName,
                                     String content) {
        Element newNode = newFile.createElement(fieldName);
        newNode.appendChild(newFile.createTextNode(content));
        element.appendChild(newNode);
    }

    public void createFile (Document newFile,
                            String myDestinationFile) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(newFile);
        StreamResult result = new StreamResult(new File(myDestinationFile));
        transformer.transform(source, result);
    }

}
