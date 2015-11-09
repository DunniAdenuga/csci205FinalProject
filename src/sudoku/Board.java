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
    private boolean[][] isEditable;
    private Square[] squares;
    private Row[] rows;
    private Col[] cols;
    
    public Board(){
        //sets up an empty 9x9 array of ints for the grid contents, 
        //and a 9x9 array of booleans for holding the editability of each spot on the grid
        this.grid = new CellValue[9][9];
        this.isEditable = new boolean[9][9];
        
        this.rows = new Row[9];
        this.cols = new Col[9];
        
    }
    
    public CellValue getValueAtLoc(Location loc){
        return this.grid[loc.getX()][loc.getY()];
    }
    
    public boolean getEditabilityAtLoc(Location loc){
        return this.isEditable[loc.getX()][loc.getY()];
    }
    
    public int getSquareNumFromLoc(Location loc){
        int locX = loc.getX();
        int locY = loc.getY();
        
        if((locX == 0 || locX == 1 || locX == 2) && (locY == 0 || locY == 1 || locY == 2)){
            return 0;
        }else if((locX == 3 || locX == 4 || locX == 5) && (locY == 0 || locY == 1 || locY == 2)){
            return 1;
        }else if((locX == 6 || locX == 7 || locX == 8) && (locY == 0 || locY == 1 || locY == 2)){
            return 2;
        }else if((locX == 0 || locX == 1 || locX == 2) && (locY == 3 || locY == 4 || locY == 5)){
            return 3;
        }else if((locX == 3 || locX == 4 || locX == 5) && (locY == 3 || locY == 4 || locY == 5)){
            return 4;
        }else if((locX == 6 || locX == 7 || locX == 8) && (locY == 3 || locY == 4 || locY == 5)){
            return 5;
        }else if((locX == 0 || locX == 1 || locX == 2) && (locY == 6 || locY == 7 || locY == 8)){
            return 6;
        }else if((locX == 3 || locX == 4 || locX == 5) && (locY == 6 || locY == 7 || locY == 8)){
            return 7;
        }else if((locX == 6 || locX == 7 || locX == 8) && (locY == 6 || locY == 7 || locY == 8)){
            return 8;
        }else{return -1;}
        
    }
    
    public int getIndexWithinSquareListFromLoc(Location loc){
        
        Location[] listOfSquareOrigins = {new Location(0,0), new Location(3, 0), new Location(6, 0), new Location(0, 3), new Location(3, 3), new Location(6, 3), new Location(0, 6), new Location(3, 6), new Location(6, 6)};
        
        int square = this.getSquareNumFromLoc(loc);
        if(square == -1){return -1;}
        
        Location originOfSquareThatContainsloc = listOfSquareOrigins[square];
        int originX = originOfSquareThatContainsloc.getX();
        int originY = originOfSquareThatContainsloc.getY();
        
        int distanceFromOrigin = 3*(loc.getY() - originY) + (loc.getX() - originX);
        
        return distanceFromOrigin;
    }
    
    public boolean isValid(){
        for(Row row: this.rows){
            if(!row.isValid()){
                return false;
            }
        }
        
        for(Col col: this.cols){
            if(!col.isValid()){
                return false;
            }
        }
        
        for(Square sq: this.squares){
            if(!sq.isValid()){
                return false;
            }
        }
                
        return true;
    }
    
    public boolean isCompleted(){
        
        for(Row row:this.rows){
            if(!row.isCompleted()){
                return false;
            }
        }

        for(Col col:this.cols){
            if(!col.isCompleted()){
                return false;
            }
        }

        for(Square sq:this.squares){
            if(!sq.isCompleted()){
                return false;
            }
        }
        
        
        return true;
    }
    
    public void setEditabilityAtLoc(Location loc, boolean editable){
        this.isEditable[loc.getX()][loc.getY()] = editable;
    }
    
    public void setValueAtLoc(Location loc, CellValue newValue){
        
        if(this.getEditabilityAtLoc(loc)){
            //update this.grid with newValue
            this.grid[loc.getX()][loc.getY()] = newValue;
                        
            //update the proper element in the proper column with newValue
            this.cols[loc.getX()].setColWithValueAtIndex(newValue, loc.getY());
            
            //update the proper element in the proper row with newValue
            this.rows[loc.getY()].setRowWithValueAtIndex(newValue, loc.getX());
            
            //update proper square in the proper index with newValue
            this.squares[this.getSquareNumFromLoc(loc)].setSquareWithValueAtIndex(newValue, this.getIndexWithinSquareListFromLoc(loc));
            
        }
        
    }
    
    public void setBoardWithTwoDGrid(CellValue[][] grid){
        this.grid = grid;
        for(int x = 0; x < this.grid.length; x++){
            for(int y = 0; y < this.grid[x].length; y++){
                CellValue currentCell = this.grid[x][y];
                if(currentCell.isEmpty()){
                    this.isEditable[x][y] = true;
                }else{
                    this.isEditable[x][y] = false;
                }
            }
        }
        
    }
    
    public void clearBoard(){
        for(int i = 0; i < this.grid.length; i++){
            for(int j = 0; j < this.grid.length; j++){
                //0 is the default value, and the program sees 0 as an empty cell
                this.setValueAtLoc(new Location(i, j), CellValue.EMPTY);
            }
        }
    }
    
}
