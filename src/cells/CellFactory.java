package cells;


/**
 * Factory class to generate the concrete cell class needed
 *
 * @author Brenna Milligan
 */

public class CellFactory {

    @SuppressWarnings("unchecked")
    public Cell createCell (String cellID) {
        Class<Cell> clazz;
        try {
            clazz = (Class<Cell>) Class.forName(String.format("cells.%sCell", cellID));
            return (Cell) clazz.newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
