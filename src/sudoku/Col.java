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
 * File: Col
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author ajn008
 */
public class Col extends BoardSegment {

    private int colNum;
    private Board b;

    public Col(int colNum, Board b) {
        this.colNum = colNum;
        this.b = b;
    }

    /**
     * @param index
     * @return CellValue at given index within the Col.
     */
    @Override
    public CellValue getValueAtIndex(int index) {
        return this.b.getValueAtLoc(new Location(this.colNum, index));

    }

    /**
     * This method returns a Location[] with all Locations in the Col
     *
     * @return Location[]
     */
    @Override
    public Location getLocationInSquare(int index) {
        return new Location(this.colNum, index);
    }

    /**
     * This method returns a Location[] with all Locations in the Col
     *serves as helper function to updateBoardColors
     * @return Location[]
     */
    @Override
    public Location[] getArrayOfLocationsInSegment() {
        Location[] arrayOfLocations = new Location[Board.BOARD_SIZE];
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            arrayOfLocations[i] = new Location(this.colNum, i);
        }
        return arrayOfLocations;
    }

}
