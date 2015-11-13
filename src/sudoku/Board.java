/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author andrewnyhus
 */
public class Board {
    private CellValue[][] grid;
    private int[][] numGrid;
    private boolean[][] isEditable;
    private Square[] squares;
    private Row[] rows;
    private Col[] cols;
    private final int N = 9;
    
    
    public Board(CellValue[][] sudokuGrid){
        //sets up an empty 9x9 array of ints for the grid contents, 
        //and a 9x9 array of booleans for holding the editability of each spot on the grid
        
        this.grid = new CellValue[9][9];
        this.rows = new Row[9];
        this.cols = new Col[9];
        this.squares = new Square[9];

        for(int i = 0; i < N; i++){
            this.squares[i] = new Square(i, this);
            this.rows[i] = new Row(i,this);
            this.cols[i] = new Col(i, this);
        }

        this.setBoardWithTwoDGrid(sudokuGrid);
        this.isEditable = new boolean[9][9];
        
                
    }
    
    public int getLengthOfRowsColsAndSquares(){
        return this.N;
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

    /**
     * This method takes in Location loc, and determines which 3x3 square it is
     * in.
     *
     * @param loc
     * @return It then returns the number of that square 0-8. It will return -1
     * if the Location is not on the board
     */
    public int getSquareNumFromLoc(Location loc) {
        int locX = loc.getX();
        int locY = loc.getY();

        if ((locX == 0 || locX == 1 || locX == 2) && (locY == 0 || locY == 1 || locY == 2)) {
            return 0;
        } else if ((locX == 3 || locX == 4 || locX == 5) && (locY == 0 || locY == 1 || locY == 2)) {
            return 1;
        } else if ((locX == 6 || locX == 7 || locX == 8) && (locY == 0 || locY == 1 || locY == 2)) {
            return 2;
        } else if ((locX == 0 || locX == 1 || locX == 2) && (locY == 3 || locY == 4 || locY == 5)) {
            return 3;
        } else if ((locX == 3 || locX == 4 || locX == 5) && (locY == 3 || locY == 4 || locY == 5)) {
            return 4;
        } else if ((locX == 6 || locX == 7 || locX == 8) && (locY == 3 || locY == 4 || locY == 5)) {
            return 5;
        } else if ((locX == 0 || locX == 1 || locX == 2) && (locY == 6 || locY == 7 || locY == 8)) {
            return 6;
        } else if ((locX == 3 || locX == 4 || locX == 5) && (locY == 6 || locY == 7 || locY == 8)) {
            return 7;
        } else if ((locX == 6 || locX == 7 || locX == 8) && (locY == 6 || locY == 7 || locY == 8)) {
            return 8;
        } else {
            return -1;
        }

    }
    
    
    
    public Location getOriginOfSquareWithNum(int squareNum){
        Location[] listOfSquareOrigins = {new Location(0,0), new Location(3, 0), new Location(6, 0), new Location(0, 3), new Location(3, 3), new Location(6, 3), new Location(0, 6), new Location(3, 6), new Location(6, 6)};
        return listOfSquareOrigins[squareNum];
    }
    
    public CellValue[][] returnCopyOfGrid(){
        CellValue[][] returnGrid = new CellValue[this.N][this.N];
        for(int x = 0; x < this.N; x++){
            for(int y = 0; y < this.N; y++){
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
    public boolean isValid(){
        
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
 use for replacing the current values in the board.
     * @param inputGrid 
     */
    public void setBoardWithTwoDGrid(CellValue[][] inputGrid){
        for(int x = 0; x < N; x++){
            for(int y = 0; y < N; y++){
                CellValue currentCell = inputGrid[x][y];
                this.setValueAtLoc(new Location(x, y), currentCell);
            }
        }

    }

    /**
     * This method traverses every cell in the sudoku board and sets each value
     * to CellValue.EMPTY, and it's editability to true
     */
    public void clearBoard(){
        for(int x = 0; x < N; x++){
            for(int y = 0; y < N; y++){
                //0 is the default value, and the program sees 0 as an empty cell
                if(this.getEditabilityAtLoc(new Location(x, y))){
                    this.setValueAtLoc(new Location(x, y), CellValue.EMPTY);
                }
            }
        }
    }
    
    
    public void printGrid(CellValue grid[][]) {
       for (int x = 0; x < N; x++) {
           for (int y = 0; y < N; y++) {
               System.out.print(grid[x][y].toString() + ",");
           }
           System.out.print("\n");
       }
    } 
    
    public Board clone(){
        try{
            return (Board) super.clone();
        } catch(CloneNotSupportedException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    
}
