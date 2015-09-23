package cells;


/**
 * Factory class to generate the concrete cell class needed
 *
 * @author Brenna Milligan
 */

public class CellFactory {

    public Cell createCell (String cellID) {
        Class clazz;
        try {
            clazz = Class.forName(String.format("cells.%sCell", cellID));
            return (Cell) clazz.newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
