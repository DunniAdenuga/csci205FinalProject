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
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import sudoku.Board;
import sudoku.CellValue;
import sudoku.Location;
import sudoku.controller.SudokuController;
import sudoku.view.Cell.JTextFieldLimit;

/**
 *
 * @author ajn008
 */
public class GridPanel extends javax.swing.JPanel{
    private Cell[][] sudokuCells;
    private Window window;

    public GridPanel(Window w){
        this.window = w;
        this.sudokuCells = new Cell[9][9];
        this.init();
    }
    
    
    //Editability related functions
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-

    
    /**This function sets the editability of the Cell
     * at Location loc.
     * @param editable
     * @param loc 
     */
    public void setEditabilityAtLoc(boolean editable, Location loc){
        this.sudokuCells[loc.getX()][loc.getY()].setCellFieldEditable(editable);        
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
                this.sudokuCells[x][y].setCellFieldEditable(false);
            }        
        }
    }

    
    /**
     * This function frees up the board and makes all cells editable.
     */
    public void setAllCellsEditable(){
        for(int x = 0; x < Board.BOARD_SIZE; x++){    
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                this.sudokuCells[x][y].setCellFieldEditable(true);
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
     * This method sets up the GUI grid, along with the borders for each cell, and
     * a thicker border for each square.
     */
    private void init(){
        setLayout(new GridLayout(9, 9));
        Border thinBorder = BorderFactory.createLineBorder(Color.black, 1);
        Border thickBorder = BorderFactory.createLineBorder(Color.black, 4);
        setBorder(thickBorder);
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++){
                this.sudokuCells[x][y] = new Cell(new Location(x, y), true);
                this.sudokuCells[x][y].setBackground(SudokuController.offwhite);
                int topBorderPixelThickness = 1, bottomBorderPixelThickness, leftBorderPixelThickness = 1, rightBorderPixelThickness;

                if(y == 2 || y == 5){
                    bottomBorderPixelThickness = 4;
                }else{
                    bottomBorderPixelThickness = 1;                
                } 
                    
                if(x == 2 || x == 5){
                    rightBorderPixelThickness = 4;
                }else{
                    rightBorderPixelThickness = 1;
                }
                
                this.sudokuCells[x][y].setBorder(BorderFactory.createMatteBorder(topBorderPixelThickness, leftBorderPixelThickness, rightBorderPixelThickness, bottomBorderPixelThickness, Color.BLACK));
                this.sudokuCells[x][y].setFont(new Font("Arial Bold", Font.ITALIC, 22));
                this.sudokuCells[x][y].createTextFieldLimitDocument(1, this.window);
                
                
                add(this.sudokuCells[x][y]);
            }
        }
    }
    
    /**
     * This method repeats an animation the number of times
     * provided by the parameter, to show that the player has won
     * and to make them feel better about their day :).
     * @param numTimesToRepeat 
     */
    public void runVictoryAnimation(/*int numTimesToRepeat*/) {
        //set all cells editable so we can edit their color easily through the
        //paintCellsInLocArrayWithColor method.
        this.setAllCellsEditable();

        Color[] colorsToBeUsedArray = new Color[Board.BOARD_SIZE];
        colorsToBeUsedArray[0] = new Color(0, 0, 255);//blue
        colorsToBeUsedArray[1] = new Color(255, 0, 0);//red
        colorsToBeUsedArray[2] = new Color(0, 255, 0);//green
        colorsToBeUsedArray[3] = new Color(128, 0, 128);//purple
        colorsToBeUsedArray[4] = new Color(255, 165, 0);//orange
        colorsToBeUsedArray[5] = new Color(128, 128, 128);//gray
        colorsToBeUsedArray[6] = new Color(255, 255, 0);//yellow
        colorsToBeUsedArray[7] = new Color(0, 0, 0);//black
        colorsToBeUsedArray[8] = new Color(255, 255, 255);//white
        
        for(int colorIndex = 0; colorIndex < Board.BOARD_SIZE; colorIndex++){
            
            Color currentColor = colorsToBeUsedArray[colorIndex];
            for(int x = 0; x < Board.BOARD_SIZE; x++){
                for(int y = 0; y < Board.BOARD_SIZE; y++){
                    this.paintCellWithColorAtLoc(currentColor, new Location(x, y));

                    try {
                        Thread.sleep(18);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GridPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }        
            }
        }
        
        /*Color[] colorsToBeUsedArray = new Color[Board.BOARD_SIZE];
        colorsToBeUsedArray[0] = new Color(0, 0, 255);//blue
        colorsToBeUsedArray[1] = new Color(255, 0, 0);//red
        colorsToBeUsedArray[2] = new Color(0, 255, 0);//green
        colorsToBeUsedArray[3] = new Color(128, 0, 128);//purple
        colorsToBeUsedArray[4] = new Color(0, 0, 0);//black
        colorsToBeUsedArray[5] = new Color(255, 255, 255);//white
        colorsToBeUsedArray[6] = new Color(255, 165, 0);//orange
        colorsToBeUsedArray[7] = new Color(128, 128, 128);//gray
        colorsToBeUsedArray[8] = new Color(255, 255, 0);//yellow

        Location[][] arrayOfLocsByCol = new Location[Board.BOARD_SIZE][Board.BOARD_SIZE];
        //set up the 2d grid of locations by column so that we can easily paint all
        //cells in a given column with the same color
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int colNum = 0; colNum < Board.BOARD_SIZE; colNum++){
                arrayOfLocsByCol[colNum][row] = new Location(row, colNum);
            }
        }
        
        //loop through given number of repetitions
        for(int repetitionNum = 0; repetitionNum < numTimesToRepeat; repetitionNum++){
            //loop through each color possibility in each column
            for(int currentOffset = 0; currentOffset < Board.BOARD_SIZE; currentOffset++){
                
                //loop through each column and paint the cells in it.
                for(int col = 0; col < Board.BOARD_SIZE; col++){
                    //first we must determine the color of this column
                    int indexOfColor = col + currentOffset;
                    
                    //if the addition of the offset creates an index larger than
                    //the size of the color array, then we simply take care of this wraparound
                    //by subtracting the size of the color array.
                    if(indexOfColor >=Board.BOARD_SIZE){
                        indexOfColor -= Board.BOARD_SIZE;
                    }
                    //paint the cells with the proper color in the proper column (Picasso style :P)
                    this.paintCellsInLocArrayWithColor(colorsToBeUsedArray[indexOfColor], arrayOfLocsByCol[col]);
                }
                
                try {
                    //pause for 1/4 of a second (250 milliseconds) in between each "frame" of the animation
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GridPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }*/
        
    }
    
}
