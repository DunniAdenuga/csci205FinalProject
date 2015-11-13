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
    
    
    public Board(CellValue[][] sudokuGrid){
        //sets up an empty 9x9 array of ints for the grid contents, 
        //and a 9x9 array of booleans for holding the editability of each spot on the grid
        
        this.grid = new CellValue[9][9];
        this.rows = new Row[9];
        this.cols = new Col[9];
        this.squares = new Square[9];

        for(int i = 0; i < BOARD_SIZE; i++){
            this.squares[i] = new Square(i, this);
            this.rows[i] = new Row(i,this);
            this.cols[i] = new Col(i, this);
        }

        this.setBoardWithTwoDGrid(sudokuGrid);
        this.isEditable = new boolean[9][9];
        
                
    }
    
    
    public Row getRow(int rownum){
        return this.rows[rownum];
    }
    
    public Col getCol(int colnum){
        return this.cols[colnum];
    }
    public Square getSquare(int squarenum){
        return this.squares[squarenum];
    }
    
    /**
     * This method gets the value at a specified location in the board.
     * @param loc
     * @return CellValue at Location loc
     */
    public CellValue getValueAtLoc(Location loc){
        return this.grid[loc.getX()][loc.getY()];
    }
    
    
    /**
     * @param loc
     * @return boolean value representing whether the user can edit a given location
     */
    public boolean getEditabilityAtLoc(Location loc){
        return this.isEditable[loc.getX()][loc.getY()];
    }
    
    
    
    
    
    public CellValue[][] returnCopyOfGrid(){
        CellValue[][] returnGrid = new CellValue[this.BOARD_SIZE][this.BOARD_SIZE];
        for(int x = 0; x < this.BOARD_SIZE; x++){
            for(int y = 0; y < this.BOARD_SIZE; y++){
                returnGrid[x][y] = this.getValueAtLoc(new Location(x, y));
            }
        }
        return returnGrid;
    }
    
    
    /**
     * This method determines whether or not the board is valid.
     * If no col, row, or square contains duplicates, then the board is valid.
     * @return a boolean value representing the validity of the Board
     */
    public boolean isValid(){
        
        //iterate all rows and check
        for(Row row: this.rows){
            if(!row.isValid()){
                return false;
            }
        }
        
        //iterate all cols and check
        for(Col col: this.cols){
            if(!col.isValid()){
                return false;
            }
        }
        
        //iterate all 3x3 squares and check
        for(Square sq: this.squares){
            if(!sq.isValid()){
                return false;
            }
        }
                
        return true;
    }
    
    
    /**
     * The purpose of this method is to determine if the board is completed.
     * For the board to be completed, all rows, cols, and squares must contain every value from 1-9,
     * and no empty spaces are allowed.
     * @return a boolean to represent whether or not the board is completed.
     */
    
    public boolean isCompleted(){
    
        //iterate all rows and check
        for(Row row:this.rows){
            if(!row.isCompleted()){
                return false;
            }
        }
        
        //iterate all cols and check
        for(Col col:this.cols){
            if(!col.isCompleted()){
                return false;
            }
        }
        
        //iterate all squares and check
        for(Square sq:this.squares){
            if(!sq.isCompleted()){
                return false;
            }
        }
        
        
        return true;
    }
    
    /**
     * Modifies the ability for the user to edit a cells value at a specified Location
     * @param loc
     * @param editable 
     */
    public void setEditabilityAtLoc(Location loc, boolean editable){
        this.isEditable[loc.getX()][loc.getY()] = editable;
    }
    
    /**
     * This method takes newValue and uses loc to determine where in this.grid, 
     * this.cols, this.rows and this.squares to place the newValue.
     * @param loc
     * @param newValue 
     */
    public void setValueAtLoc(Location loc, CellValue newValue){
        
        //update this.grid with newValue
        this.grid[loc.getX()][loc.getY()] = newValue;

        
    }
    
    /**
     * This method allows for the passing in of a 2d inputGrid of CellValues to
 use for replacing the current values in the board.
     * @param inputGrid 
     */
    public void setBoardWithTwoDGrid(CellValue[][] inputGrid){
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                CellValue currentCell = inputGrid[x][y];
                this.setValueAtLoc(new Location(x, y), currentCell);
            }
        }
        
    }
    
    /**
     * This method traverses every cell in the sudoku board
     * and sets each value to CellValue.EMPTY, and it's editability to true
     */
    public void clearBoard(){
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                //0 is the default value, and the program sees 0 as an empty cell
                if(this.getEditabilityAtLoc(new Location(x, y))){
                    this.setValueAtLoc(new Location(x, y), CellValue.EMPTY);
                }
            }
        }
    }
    
    
    public void printGrid(CellValue grid[][]) {
       for (int x = 0; x < BOARD_SIZE; x++) {
           for (int y = 0; y < BOARD_SIZE; y++) {
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
