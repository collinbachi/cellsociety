package cellsociety_team09;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import configurations.RandomConfiguration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import xmlManagement.ExportingStates;
import xmlManagement.SimReader;
import xmlManagement.StyleReader;


/*
 * Responsible for configuring and placing the GUI elements for the user interface
 * 
 * @author Jasper Hancock
 */
public class UIView {

    private Scene myScene;
    private File xmlFileFolder = new File("XML");
    private File styleFileFolder = new File("XML/Styles");
    private Grid myGrid;
    private SimReader myXMLReader;
    private StyleReader myStyleReader;
    private SpecificParameters mySpecificParameters;
    private GridPane gridPane;
    private Rectangle grid;
    private Timeline animation = new Timeline();
    private Slider speedSlider = new Slider();
    private Text simulationName = new Text();
    private Text authorName = new Text();
    private GridPane specificParameters = new GridPane();
    private GridView gridView;
    private ScrollPane sp;
    private final int GRID_ROW_SPAN=10;
    private final int GRID_COL_SPAN=4;
    public Scene init (int width, int height) {

        BorderPane root = new BorderPane();
        myScene = new Scene(root, width, height, Color.GRAY);

        gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        // gridPane.setPadding(new Insets(0, 10, 0, 10));

        grid = new Rectangle(500, 500);
        gridPane.add(grid, 0, 0, GRID_COL_SPAN, GRID_ROW_SPAN);

        Button selectSim = new Button();
        selectSim.setText("Select New Simulation");
        FileChooser simBrowser = new FileChooser();
        simBrowser.setInitialDirectory(xmlFileFolder);
        selectSim.setOnAction(event -> selectSimulation(simBrowser));
        gridPane.add(selectSim, 5, 1);

        Button selectStyle = new Button("Select grid style");
        FileChooser styleBrowser = new FileChooser();
        styleBrowser.setInitialDirectory(styleFileFolder);
        selectStyle.setOnAction(event -> selectStyle(styleBrowser));
        gridPane.add(selectStyle, 5, 2);

        Button startSim = new Button();
        startSim.setText("Start Simulation");
        startSim.setOnAction( (ActionEvent event) -> animation.play());
        gridPane.add(startSim, 5, 3);

        Button stopSim = new Button();
        stopSim.setText("Stop Simulation");
        stopSim.setOnAction(event -> animation.pause());
        gridPane.add(stopSim, 5, 4);

        Button stepSim = new Button();
        stepSim.setText("Increment Simulation");
        stepSim.setOnAction(event -> incrementSimulation());
        gridPane.add(stepSim, 5, 5);

        Button randomizeConfig = new Button();
        randomizeConfig.setText("Generate Random Configuration");
        randomizeConfig.setOnAction(event -> randomizeGrid());
        gridPane.add(randomizeConfig, 5, 6);

        Button exportStates = new Button("Export current cell states");
        ExportingStates exporter = new ExportingStates();
        exportStates.setOnAction(event -> exporter.modifyXMLFile(myXMLReader, myGrid));
        gridPane.add(exportStates, 5, 7);

        configureSpeedSlider(speedSlider);
        gridPane.add(speedSlider, 5, 8);

        GridPane descriptionPane = new GridPane();
        descriptionPane.add(simulationName, 0, 0);
        descriptionPane.add(authorName, 0, 1);

        root.setCenter(gridPane);
        root.setBottom(descriptionPane);
        root.setRight(specificParameters);

        return myScene;

    }

    private void randomizeGrid () {
        try {
            RandomConfiguration randomizer = new RandomConfiguration();
            myGrid.init(randomizer.populateGrid(myXMLReader.getCellArray(), null,
                                                myXMLReader.getNumberOfStates()),
                        myXMLReader.getMyFileName(), myXMLReader.populateParameterMap());
            myGrid.updateView();
        }
        catch (NullPointerException e) {
            createErrorMessage("No simulation has been loaded yet",
                               "Please select a simulation first");
        }

    }

