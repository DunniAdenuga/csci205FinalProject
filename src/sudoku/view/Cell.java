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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import sudoku.CellValue;
import sudoku.Location;
import sudoku.controller.SudokuController;

/**
 *
 * @author ajn008
 */
public class Cell extends JTextField{
    
    private final Location location;    
    private SudokuController controller;
    
    /**
     * Constructor for Cell that takes in Location and editability of Cell
     * @param location
     * @param editable 
     * @param controller 
     */
    public Cell(Location location, boolean editable, SudokuController controller){
        this.setEditable(editable);
        this.location = location;
        this.controller = controller;
                
    }

    /**
     * @return Location object for 'this' Cell in Board
     */
    public Location getLocationInBoard(){
        return this.location;
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
    
    public void createTextFieldLimitDocument(int limit){
        JTextFieldLimit j = new JTextFieldLimit(limit);
        this.setDocument(j);
    }
    
    /**
     * To represent cell as a string to help with debugging.
     * @return 
     */
    public String toString(){
        String valueString;
        if(this.getCellValue().isEmpty()){
            valueString = "EMPTY";
        }else{
            valueString = "" + this.getCellValue().getValue();
        }
        
        return "Location (" + this.getLocationInBoard().getX() + ", " + this.getLocationInBoard().getY() + ") the value is " + valueString;
    }

    /**This JTextFieldLimit class is necessary, it is an extension of the PlainDocument class
     * It allows us to do two main things related to user input
     * 1) It allows us to stop the user from entering bad input
     * 2) It allows us to update our controller class when the user updates the grid.
     * Referred to: http://stackoverflow.com/questions/3519151/how-to-limit-the-number-of-characters-in-jtextfield
     */
    public class JTextFieldLimit extends PlainDocument{
        private int limit;
        private boolean ignoreEvents = false;
        JTextFieldLimit(int limit){
            super();
            this.limit = limit;
        }
        
        @Override
        public void remove(int offs, int len) throws BadLocationException {
            String originalValue = Cell.this.getText();
            super.remove(offs, len);
            String newValue = Cell.this.getText();
            
            // source: http://ask.webatall.com/java/1959_value-change-listener-to-jtextfield.html
            if (!ignoreEvents && !originalValue.equals(newValue)) {
                //Cell.this.firePropertyChange("text", originalValue, newValue);
            }

            Cell.this.controller.updateBoard();
        }
        
                
        @Override
        public void insertString(int offset, String string, AttributeSet attributes) throws BadLocationException{
            
            //If string is for some reason a nullpointer, end the method here.
            if(string == null){
                return;
            }
                                    
            if((getLength() + string.length()) <= limit){
                //we know the length of string should be 1, so we evaluate whether or not the stringOfValidValues contains string.
                String strOfValidValues = "123456789";
                if(strOfValidValues.contains(string)){
                    //if true, the input value is valid and we insert string into the text field in the given sudoku cell
                    super.insertString(offset, string, attributes);
                    Cell.this.controller.updateBoard();
                }
                
            }else{
                System.out.println("You cannot enter more than 1 character in a Sudoku cell.");
            }
            
        }
               
    }        





}
