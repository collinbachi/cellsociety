package cellsociety_team09;

import cells.Cell;


public class WrapBorderGrid extends Grid {

    @Override
    protected Cell index (int i, int j) {
        if (i >= myRows.size())
            i = 0;
        if (i < 0)
            i = myRows.size() - 1;
        if (j >= myRows.get(i).size())
            j = 0;
        if (j < 0)
            j = myRows.get(i).size() - 1;
        return myRows.get(i).get(j);
    }

}
