/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Dunni Adenuga
 Tim Woodford
 Andrew Nyhus
 * Date: Nov 13, 2015
 * Time: 12:09:19 AM
 *
 * Project: csci205FinalProject
 * Package: sudoku.view
 * File: Window
 * Description:
 *
 * ****************************************
 */
package sudoku.view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import sudoku.Location;
import sudoku.controller.SudokuController;

/**
 *
 * @author ajn008
 */
public class Window extends javax.swing.JFrame{
    private GridPanel gridPanel;
    private JLabel statusLabel;
    private JLabel timerLabel;
    private JPanel topPanel;
    public JButton submitManualBoardEntry;
    public JMenuItem newManuallyEnteredBoardMenuItem;
    public JMenuItem newEasyBoardMenuItem;
    public JMenuItem newMediumBoardMenuItem;
    public JMenuItem newDifficultBoardMenuItem;
    public JMenuItem backtrackingItem;
    public JMenuItem simulatedAnnealingItem;
    private JPanel masterPanel;
    private JMenuBar menuBar;
    private JMenu newMenu;
    private JMenu generateBoardOfVaryingDifficulty;
    public JMenu solveMenu;
    
    
    /**
     * Creates new form Window
     */
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        
            this.setMinimumSize(new Dimension(700, 700));
            this.setSize(600, 600);
            this.setBounds(0, 0, 600, 600);
            this.gridPanel = new GridPanel();
            
            
            
                        
            
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        
        
    }

            
    public void init1(){
            this.gridPanel.setBounds(200, 200, 400, 400);
            
            this.statusLabel = new JLabel("Welcome to Sudoku!");
            this.statusLabel.setHorizontalTextPosition(JLabel.CENTER);
            
            this.timerLabel = new JLabel("");
            this.timerLabel.setHorizontalTextPosition(JLabel.CENTER);
            
            this.topPanel = new JPanel();
            this.topPanel.setLayout(new BoxLayout(this.topPanel, BoxLayout.X_AXIS));
            this.topPanel.setMinimumSize(new Dimension(700, 200));
            
            this.submitManualBoardEntry = new JButton("Enter");            

            this.topPanel.add(this.submitManualBoardEntry);            
            
            solveMenu = new JMenu("CPU Solve");
            
            simulatedAnnealingItem = new JMenuItem("by Simulated Annealing");
            backtrackingItem = new JMenuItem("by Backtracking");
    
            simulatedAnnealingItem.setActionCommand("Simulated Annealing");
            backtrackingItem.setActionCommand("Backtracking");

            
            this.solveMenu.add(this.backtrackingItem);
            this.solveMenu.add(this.simulatedAnnealingItem);
        
            
            
            this.topPanel.add(this.statusLabel);
            this.topPanel.add(this.timerLabel);
            
            
            masterPanel = new JPanel();
            masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
            masterPanel.add(this.topPanel);
            masterPanel.add(this.gridPanel);

            getContentPane().add(masterPanel);
            
            this.hideEnterButtonFromTopPanel();
            
            //this.gridPanel.paintOuterBorderWithColor(SudokuController.offwhite);
            //this.gridPanel.paintAllCellsWithColor(SudokuController.offwhite);

            //Menu Bar Stuff Begins
            //https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
            //http://alvinalexander.com/java/java-menubar-example-jmenubar-jframe
            //-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-
            
            menuBar = new JMenuBar();

            newMenu = new JMenu("New");

            newManuallyEnteredBoardMenuItem = new JMenuItem("Board (Manually Entered)");
            newManuallyEnteredBoardMenuItem.setActionCommand("Enter My Own Board");

            generateBoardOfVaryingDifficulty = new JMenu("Board (Computer Generated)");

            newEasyBoardMenuItem = new JMenuItem("Easy");
            newEasyBoardMenuItem.setActionCommand("Let the computer make my board (Difficulty:Easy)");

            newMediumBoardMenuItem = new JMenuItem("Medium");
            newMediumBoardMenuItem.setActionCommand("Let the computer make my board (Difficulty:Medium)");

            newDifficultBoardMenuItem = new JMenuItem("Hard");
            newDifficultBoardMenuItem.setActionCommand("Let the computer make my board (Difficulty:Hard)");
            

            
            
    }
    
    
    /**
     * The purpose of this method is to allow for the controller to attach a listener to
     * the necessary components in the gui before they are added in.
     */
    public void init2() {
        
            menuBar.add(newMenu);
            this.menuBar.add(this.solveMenu);
            
            newMenu.add(newManuallyEnteredBoardMenuItem);
            newMenu.add(generateBoardOfVaryingDifficulty);
            
            generateBoardOfVaryingDifficulty.add(newEasyBoardMenuItem);
            generateBoardOfVaryingDifficulty.add(newMediumBoardMenuItem);           
            generateBoardOfVaryingDifficulty.add(newDifficultBoardMenuItem);
            this.setJMenuBar(menuBar);            
        
        
        pack();        
        setVisible(true);
        initComponents();

        
        
        
    }
    
    
    /**
     * This method is called by the GridPanel class when a Cell is edited,
     * and in response this method notifies the controller and passes in a 2d array
     * of the CellValue objects in the grid.
     * @param s
     */
    /*public void notifySudokuControllerOfBoardUpdates(){        
        CellValue[][] currentFieldValues = this.gridPanel.getCellValueArray();
        this.controller.boardWasUpdated(currentFieldValues);
    }/*
    
    
    
    /**
     * Takes in String s and sets it to the text of the label on the top of the screen.
     * @param s 
     */
    public void setStatusLabel(String s){
        String newStatus = s + "        ";
        this.statusLabel.setText(newStatus);
    }
    
    
    /**
     * Adds the Enter button for when the user is entering a manual board.
     */
    public void showEnterButtonFromTopPanel(){
        this.submitManualBoardEntry.setVisible(true);
    }
    
    
    /**
     * sets editability of the Cell at location loc with the boolean value newValue
     * @param loc
     * @param newValue 
     */
    public void setCellEditability(Location loc, boolean newValue){
        this.gridPanel.setEditabilityAtLoc(newValue, loc);
    }
    
    /**
     * Removes the Enter Button for when the user is done entering the manual board.
     */
    public void hideEnterButtonFromTopPanel(){
        this.submitManualBoardEntry.setVisible(false);
    }
    
    
    /**
     * Updates the JLabel that represents the elapsed time since beginning of the game.
     * @param s 
     */
    public void setTimerLabel(String s){
        if(!s.equals("")){
            this.timerLabel.setText("Time elapsed (mm:ss): " + s);        
        }else{
            this.timerLabel.setText("");
        }
    }
    
    /**
     * This method allows the SudokuController to access the gridPanel.
     * @return this.gridPanel
     */
    public GridPanel getGridPanel(){
        return this.gridPanel;
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
