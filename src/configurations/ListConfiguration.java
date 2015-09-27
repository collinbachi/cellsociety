package configurations;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xmlManagement.XMLTags;


public class ListConfiguration extends Configuration {

    @Override
    public int[][] populateGrid (int[][] cellArray, Element fileContents, int myNumberOfStates) {
        NodeList cellList = fileContents.getElementsByTagName(XMLTags.CELL_TAG_TITLE);
        for (int pos = 0; pos < cellList.getLength(); pos++) {
            int xPos = getCellX(cellList, pos);
            int yPos = getCellY(cellList, pos);
            int state =
                    Integer.parseInt(cellList.item(pos).getChildNodes().item(2).getTextContent());

            try {

                if (state < myNumberOfStates)
                    cellArray[xPos][yPos] = state;
                else
                    cellArray[xPos][yPos] = 0;
            }
            catch (ArrayIndexOutOfBoundsException e) {

            }

        }
        return cellArray;

    }

    public int getCellY (NodeList cellList, int pos) {
        int yPos = Integer.parseInt(cellList.item(pos).getChildNodes().item(1).getTextContent());
        return yPos;
    }

    public int getCellX (NodeList cellList, int pos) {
        int xPos = Integer.parseInt(cellList.item(pos).getChildNodes().item(0).getTextContent());
        return xPos;
    }

}
