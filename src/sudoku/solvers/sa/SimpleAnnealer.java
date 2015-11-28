/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 10, 2015
 * Time: 11:30:58 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.sa
 * File: SimpleAnnealer
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.sa;

import java.util.Random;

/**
 * A simulated annealing processor that does not use parallelism.
 *
 * @author Tim Woodford
 * @param <T> The specific state type
 */
public class SimpleAnnealer<T extends SAState> {

    /**
     * The current SA temperature
     */
    private double temp;

    private final double initTemp;

    /**
     * The cut-off temperature at which the algorithm will stop running
     */
    private final double minTemp;

    /**
     * PRNG for stochastic parts of algorithm
     */
    private final Random rnd;

    /**
     * The factor by which the temperature is multiplied after each iteration
     */
    private final double coolingFactor;

    /**
     * Create a new simulated annealing scheme.
     *
     * @param initTemp The initial temperature value
     * @param minTemp The cut-off temperature at which the algorithm will stop
     * running
     * @param coolingFactor The factor by which the temperature is multiplied
     * after each iteration
     */
    public SimpleAnnealer(double initTemp, double minTemp, double coolingFactor) {
        this.initTemp = initTemp;
        this.minTemp = minTemp;
        this.coolingFactor = coolingFactor;
        this.rnd = new Random();
    }

    public T anneal(T state) {
        temp = initTemp;
        // "best" is used loosely here - this isn't a hill-climbing algorithm
        T best = state;
        double bestValue = state.evaluate();
        while (temp > minTemp) {
            state = (T) state.randomize();
            if (checkState(bestValue, state.evaluate())) {
                best = state;
                bestValue = state.evaluate();
            }
            updateTemp();
        }
        return best;
    }

    /**
     * Cool the temperature. The method used here is exponential multiplicative
     * cooling, but other methods may work better.
     *
     * @see
     * http://what-when-how.com/artificial-intelligence/a-comparison-of-cooling-schedules-for-simulated-annealing-artificial-intelligence/
     */
    protected void updateTemp() {
        temp *= coolingFactor;
    }

    /**
     * Check if the new state should replace the old one.
     *
     * @param currentBest The current best state evaluation (higher is better)
     * @param possible The evaluation value for the potential new state
     * @return
     */
    protected boolean checkState(double currentBest, double possible) {
        return (currentBest < possible) ? true : Math.
                exp(-(possible - currentBest) / temp) < rnd.nextDouble();
    }

    /**
     * Set the current SA temperature
     *
     * @param temp The current SA temperature
     */
    protected void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * Get the current SA temperature
     *
     * @return The current SA temperature
     */
    public double getTemp() {
        return temp;
    }

    /**
     * Get the cut-off temperature at which the algorithm will stop running
     *
     * @return The cut-off temperature at which the algorithm will stop running
     */
    public double getMinTemp() {
        return minTemp;
    }

    /**
     * Get the factor by which the temperature is multiplied after each
     * iteration
     *
     * @return The factor by which the temperature is multiplied after each
     * iteration
     */
    public double getCoolingFactor() {
        return coolingFactor;
    }
}
