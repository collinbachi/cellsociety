package cellsociety_team09;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.GroupLayout.Alignment;
import configurations.DistributionConfiguration;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import xmlManagement.SimReader;


public class SpecificParameters {
    ArrayList<TextField> fieldList = new ArrayList<TextField>();
    ArrayList<TextField> stateList=new ArrayList<TextField>();
    
    public void displayParameterFields (GridPane specificParameters, SimReader myXMLReader,Grid myGrid) {
        int rowIndex = 2;
        specificParameters.setVgap(30);
        for (String s : myXMLReader.populateParameterMap().keySet()) {
            fieldList = new ArrayList<TextField>();
            TextField inputField = new TextField();
            inputField.setMaxWidth(100);
            inputField.setText(myXMLReader.populateParameterMap().get(s).toString());
            inputField.setPromptText(s);
            Label parameterName=new Label(s);
            parameterName.setMaxWidth(100);
            parameterName.setFont(new Font("Cambrira",10));
            specificParameters.add(parameterName, 0, rowIndex);
            specificParameters.add(inputField, 1, rowIndex);
            specificParameters.setMaxWidth(200);
            fieldList.add(inputField);
            rowIndex++;
        }
        if(rowIndex!=2)
        {
        Button setParameters = new Button("Change Simluation Parameters");
        setParameters.setOnAction(event -> myGrid.setParameterMap(
                changeParameters(myXMLReader.populateParameterMap())));
        specificParameters.add(setParameters, 0, 1,2,1);
        }

        specificParameters.setAlignment(Pos.TOP_LEFT);
    }

    public Map<String, Double> changeParameters (Map<String, Double> parameterMap) {
        for (TextField field : fieldList) {
            try {
                parameterMap.put(field.getPromptText(), Double.parseDouble(field.getText()));
                System.out.println(field.getText());
            }
            catch (NumberFormatException e)

            {
                UIView popup = new UIView();
                popup.createErrorMessage("Invalid Input", "Please enter a different input");
            }
        }
        return parameterMap;
    }

    public void displayStateDistributions(GridPane pane,SimReader myXMLReader,Grid myGrid)
    {
        int rowIndex=2;
        
        for(int state=0;state<myXMLReader.getNumberOfStates();state++)
        {
            Label stateName=new Label("State "+(state)+":");
            stateName.setFont(new Font("Cambria",10));
            TextField stateField=new TextField();
            pane.add(stateName, 3, rowIndex);
            pane.add(stateField, 4, rowIndex);
            stateList.add(stateField);
            rowIndex++;
        }
        
        Button setDistribution=new Button("Change cell distribution");
        setDistribution.setOnAction(event -> changeStateDistributions(myGrid,myXMLReader));
        pane.add(setDistribution, 3, 1,2,1);
    }
    
    public void changeStateDistributions(Grid myGrid,SimReader myXMLReader)
    {
        ArrayList<Integer> distributionList=new ArrayList<Integer>();
        DistributionConfiguration config=new DistributionConfiguration();

        for(TextField field: stateList)
        {
           try{
               distributionList.add(Integer.parseInt(field.getText()));
           }
           catch (NumberFormatException e)
           {
               
           }
        }
        myGrid.init(config.populateGrid(myXMLReader.getCellArray(), distributionList), myXMLReader.getMyFileName(), myXMLReader.populateParameterMap());
        myGrid.step();
        
        
    }
    
}
