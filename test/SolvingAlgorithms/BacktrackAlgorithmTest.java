/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Dec 7, 2015
 * Time: 5:57:03 PM
 *
 * Project: csci205FinalProject
 * Package: SolvingAlgorithms
 * File: BacktrackAlgorithmTest
 * Description:
 *
 * ****************************************
 */
package SolvingAlgorithms;

import SudokuBoardGenerator.SudokuBoardGenerator;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sudoku.Board;

/**
 *
 * @author ia005
 */
public class BacktrackAlgorithmTest {
    int[][] grid = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
                    {5, 2, 0, 0, 0, 0, 0, 0, 0},
                    {0, 8, 7, 0, 0, 0, 0, 3, 1},
                    {0, 0, 3, 0, 1, 0, 0, 8, 0},
                    {9, 0, 0, 8, 6, 3, 0, 0, 5},
                    {0, 5, 0, 0, 9, 0, 6, 0, 0},
                    {1, 3, 0, 0, 0, 0, 2, 5, 0},
                    {0, 0, 0, 0, 0, 0, 0, 7, 4},
                    {0, 0, 5, 2, 0, 6, 3, 0, 0}};
    Board newBoard1 = new Board(grid);

    BacktrackAlgorithm backtrack = new BacktrackAlgorithm(grid);

    public BacktrackAlgorithmTest() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of checkRow method, of class BacktrackAlgorithm.
     */
    @Test
    public void testCheckRow() {
        System.out.println("checkRow");
        int row = 1;
        int num = 1;
        boolean expResult = true;
        boolean result = backtrack.checkCol(row, num);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkCol method, of class BacktrackAlgorithm.
     */
    @Test
    public void testCheckCol() {
        System.out.println("checkCol");
        int col = 1;
        int num = 1;
        boolean expResult = true;
        boolean result = backtrack.checkCol(col, num);
        assertEquals(expResult, result);

    }

    /**
     * Test of checkBox method, of class BacktrackAlgorithm.
     */
    @Test
    public void testCheckBox() {
        System.out.println("checkBox");
        int row = 5;
        int col = 3;
        int num = 8;
        boolean expResult = false;
        boolean result = backtrack.checkBox(row, col, num);
        assertEquals(expResult, result);

    }

    /**
     * Test of solveBoard method, of class BacktrackAlgorithm.
     */
    @Test
    public void testSolveBoard_Board() {
        System.out.println("solveBoard");
        SudokuBoardGenerator sudoku = new SudokuBoardGenerator();
        Board input = sudoku.generateBoard();
        BacktrackAlgorithm instance = new BacktrackAlgorithm();
        boolean expResult = true;
        Board result = instance.solveBoard(input);
        assertEquals(expResult, result.checkGrid(result.getIntGrid()));

    }

}
