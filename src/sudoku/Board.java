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
 * File: Board
 * Description:
 *
 * ****************************************
 */
package sudoku;

import java.util.ArrayList;

/**
 *
 * @author ajn008
 */
public class Board {

    private CellValue[][] grid;
    private boolean[][] isEditable;
    private Square[] squares;
    private Row[] rows;
    private Col[] cols;

    public static final int BOARD_SIZE = 9;

    //Constructors and related functions
    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-
    /**
     * Constructor that takes in a 2d array of CellValues to make up the grid.
     *
     * @param sudokuGrid
     */
    public Board(CellValue[][] sudokuGrid) {

        init();

        this.setBoardWithTwoDGrid(sudokuGrid);
    }

    /**
     * Alternate constructor that takes in a 2d array of int objects instead of
     * CellValues.
     *
     * @param grid
     */
    public Board(int[][] grid) {
        init();
        CellValue[][] sudokuGrid = generate2DGridFromInts(grid);
        this.setBoardWithTwoDGrid(sudokuGrid);
        
    }

    /**
     * This function initializes the various BoardSegments in the Board.
     */
    private void init() {
        //sets up an empty 9x9 array of ints for the grid contents,
        this.isEditable = new boolean[BOARD_SIZE][BOARD_SIZE];
        this.grid = new CellValue[BOARD_SIZE][BOARD_SIZE];
        this.rows = new Row[BOARD_SIZE];
        this.cols = new Col[BOARD_SIZE];
        this.squares = new Square[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            this.squares[i] = new Square(i, this);
            this.rows[i] = new Row(i, this);
            this.cols[i] = new Col(i, this);
        }
    }

    //-=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=--=-=-=-=-=-=-
    //End of Constructors and related functions
    /**
     * Sets all cells as not editable, and then iterates through arrayListOfLocs
     * and sets each Location object to be editable.
     *
     * @param arrayListOfLocs
     */
    /*public void makeOnlyLocationsInArrayListEditable(ArrayList<Location> arrayListOfLocs) {
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                this.setEditabilityAtLoc(new Location(x, y), false);
            }
        }

        for (Location loc : arrayListOfLocs) {
            this.setEditabilityAtLoc(loc, true);
        }
        
    }*/
    
    /**
     * Takes in int rownum, returns proper Row object
     *
     * @param rownum
     * @return Row
     */
    public Row getRow(int rownum) {
        return this.rows[rownum];
    }

    /**
     * Takes in int colnum, returns proper Col object
     *
     * @param colnum
     * @return Col
     */
    public Col getCol(int colnum) {
        return this.cols[colnum];
    }

    /**
     * Takes in int to figure out which Square.
     *
     * @param squarenum
     * @return Square object
     */
    public Square getSquare(int squarenum) {
        return this.squares[squarenum];
    }

    /**
     * Converts int object to CellValue
     *
     * @param value
     * @return CellValue object with the value the int parameter 'value'
     */
    public CellValue createCellValueFromInt(int value) {
        if ((value > 9) || (value < 0)) {
            //invalid integer values will return an empty CellValue
            return CellValue.EMPTY;
        }
        return CellValue.values()[value];
    }

