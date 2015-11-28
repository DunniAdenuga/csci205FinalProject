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
 * File: Row
 * Description:
 *
 * ****************************************
 */
package sudoku;

/**
 *
 * @author ajn008
 */
public class Row extends BoardSegment {

    private int rowNum;
    private Board b;

    public Row(int rowNum, Board b) {
        this.rowNum = rowNum;
        this.b = b;
    }

    /**
     * @param index
     * @return CellValue at given index within the Row.
     */
    @Override
    public CellValue getValueAtIndex(int index) {
        return this.b.getValueAtLoc(new Location(index, this.rowNum));

    }

    /**
     * This method returns a Location[] with all Locations in the Row
     *
     * @return Location[]
     */
    @Override
    public Location[] getArrayOfLocationsInSegment() {
        Location[] arrayOfLocations = new Location[Board.BOARD_SIZE];
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            arrayOfLocations[i] = new Location(i, this.rowNum);
        }
        return arrayOfLocations;
    }

}
