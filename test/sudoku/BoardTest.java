/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Dec 7, 2015
 * Time: 2:37:24 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku
 * File: BoardTest
 * Description:
 *
 * ****************************************
 */
package sudoku;

import SudokuBoardGenerator.SudokuBoardGenerator;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ia005
 */
public class BoardTest {

    public BoardTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getRow method, of class Board.
     */
    @Test
    public void testGetRow() {
        System.out.println("getRow");
        SudokuBoardGenerator sudoku = new SudokuBoardGenerator();
        sudoku.chooseGrid();
        int rownum = 1;
        Board instance = new Board(sudoku.getIntGrid());
        Row expResult = new Row(1, instance);
        Row result = instance.getRow(rownum);
        assertEquals(expResult.getValueAtIndex(0), result.getValueAtIndex(0));
        assertEquals(expResult.getValueAtIndex(1), result.getValueAtIndex(1));
        assertEquals(expResult.getValueAtIndex(2), result.getValueAtIndex(2));
    }

    /**
     * Test of getCol method, of class Board.
     */
    @Test
    public void testGetCol() {
        System.out.println("getCol");
        SudokuBoardGenerator sudoku = new SudokuBoardGenerator();
        sudoku.chooseGrid();
        int colnum = 1;
        Board instance = new Board(sudoku.getIntGrid());
        Col expResult = new Col(1, instance);
        Col result = instance.getCol(colnum);
        assertEquals(expResult.getValueAtIndex(0), result.getValueAtIndex(0));
        assertEquals(expResult.getValueAtIndex(1), result.getValueAtIndex(1));
        assertEquals(expResult.getValueAtIndex(2), result.getValueAtIndex(2));
    }

    /**
     * Test of getSquare method, of class Board.
     */
    @Test
    public void testGetSquare() {
        System.out.println("getSquare");

        SudokuBoardGenerator sudoku = new SudokuBoardGenerator();
        sudoku.chooseGrid();
        int squarenum = 1;
        Board instance = new Board(sudoku.getIntGrid());
        Square expResult = new Square(1, instance);
        Square result = instance.getSquare(squarenum);
        assertEquals(expResult.getValueAtIndex(0), result.getValueAtIndex(0));
        assertEquals(expResult.getValueAtIndex(1), result.getValueAtIndex(1));
        assertEquals(expResult.getValueAtIndex(2), result.getValueAtIndex(2));
    }

    /**
     * Test of createCellValueFromInt method, of class Board.
     */
    @Test
    public void testCreateCellValueFromInt() {
        System.out.println("createCellValueFromInt");
        int value = 0;
        Board instance = new Board();
        CellValue expResult = CellValue.EMPTY;
        CellValue result = instance.createCellValueFromInt(value);
        assertEquals(expResult, result);

    }

    /**
     * Test of clearBoard method, of class Board.
     */
    @Test
    public void testClearBoard() {
        System.out.println("clearBoard");
        SudokuBoardGenerator sudoku = new SudokuBoardGenerator();
        Board instance = new Board(sudoku.getIntGrid());
        instance.clearBoard();
        CellValue expResult = CellValue.EMPTY;
        for (int i = 0; i < instance.getIntGrid().length; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(expResult, instance.getValueAtLoc(
                             new Location(i, j)));
            }
        }

    }

    /**
     * Test of clone method, of class Board.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        SudokuBoardGenerator sudoku = new SudokuBoardGenerator();
        Board instance = new Board(sudoku.getIntGrid());
        Board instance1 = new Board(instance.returnCopyOfGrid());
        Board result = instance1.clone();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(instance1.getValueAtLoc(new Location(i, j)),
                             result.getValueAtLoc(new Location(i, j)));
            }
        }

    }

    /**
     * Test of checkGrid method, of class Board.
     */
    @Test
    public void testCheckGrid() {
        System.out.println("checkGrid");
        int[][] grid = {{10, 5, 3}, {0, 15, 6}, {0, 1, 0}};
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.checkGrid(grid);
        assertEquals(expResult, result);

    }

    /**
     * Test of equal method, of class Board.
     */
    @Test
    public void testEqual() {
        System.out.println("equal");
        SudokuBoardGenerator sudoku = new SudokuBoardGenerator();
        sudoku.chooseGrid();
        Board old = new Board(sudoku.getIntGrid());

        Board instance = sudoku.generateBoard();
        boolean expResult = false;
        boolean result = instance.equal(old);
        assertEquals(expResult, result);

    }

}
