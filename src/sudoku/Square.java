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
public class Square {
    
    private CellValue[] listOfSquareContents;
    
    public Square(CellValue[] listOfSquareContents){
        
        this.listOfSquareContents = listOfSquareContents;
    }
    public Square(){
        //squares are 3x3, therefore the contents array has length 9.
        this.listOfSquareContents = new CellValue[9];
    }
    
    public void setSquareWithValueAtIndex(CellValue newValue, int index){
        this.listOfSquareContents[index] = newValue;
    }
    
    public boolean isValid(){
        //we must determine if duplicates exist. If they do, the square is invalid.
        //Since we know that this.listOfSquareContents when untouched is an array
        //with nine elements, all of cell zero, we can allow multiple 
        //occurrences of 0 in the array, and will not consider a zero cell as a duplicate
        
        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        for(CellValue cell: this.listOfSquareContents){
            
            if(cell.getValue() != 0){
                if(listOfValuesFound.contains(cell)){
                    return false;
                    //a duplicate exists in the square, and therefore the square is invalid
                }else{
                    listOfValuesFound.add(cell);
                }             
                
            }//no need for an else here, if cell is zero, we leave it alone
                
        }
        return true;
    }
    
    public boolean isCompleted(){
        boolean squareIsValid = this.isValid();
        boolean[] indexWasFoundInSquare = {false,false,false,false,false,false,false,false,false};
        for(CellValue cell: this.listOfSquareContents){
            if(cell.getValue() == 0){return false;}
            
            indexWasFoundInSquare[cell.getValue() - 1] = true;
        }
        
        for(boolean numFoundInSquare: indexWasFoundInSquare){
            if(!numFoundInSquare){
                return false;
            }
        }
        return true;
        
    }    
    
       
}
