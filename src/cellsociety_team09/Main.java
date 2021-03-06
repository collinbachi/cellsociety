package cellsociety_team09;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * This is the main program, it is basically boilerplate to create
 * an animated scene.
 * 
 * @author D. Collin Bachi (adapted from class example)
 */
public class Main extends Application {
    private UIView myUIView;

    @Override
    public void start (Stage s) {
        myUIView = new UIView();
        Scene scene = myUIView.init(1200, 600);
        s.setScene(scene);
        s.setTitle("Celluar Automaton Simulator");
        s.show();
    }

    // testing - REMOVE
    public Scene init (int width, int height) {
        // Create a scene graph to organize the scene
        Group root = new Group();
        Scene myScene = new Scene(root, width, height, Color.WHITE);
        return myScene;
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {

        launch(args);
    }
}
