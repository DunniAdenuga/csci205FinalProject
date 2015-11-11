/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 10, 2015
 * Time: 11:31:35 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.sa
 * File: SAState
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.sa;

/**
 * Simulated annealing state - includes methods to randomly change state and to
 * evaluate the state.
 *
 * Before attempting to implement an <code>SAState</code>, make sure you
 * understand how simulated annealing works.
 *
 * @see
 * http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
 * @see
 * http://www.mit.edu/~dbertsim/papers/Optimization/Simulated%20annealing.pdf
 * @author Tim Woodford
 */
public interface SAState {

    /**
     * Determine how good the result is - higher numbers are better
     *
     * @return The variable that will be optimized
     */
    double evaluate();

    /**
     * Copy the current state and randomly change part of it.
     *
     * To work properly, the return value must match the type parameter of the
     * annealer.
     *
     * @return The modified state
     */
    SAState randomize();
}
