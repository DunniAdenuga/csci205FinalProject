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
 * File: Row
 * Description:
 *
 * ****************************************
 */
package sudoku;


/**
 *
 * @author ajn008
 */
public class Row extends BoardSegment{
 
    private int rowNum;
    private Board b;

    public Row(int rowNum, Board b){
        this.rowNum = rowNum;
        this.b = b;
    }
    

        

    @Override
    CellValue getValueAtIndex(int index) {
        return this.b.getValueAtLoc(new Location(index , this.rowNum));
    }
 
    
}