/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;

/**
 *
 * @author andrewnyhus
 */
public class Row {
 
    private int rowNum;
    private Board b;

    public Row(int rowNum, Board b){
        this.rowNum = rowNum;
        this.b = b;
    }
    
    
    
  
    
    /**
     * This method determines whether or not a row is valid.
     * If the row has no duplicates, the row is valid.
     * @return a boolean value representing the validity of the Row
     */
    public boolean isValid(){
        
        
        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        
        for(int i = 0; i < this.b.getLengthOfRowsColsAndSquares(); i++){

            CellValue cell = this.b.getValueAtLoc(new Location(this.rowNum, i));
            
            if(!cell.isEmpty()){
                if(listOfValuesFound.contains(cell)){
                    return false;
                    //a duplicate exists in the row, and therefore the row is invalid
                }else{
                    listOfValuesFound.add(cell);
                }             
                
            }//no need for an else here, if cell is zero, we leave it alone
                
        }
        return true;
    }
        
    /**
     * The purpose of this method is to determine if the row
     * is completed, by making sure that every value from 1-9 is found, and that there
     * are no empty spaces.
     * @return a boolean to represent whether or not the row is completed.
     */

    public boolean isCompleted(){
        if(!this.isValid()){
            return false;
        }
        
        //each boolean in this array corresponds to a number 1-9, based on it's index which ranges from 0-8.
        boolean[] indexWasFoundInRow = {false,false,false,false,false,false,false,false,false};
        
        //iterates through each CellValue in the row and gets the cell value from the board this.b
        //if an empty cell is found, we return false
        //For every value 1-9 that is found, we set it's corresponding 
        //boolean value in indexWasFoundInRow to be true.

        for(int i = 0; i < this.b.getLengthOfRowsColsAndSquares(); i++){
            CellValue cell = this.b.getValueAtLoc(new Location(i, this.rowNum));

            if(cell.isEmpty()){return false;}
            
            indexWasFoundInRow[cell.getValue() - 1] = true;
        }
        
        //then, we iterate through indexWasFoundInRow
        //and if a single element is false, then the row does not contain all 
        
        for(boolean numFoundInRow: indexWasFoundInRow){
            if(!numFoundInRow){
                return false;
            }
        }
        return true;
        
    }
 
    
}