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
import SudokuBoardGenerator.SudokuBoardGenerator;
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
import sudoku.solvers.DeterministicSquareFinder;
import sudoku.solvers.SudokuSolver;
import sudoku.solvers.cga.CGASolver;
import sudoku.solvers.sa.SASolver;
import sudoku.view.Cell;
import sudoku.view.GridPanel;
import sudoku.view.Window;

/**
 *
 * @author ajn008
 */
public class SudokuController implements ActionListener, FocusListener {

    private Board board;
    private Window window;
    private final String[] actionCommands;
    private final String instructions;
    private final String aboutString;
    public static final Color offwhite_unfocused = new Color(255, 244, 232);
    public static final Color lightgray_unfocused = new Color(211, 211, 211);
    public static final Color offwhite_focused = new Color(240, 229, 217);
    public static final Color lightgray_focused = new Color(196, 196, 196);

    private boolean setWindowWasCalled = false;
    private Timer displayTimer;
    private long beginningOfGameTime;
    private ActionListener timerListener;
    private Board solvedBoard;//this variable is only to be used within the threads responsible for solving the board.
    private boolean timerIsRunning = false;

    public SudokuController() {
        this.actionCommands = new String[9];
        this.actionCommands[0] = "Enter My Own Board";
        this.actionCommands[1] = "Let the computer make my board (Difficulty:Easy)";
        this.actionCommands[2] = "Let the computer make my board (Difficulty:Medium)";
        this.actionCommands[3] = "Let the computer make my board (Difficulty:Hard)";
        this.actionCommands[4] = "Backtracking";
        this.actionCommands[5] = "Simulated Annealing";
        this.actionCommands[6] = "Cultural Genetics";
        this.actionCommands[7] = "HowTo";
        this.actionCommands[8] = "About";

        this.instructions = "You must fill the board in such a manner that every column, row, and square\nhas no duplicates or empty spaces, and contains every value from 1-9.\n\nIf the color red is painted over a row, column or square, that means it is invalid and contains a duplicate.";
        this.aboutString = "This awesome game of Sudoku is brought to you by:\nDunni Adenuga\nAndrew Nyhus\nTimothy Woodford";
        this.initWindow();

        String usersInput = this.getUsersChoice(true);

        this.handleMenuAction(usersInput);

    }

    /**
     * This method is called only once, it initializes the view and the model
     * setting everything up.
     */
    private void initWindow() {
        setWindowWasCalled = true;

        this.window = new Window();

        //add cells
        this.addCellsToGridPanel();
        //call init1
        this.window.init1();

        this.window.backtrackingItem.addActionListener(this);
        this.window.simulatedAnnealingItem.addActionListener(this);
        this.window.culturalGeneticsItem.addActionListener(this);
        this.window.newEasyBoardMenuItem.addActionListener(this);
        this.window.newMediumBoardMenuItem.addActionListener(this);
        this.window.newDifficultBoardMenuItem.addActionListener(this);
        this.window.newManuallyEnteredBoardMenuItem.addActionListener(this);
        this.window.aboutItem.addActionListener(this);
        this.window.howToPlayItem.addActionListener(this);

        this.window.submitManualBoardEntry.
                addActionListener((ActionEvent e) -> {
                    //if button is pressed, this method is called
                    handleEnterButtonBeingPressed();
                });

        this.window.init2();

        this.window.setVisible(true);
        //this.window.getGridPanel().paintAllCellsWithColor(SudokuController.offwhite);

        CellValue[][] initialGridForBoard = this.window.getGridPanel().
                getCellValueArray();
        this.board = new Board(initialGridForBoard);

        this.window.getGridPanel().paintOuterBorderWithColor(offwhite_unfocused);

        //this.runVictoryAnimation();
        JOptionPane.showMessageDialog(this.window, this.instructions);

    }

