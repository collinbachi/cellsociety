package configurations;

import org.w3c.dom.Element;


public abstract class Configuration {

    public abstract int[][] populateGrid (int[][] cellArray, Element cellList, int numberOfStates);

}