    /**
     * This method takes in a 2d array of int objects and returns a 2d array of
     * CellValue objects.
     *
     * @param intGrid
     * @return 2d array of CellValue objects generated from intGrid.
     */
    public CellValue[][] generate2DGridFromInts(int[][] intGrid) {
        CellValue[][] gridToReturn = new CellValue[BOARD_SIZE][BOARD_SIZE];

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                gridToReturn[x][y] = this.createCellValueFromInt(intGrid[x][y]);
            }
        }

        return gridToReturn;

    }

    /**
     * This method counts the number of unsolved BoardSegments and returns the
     * count as an int.
     *
     * @return int numOfUnsolvedSegments
     */
    public int getNumOfUnsolvedSegments() {
        int numOfUnsolvedSegments = 0;

        //iterate through each of every type of segment
        for (int i = 0; i < BOARD_SIZE; i++) {

            if (!this.getCol(i).isCompleted()) {
                numOfUnsolvedSegments++;
            }
            if (!this.getRow(i).isCompleted()) {
                numOfUnsolvedSegments++;
            }
            if (!this.getSquare(i).isCompleted()) {
                numOfUnsolvedSegments++;
            }

        }
        return numOfUnsolvedSegments;
    }

    /**
     * This method gets the value at a specified location in the board.
     *
     * @param loc
     * @return CellValue at Location loc
     */
    public CellValue getValueAtLoc(Location loc) {
        return this.grid[loc.getX()][loc.getY()];
    }

    /**
     * @param loc
     * @return boolean value representing whether the user can edit a given
     * location
     */
    public boolean getEditabilityAtLoc(Location loc) {
        return this.isEditable[loc.getX()][loc.getY()];
    }

    public CellValue[][] returnCopyOfGrid() {
        CellValue[][] returnGrid = new CellValue[this.BOARD_SIZE][this.BOARD_SIZE];
        for (int x = 0; x < this.BOARD_SIZE; x++) {
            for (int y = 0; y < this.BOARD_SIZE; y++) {
                returnGrid[x][y] = this.getValueAtLoc(new Location(x, y));
            }
        }
        return returnGrid;
    }
    
    /**
     * This method determines whether or not the board is valid. If no col, row,
     * or square contains duplicates, then the board is valid.
     *
     * @return a boolean value representing the validity of the Board
     */
    public boolean isValid() {

        //iterate all rows and check
        for (Row row : this.rows) {
            if (!row.isValid()) {
                return false;
            }
        }

        //iterate all cols and check
        for (Col col : this.cols) {
            if (!col.isValid()) {
                return false;
            }
        }

        //iterate all 3x3 squares and check
        for (Square sq : this.squares) {
            if (!sq.isValid()) {
                return false;
            }
        }

        return true;
    }

    /**
     * The purpose of this method is to determine if the board is completed. For
     * the board to be completed, all rows, cols, and squares must contain every
     * value from 1-9, and no empty spaces are allowed.
     *
     * @return a boolean to represent whether or not the board is completed.
     */
    public boolean isCompleted() {

        //iterate all rows and check
        for (Row row : this.rows) {
            if (!row.isCompleted()) {
                return false;
            }
        }

        //iterate all cols and check
        for (Col col : this.cols) {
            if (!col.isCompleted()) {
                return false;
            }
        }

        //iterate all squares and check
        for (Square sq : this.squares) {
            if (!sq.isCompleted()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Modifies the ability for the user to edit a cells value at a specified
     * Location
     *
     * @param loc
     * @param editable
     */
    public void setEditabilityAtLoc(Location loc, boolean editable) {
        this.isEditable[loc.getX()][loc.getY()] = editable;
    }

    /**
     * This method takes newValue and uses loc to determine where in this.grid,
     * this.cols, this.rows and this.squares to place the newValue.
     *
     * @param loc
     * @param newValue
     */
    public void setValueAtLoc(Location loc, CellValue newValue) {

        //update this.grid with newValue
        this.grid[loc.getX()][loc.getY()] = newValue;

    }

    /**
     * This method allows for the passing in of a 2d inputGrid of CellValues to
     * use for replacing the current values in the board.
     *
     * @param inputGrid
     */
    public void setBoardWithTwoDGrid(CellValue[][] inputGrid) {
        /*for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {

                CellValue currentCell = inputGrid[x][y];
                this.setValueAtLoc(new Location(x, y), currentCell);
            }
        }*/

        for(int x = 0; x < Board.BOARD_SIZE; x++){
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                CellValue currentValue = inputGrid[x][y];
                this.grid[x][y] = currentValue;
                
                if(currentValue.isEmpty()){
                    this.isEditable[x][y] = true;
                }else{
                    this.isEditable[x][y] = false;                
                }
                
            }
        }          
        
    }

    /**
     * This method traverses every cell in the sudoku board and sets each value
     * to CellValue.EMPTY, and it's editability to true
     */
    public void clearBoard() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                this.setValueAtLoc(new Location(x, y), CellValue.EMPTY);
                this.setEditabilityAtLoc(new Location(x, y), true);
            }
        }
    }

    /**
     * This method takes in a 2d array of CellValue objects and prints the grid
     * that the 2d array resembles.
     *
     * @param grid
     */
    public void printGrid(CellValue grid[][]) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                System.out.print(grid[x][y].toString() + " ");
                if((y + 1)% 3 == 0){
                    System.out.print("|");
                }
            }
            System.out.print("\n---------------------------------\n");
        }
    }

    /**
     * This method is Overriding the Object.clone() method, so that we can make
     * a copy of the Board
     *
     * @return Board object
     * @throws CloneNotSupportedException
     */
    @Override
    public Board clone() {

        CellValue[][] values = new CellValue[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, values[i], 0, grid[i].length);
        }
        boolean[][] editable = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.
                    arraycopy(isEditable[i], 0, editable[i], 0,
                              isEditable[i].length);
        }
        Board ret = new Board(values);
        ret.isEditable = editable;
        return ret;
    }

    /**
     * This method returns a 2d array of int objects that share the same values
     * of each CellValue in the grid.
     *
     * @return int[][]
     */
    public int[][] getIntGrid() {
        int[][] numGrid = new int[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                numGrid[x][y] = this.getValueAtLoc(new Location(x, y)).
                        getValue();
            }
        }
        return numGrid;
    }

    /**
     * This method takes in a 2d array representing a grid, and returns a
     * boolean to determine if the Grid has any invalid values.
     *
     * @param grid
     * @return true if no value is out of acceptable range (0-9), otherwise
     * false
     */
    public boolean checkGrid(int[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if ((grid[i][j] < 0) || (grid[i][j] > BOARD_SIZE)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method takes in a 2d array of int objects resembling a grid, and
     * then prints the grid.
     *
     * @param grid
     */
    public static void printGrid(int grid[][]) {
        for (int row1 = 0; row1 < BOARD_SIZE; row1++) {
            for (int col1 = 0; col1 < BOARD_SIZE; col1++) {
                System.out.printf("%2d", grid[row1][col1]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * Check if boards are Equal
     *
     * @param old - board to be compared to
     * @return true if equal, else false
     */
    public boolean equal(Board old) {
        boolean check = true;
        while (check == true) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (getIntGrid()[i][j] != old.getIntGrid()[i][j]) {
                        return false;
                    }
                }
            }
        }
        return check;
    }
}