    /**
     * Used to set up the Cell objects, accomplishing this from the controller
     * is crucial because otherwise it is difficult to notify the controller of
     * changes without duplicating controller objects.
     */
    private void addCellsToGridPanel() {
        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                this.window.getGridPanel().
                        setCellAtLoc(new Location(x, y), new Cell(new Location(x, y), true, this));
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).
                        setBackground(SudokuController.offwhite_unfocused);

                int topBorderPixelThickness = 1, bottomBorderPixelThickness, leftBorderPixelThickness = 1, rightBorderPixelThickness;

                if (y == 2 || y == 5) {
                    bottomBorderPixelThickness = 4;
                } else {
                    bottomBorderPixelThickness = 1;
                }

                if (x == 2 || x == 5) {
                    rightBorderPixelThickness = 4;
                } else {
                    rightBorderPixelThickness = 1;
                }

                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).
                        addFocusListener(this);
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).
                        setBorder(BorderFactory.
                                createMatteBorder(topBorderPixelThickness, leftBorderPixelThickness, rightBorderPixelThickness, bottomBorderPixelThickness, Color.BLACK));
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).
                        setFont(new Font("Arial Bold", Font.ITALIC, 26));
                this.window.getGridPanel().getCellAtLoc(new Location(x, y)).
                        createTextFieldLimitDocument(1);

                this.window.getGridPanel().add(this.window.getGridPanel().
                        getCellAtLoc(new Location(x, y)));

            }
        }

    }

    /**
     * Prompts user for choice, and does not let user
     *
     * @param isFirstGameSession
     * @return String that represents the choice of the user
     */
    public String getUsersChoice(boolean isFirstGameSession) {
        String titleString;
        String[] choices = Arrays.copyOfRange(this.actionCommands, 0, 4);
        if (isFirstGameSession) {
            titleString = "Welcome to Sudoku!";

            String usersChoice = (String) JOptionPane.
                    showInputDialog(null, "Please choose one of the following...", titleString, JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
            //user cancelled or xed out
            while (usersChoice == null) {
                //tell user to give input
                JOptionPane.
                        showMessageDialog(this.window, "You must pick one of the options.");
                //get users input
                usersChoice = (String) JOptionPane.
                        showInputDialog(null, "Please choose one of the following...", titleString, JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
            }

            return usersChoice;
        } else {
            titleString = "Play Again?";
            String usersChoice = (String) JOptionPane.
                    showInputDialog(null, "If you want to continue, choose one of the following. Click Cancel or the X button to exit", titleString, JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
            if (usersChoice == null) {
                return "Stop";
            } else {
                return usersChoice;
            }
        }
    }

    /**
     * this is the method that is called when somebody clicks the Enter button
     * in the top of the GUI
     */
    public void handleEnterButtonBeingPressed() {

        //before doing anything, we must validate that there are no issues
        //with the manually entered board.
        //1. get the int 2d array.
        int[][] intArrayOfGridEntered = this.window.getGridPanel().getIntArray();

        //2. setup board object from the 2d int array
        Board tempBoard = new Board(intArrayOfGridEntered);

        //if tempBoard is invalid, tell the user, and do not set up game
        if (!tempBoard.isValid()) {
            String displayString = "The board you entered was invalid";

            JOptionPane.showMessageDialog(this.window, displayString);

        } else {
            //otherwise, set up game with the user defined grid
            this.window.hideEnterButtonFromTopPanel();

            CellValue[][] currentGridCellValues = this.window.getGridPanel().
                    getCellValueArray();

            GridPanel panel = this.window.getGridPanel();

            //once the submit button is clicked, iterate through every cell
            for (int i = 0; i < Board.BOARD_SIZE; i++) {

                for (int j = 0; j < Board.BOARD_SIZE; j++) {
                    //if the current cell is not empty, cell.setCellFieldEditable(false)
                    if (!currentGridCellValues[i][j].isEmpty()) {
                        this.window.getGridPanel().
                                setEditabilityAtLoc(false, new Location(i, j));
                        //this.window.getGridPanel().paintCellWithColorAtLoc(Color.lightGray, new Location(i, j));
                        this.board.
                                setEditabilityAtLoc(new Location(i, j), false);

                    } else {
                        this.board.setEditabilityAtLoc(new Location(i, j), true);
                        //this.window.getGridPanel().paintCellWithColorAtLoc(offwhite, new Location(i, j));
                    }
                    this.board.
                            setValueAtLoc(new Location(i, j), currentGridCellValues[i][j]);
                }
            }

            boolean shouldTimeGame = getFromUserIfShouldTimeGame();

            if (shouldTimeGame) {
                beginTiming();
            } else {
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
        //this.window.getGridPanel().setEnabled(false);
        //Get user response about timer
        int gameShouldBeTimed = JOptionPane.
                showConfirmDialog(null, "Should this round be timed?", "Timer?", JOptionPane.YES_NO_OPTION);
        //re-enable the grid.
        //this.window.getGridPanel().setEnabled(true);

        return (gameShouldBeTimed == JOptionPane.YES_OPTION);

    }

    /**
     * This method starts the timer at the beginning of the game.
     */
    private void beginTiming() {
        timerIsRunning = true;
        this.beginningOfGameTime = System.currentTimeMillis();

        if (displayTimer == null) {
            timerListener = new ActionListener() {
                //http://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionEvent.html
                @Override
                public void actionPerformed(ActionEvent event) {
                    long elapsedTime = event.getWhen() - beginningOfGameTime;

                    long numSeconds = elapsedTime / 1000;
                    //long numMilliseconds = elapsedTime - numSeconds*1000;
                    long numMinutes = 0;

                    while (numSeconds > 60) {
                        numMinutes++;
                        numSeconds -= 60;
                    }
                    String currentTimeString = String.
                            format("%02d:%02d", numMinutes, numSeconds);
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
     * This method is called by the Window class in method
     * Window.notifySudokuControllerOfBoardUpdates() which occurs when the user
     * updates one of the Cells (JTextField) in the grid.
     */
    public void updateBoard() {

        //update Board Class before anything else
        this.board.setBoardWithTwoDGrid(this.window.getGridPanel().
                getCellValueArray());

        if (this.board.isCompleted()) {
            String congratsString;
            if (displayTimer != null && timerIsRunning) {

                displayTimer.stop();
                timerIsRunning = false;
                long timeToSolve = System.currentTimeMillis() - this.beginningOfGameTime;
                long numSeconds = timeToSolve / 1000;
                long numMinutes = 0;

                while (numSeconds > 60) {
                    numSeconds -= 60;
                    numMinutes++;
                }

                congratsString = "Congratulations, the board was solved in " + numMinutes + " minutes, and " + numSeconds + " seconds!";
            } else {
                congratsString = "Congratulations, the board was solved!";

            }
            //call the method in GridPanel that handles the "victory" animation
            //this.runVictoryAnimation();

            //Paint Grid's outer border green
            this.window.getGridPanel().paintOuterBorderWithColor(Color.GREEN);
            //block board until user makes decision
            this.window.getGridPanel().setAllCellsNotEditable();
            //Edit Status Label to congratulate the user and prompt them to make a decision
            this.window.setStatusLabel("Board is 100% complete!");

            //prompt user for decision
            JOptionPane.showMessageDialog(this.window, congratsString);

            //store decision as a string to represent a given command
            String usersInput = this.getUsersChoice(false);

            if (usersInput.equals(this.actionCommands[0]) || usersInput.
                    equals(this.actionCommands[1]) || usersInput.
                    equals(this.actionCommands[2]) || usersInput.
                    equals(this.actionCommands[3])) {
                //close window because if above condition is true, then startNewGame
                //will be called by handleMenuAction.  In startNewGame, the new window is created
                //so here we must close this.window
                this.window.dispose();
                this.initWindow();

            }

            //pass on command to handleMenuAction
            this.handleMenuAction(usersInput);

        }
        int numTotalSegments = Board.BOARD_SIZE * 3;
        int numUnsolvedSegments = this.board.getNumOfUnsolvedSegments();

        double percentSegmentsCompleted = ((double) (numTotalSegments - numUnsolvedSegments) / (double) (numTotalSegments)) * 100;
        String newStatusLabelString = "Board is " + (int) percentSegmentsCompleted + "% complete";

        this.window.setStatusLabel(newStatusLabelString);

        this.updateBoardColors();

    }

    /**
     * Paints valid boardsegments offwhite, and invalid ones red, the cells that
     * are not editable will not be painted.
     */
    public void updateBoardColors() {
        //ArrayList<Location> locsInValidSegment = new ArrayList();
        ArrayList<Location> locsInInvalidSegment = new ArrayList();

        for (int i = 0; i < Board.BOARD_SIZE; i++) {

            Row currentRow = this.board.getRow(i);
            Location[] rowLocs = currentRow.getArrayOfLocationsInSegment();

            Col currentCol = this.board.getCol(i);
            Location[] colLocs = currentCol.getArrayOfLocationsInSegment();

            Square currentSquare = this.board.getSquare(i);
            Location[] squareLocs = currentSquare.getArrayOfLocationsInSegment();

            if (currentRow.isValid()) {
                //paint it offwhite_unfocused where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(offwhite_unfocused, rowLocs);
                for (Location loc : rowLocs) {
                    if (this.board.getEditabilityAtLoc(loc)) {
                        this.window.getGridPanel().
                                paintCellWithColorAtLoc(offwhite_unfocused, loc);
                    } else {
                        this.window.getGridPanel().
                                paintCellWithColorAtLoc(lightgray_unfocused, loc);

                    }
                }

            } else {
                //paint it red where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(Color.RED, rowLocs);
                for (Location loc : rowLocs) {
                    //if(this.board.getEditabilityAtLoc(loc)){
                    this.window.getGridPanel().
                            paintCellWithColorAtLoc(Color.RED, loc);
                    locsInInvalidSegment.add(loc);
                    //}
                }
            }

            if (currentCol.isValid()) {
                //paint it offwhite_unfocused where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(offwhite_unfocused, colLocs);
                for (Location loc : colLocs) {
                    if (this.board.getEditabilityAtLoc(loc)) {
                        this.window.getGridPanel().
                                paintCellWithColorAtLoc(offwhite_unfocused, loc);
                    } else {
                        this.window.getGridPanel().
                                paintCellWithColorAtLoc(lightgray_unfocused, loc);

                    }
                }

            } else {
                for (Location loc : colLocs) {
                    //if(this.board.getEditabilityAtLoc(loc)){
                    this.window.getGridPanel().
                            paintCellWithColorAtLoc(Color.RED, loc);
                    locsInInvalidSegment.add(loc);
                    //}
                }

            }

            if (currentSquare.isValid()) {
                //paint it offwhite_unfocused where editable
                //this.window.getGridPanel().paintCellsInLocArrayWithColor(offwhite_unfocused, squareLocs);
                for (Location loc : squareLocs) {
                    if (this.board.getEditabilityAtLoc(loc)) {
                        this.window.getGridPanel().
                                paintCellWithColorAtLoc(offwhite_unfocused, loc);
                    } else {
                        this.window.getGridPanel().
                                paintCellWithColorAtLoc(lightgray_unfocused, loc);

                    }
                }

            } else {
                for (Location loc : squareLocs) {
                    //if(this.board.getEditabilityAtLoc(loc)){
                    this.window.getGridPanel().
                            paintCellWithColorAtLoc(Color.RED, loc);
                    locsInInvalidSegment.add(loc);
                    //}
                }
            }
        }
        //repaint invalid cells in case they were painted over by being in a different
        //segment that is considered to be valid
        for (Location loc : locsInInvalidSegment) {
            this.window.getGridPanel().paintCellWithColorAtLoc(Color.RED, loc);
        }

    }

    /**
     * This method still requires the board generation method, that way in case
     * 1, 2, or 3, it can call the generation method and start the game with the
     * proper difficulty. This method starts a new game, the int gameType
     * parameter indicates whether (0) the user will fill the board, or (1-3) if
     * the computer will fill If the computer fills, this int indicates the
     * selected level of difficulty.
     *
     * @param gameType
     */
    public void startNewGame(int gameType) {

        //1. close current window
        //this.closeWindow();
        //2. create new window
        //this.initWindow();
        //3. populate according to gameType
        this.window.getGridPanel().paintOuterBorderWithColor(offwhite_unfocused);
        //this.window.getGridPanel().paintAllCellsWithColor(offwhite);

        //fill board
        this.window.hideEnterButtonFromTopPanel();

        switch (gameType) {

            case 0://free up entire board for user to edit

                //allow user to enter values into any call
                this.window.getGridPanel().setAllCellsEditable();

                //enter CellValue.EMPTY as the value for every cell
                this.window.getGridPanel().clearValuesInFields();

                //display enter button
                this.window.showEnterButtonFromTopPanel();

                //CellValue[][] emptyGrid = this.window.getGridPanel().getCellValueArray();
                //this.board = new Board(emptyGrid);
                break;

            case 1://populate with easy board

                SudokuBoardGenerator easyBoardGen = new SudokuBoardGenerator(0);
                //generates Board object with easy difficulty
                Board easyBoard = easyBoardGen.generateBoard();

                //sets easy difficulty board to this.board.
                this.board = easyBoard;

                //update GridPanel with easyboard
                this.window.getGridPanel().setGridWithBoard(easyBoard);

                break;
            case 2://populate with medium board

                SudokuBoardGenerator mediumBoardGen = new SudokuBoardGenerator(1);

                //generates Board object with medium difficulty
                Board mediumBoard = mediumBoardGen.generateBoard();

                //sets medium difficulty board to this.board.
                this.board = mediumBoard;

                //update GridPanel with easyboard
                this.window.getGridPanel().setGridWithBoard(mediumBoard);

                break;
            case 3://populate with difficult board

                SudokuBoardGenerator difficultBoardGen = new SudokuBoardGenerator(2);

                //generates Board object with difficult difficulty
                Board difficultBoard = difficultBoardGen.generateBoard();

                //sets difficult difficulty board to this.board.
                this.board = difficultBoard;

                //update GridPanel with difficult board
                this.window.getGridPanel().setGridWithBoard(difficultBoard);

                break;
        }

        //If user did not choose to manually enter board, then we can prompt user
        //to time or not, and then begin.
        if (gameType != 0) {
            //although we call the same method 'getFromUserIfShouldTimeGame()'  whether or not the user
            //is manually entering the board, we must wait for them to click the submit button, and therefore, we cannot
            //allow for the below call to the method to take place if the user is manually entering the board.

            //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
            //http://stackoverflow.com/questions/8396870/joptionpane-yes-or-no-window
            // prompt user for whether or not the timer should be on.
            //first, disable the grid while the user is responding.
            boolean shouldTimeGame = getFromUserIfShouldTimeGame();

            if (shouldTimeGame) {
                beginTiming();
            } else {
                //If we are not using the timer, clear the timer label
                this.window.setTimerLabel("");
            }

        }

    }

    /**
     * This method is responsible for taking in a commandString and handling it
     * by either creating the right kind of game, or ending the game. It also
     * handles any of the clicks by the user on the MenuBarItems.
     *
     * @param commandString
     */
    @SuppressWarnings("UnnecessaryReturnStatement")
    public void handleMenuAction(String commandString) {
        this.window.setTimerLabel("");

        int gameType;
        if (displayTimer != null) {
            displayTimer.stop();
        }
        if (commandString.equals(this.actionCommands[0])) {
            gameType = 0;
            this.startNewGame(gameType);
        } else if (commandString.equals(this.actionCommands[1])) {
            gameType = 1;
            this.startNewGame(gameType);
        } else if (commandString.equals(this.actionCommands[2])) {
            gameType = 2;
            this.startNewGame(gameType);
        } else if (commandString.equals(this.actionCommands[3])) {
            gameType = 3;
            this.startNewGame(gameType);
        } else if (commandString.equals(this.actionCommands[4])) {
            //auto solve with backtracking
            final BacktrackAlgorithm ba = new BacktrackAlgorithm(SudokuController.this.board);
            if (solveBoard(ba)) {
                return;
            }

        } else if (commandString.equals(this.actionCommands[5])) {
            //INSERT SIMULATED ANNEALING
            //auto solve with simulated annealing
            final SudokuSolver sa = new DeterministicSquareFinder(new SASolver());
            System.out.println("Simulated annealing");
            solveBoard(sa);
        } else if (commandString.equals(this.actionCommands[6])) {
            //INSERT CULTURAL GENETICS
            //auto solve with cultural genetics
            System.out.println("Cultural genetics");
            final SudokuSolver cga = new DeterministicSquareFinder(new CGASolver());
            solveBoard(cga);
        } else if (commandString.equals(this.actionCommands[7])) {

            JOptionPane.showMessageDialog(this.window, this.instructions);

        } else if (commandString.equals(this.actionCommands[8])) {

            JOptionPane.showMessageDialog(this.window, this.aboutString);

        } else if (commandString.equals("Stop")) {
            //User does not want to play any more.
            closeWindow();
            //quit program completely
            System.exit(0);
        } else {
            System.out.println("Game could not start, invalid command.");
            return;
        }

    }

    private boolean solveBoard(final SudokuSolver ba) throws HeadlessException {
        if (!this.board.isValid()) {
            JOptionPane.
                    showMessageDialog(this.window, "The Computer cannot solve an invalid board.");
            return true;
        }
        final Runnable runSolveWithBacktrack;
        runSolveWithBacktrack = () -> {
            solvedBoard = ba.solveBoard(SudokuController.this.board);

            SwingUtilities.
                    invokeLater(new UpdateGUIAfterBoardSolved(solvedBoard));
        };
        new Thread(runSolveWithBacktrack).start();
        return false;
    }

    //this subclass of runnable is used when the user chooses to let the CPU
    //solve the board with either simulated annealing or backtracking
    class UpdateGUIAfterBoardSolved implements Runnable {

        private final Board solvedBoard;

        public UpdateGUIAfterBoardSolved(Board solvedBoard) {
            this.solvedBoard = solvedBoard;
        }

        @Override
        public void run() {
            SudokuController.this.
                    setBoardandGUIWithNewBoard(this.solvedBoard);

        }
    }

    private void closeWindow() {
        //exit window
        //http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        this.window.setVisible(false);
        this.window.dispose();
        //System.exit(0);
    }

    public CellValue[][] getCellValueGridOfEmptyValues() {
        CellValue[] nineEmpties = {CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY, CellValue.EMPTY};
        CellValue[][] returnArray = {nineEmpties, nineEmpties, nineEmpties, nineEmpties, nineEmpties, nineEmpties, nineEmpties, nineEmpties, nineEmpties};
        return returnArray;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleMenuAction(e.getActionCommand());
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (((Cell) (e.getComponent())).isEditable()) {
            ((Cell) (e.getComponent())).setBackground(offwhite_focused);
        } else {
            ((Cell) (e.getComponent())).setBackground(lightgray_focused);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (((Cell) (e.getComponent())).isEditable()) {
            ((Cell) (e.getComponent())).setBackground(offwhite_unfocused);
        } else {
            ((Cell) (e.getComponent())).setBackground(lightgray_unfocused);
        }

    }

    private void setBoardandGUIWithNewBoard(Board solvedBoard) {
        //System.out.println("at p board:\n");
        //this.board.printGrid(this.board.returnCopyOfGrid());

        this.window.getGridPanel().setGridWithBoard(solvedBoard);
        this.updateBoard();

    }

}
