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
 * File: Square
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author ajn008
 */
public class Square extends BoardSegment {

    private int squareNum;
    private Board b;
    private Location origin;

    public Square(int squareNum, Board b) {
        this.squareNum = squareNum;
        this.b = b;
        this.origin = this.getOrigin();
    }

    private Location getOrigin() {
        Location[] listOfSquareOrigins = {new Location(0, 0), new Location(3, 0), new Location(
                                          6, 0), new Location(0, 3), new Location(
                                          3, 3), new Location(6, 3), new Location(
                                          0, 6), new Location(3, 6), new Location(
                                          6, 6)};
        return listOfSquareOrigins[this.squareNum];
    }

    //helper to getValueAtIndex
    //returns list of Location objects present in this square
    private Location[] getListOfLocationsInSquare() {
        int origX = this.origin.getX(), origY = this.origin.getY();

        Location[] listOfLocations = {this.origin, new Location(origX + 1, origY), new Location(
                                      origX + 2, origY), new Location(origX,
                                                                      origY + 1), new Location(
                                      origX + 1, origY + 1), new Location(
                                      origX + 2, origY + 1), new Location(origX,
                                                                          origY + 2), new Location(
                                      origX + 1, origY + 2), new Location(
                                      origX + 2, origY + 2)};
        return listOfLocations;

    }

    @Override
    public CellValue getValueAtIndex(int index) {
        Location desiredLoc = this.getListOfLocationsInSquare()[index];
        return this.b.getValueAtLoc(desiredLoc);
    }

    public boolean getEditableAtIndex(int index) {
        return b.getEditabilityAtLoc(this.getListOfLocationsInSquare()[index]);
    }

    public void setValueAtIndex(int index, CellValue cellVal) {
        b.setValueAtLoc(this.getListOfLocationsInSquare()[index], cellVal);
    }
}
