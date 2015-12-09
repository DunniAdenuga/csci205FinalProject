/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Dec 6, 2015
 * Time: 3:57:54 PM
 *
 * Project: csci205FinalProject
 * Package: SudokuBoardGenerator
 * File: SudokuBoardGeneratorTest
 * Description:
 *
 * ****************************************
 */
package SudokuBoardGenerator;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import sudoku.Board;

/**
 *
 * @author ia005
 */
public class SudokuBoardGeneratorTest {

    public SudokuBoardGeneratorTest() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of chooseGrid method, of class SudokuBoardGenerator.
     */
    @Test
    public void testChooseGrid() {
        System.out.println("chooseGrid");
        SudokuBoardGenerator instance = new SudokuBoardGenerator();
        instance.chooseGrid();
        assertNotNull(instance.getIntGrid());
    }

    /**
     * Test of groupSwapRowCol method, of class SudokuBoardGenerator.
     */
    @Test
    public void testGroupSwapRowCol() {
        System.out.println("groupSwapRowCol");
        SudokuBoardGenerator instance = new SudokuBoardGenerator();
        instance.chooseGrid();
        Board old = new Board(instance.getIntGrid());
        instance.groupSwapRowCol();

        assertFalse(old.equals(new Board(instance.getIntGrid())));

    }

    /**
     * Test of wholeGroupSwaps method, of class SudokuBoardGenerator.
     */
    @Test
    public void testWholeGroupSwaps() {
        System.out.println("wholeGroupSwaps");
        SudokuBoardGenerator instance = new SudokuBoardGenerator();
        instance.chooseGrid();
        Board old = new Board(instance.getIntGrid());
        instance.wholeGroupSwaps();
        assertFalse(old.equals(new Board(instance.getIntGrid())));
    }

    /**
     * Test of transpose method, of class SudokuBoardGenerator.
     */
    @Test
    public void testTranspose() {
        System.out.println("transpose");
        SudokuBoardGenerator instance = new SudokuBoardGenerator();
        instance.chooseGrid();
        Board old = new Board(instance.getIntGrid());
        instance.transpose();
        assertFalse(old.equals(new Board(instance.getIntGrid())));
    }

    /**
     * Test of eraser method, of class SudokuBoardGenerator.
     */
    @Test
    public void testEraser() {
        System.out.println("strikeOutCells");
        SudokuBoardGenerator instance = new SudokuBoardGenerator();
        instance.chooseGrid();
        Board old = new Board(instance.getIntGrid());
        instance.eraser(56);
        assertFalse(old.equals(new Board(instance.getIntGrid())));
    }

    /**
     * Test of getType method, of class SudokuBoardGenerator.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        SudokuBoardGenerator instance = new SudokuBoardGenerator(2);
        String expResult = "Board is difficult";
        String result = instance.getType();
        assertEquals(expResult, result);

    }

}
