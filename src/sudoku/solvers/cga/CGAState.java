/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 18, 2015
 * Time: 8:48:07 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.cga
 * File: CGAState
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.cga;

/**
 *
 * @author tww014
 * @param <T>
 */
public interface CGAState {

    /**
     * Determine how good the result is - higher numbers are better
     *
     * @return The variable that will be optimized
     */
    double fitness();

    /**
     * Conduct a single mutation on the current state.
     *
     * @return The mutated CGA state
     */
    CGAState mutate();

    /**
     * Breed this state with another state
     *
     * @param other The state to breed with
     * @return The offspring
     */
    CGAState breed(CGAState other);

    /**
     * Create a random state in the search space
     *
     * @return
     */
    CGAState initialize();
}
