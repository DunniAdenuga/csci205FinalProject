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
 * File: Col
 * Description:
 *
 * ****************************************
 */
package sudoku;


/**
 *
 * @author andrewnyhus
 */
public class Col extends BoardSegment{
 
    private int colNum;
    private Board b;

    public Col(int colNum, Board b){
        this.colNum = colNum;
        this.b = b;
    }

    @Override
    CellValue getValueAtIndex(int index) {
        return this.b.getValueAtLoc(new Location(this.colNum, index));
    }
    
    
    
}
