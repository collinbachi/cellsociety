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
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import xmlManagement.XMLReader;

/*
 * Responsible for configuring and placing the GUI elements for the user interface
 * 
 * @author Jasper Hancock
 */
public class UIView {

	private Scene myScene;
	private File xmlFileFolder = new File("XML");
	private Grid myGrid;
	private XMLReader myXMLReader;
	private GridPane gridPane;
	private Rectangle grid;
	private Timeline animation = new Timeline();


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

		Slider speedSlider = new Slider();
		configureSlider(speedSlider);
		gridPane.add(speedSlider, 5, 5);

		GridPane description = new GridPane();
		root.setCenter(gridPane);

		return myScene;

	}

	public void selectSimulation(FileChooser simBrowser) {
		File selectedFile = simBrowser.showOpenDialog(myScene.getWindow());
		try {

			if (selectedFile != null) {
				myGrid = new Grid();
				myXMLReader = new XMLReader();
				myXMLReader.parseFile(selectedFile, myGrid);
				GridView gridView = new GridView(myGrid, grid.getBoundsInLocal());	
				gridPane.add(gridView, 0, 0, 4, 6);
				myGrid.step();
				KeyFrame frame = new KeyFrame(Duration.millis(300), e -> myGrid.step());
				animation.setCycleCount(Timeline.INDEFINITE);
				animation.getKeyFrames().add(frame);
				//animation.play();
			
			}
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			e1.printStackTrace();
		}
	}

	public void incrementSimulation()
	{
		if(myGrid!=null)
			myGrid.step();
	}
	public void configureSlider(Slider slider) {
		slider.setMin(0);
		slider.setMax(100);
		slider.setValue(40);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);
	}

}
