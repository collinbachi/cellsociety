package cellsociety_team09;

import java.awt.List;
import java.util.ArrayList;
import javax.swing.GroupLayout.Alignment;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import xmlManagement.SimReader;

public class SpecificParameters {
    ArrayList<TextField> fieldList=new ArrayList<TextField>();
    public void displayParameterFields(GridPane specificParameters,SimReader myXMLReader)
    {
            int rowIndex=2;
            for(String s: myXMLReader.populateParameterMap().keySet())
    {
                    fieldList=new ArrayList<TextField>();
                     TextField inputField=new TextField();
                     inputField.setPromptText(s);
                     
                     inputField.setMaxWidth(100);
                    specificParameters.add(inputField,1,rowIndex);
                   fieldList.add(inputField);
                    rowIndex++;                      
            }
            specificParameters.setAlignment(Pos.CENTER_LEFT);
    }
    
}
