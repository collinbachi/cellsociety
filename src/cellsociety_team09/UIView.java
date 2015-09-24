package cellsociety_team09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	private GridPane gridPane;
	private Rectangle grid;
	private Timeline animation = new Timeline();
	private Slider speedSlider = new Slider();
	private Text simulationName = new Text();
	private Text authorName = new Text();
	private GridPane specificParameters=new GridPane();

	public Scene init(int width, int height) {

		BorderPane root = new BorderPane();
		myScene = new Scene(root, width, height, Color.GRAY);

		gridPane = new GridPane();
		gridPane.setHgap(50);
		gridPane.setVgap(50);
		gridPane.setPadding(new Insets(0, 10, 0, 10));

		grid = new Rectangle(500, 500);
		gridPane.add(grid, 0, 0, 4, 6);

		Button selectSim = new Button();
		selectSim.setText("Select New Simulation");
		FileChooser simBrowser = new FileChooser();
		simBrowser.setInitialDirectory(xmlFileFolder);

		selectSim.setOnAction(event -> selectSimulation(simBrowser));

		gridPane.add(selectSim, 5, 1);

		Button startSim = new Button();
		startSim.setText("Start Simulation");
		startSim.setOnAction((ActionEvent event) -> animation.play());
		gridPane.add(startSim, 5, 2);

		Button stopSim = new Button();
		stopSim.setText("Stop Simulation");
		stopSim.setOnAction(event -> animation.pause());

		// TODO eventHandler
		gridPane.add(stopSim, 5, 3);

		Button stepSim = new Button();
		stepSim.setText("Increment Simulation");
		stepSim.setOnAction(event -> incrementSimulation());
		gridPane.add(stepSim, 5, 4);

		Button randomizeConfig=new Button();
		randomizeConfig.setText("Generate Random Configuration");
		randomizeConfig.setOnAction(event -> randomizeGrid());
		gridPane.add(randomizeConfig, 5, 5);
		
		configureSpeedSlider(speedSlider);
		gridPane.add(speedSlider, 5, 6);

		GridPane descriptionPane = new GridPane();
		descriptionPane.add(simulationName,0,0);
		descriptionPane.add(authorName, 0, 1);
	
		root.setCenter(gridPane);
		root.setBottom(descriptionPane);
		root.setRight(specificParameters);

		return myScene;

	}

	public void randomizeGrid()
	{
		try {
		RandomConfiguration randomizer=new RandomConfiguration();
		myGrid.init(randomizer.populateGrid(myXMLReader.getCellArray(), null, myXMLReader.getNumberOfStates()), myXMLReader.getMyFileName()
				, myXMLReader.populateParameterMap());
		myGrid.step();
		}
		catch(NullPointerException e)
		{
			Alert noSim=new Alert(AlertType.ERROR);
			noSim.setContentText("Please select a simulation first");
			noSim.setHeaderText("No simulation has been loaded yet");
			noSim.show();
		}
		
	}

	public void selectSimulation(FileChooser simBrowser) {
		File selectedFile = simBrowser.showOpenDialog(myScene.getWindow());
			if (selectedFile != null) {
			specificParameters.getChildren().clear();
				animation.pause();
				myGrid = new Grid();
		
			myXMLReader = new SimReader();
			try{
				myXMLReader.parseFile(selectedFile, myGrid);
				simulationName.setText("Simulation Name: "+myXMLReader.getTitle());
				authorName.setText("Simulation Author: " +myXMLReader.getAuthor());

				TriangleView gridView = new TriangleView(myGrid, grid.getBoundsInLocal());	
				gridPane.add(gridView, 0, 0, 4, 6);
			
				myGrid.step();
				KeyFrame frame = new KeyFrame(Duration.millis(150), e -> myGrid.step());
				animation.setCycleCount(Timeline.INDEFINITE);
				animation.getKeyFrames().add(frame);
			
			}
			catch(NullPointerException | ParserConfigurationException | SAXException | IOException e)
			{
				displayInvalidSim();
	}

			
			
		}
	}
	public void displayParameterSliders()
	{
		int rowIndex=0;
		for(String s: myXMLReader.populateParameterMap().keySet())
	{
			Text parameterName=new Text(s);
			specificParameters.add(parameterName, 0, rowIndex);
			 
		}
	}
	public void displayInvalidSim() {
		Alert invalidSim = new Alert(AlertType.INFORMATION);
		invalidSim.setTitle("Corrupted/Invalid XML File selected");
		invalidSim.setHeaderText(
				"Unfortunately the file you selected does not appear to be " + "supported by this application");
		invalidSim.setContentText("Please choose a different simulation XML file");
		invalidSim.show();
	}

	public void incrementSimulation() {
		try {
			myGrid.step();
		} catch (NullPointerException e) {
			displayInvalidSim();
	}
	}

	public void configureSpeedSlider(Slider slider) {
		slider.setMin(0);
		slider.setMax(1);
		slider.setValue(40);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				animation.setRate(new_val.doubleValue());
                        
            }
	});

}
}
