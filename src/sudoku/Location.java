/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 13, 2015
 * Time: 09:42:00 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku
 * File: Location
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author andrewnyhus
 */
public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return int representation of X coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return int representation of Y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * @param obj
     * @return boolean as to whether this Location is equal to the other.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    /**
     * @return String representation of this Location object
     */
    @Override
    public String toString(){
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}
