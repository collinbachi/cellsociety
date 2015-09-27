package configurations;

import java.util.concurrent.ThreadLocalRandom;
import org.w3c.dom.Element;


public class RandomConfiguration extends Configuration {

    @Override
    public int[][] populateGrid (int[][] cellArray, Element cellList, int numberOfStates) {
        for (int x = 0; x < cellArray.length; x++) {
            for (int y = 0; y < cellArray[0].length; y++)

            {
                cellArray[x][y] = ThreadLocalRandom.current().nextInt(0, numberOfStates);
            }
        }

        return cellArray;
    }

}
