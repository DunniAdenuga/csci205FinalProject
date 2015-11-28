/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 12, 2015
 * Time: 9:55:12 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.view
 * File: Cell
 * Description:
 *
 * ****************************************
 */
package sudoku.view;

import java.awt.Color;
import javax.swing.JTextField;
import sudoku.CellValue;
import sudoku.Location;

/**
 *
 * @author ajn008
 */
public class Cell extends JTextField{
    
    private final Location location;    
    
    /**
     * Constructor for Cell that takes in Location and editability of Cell
     * @param location
     * @param editable 
     */
    public Cell(Location location, boolean editable){
        this.setCellFieldEditable(editable);
        this.location = location;
        
        
    }
    
    
    /**
     * Returns CellValue based off of this.getText()
     * @return CellValue with value 0 if this Cell is empty, and otherwise returns the 
     * CellValue with the numeric value in this Cell
     */
    public CellValue getCellValue(){
        if(this.getText().length() == 0){
            //if this is true, we know that the field is empty
            //and therefore we should return CellValue.EMPTY\
            return CellValue.EMPTY;
        }else{
            return CellValue.values()[Integer.parseInt(this.getText())];
        }        
    }
    
    /**
     * Sets the editability of this Cell from a boolean parameter.
     * @param isEditable 
     */
    public void setCellFieldEditable(boolean isEditable){
        this.setEditable(isEditable);
    }
    
    /**
     * Sets the text of this cell based off of a CellValue
     * @param value 
     */
    public void setCellFieldValue(CellValue value){
        if(value.isEmpty()){
            this.setText("");
        }else{
            this.setText(value.getValue() + "");
        }
            
    }
    
    /**
     * Sets the text of this cell based off of an int
     * @param intValue 
     */
    public void setCellFieldValueWithInt(int intValue){
        if(intValue == 0){
            this.setText("");
        }else{
            this.setText(intValue + "");
        }
    }

    /**
     * @param num
     * @return boolean representing whether or not the given int (num)
     * is a nonempty and valid CellValue
     */
    public boolean numIsAValidNonEmptyCellValue(int num){
        return (num == 1) || (num == 2) || (num == 3) || (num == 4) ||(num == 5) || (num == 6) ||(num == 7) || (num == 8) || (num == 9);
    }
    
    /**
     * Sets the background of the cell to a given color
     * @param color 
     */
    public void setCellBackgroundColor(Color color){
        this.setBackground(color);
    }
    
}
