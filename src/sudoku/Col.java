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
public class Col {
 
    private int colNum;
    private Board b;

    public Col(int colNum, Board b){
        this.colNum = colNum;
        this.b = b;
    }
    
    
    
  
    
    /**
     * This method determines whether or not a col is valid.
     * If the col has no duplicates, the col is valid.
     * @return a boolean value representing the validity of the Col
     */
    public boolean isValid(){

        
        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        
        for(int i = 0; i < this.b.getLengthOfRowsColsAndSquares(); i++){

            CellValue cell = this.b.getValueAtLoc(new Location(this.colNum, i));
            
            if(!cell.isEmpty()){
                if(listOfValuesFound.contains(cell)){
                    return false;
                    //a duplicate exists in the col, and therefore the col is invalid
                }else{
                    listOfValuesFound.add(cell);
                }             
                
            }//no need for an else here, if cell is zero, we leave it alone
                
        }
        return true;
    }
        
    /**
     * The purpose of this method is to determine if the col
     * is completed, by making sure that every value from 1-9 is found, and that there
     * are no empty spaces.
     * @return a boolean to represent whether or not the col is completed.
     */

    public boolean isCompleted(){
        if(!this.isValid()){
            return false;
        }
        
        //each boolean in this array corresponds to a number 1-9, based on it's index which ranges from 0-8.
        boolean[] indexWasFoundInCol = {false,false,false,false,false,false,false,false,false};
        
        //iterates through each CellValue in the col and gets the cell value from the board this.b
        //if an empty cell is found, we return false
        //For every value 1-9 that is found, we set it's corresponding 
        //boolean value in indexWasFoundInCol to be true.

        for(int i = 0; i < this.b.getLengthOfRowsColsAndSquares(); i++){
            CellValue cell = this.b.getValueAtLoc(new Location(this.colNum, i));

            if(cell.isEmpty()){return false;}
            
            indexWasFoundInCol[cell.getValue() - 1] = true;
        }
        
        //then, we iterate through indexWasFoundInCol
        //and if a single element is false, then the col does not contain all 
        
        for(boolean numFoundInCol: indexWasFoundInCol){
            if(!numFoundInCol){
                return false;
            }
        }
        return true;
        
    }
 
    
}
