/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;

/**
 *
 * @author andrewnyhus
 */
public class Row {

    private CellValue[] arrOfRowContents;
    private int[] rowValues;

    public Row(CellValue[] listOfRowContents) {
        this.arrOfRowContents = listOfRowContents;
    }

    public Row(int[] rowNum) {
        rowValues = rowNum.clone();
    }

    public Row() {
        this.arrOfRowContents = new CellValue[9];
        this.rowValues = new int[9];
    }

    /**
     * Sets specified value at a specified index in this.arrOfRowContents
     *
     * @param newValue
     * @param index
     */
    public void setRowWithValueAtIndex(CellValue newValue, int index) {
        this.arrOfRowContents[index] = newValue;
    }

    /**
     * This method determines whether or not a row is valid. If the row has no
     * duplicates, the row is valid.
     *
     * @return a boolean value representing the validity of the Row
     */
    public boolean isValid() {
        //we must determine if duplicates exist. If they do, the row is invalid.
        //Since we know that this.arrOfRowContents when untouched is an array
        //with nine elements, all of cell zero, we can allow multiple
        //occurrences of 0 in the array, and will not consider a zero cell as a duplicate

        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        for (CellValue cell : this.arrOfRowContents) {

            if (!cell.isEmpty()) {
                if (listOfValuesFound.contains(cell)) {
                    return false;
                    //a duplicate exists in the row, and therefore the row is invalid
                } else {
                    listOfValuesFound.add(cell);
                }

            }//no need for an else here, if cell is zero, we leave it alone

        }
        return true;
    }

    /**
     * The purpose of this method is to determine if the row is completed, by
     * making sure that every value from 1-9 is found, and that there are no
     * empty spaces.
     *
     * @return a boolean to represent whether or not the row is completed.
     */
    public boolean isCompleted() {
        if (!this.isValid()) {
            return false;
        }

        //each boolean in this array corresponds to a number 1-9, based on it's index which ranges from 0-8.
        boolean[] indexWasFoundInRow = {false, false, false, false, false, false, false, false, false};

        //iterates through each CellValue in this.arrOfRowContents
        //if an empty cell is found, we return false
        //For every value 1-9 that is found, we set it's corresponding
        //boolean value in indexWasFoundInRow to be true.
        for (CellValue cell : this.arrOfRowContents) {

            if (cell.isEmpty()) {
                return false;
            }

            indexWasFoundInRow[cell.getValue() - 1] = true;
        }

        //then, we iterate through indexWasFoundInRow
        //and if a single element is false, then the row does not contain all
        for (boolean numFoundInRow : indexWasFoundInRow) {
            if (!numFoundInRow) {
                return false;
            }
        }
        return true;

    }

}
