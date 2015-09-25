package cellsociety_team09;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import xmlManagement.RandomConfiguration;
import xmlManagement.SimReader;


/*
 * Responsible for configuring and placing the GUI elements for the user interface
 * 
 * @author Jasper Hancock
 */
public class UIView {

    private Scene myScene;
    private File xmlFileFolder = new File("XML");
    private Grid myGrid;
    private SimReader myXMLReader;
    private SpecificParameters mySpecificParameters;
    private GridPane gridPane;
    private Rectangle grid;
    private Timeline animation = new Timeline();
    private Slider speedSlider = new Slider();
    private Text simulationName = new Text();
    private Text authorName = new Text();
    private GridPane specificParameters = new GridPane();

    public Scene init (int width, int height) {

        BorderPane root = new BorderPane();
        myScene = new Scene(root, width, height, Color.GRAY);

        gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(0, 10, 0, 10));

        grid = new Rectangle(500, 500);
        gridPane.add(grid, 0, 0, 4, 8);

        Button selectSim = new Button();
        selectSim.setText("Select New Simulation");
        FileChooser simBrowser = new FileChooser();
        simBrowser.setInitialDirectory(xmlFileFolder);

        selectSim.setOnAction(event -> selectSimulation(simBrowser));

        gridPane.add(selectSim, 5, 1);

        Button startSim = new Button();
        startSim.setText("Start Simulation");
        startSim.setOnAction( (ActionEvent event) -> animation.play());
        gridPane.add(startSim, 5, 2);

        Button stopSim = new Button();
        stopSim.setText("Stop Simulation");
        stopSim.setOnAction(event -> animation.pause());
        gridPane.add(stopSim, 5, 3);

        Button stepSim = new Button();
        stepSim.setText("Increment Simulation");
        stepSim.setOnAction(event -> incrementSimulation());
        gridPane.add(stepSim, 5, 4);

        Button randomizeConfig = new Button();
        randomizeConfig.setText("Generate Random Configuration");
        randomizeConfig.setOnAction(event -> randomizeGrid());
        gridPane.add(randomizeConfig, 5, 5);

        Button setParameters = new Button("Change Simluation Parameters");
        setParameters.setOnAction(event -> myGrid.setParameterMap(mySpecificParameters
                .changeParameters(myXMLReader.populateParameterMap())));
        gridPane.add(setParameters, 5, 6);

        configureSpeedSlider(speedSlider);
        gridPane.add(speedSlider, 5, 7);

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
            myGrid.step();
        }
        catch (NullPointerException e) {
            createErrorMessage("No simulation has been loaded yet",
                               "Please select a simulation first");
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
            specificParameters.getChildren().clear();

            animation.pause();
            myGrid = new NormalBorderGrid();

            myXMLReader = new SimReader();
            mySpecificParameters = new SpecificParameters();
            try {
                myXMLReader.parseFile(selectedFile, myGrid);
                simulationName.setText("Simulation Name: " + myXMLReader.getTitle());
                authorName.setText("Simulation Author: " + myXMLReader.getAuthor());

                HexagonView gridView = new HexagonView(myGrid, grid.getBoundsInLocal());
                gridPane.add(gridView, 0, 0, 4, 8);

                KeyFrame frame = new KeyFrame(Duration.millis(150), e -> myGrid.step());
                animation.setCycleCount(Timeline.INDEFINITE);
                animation.getKeyFrames().add(frame);
                mySpecificParameters.displayParameterFields(specificParameters, myXMLReader);

            }
            catch (NullPointerException | ParserConfigurationException | SAXException
                    | IOException e) {
                displayInvalidSim();}}
            }
	
	private void displayParameterSliders()
	{
		int rowIndex=0;
		for(String s: myXMLReader.populateParameterMap().keySet())
	{
			Text parameterName=new Text(s);
			specificParameters.add(parameterName, 0, rowIndex);
			 
		}
	}
	

    public void displayInvalidSim () {
        Alert invalidSim = new Alert(AlertType.INFORMATION);
        invalidSim.setTitle("Corrupted/Invalid XML File selected");
        invalidSim.setHeaderText(
                                 "Unfortunately the file you selected does not appear to be " +
                                 "supported by this application");
        invalidSim.setContentText("Please choose a different simulation XML file");
        invalidSim.show();
    }

    private void incrementSimulation () {
        try {
            myGrid.step();
        }
        catch (NullPointerException e) {
            displayInvalidSim();
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
