package configurations;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import org.w3c.dom.Element;
import cellsociety_team09.UIView;


public class DistributionConfiguration {

    int[] distributionArray = new int[100];

    private void fillDistributionArray (ArrayList<Integer> distributions) {
        int startIndex = 0;
        try {
            for (int state = 0; state < distributions.size(); state++) {
                for (int fillerIndex = startIndex; fillerIndex < distributions.get(state) +
                                                                 startIndex; fillerIndex++) {
                    distributionArray[fillerIndex] = state;
                }
                startIndex = startIndex + distributions.get(state);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            UIView popup = new UIView();
            popup.createErrorMessage("Invalid distribution values",
                                     "Please input integers that sum up to or less than 100");
        }
    }

    public int[][] populateGrid (int[][] cellArray, ArrayList<Integer> distributions) {

        fillDistributionArray(distributions);

        for (int x = 0; x < cellArray.length; x++) {
            for (int y = 0; y < cellArray[0].length; y++)
                cellArray[x][y] = distributionArray[ThreadLocalRandom.current().nextInt(0, 100)];
        }

        return cellArray;
    }

}
