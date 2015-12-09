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

import javax.swing.SwingUtilities;
import sudoku.controller.SudokuController;

public class main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuController controller = new SudokuController();
        });

    }

}
