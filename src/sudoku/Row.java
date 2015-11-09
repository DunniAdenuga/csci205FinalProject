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

    private CellValue[] listOfRowContents;
    
    public Row(CellValue[] listOfRowContents){
        this.listOfRowContents = listOfRowContents;
    }
    
    public Row(){
        this.listOfRowContents = new CellValue[9];
    }
            
    public void setRowWithValueAtIndex(CellValue newValue, int index){
        this.listOfRowContents[index] = newValue;
    }
    
    public boolean isValid(){
        //we must determine if duplicates exist. If they do, the row is invalid.
        //Since we know that this.listOfRowContents when untouched is an array
        //with nine elements, all of cell zero, we can allow multiple 
        //occurrences of 0 in the array, and will not consider a zero cell as a duplicate
        
        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        for(CellValue cell: this.listOfRowContents){
            
            if(cell.getValue() != 0){
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
    
    public boolean isCompleted(){
        boolean rowIsValid = this.isValid();
        boolean[] indexWasFoundInRow = {false,false,false,false,false,false,false,false,false};
        for(CellValue cell: this.listOfRowContents){
            if(cell.getValue() == 0){return false;}
            
            indexWasFoundInRow[cell.getValue() - 1] = true;
        }
        
        for(boolean numFoundInRow: indexWasFoundInRow){
            if(!numFoundInRow){
                return false;
            }
        }
        return true;
        
    }
    
}
