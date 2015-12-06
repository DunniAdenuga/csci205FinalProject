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

import SolvingAlgorithms.BacktrackAlgorithm;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import sudoku.Board;
import sudoku.CellValue;
import sudoku.Col;
import sudoku.Location;
import sudoku.Row;
import sudoku.Square;
import sudoku.view.Cell;
import sudoku.view.GridPanel;
import sudoku.view.Window;

/**
 *
 * @author ajn008
 */
public class SudokuController implements ActionListener, FocusListener{
    
    private Board board;
    private Window window;
    private final String[] actionCommands;
    private final String instructions;
    public static final Color offwhite = new Color(255,244,232);
    private boolean setWindowWasCalled = false;
    private Timer displayTimer;
    private long beginningOfGameTime;
    private ActionListener timerListener;
    private Board solvedBoard;//this variable is only to be used within the threads responsible for solving the board.
    
    
    public SudokuController(){
        this.actionCommands = new String[6];
        this.actionCommands[0] = "Enter My Own Board";
        this.actionCommands[1] = "Let the computer make my board (Difficulty:Easy)";
        this.actionCommands[2] = "Let the computer make my board (Difficulty:Medium)";
        this.actionCommands[3] = "Let the computer make my board (Difficulty:Hard)";
        this.actionCommands[4] = "Backtracking";
        this.actionCommands[5] = "Simulated Annealing";
        
        this.instructions = "You must fill the board in such a manner that every column, row, and square\nhas no duplicates or empty spaces, and contains every value from 1-9.\n\nIf the color red is painted over a row, column or square, that means it is invalid and contains a duplicate.";
        this.init();
            
    }
    
    
    /**
     * This method is called only once, it initializes the view and the model
     * setting everything up.
     */
    private void init(){
            setWindowWasCalled = true;
            
            this.window = new Window();
 
            //add cells
                
            this.addCellsToGridPanel();
            
            //call init1
            this.window.init1();
            
            this.window.backtrackingItem.addActionListener(this);
            this.window.simulatedAnnealingItem.addActionListener(this);
            this.window.newEasyBoardMenuItem.addActionListener(this);
            this.window.newMediumBoardMenuItem.addActionListener(this);
            this.window.newDifficultBoardMenuItem.addActionListener(this);
            this.window.newManuallyEnteredBoardMenuItem.addActionListener(this);
            this.window.submitManualBoardEntry.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //if button is pressed, this method is called
                    handleEnterButtonBeingPressed();
                }
            });
            
            this.window.init2();

               
            
            this.window.setVisible(true);
            this.window.getGridPanel().paintAllCellsWithColor(SudokuController.offwhite);
            
            CellValue[][] initialGridForBoard = this.window.getGridPanel().getCellValueArray();
            this.board = new Board(initialGridForBoard);

            
            
            this.window.getGridPanel().paintOuterBorderWithColor(offwhite);            
            
            //this.runVictoryAnimation();
            JOptionPane.showMessageDialog(this.window, this.instructions);

            String usersInput = this.getUsersChoice(true);

            this.handleMenuAction(usersInput);
    }
    
    
    
    /**
     * Used to set up the Cell objects, accomplishing this from the controller
     * is crucial because otherwise it is difficult to notify the controller of changes
     * without duplicating controller objects.
     */
    private void addCellsToGridPanel(){
        for(int x = 0; x < Board.BOARD_SIZE; x++){
            for(int y = 0; y < Board.BOARD_SIZE; y++){
                this.window.getGridPanel().setCellAtLoc(new Location(x, y), new Cell(new Location(x, y), true, this));
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).setBackground(offwhite);
                
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

                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).addFocusListener(this);
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).setBorder(BorderFactory.createMatteBorder(topBorderPixelThickness, leftBorderPixelThickness, rightBorderPixelThickness, bottomBorderPixelThickness, Color.BLACK));
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).setFont(new Font("Arial Bold", Font.ITALIC, 26));
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).createTextFieldLimitDocument(1);
                
                this.window.getGridPanel().add(this.window.getGridPanel().getCellAtLoc(new Location(x, y)));
                
            }
        }
    
    }
    
    
    /**
     * Prompts user for choice, and does not let user 
     * @param isFirstGameSession
     * @return String that represents the choice of the user
     */
    public String getUsersChoice(boolean isFirstGameSession){
        String titleString;
        String[] choices = Arrays.copyOfRange(this.actionCommands, 0, 4);
        if(isFirstGameSession){
            titleString = "Welcome to Sudoku!";

            String usersChoice = (String) JOptionPane.showInputDialog(null, "Please choose one of the following...", titleString, JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
            //user cancelled or xed out
            while(usersChoice == null){
                //tell user to give input
                JOptionPane.showMessageDialog(this.window, "You must pick one of the options.");
                //get users input
                usersChoice = (String) JOptionPane.showInputDialog(null, "Please choose one of the following...", titleString, JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
            }

            return usersChoice;
        }else{
            titleString = "Play Again?";
            String usersChoice = (String) JOptionPane.showInputDialog(null, "If you want to continue, choose one of the following. Click Cancel or the X button to exit", titleString, JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
            if(usersChoice == null){
                return "Stop";        
            }else{
                return usersChoice;
            }
        }
    }
    

    /**
     * this is the method that is called when somebody clicks the Enter button in the top of the GUI
     */
    public void handleEnterButtonBeingPressed(){
        
        //before doing anything, we must validate that there are no issues
        //with the manually entered board.
        //1. get the int 2d array.
        int[][] intArrayOfGridEntered = this.window.getGridPanel().getIntArray();
        
        //2. setup board object from the 2d int array
        Board tempBoard = new Board(intArrayOfGridEntered);
        
        //if tempBoard is invalid, tell the user, and do not set up game
        if(!tempBoard.isValid()){
            String displayString = "The board you entered was invalid";
          
            JOptionPane.showMessageDialog(this.window, displayString);
            
        }else{
            //otherwise, set up game with the user defined grid
            this.window.hideEnterButtonFromTopPanel();

            CellValue[][] currentGridCellValues = this.window.getGridPanel().getCellValueArray();        

            //once the submit button is clicked, iterate through every cell
            for(int i = 0; i < Board.BOARD_SIZE; i++){

                for(int j = 0 ; j < Board.BOARD_SIZE; j++){
                    //if the current cell is not empty, cell.setCellFieldEditable(false)
                    if(!currentGridCellValues[i][j].isEmpty()){
                        GridPanel panel = this.window.getGridPanel();
                        this.window.getGridPanel().setEditabilityAtLoc(false, new Location(i, j));
                        this.window.getGridPanel().paintCellWithColorAtLoc(Color.lightGray, new Location(i, j));
                        this.board.setEditabilityAtLoc(new Location(i, j), false);

                    }else{
                        this.board.setEditabilityAtLoc(new Location(i, j), true);
                        this.window.getGridPanel().paintCellWithColorAtLoc(offwhite, new Location(i, j));
                    }
                }
            }

            boolean shouldTimeGame = getFromUserIfShouldTimeGame();

            if(shouldTimeGame){
                beginTiming();
            }else{
                //If we are not using the timer, clear the timer label
                this.window.setTimerLabel("");                    
            }
        
        }
    }

    private boolean getFromUserIfShouldTimeGame() throws HeadlessException {
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        //Part 2 of method:
        //figure out if user wants to be timed and start game
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        //first, disable the grid while the user is responding.
        this.window.getGridPanel().setEnabled(false);
        //Get user response about timer
        int gameShouldBeTimed = JOptionPane.showConfirmDialog(null, "Should this round be timed?", "Timer?", JOptionPane.YES_NO_OPTION);
        //re-enable the grid.
        this.window.getGridPanel().setEnabled(true);
        
        return (gameShouldBeTimed == JOptionPane.YES_OPTION);
        
    }
    
    /**
     * This method starts the timer at the beginning of the game.
     */
    private void beginTiming(){
            
            this.beginningOfGameTime = System.currentTimeMillis();

            if(displayTimer == null){
            timerListener = new ActionListener(){
                //http://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionEvent.html
                @Override
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
                    window.setTimerLabel(currentTimeString);
                    
                }
            };
            
            //http://docs.oracle.com/javase/6/docs/api/javax/swing/Timer.html
            displayTimer = new Timer(1000, timerListener);
            displayTimer.setInitialDelay(2);
            }
            
            displayTimer.start();
                    
    }
    
    /**
     * This method is called by the Window class in method Window.notifySudokuControllerOfBoardUpdates()
     * which occurs when the user updates one of the Cells (JTextField) in the grid.
     */
    public void updateBoard(){

        
        //update Board Class before anything else
        this.board.setBoardWithTwoDGrid(this.window.getGridPanel().getCellValueArray(), false);
        
        if(this.board.isCompleted()){
            String congratsString;
            if(displayTimer != null && displayTimer.isRunning()){
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
            //call the method in GridPanel that handles the "victory" animation
            //this.runVictoryAnimation();
            
            //Paint Grid's outer border green
            this.window.getGridPanel().paintOuterBorderWithColor(Color.GREEN);
            //block board until user makes decision
            //this.window.getGridPanel().setAllCellsNotEditable();
            //Edit Status Label to congratulate the user and prompt them to make a decision
            this.window.setStatusLabel("Board is 100% complete!");
            
            
            
            JOptionPane.showMessageDialog(this.window, congratsString);
            
            String usersInput = this.getUsersChoice(false);
            
            this.handleMenuAction(usersInput);
            
        }
        int numTotalSegments = Board.BOARD_SIZE * 3;
        int numUnsolvedSegments = this.board.getNumOfUnsolvedSegments();

        double percentSegmentsCompleted = ((double)(numTotalSegments - numUnsolvedSegments)/(double)(numTotalSegments))*100;
        String newStatusLabelString =  "Board is " + (int)percentSegmentsCompleted + "% complete";
        
        this.window.setStatusLabel(newStatusLabelString);
        
        this.updateBoardColors();
        
    }
    
    /**
     * Paints valid boardsegments offwhite, and invalid ones red,
     * the cells that are not editable will not be painted.
     */
    public void updateBoardColors(){
        ArrayList<Location> locsInValidSegment = new ArrayList();
        ArrayList<Location> locsInInvalidSegment = new ArrayList();
        
        
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            
            Row currentRow = this.board.getRow(i);
            Location[] rowLocs = currentRow.getArrayOfLocationsInSegment();

            Col currentCol = this.board.getCol(i);
            Location[] colLocs = currentCol.getArrayOfLocationsInSegment();
            
            Square currentSquare = this.board.getSquare(i);
            Location[] squareLocs = currentSquare.getArrayOfLocationsInSegment();
            
            
            if(currentRow.isValid()){
                //paint it offwhite where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(offwhite, rowLocs);
                for(Location loc: rowLocs)
                    if(this.board.getEditabilityAtLoc(loc))
                        locsInValidSegment.add(loc);
            
            }else{
                //paint it red where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(Color.RED, rowLocs);                        
                for(Location loc: rowLocs)
                    if(this.board.getEditabilityAtLoc(loc))
                        locsInInvalidSegment.add(loc);
                
            }
            
            
            
            if(currentCol.isValid()){
                //paint it offwhite where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(offwhite, colLocs);
                for(Location loc: colLocs)
                    if(this.board.getEditabilityAtLoc(loc))
                        locsInValidSegment.add(loc);

            }else{
                //paint it red where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(Color.RED, colLocs);    
                for(Location loc: colLocs)
                    if(this.board.getEditabilityAtLoc(loc))
                        locsInInvalidSegment.add(loc);
                
            }
            
            
            if(currentSquare.isValid()){
                //paint it offwhite where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(offwhite, squareLocs);
                for(Location loc: squareLocs)
                    if(this.board.getEditabilityAtLoc(loc))
                        locsInValidSegment.add(loc);
            }else{
                //paint it red where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(Color.RED, squareLocs);                
                for(Location loc: squareLocs)
                    if(this.board.getEditabilityAtLoc(loc))
                       locsInInvalidSegment.add(loc);
            }
        }            
        
        //paint offwhite cells first so that there is no offwhite painted over an invalid cell
        for(Location loc: locsInValidSegment){
            if(this.board.getEditabilityAtLoc(loc))
                this.window.getGridPanel().paintCellWithColorAtLoc(offwhite, loc);
        }
        
        for(Location loc: locsInInvalidSegment){
            if(this.board.getEditabilityAtLoc(loc))
                this.window.getGridPanel().paintCellWithColorAtLoc(Color.RED, loc);
        }
        
    }
    
    /**
     * This function updates the board class and then the gui
     * with the grid of a new board object.
     * @param newBoard 
     */
    public void setBoardandGUIWithNewBoard(Board newBoard){

        //update this.board with the grid from newBoard
        this.board.setBoardWithTwoDGrid(newBoard.returnCopyOfGrid(), true);

        //update GUI
        this.window.getGridPanel().loadGridWithInitialGameLayout(this.board.returnCopyOfGrid());
        
    }
    
    
    /** This method still requires the board generation method, that way in case 1, 2, or 3, it can call 
     * the generation method and start the game with the proper difficulty.
     * This method starts a new game, the int gameType parameter
     * indicates whether (0) the user will fill the board, or (1-3) if the computer will fill
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
        this.window.hideEnterButtonFromTopPanel();
        
        switch(gameType){
        
            
            case 0://free up entire board for user to edit
                
                this.window.showEnterButtonFromTopPanel();
                this.window.getGridPanel().setAllCellsEditable();
                this.window.getGridPanel().setAllCellsWithEmptyValue();
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
                //ArrayList<Location> emptyAndEditableLocs = 
                //this.board.makeOnlyLocationsInArrayListEditable(emptyAndEditableLocs);
                
                this.window.getGridPanel().loadGridWithInitialGameLayout(easyTestCellValueGrid);
                
                
                break;
            case 2://populate with medium board
                /*int[][] mediumTestGrid = {{0,0,2,7,1,8,0,4,0},
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
                this.board.makeOnlyLocationsInArrayListEditable(emptyAndEditableLocs);*/
                
                break;
            case 3://populate with difficult board
                /*int[][] difficultTestGrid = {{0,0,2,0,1,8,0,4,0},
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
                this.board.makeOnlyLocationsInArrayListEditable(emptyAndEditableLocs);*/
                
                break;        
        }
        
        
        //If user selected "manual"
        if(gameType == 0){
        
            //display "submit" button
            this.window.showEnterButtonFromTopPanel();
            
            //the rest will be taken care of in the handleEnterButtonBeingPressed method
            //which is called from the timerListener attached to the JButton when it is pressed.
            
        }else{
            //although we call the same method 'getFromUserIfShouldTimeGame()'  whether or not the user
            //is manually entering the board, we must wait for them to click the submit button, and therefore, we cannot
            //allow for the below call to the method to take place if the user is manually entering the board.

            //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
            //http://stackoverflow.com/questions/8396870/joptionpane-yes-or-no-window
            // prompt user for whether or not the timer should be on.

            //first, disable the grid while the user is responding.
            boolean shouldTimeGame = getFromUserIfShouldTimeGame();
            
            if(shouldTimeGame){
                beginTiming();
            }else{
                //If we are not using the timer, clear the timer label
                this.window.setTimerLabel("");
            }
            
        }
        
    }
    
    /**
     * This method is responsible for taking in a commandString and 
     * handling it by either creating the right kind of game, or ending the game.
     * @param commandString 
     */
    @SuppressWarnings("UnnecessaryReturnStatement")
    public void handleMenuAction(String commandString){
        this.window.setTimerLabel("");
        
        
        //this subclass of runnable is used when the user chooses to let the CPU
        //solve the board with either simulated annealing or backtracking
        class UpdateGUIAfterBoardSolved implements Runnable {
            
            private Board solvedBoard;
            
            public UpdateGUIAfterBoardSolved(Board solvedBoard){
                this.solvedBoard = solvedBoard;
            }
            
            @Override
            public void run() {
                SudokuController.this.setBoardandGUIWithNewBoard(this.solvedBoard);
                
            }
        }
        
        
        
        
        int gameType;
        if(displayTimer != null)
            displayTimer.stop();
        if(commandString.equals(this.actionCommands[0])){
            gameType = 0;
            this.startNewGame(gameType);
        }else if(commandString.equals(this.actionCommands[1])){
            gameType = 1;
            this.startNewGame(gameType);
        }else if(commandString.equals(this.actionCommands[2])){
            gameType = 2;
            this.startNewGame(gameType);
        }else if(commandString.equals(this.actionCommands[3])){
            gameType = 3;
            this.startNewGame(gameType);
        }else if(commandString.equals(this.actionCommands[4])){
            //auto solve with backtracking
            
            
            final Runnable runSolveWithBacktrack = new Runnable() {
                @Override
                public void run() {
                    
                    System.out.println("inside solve thread" + System.currentTimeMillis());
                    BacktrackAlgorithm ba = new BacktrackAlgorithm(SudokuController.this.board);
                    solvedBoard = ba.solveBoard(SudokuController.this.board);
                    System.out.println("end of solve thread"+ System.currentTimeMillis());
                    SwingUtilities.invokeLater(new UpdateGUIAfterBoardSolved(solvedBoard));
                    
                }
            };
            
            new Thread(runSolveWithBacktrack).start();
            
            System.out.println("line after solveWithBacktrack.start(); executed"+ System.currentTimeMillis());
            
            
        }else if(commandString.equals(this.actionCommands[5])){
            //auto solve with simulated annealing
        }else if(commandString.equals("Stop")){
            //User does not want to play any more.
            //exit
            //http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
            this.window.setVisible(false);
            this.window.dispose();
            System.exit(0);
        }else{
            System.out.println("Game could not start, invalid command.");
            return;
        }
        
    }
    
    
    public CellValue[][] getCellValueGridOfEmptyValues(){
        CellValue[] nineEmpties = {CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY};
        CellValue[][] returnArray = {nineEmpties,nineEmpties,nineEmpties,nineEmpties,nineEmpties,nineEmpties,nineEmpties,nineEmpties,nineEmpties};
        return returnArray;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleMenuAction(e.getActionCommand());
    }
    
    /**
     * This method repeats an animation the number of times
     * provided by the parameter, to show that the player has won
     * and to make them feel better about their day.
     */
    public void runVictoryAnimation() {
        //set all cells editable so we can edit their color easily through the
        //paintCellsInLocArrayWithColor method.
        //System.out.println("animation run");
        //this.window.getGridPanel().setAllCellsEditable();

        Color[] colorsToBeUsedArray = new Color[5];
        colorsToBeUsedArray[0] = new Color(0, 0, 255);//blue
        colorsToBeUsedArray[1] = new Color(255, 0, 0);//red
        colorsToBeUsedArray[2] = new Color(0, 255, 0);//green
        colorsToBeUsedArray[3] = new Color(0, 0, 0);//black
        colorsToBeUsedArray[4] = new Color(255, 255, 255);//white
        //colorsToBeUsedArray[3] = new Color(128, 0, 128);//purple
        //colorsToBeUsedArray[4] = new Color(255, 165, 0);//orange
        //colorsToBeUsedArray[5] = new Color(128, 128, 128);//gray
        //colorsToBeUsedArray[6] = new Color(255, 255, 0);//yellow
        
        

        this.window.getGridPanel().getCellAtLoc(new Location(0,3)).setBackground(Color.ORANGE);
        /*for(int colorIndex = 0; colorIndex < colorsToBeUsedArray.length; colorIndex++){
            
            Color currentColor = colorsToBeUsedArray[colorIndex];
            for(int x = 0; x < Board.BOARD_SIZE; x++){
                for(int y = 0; y < Board.BOARD_SIZE; y++){
                    this.window.getGridPanel().paintCellWithColorAtLoc(currentColor, new Location(x, y));

                    long millisBeforeDelay = System.currentTimeMillis();
                    long timeElapsed = 0;
                    
                    while(timeElapsed < 20)
                        timeElapsed = System.currentTimeMillis() - millisBeforeDelay;
                    
                    

                }        
            }
        }*/
        
        
        
    }    

    @Override
    public void focusGained(FocusEvent e) {
        //Border origBorder = ((Cell)(e.getComponent())).getBorder();
        //Border modifiedBorder = origBorder.
        //System.out.println("focus gained on cell: " + ((Cell)(e.getComponent())).getLocationInBoard());
   }

    @Override
    public void focusLost(FocusEvent e) {
        //System.out.println("focus lost on cell: " + ((Cell)(e.getComponent())).getLocationInBoard());
    }
    
    
    
    
}

