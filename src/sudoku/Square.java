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
    
    private int squareNum;
    private Board b;
    private Location origin;
    
    public Square(int squareNum, Board b){
        this.squareNum = squareNum;
        this.b = b;
        this.origin = this.b.getOriginOfSquareWithNum(squareNum);
        
    }
    
    

    
    /**
     *This method determines whether or not a square is valid.
     * If the square has no duplicates, the square is valid.
     * @return a boolean value representing the validity of the Square
     */
    public boolean isValid(){
        //we must determine if duplicates exist. If they do, the square is invalid.
        //with nine elements, all of cell zero, we can allow multiple 
        //occurrences of 0 in the array, and will not consider a zero cell as a duplicate
        
        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        int originX = this.origin.getX();
        int originY = this.origin.getY();
        
        Location[] locsInSquare = {this.origin, new Location(originX + 1, originY), new Location(originX + 2, originY), new Location(originX, originY + 1), new Location(originX + 1, originY + 1), new Location(originX + 2, originY + 1), new Location(originX, originY + 2), new Location(originX + 1, originY + 2), new Location(originX + 2, originY + 2)};
        
        for(Location loc: locsInSquare){
            CellValue cell = this.b.getValueAtLoc(loc);
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

        //iterates through each CellValue in the square
        //if an empty cell is found, we return false
        //For every value 1-9 that is found, we set it's corresponding 
        //boolean value in indexWasFoundInSquare to be true.
        int originX = this.origin.getX();
        int originY = this.origin.getY();
        
        Location[] locsInSquare = {this.origin, new Location(originX + 1, originY), new Location(originX + 2, originY), new Location(originX, originY + 1), new Location(originX + 1, originY + 1), new Location(originX + 2, originY + 1), new Location(originX, originY + 2), new Location(originX + 1, originY + 2), new Location(originX + 2, originY + 2)};
        
        for(Location loc: locsInSquare){
            CellValue cell = this.b.getValueAtLoc(loc);
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
