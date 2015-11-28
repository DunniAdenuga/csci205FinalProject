/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 18, 2015
 * Time: 8:51:04 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku.solvers.cga
 * File: CGAEvolver
 * Description:
 *
 * ****************************************
 */
package sudoku.solvers.cga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Tim Woodford
 */
public class CGAEvolver<T extends CGAState> {
    /**
     * The number individuals to evaluate in each round
     */
    private int searchSize = 50;

    /**
     * The number of individuals to evaluate in tournament selection
     */
    private int tournamentSize = 25;

    public CGAState<T> evolve(CGAState<T> input) {
        ArrayList<CGAState<T>> individuals = new ArrayList<>(getSearchSize());
        for (int i = 0; i < getSearchSize(); i++) {
            individuals.add(input.initialize());
        }
        Collections.shuffle(individuals);
        List<CGAState<T>> tournament
                          = individuals.subList(0, getTournamentSize());
        CGAState<T> parentOne = tournament.stream().max(
                (x, y) -> (int) Math.signum(x.fitness() - y.fitness()))
                .orElseThrow(() -> new RuntimeException());
    }

    /**
     * Get the number individuals to evaluate in each round
     *
     * @return The number individuals to evaluate in each round
     */
    public int getSearchSize() {
        return searchSize;
    }

    /**
     * Set the number individuals to evaluate in each round
     *
     * @param searchSize The number individuals to evaluate in each round
     */
    public void setSearchSize(int searchSize) {
        this.searchSize = searchSize;
    }

    /**
     * Get the number of individuals to evaluate in tournament selection
     *
     * @return The number of individuals to evaluate in tournament selection
     */
    public int getTournamentSize() {
        return tournamentSize;
    }

    /**
     * Set the number of individuals to evaluate in tournament selection
     *
     * @param tournamentSize The number of individuals to evaluate in tournament
     * selection
     */
    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

}