    private void selectStyle (FileChooser styleBrowser) {
        File selectedStyle = styleBrowser.showOpenDialog(myScene.getWindow());
        if (selectedStyle != null) {
            try {
                myStyleReader = new StyleReader();
                myStyleReader.parseStyle(selectedStyle);

                myGrid =
                        (Grid) Class.forName("cellsociety_team09." + myStyleReader.getMyGridEdge())
                                .newInstance();
                myXMLReader.passToGrid(myGrid);
                myGrid.isHex = myStyleReader.getMyGridShape() == "HexagonView"; // bad

                Class<?> clazz =
                        Class.forName("cellsociety_team09." + myStyleReader.getMyGridShape());
                Constructor<?> constructor = clazz.getConstructor(Grid.class, Bounds.class);
                gridView = (GridView) constructor.newInstance(myGrid, grid.getBoundsInLocal());

                mySpecificParameters.displayParameterFields(specificParameters, myXMLReader,
                                                            myGrid);
                    mySpecificParameters.displayStateDistributions(specificParameters, myXMLReader,
                                                                   myGrid);
                
                if (sp != null)
                    gridPane.getChildren().remove(sp);
                sp = new ScrollPane();
                sp.setContent(gridView);
                sp.setPrefSize(grid.getWidth(), grid.getHeight());
                myGrid.setSP(sp);
                gridPane.add(sp, 0, 0, GRID_COL_SPAN, GRID_ROW_SPAN);
                
            }
            catch (ParserConfigurationException | SAXException | IOException
                    | InstantiationException | IllegalAccessException | ClassNotFoundException
                    | NoSuchMethodException | SecurityException | IllegalArgumentException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void createErrorMessage (String header, String content) {
        Alert noSim = new Alert(AlertType.ERROR);
        noSim.setContentText(content);
        noSim.setHeaderText(header);
        noSim.show();
    }

    private void selectSimulation (FileChooser simBrowser) {
        File selectedFile = simBrowser.showOpenDialog(myScene.getWindow());
        if (selectedFile != null) {
            // specificParameters.getChildren().clear();

            animation.pause();
            myGrid = new NormalBorderGrid();

            myXMLReader = new SimReader();
            mySpecificParameters = new SpecificParameters();
            try {
                myXMLReader.parseFile(selectedFile, myGrid);
                simulationName.setText("Simulation Name: " + myXMLReader.getTitle());
                authorName.setText("Simulation Author: " + myXMLReader.getAuthor());

                // IF YOU CHANGE THIS: also change the isHex boolean in grid!

                gridView = new SquareView(myGrid, grid.getBoundsInLocal());

                sp = new ScrollPane();
                sp.setContent(gridView);
                gridPane.add(sp, 0, 0, GRID_COL_SPAN, GRID_ROW_SPAN);

                KeyFrame frame = new KeyFrame(Duration.millis(150), e -> step());
                animation.setCycleCount(Timeline.INDEFINITE);
                animation.getKeyFrames().add(frame);
                mySpecificParameters.displayParameterFields(specificParameters, myXMLReader,
                                                            myGrid);
                    mySpecificParameters.displayStateDistributions(specificParameters, myXMLReader,
                                                                   myGrid);
                
         
            }
            catch (NullPointerException | ParserConfigurationException | SAXException
                    | IOException e) {
                displayInvalidFile();
            }
        }
    }

    private void step () {
        myGrid.step();
    }

    public void displayInvalidFile () {
        Alert invalidSim = new Alert(AlertType.INFORMATION);
        invalidSim.setTitle("Corrupted/Invalid XML File selected");
        invalidSim.setHeaderText(
                                 "Unfortunately the file you selected does not appear to be " +
                                 "supported by this application");
        invalidSim.setContentText("Please choose a different XML file");
        invalidSim.show();
    }

    private void incrementSimulation () {
        try {
            myGrid.step();
        }
        catch (NullPointerException e) {
            displayInvalidFile();
        }
    }

    private void configureSpeedSlider (Slider slider) {
        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(40);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed (ObservableValue<? extends Number> ov,
                                 Number old_val,
                                 Number new_val) {
                animation.setRate(new_val.doubleValue());

            }
        });

    }
}
