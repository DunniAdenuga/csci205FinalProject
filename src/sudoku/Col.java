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
 
    private CellValue[] listOfColContents;
    
    public Col(CellValue[] listOfColContents){
        this.listOfColContents = listOfColContents;
    }
    
    public Col(){
        this.listOfColContents = new CellValue[9];
    }
    
    public void setColWithValueAtIndex(CellValue newValue, int index){
        this.listOfColContents[index] = newValue;
    }
  
    public boolean isValid(){
        //we must determine if duplicates exist. If they do, the col is invalid.
        //Since we know that this.listOfColContents when untouched is an array
        //with nine elements, all of cell zero, we can allow multiple 
        //occurrences of 0 in the array, and will not consider a zero cell as a duplicate
        
        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        for(CellValue cell: this.listOfColContents){
            //if valu
            if(cell.getValue() != 0){
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
    
    public boolean isCompleted(){
        boolean colIsValid = this.isValid();
        boolean[] indexWasFoundInCol = {false,false,false,false,false,false,false,false,false};
        for(CellValue cell: this.listOfColContents){
            if(cell.getValue() == 0){return false;}
            
            indexWasFoundInCol[cell.getValue() - 1] = true;
        }
        
        for(boolean numFoundInCol: indexWasFoundInCol){
            if(!numFoundInCol){
                return false;
            }
        }
        return true;
        
    }
 
    
}
