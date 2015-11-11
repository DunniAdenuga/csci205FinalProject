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
    
    private CellValue[] arrOfSquareContents;
    
    public Square(CellValue[] listOfSquareContents){
        
        this.arrOfSquareContents = listOfSquareContents;
    }
    public Square(){
        //squares are 3x3, therefore the contents array has length 9.
        this.arrOfSquareContents = new CellValue[9];
    }
    
    
    /**
     * Sets specified value at a specified index in this.arrOfSquareContents
     * @param newValue
     * @param index 
     */
    public void setSquareWithValueAtIndex(CellValue newValue, int index){
        this.arrOfSquareContents[index] = newValue;
    }
    
    /**
     *This method determines whether or not a square is valid.
     * If the square has no duplicates, the square is valid.
     * @return a boolean value representing the validity of the Square
     */
    public boolean isValid(){
        //we must determine if duplicates exist. If they do, the square is invalid.
        //Since we know that this.arrOfSquareContents when untouched is an array
        //with nine elements, all of cell zero, we can allow multiple 
        //occurrences of 0 in the array, and will not consider a zero cell as a duplicate
        
        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        for(CellValue cell: this.arrOfSquareContents){
            
            if(!cell.isEmpty()){
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
    
    
    /**
     * The purpose of this method is to determine if the square
     * is completed, by making sure that every value from 1-9 is found, and that there
     * are no empty spaces.
     * @return a boolean to represent whether or not the square is completed.
     */
    
    public boolean isCompleted(){
        if(!this.isValid()){
            return false;
        }

        //each boolean in this array corresponds to a number 1-9, based on it's index which ranges from 0-8.
        boolean[] indexWasFoundInSquare = {false,false,false,false,false,false,false,false,false};

        //iterates through each CellValue in this.arrOfSquareContents
        //if an empty cell is found, we return false
        //For every value 1-9 that is found, we set it's corresponding 
        //boolean value in indexWasFoundInSquare to be true.
        
        for(CellValue cell: this.arrOfSquareContents){
            if(cell.isEmpty()){return false;}
            
            indexWasFoundInSquare[cell.getValue() - 1] = true;
        }
        
        //then, we iterate through indexWasFoundInSquare
        //and if a single element is false, then the square does not contain all 
        for(boolean numFoundInSquare: indexWasFoundInSquare){
            if(!numFoundInSquare){
                return false;
            }
        }
        return true;
        
    }    
    
       
}
