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
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
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
    
    
    /**This function makes all of the cells in the grid not editable.
     * This is useful when we want the user to select an option for playing a new game.
     * This could also possibly be useful in between the time that the user selects
     * a difficulty, and the time when the board is actually loaded.
     */
    public void blockUserFromEditingGrid(){
        for(int x = 0; x < Board.BOARD_SIZE; x++){    
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                this.sudokuCells[x][y].setCellFieldEditable(false);
            }        
        }
    }

    
    /**
     * This function frees up the board and makes all cells editable.
     */
    public void allowUserToEditAllCells(){
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
        this.blockUserFromEditingGrid();
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
            this.paintCellWithColorAtLoc(color, loc);            
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
                this.sudokuCells[x][y].setDocument(new JTextFieldLimit(1, this.window));
                
                
                add(this.sudokuCells[x][y]);
            }
        }
    
    
    }
    
        
    /**This JTextFieldLimit class is necessary, it is an extension of the PlainDocument class
     * It allows us to do two main things related to user input
     * 1) It allows us to stop the user from entering bad input
     * 2) It allows us to update our controller class when the user updates the grid.
     * Referred to: http://stackoverflow.com/questions/3519151/how-to-limit-the-number-of-characters-in-jtextfield
     */
    public class JTextFieldLimit extends PlainDocument{
        private int limit;
        private Window window;
        JTextFieldLimit(int limit, Window w){
            super();
            this.limit = limit;
            this.window = w;
        }
        
        public void insertString(int offset, String string, AttributeSet attributes) throws BadLocationException{
            if(string == null)
                return;
                        
            if((getLength() + string.length()) <= limit){
                //we know the length of string should be 1, so we evaluate whether or not the stringOfValidValues contains string.
                String strOfValidValues = "123456789";
                if(strOfValidValues.contains(string)){
                    //if true, the input value is valid and we insert string into the text field in the given sudoku cell
                    super.insertString(offset, string, attributes);
                    this.window.notifySudokuControllerOfBoardUpdates();
                    
                }
            }
        }
        
    }        
}
