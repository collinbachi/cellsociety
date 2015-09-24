package xmlManagement;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class Configuration {

	public abstract int[][] populateGrid(int[][] cellArray, Element cellList,int numberOfStates);
	
}
