/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 10, 2015
 * Time: 9:42:27 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku
 * File: main
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author ajn008
 */
public class main {
    
    public static void main(String[] args){
        System.out.println("Testing stuff");
        CellValue[][] testGridValidIncomplete = { {CellValue.EMPTY, CellValue.EIGHT, CellValue.TWO, CellValue.ONE, CellValue.THREE, CellValue.FOUR, CellValue.SIX, CellValue.SEVEN, CellValue.NINE}, {CellValue.NINE, CellValue.SEVEN, CellValue.ONE, CellValue.EIGHT, CellValue.FIVE, CellValue.SIX, CellValue.TWO, CellValue.THREE, CellValue.FOUR}, {CellValue.THREE, CellValue.FOUR, CellValue.SIX, CellValue.NINE, CellValue.SEVEN, CellValue.TWO, CellValue.ONE, CellValue.EIGHT, CellValue.FIVE}, {CellValue.TWO, CellValue.FIVE, CellValue.NINE, CellValue.SEVEN, CellValue.SIX, CellValue.THREE, CellValue.FOUR, CellValue.ONE, CellValue.EIGHT}, {CellValue.EIGHT, CellValue.THREE, CellValue.SEVEN, CellValue.FOUR, CellValue.ONE, CellValue.NINE, CellValue.FIVE, CellValue.TWO, CellValue.SIX}, {CellValue.ONE, CellValue.SIX, CellValue.FOUR, CellValue.FIVE, CellValue.TWO, CellValue.EIGHT, CellValue.THREE, CellValue.NINE, CellValue.SEVEN}, {CellValue.SIX, CellValue.NINE, CellValue.EIGHT, CellValue.TWO, CellValue.FOUR, CellValue.ONE, CellValue.SEVEN, CellValue.FIVE, CellValue.THREE}, {CellValue.SEVEN, CellValue.ONE, CellValue.THREE, CellValue.SIX, CellValue.NINE, CellValue.FIVE, CellValue.EIGHT, CellValue.FOUR, CellValue.TWO}, {CellValue.FOUR, CellValue.TWO, CellValue.FIVE, CellValue.THREE, CellValue.EIGHT, CellValue.SEVEN, CellValue.NINE, CellValue.SIX, CellValue.ONE}};
        CellValue[][] testGridValidComplete = { {CellValue.FIVE, CellValue.EIGHT, CellValue.TWO, CellValue.ONE, CellValue.THREE, CellValue.FOUR, CellValue.SIX, CellValue.SEVEN, CellValue.NINE}, {CellValue.NINE, CellValue.SEVEN, CellValue.ONE, CellValue.EIGHT, CellValue.FIVE, CellValue.SIX, CellValue.TWO, CellValue.THREE, CellValue.FOUR}, {CellValue.THREE, CellValue.FOUR, CellValue.SIX, CellValue.NINE, CellValue.SEVEN, CellValue.TWO, CellValue.ONE, CellValue.EIGHT, CellValue.FIVE}, {CellValue.TWO, CellValue.FIVE, CellValue.NINE, CellValue.SEVEN, CellValue.SIX, CellValue.THREE, CellValue.FOUR, CellValue.ONE, CellValue.EIGHT}, {CellValue.EIGHT, CellValue.THREE, CellValue.SEVEN, CellValue.FOUR, CellValue.ONE, CellValue.NINE, CellValue.FIVE, CellValue.TWO, CellValue.SIX}, {CellValue.ONE, CellValue.SIX, CellValue.FOUR, CellValue.FIVE, CellValue.TWO, CellValue.EIGHT, CellValue.THREE, CellValue.NINE, CellValue.SEVEN}, {CellValue.SIX, CellValue.NINE, CellValue.EIGHT, CellValue.TWO, CellValue.FOUR, CellValue.ONE, CellValue.SEVEN, CellValue.FIVE, CellValue.THREE}, {CellValue.SEVEN, CellValue.ONE, CellValue.THREE, CellValue.SIX, CellValue.NINE, CellValue.FIVE, CellValue.EIGHT, CellValue.FOUR, CellValue.TWO}, {CellValue.FOUR, CellValue.TWO, CellValue.FIVE, CellValue.THREE, CellValue.EIGHT, CellValue.SEVEN, CellValue.NINE, CellValue.SIX, CellValue.ONE}};
        CellValue[][] testGridInvalid = { {CellValue.EIGHT, CellValue.EIGHT, CellValue.TWO, CellValue.ONE, CellValue.THREE, CellValue.FOUR, CellValue.SIX, CellValue.SEVEN, CellValue.NINE}, {CellValue.NINE, CellValue.SEVEN, CellValue.ONE, CellValue.EIGHT, CellValue.FIVE, CellValue.SIX, CellValue.TWO, CellValue.THREE, CellValue.FOUR}, {CellValue.THREE, CellValue.FOUR, CellValue.SIX, CellValue.NINE, CellValue.SEVEN, CellValue.TWO, CellValue.ONE, CellValue.EIGHT, CellValue.FIVE}, {CellValue.TWO, CellValue.FIVE, CellValue.NINE, CellValue.SEVEN, CellValue.SIX, CellValue.THREE, CellValue.FOUR, CellValue.ONE, CellValue.EIGHT}, {CellValue.EIGHT, CellValue.THREE, CellValue.SEVEN, CellValue.FOUR, CellValue.ONE, CellValue.NINE, CellValue.FIVE, CellValue.TWO, CellValue.SIX}, {CellValue.ONE, CellValue.SIX, CellValue.FOUR, CellValue.FIVE, CellValue.TWO, CellValue.EIGHT, CellValue.THREE, CellValue.NINE, CellValue.SEVEN}, {CellValue.SIX, CellValue.NINE, CellValue.EIGHT, CellValue.TWO, CellValue.FOUR, CellValue.ONE, CellValue.SEVEN, CellValue.FIVE, CellValue.THREE}, {CellValue.SEVEN, CellValue.ONE, CellValue.THREE, CellValue.SIX, CellValue.NINE, CellValue.FIVE, CellValue.EIGHT, CellValue.FOUR, CellValue.TWO}, {CellValue.FOUR, CellValue.TWO, CellValue.FIVE, CellValue.THREE, CellValue.EIGHT, CellValue.SEVEN, CellValue.NINE, CellValue.SIX, CellValue.ONE}};
        
        System.out.println("Setting a to testGridValidIncomplete, b to testGridValidComplete, and c to testGridInvalid.");
        Board a = new Board(testGridValidIncomplete);
        Board b = new Board(testGridValidComplete);
        Board c = new Board(testGridInvalid);
        
        System.out.println("Board B:Should be valid and complete.  isValid()? " + b.isValid() + " isComplete()? " + b.isCompleted());
        System.out.println("Board A:Should be valid and incomplete.  isValid()? " + a.isValid() + " isComplete()? " + a.isCompleted());
        System.out.println("Board C:Should be invalid and incomplete.  isValid()? " + c.isValid() + " isComplete()? " + c.isCompleted());
        
        CellValue[][] returnCopyOfGridB = b.returnCopyOfGrid();
        //System.out.println("Printing grid copy of board b");
        b.printGrid(returnCopyOfGridB);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("Setting spot (0,0) in board b to value 4.");
        b.setValueAtLoc(new Location(0, 0), CellValue.FOUR);
        //System.out.println("Printing grid copy of board b again");
        returnCopyOfGridB = b.returnCopyOfGrid();
        b.printGrid(returnCopyOfGridB);
        

    
    }
    
    
    
}
