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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import sudoku.CellValue;
import sudoku.Location;
import sudoku.controller.SudokuController;

/**
 *
 * @author ajn008
 */
public class Window extends javax.swing.JFrame implements ActionListener{
    private GridPanel gridPanel;
    private JLabel statusLabel;
    private JLabel timerLabel;
    private JPanel topPanel;
    protected SudokuController controller;
    private JButton submitManualBoardEntry;

    /**
     * Creates new form Window
     * @param controller
     */
    public Window(SudokuController controller) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        init(controller);
        
        pack();
        setVisible(true);
        initComponents();
        
        
    }


    /**
     * This function is to cut down on clutter in the constructor
     * @param controller 
     */
    private void init(final SudokuController controller) {
        this.controller = controller;
        this.setMinimumSize(new Dimension(700, 700));
        this.setSize(600, 600);
        this.setBounds(0, 0, 600, 600);
        this.gridPanel = new GridPanel(this);
        this.gridPanel.setBounds(200, 200, 400, 400);
        
        this.statusLabel = new JLabel("Welcome to Sudoku!        ");
        this.statusLabel.setHorizontalTextPosition(JLabel.CENTER);

        this.timerLabel = new JLabel("");
        this.timerLabel.setHorizontalTextPosition(JLabel.CENTER);
        
        
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new BoxLayout(this.topPanel, BoxLayout.X_AXIS));
        this.topPanel.setMinimumSize(new Dimension(700, 200));

        this.submitManualBoardEntry = new JButton("Enter");
                
        this.topPanel.add(this.submitManualBoardEntry);
        this.submitManualBoardEntry.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //if button is pressed, this method is called
                controller.handleEnterButtonBeingPressed();
            }
        });
        
        
        
        this.topPanel.add(this.statusLabel);
        this.topPanel.add(this.timerLabel);
        this.topPanel.add(this.submitManualBoardEntry);
        
        this.hideEnterButtonFromTopPanel();
        
        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
        masterPanel.add(this.topPanel);
        masterPanel.add(this.gridPanel);
        
        getContentPane().add(masterPanel);

        this.gridPanel.paintAllCellsWithColor(SudokuController.offwhite);

        //Menu Bar Stuff Begins
        //https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
        //http://alvinalexander.com/java/java-menubar-example-jmenubar-jframe
        //-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-
        JMenuBar menuBar = new JMenuBar();
        
        JMenu newMenu = new JMenu("New");
        menuBar.add(newMenu);
        
        JMenuItem newManuallyEnteredBoard = new JMenuItem("Board (Manually Entered)");
        newManuallyEnteredBoard.setActionCommand("Enter My Own Board");
        newManuallyEnteredBoard.addActionListener(this);
        newMenu.add(newManuallyEnteredBoard);
        
        JMenu generateBoardOfVaryingDifficulty = new JMenu("Board (Computer Generated)");
        newMenu.add(generateBoardOfVaryingDifficulty);
        
        JMenuItem newEasyBoard = new JMenuItem("Easy");
        newEasyBoard.setActionCommand("Let the computer make my board (Difficulty:Easy)");
        newEasyBoard.addActionListener(this);
        generateBoardOfVaryingDifficulty.add(newEasyBoard);
        
        JMenuItem newMediumBoard = new JMenuItem("Medium");
        newMediumBoard.setActionCommand("Let the computer make my board (Difficulty:Medium)");
        newMediumBoard.addActionListener(this);
        generateBoardOfVaryingDifficulty.add(newMediumBoard);
        
        JMenuItem newDifficultBoard = new JMenuItem("Hard");
        newDifficultBoard.setActionCommand("Let the computer make my board (Difficulty:Hard)");
        newDifficultBoard.addActionListener(this);
        generateBoardOfVaryingDifficulty.add(newDifficultBoard);
        this.setJMenuBar(menuBar);
        //-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-
        //Menu Stuff is Over
    }
    
    
    /**
     * This method is called by the GridPanel class when a Cell is edited,
     * and in response this method notifies the controller and passes in a 2d array
     * of the CellValue objects in the grid.
     */
    public void notifySudokuControllerOfBoardUpdates(){        
        CellValue[][] currentFieldValues = this.gridPanel.getCellValueArray();
        this.controller.boardWasUpdated(currentFieldValues);
    }
    
    
    
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

    
    @Override
    public void actionPerformed(ActionEvent e) {
            this.controller.handleMenuAction(e.getActionCommand());
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
