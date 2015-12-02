/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 12, 2015
 * Time: 10:13:22 PM
 *
 * Project: csci205FinalProject
 * Package: sudoku.controller
 * File: SudokuController
 * Description:
 *
 * ****************************************
 */
package sudoku.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import sudoku.Board;
import sudoku.CellValue;
import sudoku.Location;
import sudoku.view.Window;

/**
 *
 * @author ajn008
 */
public class SudokuController {
    
    private Board board;
    private Window window;
    private final String[] actionCommands;
    private final String instructions;
    public static final Color offwhite = new Color(255,244,232);
    private boolean setWindowWasCalled = false;
    private Timer displayTimer;
    private long beginningOfGameTime;
    private ActionListener listener;
    
    
    public SudokuController(){
        this.actionCommands = new String[4];
        this.actionCommands[0] = "Enter My Own Board";
        this.actionCommands[1] = "Let the computer make my board (Difficulty:Easy)";
        this.actionCommands[2] = "Let the computer make my board (Difficulty:Medium)";
        this.actionCommands[3] = "Let the computer make my board (Difficulty:Hard)";
        
        this.instructions = "You must fill the board in such a manner that every column, row, and square\nhas no duplicates or empty spaces, and contains every value from 1-9.\n\nIf the color surrounding the board turns red, the current grid is not a valid one.\nIf it turns green, you have won. As you complete more and more of the board,\nthe color of the square surrounding the board will gradually get closer and closer to white.";
        
            
    }
    
    
    /**
     * This method is called only once, from sudoku.main.java
     * It initializes the Window (extends JFrame) and begins the game.
     * @param w 
     */
    public void setWindow(Window w){
        if(!setWindowWasCalled){
            setWindowWasCalled = true;
            
            this.window = w;
            this.window.setVisible(true);
            CellValue[][] initialGridForBoard = this.window.getGridPanel().getCellValueArray();
            this.board = new Board(initialGridForBoard);

            JOptionPane.showMessageDialog(this.window, this.instructions);

            String usersInput = this.getUsersChoice(true);

            this.handleMenuAction(usersInput);
            
        }
    }
    
    /**
     * Prompts user for choice, and does not let user 
     * @param isFirstGameSession
     * @return String that represents the choice of the user
     */
    public String getUsersChoice(boolean isFirstGameSession){
        String titleString;
        if(isFirstGameSession){
            titleString = "Welcome to Sudoku!";

            String usersChoice = (String) JOptionPane.showInputDialog(null, "Please choose one of the following...", titleString, JOptionPane.QUESTION_MESSAGE, null, this.actionCommands, this.actionCommands[1]);
            //user cancelled or xed out
            while(usersChoice == null){
                //tell user to give input
                JOptionPane.showMessageDialog(this.window, "You must pick one of the options.");
                //get users input
                usersChoice = (String) JOptionPane.showInputDialog(null, "Please choose one of the following...", titleString, JOptionPane.QUESTION_MESSAGE, null, this.actionCommands, this.actionCommands[1]);
            }

            return usersChoice;
        }else{
            titleString = "Play Again?";
            String usersChoice = (String) JOptionPane.showInputDialog(null, "If you want to continue, choose one of the following. Click Cancel or the X button to exit", titleString, JOptionPane.QUESTION_MESSAGE, null, this.actionCommands, this.actionCommands[1]);
            if(usersChoice == null){
                return "Stop";        
            }else{
                return usersChoice;
            }
        }
    }
    
    
    
