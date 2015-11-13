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
public class Col {

    private CellValue[] arrOfColContents;
    private int[] colValues;

    public Col(CellValue[] listOfColContents) {
        this.arrOfColContents = listOfColContents;
    }

    public Col() {
        this.arrOfColContents = new CellValue[9];
        this.colValues = new int[9];
    }

    public Col(int[] colValues) {
        this.colValues = colValues.clone();
    }

    /**
     * Sets specified value at a specified index in this.arrOfColContents
     *
     * @param newValue
     * @param index
     */
    public void setColWithValueAtIndex(CellValue newValue, int index) {
        this.arrOfColContents[index] = newValue;
    }

    /**
     * This method determines whether or not a col is valid. If the col has no
     * duplicates, the col is valid.
     *
     * @return a boolean value representing the validity of the Col
     */
    public boolean isValid() {
        //we must determine if duplicates exist. If they do, the col is invalid.
        //Since we know that this.arrOfColContents when untouched is an array
        //with nine elements, all of cell zero, we can allow multiple
        //occurrences of 0 in the array, and will not consider a zero cell as a duplicate

        ArrayList<CellValue> listOfValuesFound = new ArrayList();

        for (CellValue cell : this.arrOfColContents) {
            //
            if (!cell.isEmpty()) {
                if (listOfValuesFound.contains(cell)) {
                    return false;
                    //a duplicate exists in the col, and therefore the col is invalid
                } else {
                    listOfValuesFound.add(cell);
                }

            }//no need for an else here, if cell is zero, we leave it alone

        }
        return true;
    }

    /**
     * The purpose of this method is to determine if the col is completed, by
     * making sure that every value from 1-9 is found, and that there are no
     * empty spaces.
     *
     * @return a boolean to represent whether or not the col is completed.
     */
    public boolean isCompleted() {
        if (!this.isValid()) {
            return false;
        }

        //each boolean in this array corresponds to a number 1-9, based on it's index which ranges from 0-8.
        boolean[] indexWasFoundInCol = {false, false, false, false, false, false, false, false, false};

        //iterates through each CellValue in this.arrOfColContents
        //if an empty cell is found, we return false
        //For every value 1-9 that is found, we set it's corresponding
        //boolean value in indexWasFoundInRow to be true.
        for (CellValue cell : this.arrOfColContents) {
            if (cell.isEmpty()) {
                return false;
            }

            indexWasFoundInCol[cell.getValue() - 1] = true;
        }

        //then, we iterate through indexWasFoundInCol
        //and if a single element is false, then the col does not contain all
        for (boolean numFoundInCol : indexWasFoundInCol) {
            if (!numFoundInCol) {
                return false;
            }
        }
        return true;

    }

}
