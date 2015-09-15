import javafx.application.Application;
import javafx.scene.Scene;
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
        myView = new UIView();
        s.setTitle(myView.getTitle());
        Scene scene = myView.init(800, 800);
        s.setScene(scene);
        s.show();    
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}