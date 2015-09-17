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
import xmlManagement.XMLReader;

public class UIView {

	private Scene myScene;
	XMLReader fileReader=new XMLReader();
	
	public Scene init(int width, int height) {

		BorderPane root = new BorderPane();
		myScene = new Scene(root, width, height, Color.GRAY);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(50);
		gridPane.setVgap(50);
		gridPane.setPadding(new Insets(0, 10, 0, 10));

		Rectangle grid = new Rectangle(500, 500);
		gridPane.add(grid, 0, 0, 4, 6);

		Button selectSim = new Button();
		selectSim.setText("Select New Simulation");
		FileChooser simBrowser=new FileChooser();
		selectSim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(final ActionEvent e) {
			selectSimulation(simBrowser);
			
			}

		
		});
		gridPane.add(selectSim, 5, 1);

		Button startSim = new Button();
		startSim.setText("Start Simulation");
		// TODO eventHandler
		gridPane.add(startSim, 5, 2);

		Button stopSim = new Button();
		stopSim.setText("Stop Simulation");
		// TODO eventHandler
		gridPane.add(stopSim, 5, 3);

		Button stepSim = new Button();
		stepSim.setText("Increment Simulation");
		gridPane.add(stepSim, 5, 4);

		Slider speedSlider = new Slider();
		configureSlider(speedSlider);
		gridPane.add(speedSlider, 5, 5);

		root.setCenter(gridPane);

		return myScene;

	}
	public void selectSimulation(FileChooser simBrowser) {
		File selectedFile=simBrowser.showOpenDialog(myScene.getWindow());
		try {
			if(selectedFile!=null)
			fileReader.parseFile(selectedFile);
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			e1.printStackTrace();
		}
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
