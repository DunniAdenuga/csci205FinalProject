/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 13, 2015
 * Time: 12:07:33 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku.view
 * File: GridPanel
 * Description:
 *
 * ****************************************
 */
package sudoku.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import sudoku.Board;
import sudoku.CellValue;
import sudoku.Location;
import sudoku.controller.SudokuController;

/**
 *
 * @author ajn008
 */
public class GridPanel extends javax.swing.JPanel{
    private Cell[][] sudokuCells;
    //private Window window;

    public GridPanel(/*Window w*/){
        //this.window = w;

        this.sudokuCells = new Cell[Board.BOARD_SIZE][Board.BOARD_SIZE];
        setLayout(new GridLayout(Board.BOARD_SIZE, Board.BOARD_SIZE));
        
        
        Border thinBorder = BorderFactory.createLineBorder(Color.black, 1);
        Border thickBorder = BorderFactory.createLineBorder(Color.black, 4);
        setBorder(thickBorder);

    
    }
    
    
    //Editability related functions
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-

    
    /**This function sets the editability of the Cell
     * at Location loc.
     * @param editable
     * @param loc 
     */
    public void setEditabilityAtLoc(boolean editable, Location loc){
        this.sudokuCells[loc.getX()][loc.getY()].setEditable(editable);        
    }
    
    /**
     * Find out if the cell at Location loc is editable.
     * @param loc
     * @return boolean representing editability of the cell at loc.
     */
    public boolean getEditabilityAtLoc(Location loc){
        return this.sudokuCells[loc.getX()][loc.getY()].isEditable();
    }
    
    /**This function makes all of the cells in the grid not editable.
     * This is useful when we want the user to select an option for playing a new game.
     * This could also possibly be useful in between the time that the user selects
     * a difficulty, and the time when the board is actually loaded.
     */
    public void setAllCellsNotEditable(){
        for(int x = 0; x < Board.BOARD_SIZE; x++){    
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                this.sudokuCells[x][y].setEditable(false);
            }        
        }
    }

    
    /**
     * This function frees up the board and makes all cells editable.
     */
    public void setAllCellsEditable(){
        for(int x = 0; x < Board.BOARD_SIZE; x++){    
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                this.sudokuCells[x][y].setEditable(true);
            }
        
        }
    }    
    
    /**
     * This function uses an ArrayList of Location objects to 
     * set the proper cells as editable and the rest as not editable
     * this function also adds a light gray background to all cells 
     * that are not editable, and an offwhite background to the ones that are.
     * @param arrayOfLocsToBeEditable 
     */
    public void allowUserToEditCellsInArray(ArrayList<Location> arrayOfLocsToBeEditable){
        //setting all cells not editable
        this.setAllCellsNotEditable();
        this.paintAllCellsWithColor(Color.LIGHT_GRAY);
        //iterating through list of locations that should be editable
        for(Location loc : arrayOfLocsToBeEditable){
            //makes these locations editable
            this.setEditabilityAtLoc(true, loc);
            this.paintCellWithColorAtLoc(SudokuController.offwhite, loc);
        
        }
        
    }    
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-
    

    
    //Value related functions
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-
    
    
    /**
     * Sets given CellValue at Location loc
     * @param cellValue
     * @param loc 
     */
    public void setCellValueAtLoc(CellValue cellValue, Location loc){
        this.sudokuCells[loc.getX()][loc.getY()].setCellFieldValue(cellValue);
    }
    
    
    /**
     * Sets empty CellValue objects at all cells.
     */
    public void clearValuesInFields(){
        for(int x = 0; x < Board.BOARD_SIZE; x++){        
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                this.sudokuCells[x][y].setCellFieldValue(CellValue.EMPTY);
            }        
        }
    }
    

    /**This method gathers all of the CellValues from
     * the grid.
     * 
     * @return It then returns a 2d Array of CellValue objects representing the grid.
     */
    public CellValue[][] getCellValueArray(){
        CellValue[][] arrayToReturn = new CellValue[Board.BOARD_SIZE][Board.BOARD_SIZE];
        for(int x = 0; x < Board.BOARD_SIZE; x++){
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                arrayToReturn[x][y] = this.sudokuCells[x][y].getCellValue();
            }
        }
        
        return arrayToReturn;
    }
    
    /**
     * Returns a 2d array of int objects representing the values of the grid.
     * @return 2d int array
     */
    public int[][] getIntArray(){
        int[][] arrayToReturn = new int[Board.BOARD_SIZE][Board.BOARD_SIZE];
        
        for(int x = 0; x < Board.BOARD_SIZE; x++){
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                arrayToReturn[x][y] = this.sudokuCells[x][y].getCellValue().getValue();
            }
        }
        return arrayToReturn;
    }
        
    
    /**
     ** Loads in the values from a 2d CellValue array into the correct text fields.
     * This function also then makes sure that the proper Cells are editable and not editable.
     * @param cellValueGrid
     * @return ArrayList of Location objects that are editable to the user, 
     * the rest of them are assumed to be not editable 
     */
    public ArrayList<Location> setGridWith2DArray(CellValue[][] cellValueGrid){
        ArrayList<Location> arrayListOfEmptyLocs = new ArrayList(); 
        for(int x = 0; x < Board.BOARD_SIZE; x++){
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                CellValue currentValue = cellValueGrid[x][y];
                this.sudokuCells[x][y].setCellFieldValue(currentValue);
                
                if(currentValue.isEmpty()){
                    arrayListOfEmptyLocs.add(new Location(x, y));
                }
            }
        }        
        this.allowUserToEditCellsInArray(arrayListOfEmptyLocs);
        return arrayListOfEmptyLocs;
    }    
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-
    
    
    
    

    //Painting functions
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-
    
    
    /**
     * Paints every cell in the boards with a given Color,
     * This is useful in several places including but not limited to the allowUserToEditCellsInArray function.
     * @param color 
     */
    public void paintAllCellsWithColor(Color color){
        for(int x = 0; x < Board.BOARD_SIZE; x++){
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                this.paintCellWithColorAtLoc(color, new Location(x, y));
            }        
        }        
    }
    
    /**
     * This function paints the Cells that correspond to the Location objects
     * in the Array locsToPaint with Color color. 
     * @param color
     * @param locsToPaint 
     */
    public void paintCellsInLocArrayWithColor(Color color, Location[] locsToPaint){
        for(Location loc: locsToPaint){
            if(this.getEditabilityAtLoc(loc)){
                this.paintCellWithColorAtLoc(color, loc);
            }            
        }        
    }

    
    /**
     * This function takes the given Color and uses it to paint the border outside of the grid,
     * giving the illusion to the user that the background of the whole window is being changed.
     * @param color 
     */
    public void paintOuterBorderWithColor(Color color){
        this.setBorder(BorderFactory.createLineBorder(color,40));        
    }

    
    /**
     * This function paints the Cell that corresponds to the given Location
     * with the given Color
     * @param color
     * @param loc 
     */
    public void paintCellWithColorAtLoc(Color color, Location loc){
        this.sudokuCells[loc.getX()][loc.getY()].setCellBackgroundColor(color);
    }
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-
    
    
    
    
     
    /**
     * Sets cell object to corresponding spot in this.sudokuCells for the Location object provided
     * @param loc
     * @param cell 
     */
    public void setCellAtLoc(Location loc, Cell cell){
        this.sudokuCells[loc.getX()][loc.getY()] = cell;
    }
    
    
    public Cell getCellAtLoc(Location loc){
        return this.sudokuCells[loc.getX()][loc.getY()];
    }
    
}
