/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2015
*
* Name: Dunni Adenuga
               Tim Woodford
               Andrew Nyhus
* Date: Nov 29, 2015
* Time: 10:34:35 PM
*
* Project: csci205FinalProject
* Package: sudoku.solvers.cga
* File: CGASudokuState
* Description:
*
* ****************************************
 */
package sudoku.solvers.cga;

import sudoku.Board;
import sudoku.solvers.sa.SASudokuState;

/**
 * Cultural Genetic Algorithm state for sudoku. Basically a wrapper around
 * <code>SASudokuState</code> with extra methods for breeding and
 * initialization. Based on algorithm presented in paper from arXiv.
 *
 * @author Tim Woodford
 * @see http://arxiv.org/pdf/0805.0697v1.pdf
 */
public class CGASudokuState implements CGAState {

    private final SASudokuState innerState;

    public CGASudokuState(Board board) {
        this(new SASudokuState(board));
    }

    private CGASudokuState(SASudokuState state) {
        innerState = state;
    }

    @Override
    public double fitness() {
        return innerState.evaluate();
    }

    @Override
    public CGAState mutate() {
        return new CGASudokuState((SASudokuState) innerState.randomize());
    }

    @Override
    public CGAState breed(CGAState other) {
        return this; //TODO
    }

    @Override
    public CGAState initialize() {
        Board newBoard = innerState.getBoard().clone();
        SASudokuState state = new SASudokuState(newBoard);
        state.invalidFill();
        return new CGASudokuState(state);
    }

    public Board getBoard() {
        return innerState.getBoard();
    }
}
