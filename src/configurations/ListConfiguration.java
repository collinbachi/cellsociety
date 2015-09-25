package configurations;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xmlManagement.XMLTags;

public class ListConfiguration extends Configuration {

	@Override
	public int[][] populateGrid(int[][] cellArray, Element fileContents,int myNumberOfStates) {
		NodeList cellList=fileContents.getElementsByTagName(XMLTags.CELL_TAG_TITLE);
		for (int pos = 0; pos < cellList.getLength(); pos++) {
			int xPos = Integer.parseInt(cellList.item(pos).getChildNodes().item(0).getTextContent());
			int yPos = Integer.parseInt(cellList.item(pos).getChildNodes().item(1).getTextContent());
			int state = Integer.parseInt(cellList.item(pos).getChildNodes().item(2).getTextContent());

			try {

				if (state < myNumberOfStates)
					cellArray[xPos][yPos] = state;
				else
					cellArray[xPos][yPos] = 0;
			} catch (ArrayIndexOutOfBoundsException e) {

			}

		}
		return cellArray;
		
	}

}
