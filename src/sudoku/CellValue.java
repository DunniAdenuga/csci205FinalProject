/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 13, 2015
 * Time: 09:42:00 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku
 * File: CellValue
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author andrewnyhus
 */
public enum CellValue {
    EMPTY(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);
    private int value;
    
    private CellValue(int value){
        this.value = value;
    }
    
    /**
     * @return int value of this CellValue
     */
    public int getValue(){
        return this.value;
    }
    
    /**
     * @return boolean indicating whether this CellValue
     * is empty (this.getValue() == 1) or not.
     */
    public boolean isEmpty(){
        if(this.getValue() == 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * This method represents the CellValue as a String
     * @return String 
     */
    public String toString(){
        if(this.isEmpty()){
            return "Empty";
        }else{
            return "" + this.getValue();
        }
    }
    
};
