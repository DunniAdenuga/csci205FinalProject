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

    private final int squareNum;
    private final Board b;
    private final Location origin;

    public static final Location[] listOfSquareOrigins = {
        new Location(0, 0),
        new Location(3, 0),
        new Location(6, 0),
        new Location(0, 3),
        new Location(3, 3),
        new Location(6, 3),
        new Location(0, 6),
        new Location(3, 6),
        new Location(6, 6)};

    public Square(int squareNum, Board b) {
        this.squareNum = squareNum;
        this.b = b;
        this.origin = this.getOrigin();
    }

    /**
     * Helper function for getArrayOfLocationsInSegment
     *
     * @return Location of the top left corner of the Square
     */
    private Location getOrigin() {
        return listOfSquareOrigins[this.squareNum];
    }

    /**
     * @param index
     * @return CellValue at given index within the Square.
     */
    @Override
    public CellValue getValueAtIndex(int index) {
        Location desiredLoc = this.getArrayOfLocationsInSegment()[index];
        return this.b.getValueAtLoc(desiredLoc);
    }

    /**
     * This method returns a Location[] with all Locations in the Square
     *
     * @return Location[]
     */
    @Override
    public Location[] getArrayOfLocationsInSegment() {
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

    public boolean getEditabilityAtIndex(int index) {
        return b.getEditabilityAtLoc(this.getArrayOfLocationsInSegment()[index]);
    }

    public void setValueAtIndex(int index, CellValue cellVal) {
        b.setValueAtLoc(this.getArrayOfLocationsInSegment()[index], cellVal);
    }
}
