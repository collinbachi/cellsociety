package cellsociety_team09;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public abstract class Simulation {
    private String myDescription;
    private String myAuthor;
    
    public Simulation(String description, String author) {
        myDescription = description;
        myAuthor = author;
    }
    
    public abstract void checkRules(Cell cell);
    
    public abstract void updateCell(Cell cell);
}
