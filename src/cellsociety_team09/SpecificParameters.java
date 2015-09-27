package cellsociety_team09;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.GroupLayout.Alignment;
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

    public void displayParameterFields (GridPane specificParameters, SimReader myXMLReader) {
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

}