    /**
     * This method is called by the Window class in method Window.notifySudokuControllerOfBoardUpdates()
     * which occurs when the user updates one of the Cells (JTextField) in the grid.
     * @param boardGrid 
     */
    public void boardWasUpdated(CellValue[][] boardGrid){
        //update Board Class before anything else

        this.board.setBoardWithTwoDGrid(boardGrid);
        

        if(this.board.isCompleted()){

            String congratsString;
            if(displayTimer.isRunning()){
                displayTimer.stop();
                long timeToSolve = System.currentTimeMillis() - this.beginningOfGameTime;
                long numSeconds = timeToSolve/1000;
                long numMinutes = 0;
                        
                while(numSeconds > 60){
                    numSeconds -= 60;
                    numMinutes++;
                }
                    
                congratsString = "Congratulations, you solved the board in " + numMinutes + " minutes, and " + numSeconds + " seconds!";
            }else{
                congratsString = "Congratulations, you solved the board!";
            }
            
            //Paint Grid's outer border green
            this.window.getGridPanel().paintOuterBorderWithColor(Color.GREEN);
            //block board until user makes decision
            this.window.getGridPanel().blockUserFromEditingGrid();
            //Edit Status Label to congratulate the user and prompt them to make a decision
            this.window.updateStatusLabel("Board is 100% complete!");
            
            JOptionPane.showMessageDialog(window, congratsString);
            
            String usersInput = this.getUsersChoice(false);
            
            this.handleMenuAction(usersInput);
            
        }
        int numTotalSegments = Board.BOARD_SIZE * 3;
        int numUnsolvedSegments = this.board.getNumOfUnsolvedSegments();

        double percentSegmentsCompleted = ((double)(numTotalSegments - numUnsolvedSegments)/(double)(numTotalSegments))*100;
        String newStatusLabelString =  "Board is " + (int)percentSegmentsCompleted + "% complete";
        
        this.window.updateStatusLabel(newStatusLabelString);
        //do anything fun that encourages user to keep progressing here
        
    }
    
    
    /**
     * This method starts a new game, the int gameType parameter
     * indicates whether the user will fill the board, or if the computer will fill
     * If the computer fills, this int indicates the selected level of difficulty.
     * @param gameType 
     */
    public void startNewGame(int gameType){
        
        
        this.window.getGridPanel().paintOuterBorderWithColor(offwhite);
        this.window.getGridPanel().paintAllCellsWithColor(offwhite);
        
        //fill board
        //solution to test grid is:
                /*int[][] testGridSolution = {{9,5,2,7,1,8,6,4,3},
                                             {3,7,4,2,5,6,8,1,9},
                                             {6,1,8,4,9,3,5,2,7},
                                             {1,3,5,6,4,2,7,9,8},
                                             {8,4,7,9,3,5,1,6,2},
                                             {2,9,6,1,8,7,3,5,4},
                                             {5,8,1,3,2,4,9,7,6},
                                             {7,2,9,8,6,1,4,3,5},
                                             {4,6,3,5,7,9,2,8,1}};*/
        
        switch(gameType){
        
            
            case 0://free up entire board for user to edit
                this.window.getGridPanel().allowUserToEditAllCells();
                this.window.getGridPanel().clearValuesInFields();
                CellValue[][] emptyGrid = this.window.getGridPanel().getCellValueArray();
                this.board = new Board(emptyGrid);
                
                //deal with model editable grid.
                for(int x = 0; x < Board.BOARD_SIZE; x++){
                    for(int y = 0; y< Board.BOARD_SIZE; y++){
                        this.board.setEditabilityAtLoc(new Location(x, y), true);            
                    }                            
                }
                
                break;

            case 1://populate with easy board
                
                int[][] easyTestGrid = {{0,0,2,7,1,8,0,4,0},
                                     {3,7,4,2,0,0,8,1,9},
                                     {6,1,8,4,0,3,5,2,7},
                                     {1,0,5,6,4,2,7,0,8},
                                     {8,4,7,9,0,0,1,6,2},
                                     {2,9,6,1,8,7,0,5,0},
                                     {0,0,1,3,2,4,9,7,0},
                                     {7,2,0,0,0,1,0,3,5},
                                     {4,0,0,5,7,0,2,8,1}};
                
                this.board = new Board(easyTestGrid);
                CellValue[][] easyTestCellValueGrid = this.board.returnCopyOfGrid();
                ArrayList<Location> emptyAndEditableLocs = this.window.getGridPanel().setGridWith2DArray(easyTestCellValueGrid);
                this.board.makeOnlyLocationsInArrayListEditable(emptyAndEditableLocs);
                
                
                break;
            case 2://populate with medium board
                int[][] mediumTestGrid = {{0,0,2,7,1,8,0,4,0},
                                     {3,7,4,2,0,0,0,1,9},
                                     {6,1,0,4,0,3,5,2,7},
                                     {1,0,5,6,4,2,7,0,0},
                                     {0,4,7,9,0,0,1,6,2},
                                     {2,9,6,1,8,7,0,5,0},
                                     {0,0,1,3,2,4,9,7,0},
                                     {7,2,0,0,0,1,0,3,5},
                                     {4,0,0,5,7,0,2,8,1}};
                
                this.board = new Board(mediumTestGrid);
                CellValue[][] mediumTestCellValueGrid = this.board.returnCopyOfGrid();
                emptyAndEditableLocs = this.window.getGridPanel().setGridWith2DArray(mediumTestCellValueGrid);
                this.board.makeOnlyLocationsInArrayListEditable(emptyAndEditableLocs);
                
                break;
            case 3://populate with difficult board
                int[][] difficultTestGrid = {{0,0,2,0,1,8,0,4,0},
                                     {3,0,0,2,0,0,0,0,9},
                                     {6,1,0,0,0,3,5,0,0},
                                     {0,0,5,6,0,0,7,0,0},
                                     {0,4,0,9,0,0,1,6,2},
                                     {0,9,0,0,8,0,0,0,0},
                                     {0,0,0,0,2,4,9,0,0},
                                     {7,2,0,0,0,1,0,3,0},
                                     {0,0,0,5,0,0,0,8,1}};
                
                this.board = new Board(difficultTestGrid);
                CellValue[][] difficultTestCellValueGrid = this.board.returnCopyOfGrid();
                emptyAndEditableLocs = this.window.getGridPanel().setGridWith2DArray(difficultTestCellValueGrid);
                this.board.makeOnlyLocationsInArrayListEditable(emptyAndEditableLocs);
                
                break;        
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        //http://stackoverflow.com/questions/8396870/joptionpane-yes-or-no-window
        //Board is set up, prompt user for whether or not the timer should be on.
        
        //first, disable the grid while the user is responding.
        this.window.getGridPanel().setEnabled(false);
        //Get user response about timer
        int gameShouldBeTimed = JOptionPane.showConfirmDialog(null, "Should this round be timed?", "Timer?", JOptionPane.YES_NO_OPTION);
        //re-enable the grid.
        this.window.getGridPanel().setEnabled(true);
                
        if(gameShouldBeTimed == JOptionPane.YES_OPTION){
            
            this.beginningOfGameTime = System.currentTimeMillis();

            listener = new ActionListener(){
                //http://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionEvent.html
                public void actionPerformed(ActionEvent event){
                    long elapsedTime = event.getWhen() - beginningOfGameTime;
                    
                    long numSeconds = elapsedTime/1000;
                    //long numMilliseconds = elapsedTime - numSeconds*1000;
                    long numMinutes = 0;

                    while(numSeconds > 60){
                        numMinutes++;
                        numSeconds -= 60;
                    }
                    String currentTimeString = String.format("%02d:%02d", numMinutes, numSeconds);
                    window.updateTimerLabel(currentTimeString);
                    
                }
            };
            
            //http://docs.oracle.com/javase/6/docs/api/javax/swing/Timer.html
            displayTimer = new Timer(1000, listener);
            displayTimer.setInitialDelay(2);

            displayTimer.start();
                    
        }else{
            //If we are not using the timer, clear the timer label
            this.window.updateTimerLabel("");
        }
        
    }
    
    /**
     * This method is responsible for taking in a commandString and 
     * handling it by either creating the right kind of game, or ending the game.
     * @param commandString 
     */
    public void handleMenuAction(String commandString){
        int gameType;
        
        if(commandString.equals(this.actionCommands[0])){
            gameType = 0;
        }else if(commandString.equals(this.actionCommands[1])){
            gameType = 1;
        }else if(commandString.equals(this.actionCommands[2])){
            gameType = 2;
        }else if(commandString.equals(this.actionCommands[3])){
            gameType = 3;
        }else if(commandString.equals("Stop")){
            //User does not want to play any more.
            //exit
            //http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
            this.window.setVisible(false);
            this.window.dispose();
            System.exit(0);
            return;
        }else{
            System.out.println("Game could not start, invalid command.");
            return;
        }
        
        this.startNewGame(gameType);
    }
    
    /**
     * Getter method for Window
     * @return Window
     */
    public Window getWindow(){
        return this.window;
    }
    
    
}
