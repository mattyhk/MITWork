package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.JottoModel;

/**
 * Creates a GUI for the client to interact with the server
 * The GUI is thread safe as the invariants that:
 * the puzzleNumber label shows the current puzzle
 * the guessTable table shows all guesses made in order
 * are handled in a thread safe manner
 * The puzzleNumber is updated by the synchronized method handleNewPuzzle() and therefore correct locks are placed upon the class object
 * The guessTable is updated by the synchronized handleResponses() method
 */
@SuppressWarnings("serial")
public class JottoGUI extends JFrame {

    private JButton newPuzzleButton;
    private JTextField newPuzzleNumber;
    private JLabel puzzleNumber;
    private JTextField guess;
    private JTable guessTable;
    private JLabel guessLabel;
    private Integer currentPuzzle;
    private String[] guessTableColumnNames = new String[3];
    private int rowNumber;
    private JottoModel model;
    private MyModel tableModel;

    public JottoGUI() {
        rowNumber = 0;
        currentPuzzle = makeRandomNumber();
        guessTableColumnNames[0] = "guess";
        guessTableColumnNames[1] = "# characters in common";
        guessTableColumnNames[2] = "# characters in correct position";
        model = new JottoModel(new ArrayList<String>());

        newPuzzleButton = new JButton();
        newPuzzleButton.setName("newPuzzleButton");
        newPuzzleButton.setText("New Puzzle");
        newPuzzleNumber = new JTextField(20);
        newPuzzleNumber.setName("newPuzzleNumber");
        puzzleNumber = new JLabel();
        puzzleNumber.setName("puzzleNumber");
        puzzleNumber.setText("Puzzle #" + currentPuzzle.toString());
        guess = new JTextField(20);
        guess.setName("guess");
        guessLabel = new JLabel();
        guessLabel.setText("Type a guess here:");

        tableModel = new MyModel();
        guessTable = new JTable(tableModel);
        guessTable.setName("guessTable");
        tableModel.addColumn("guess");
        tableModel.addColumn("# characters in common");
        tableModel.addColumn("# characters in correct position");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Create the horizontal group
        layout.setHorizontalGroup(layout
                .createParallelGroup()
                .addComponent(guessTable.getTableHeader())
                .addComponent(guessTable)
                .addGroup(
                        layout.createSequentialGroup()
                                .addGroup(
                                        layout.createParallelGroup()
                                                .addComponent(puzzleNumber)
                                                .addComponent(guessLabel))
                                .addGroup(
                                        layout.createParallelGroup()
                                                .addComponent(guess)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        newPuzzleButton)
                                                                .addComponent(
                                                                        newPuzzleNumber)))));

        // Create the vertical group
        layout.setVerticalGroup(layout
                .createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup().addComponent(puzzleNumber)
                                .addComponent(newPuzzleButton)
                                .addComponent(newPuzzleNumber))
                .addGroup(
                        layout.createParallelGroup().addComponent(guessLabel)
                                .addComponent(guess))
                .addComponent(guessTable.getTableHeader())
                .addComponent(guessTable));
        
        layout.linkSize(SwingConstants.HORIZONTAL, newPuzzleButton);

        // Adding Action Listener to newPuzzleButton
        // - calls newPuzzleReader() 
        newPuzzleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                handleNewPuzzle();
            }
        });

        // Adding Action Listener to newPuzzleNumber
        // - if the enter button is hit, calls newPuzzleReader()
        newPuzzleNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                handleNewPuzzle();
            }
        });

        // Adding Action Listener to guess TextField
        // - if the enter button is hit, calls makeGuess
        // This should send a guess to the Blocking Queue of the model and
        // create a new row in the table
        // When the response from the Model is received, it should be inputed
        // into the table in the correct row -
        // however the next guess should be entered already if there is one
        guess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String clientGuess = guess.getText();
                guess.setText("");
                clientGuess.replaceAll("\\s", "%20");
                addGuessFirst(clientGuess);
                model.addGuess(clientGuess);
                Thread t = new Thread(new Runnable() {
                    public void run(){
                        ArrayList<String> responses = model.handleGuesses(
                                currentPuzzle, new ArrayList<String>());
                        handleResponses(responses);
                    }
                });
                t.start();
            }
        });

        pack();

    }
    
    /**
     * Handles the case when either the enter button is pressed after typing in the newPuzzleNumber text field or the newPuzzleButton is depressed
     * This method is synchronized to protect the invariant the GUI displays the current puzzle number correctly and so is thread safe
     * @modifies If the new puzzle requested is different from the current puzzle or a random puzzle is generated by newPuzzleReader() 
     * then the current puzzle number is updated and the guessTable is cleared
     */
    private synchronized void handleNewPuzzle(){
        int nextPuzzle = newPuzzleReader();
        if (nextPuzzle != currentPuzzle) {
            currentPuzzle = newPuzzleReader();
            puzzleNumber.setText("Puzzle #" + currentPuzzle.toString());
            if (!(newPuzzleNumber.getText().isEmpty())){
                newPuzzleNumber.setText("");
            }
            puzzleNumber.updateUI();
            clearTable();
        }
    }

    /**
     * Handles the responses generated by the table model
     * 
     * @param r
     *            The list of responses
     * @modifies The table rows corresponding to each guess is updated with the
     *           proper response
     */
    private synchronized void handleResponses(ArrayList<String> r) {
        while (!(r.isEmpty())) {
            String response = r.remove(0);
            String[] row = readResponse(response);
            tableModel.setValueAt(row[0], rowNumber, 1);
            tableModel.setValueAt(row[1], rowNumber, 2);
            rowNumber++;
        }
    }

    /**
     * Adds a guess initially alone to the guessTable JTable
     * 
     * @param guess
     *            The guess the client made
     * @modifies guessTable The table is updated with the current client guess
     */
    private void addGuessFirst(String guess) {
        String[] newRow = new String[3];
        newRow[0] = guess;
        newRow[1] = "";
        newRow[2] = "";
        tableModel.addRow(newRow);
        pack();
    }

    /**
     * Reads the response generated by makeGuess, and returns a 3 cell list
     * either representing [clientGuess][#characters in common][#characters in
     * the correct position] or [clientGuess][win message][] or
     * [clientGuess][error message][]
     * 
     */
    private String[] readResponse(String response) {
        List<String> responseList = new ArrayList<String>();
        String output;
        if (response.equals("error 0: Ill-formatted request.")) {
            output = "The guess you have made is not correctly formatted. Please try again - input a dictionary word of length 5";
        }
        // Should not enter this else if block
        else if (response.equals("error 1: Non-number puzzle ID.")) {
            output = "Oops! There is a problem with the current puzzle number";
        } else if (response
                .equals("error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.")) {
            output = "The guess you have entered is either not of length 5 or is not in the dictionary - try again";
        } else if (response.equals("guess 5 5")) {
            output = "You Win! Huzzah!";
        } else {
            output = response;
        }
        
        if (output.equals("You Win! Huzzah!")) {
            responseList.add("you win");
            responseList.add("");
        } else if (output
                .equals("The guess you have made is not correctly formatted. Please try again - input a dictionary word of length 5")
                || output
                        .equals("Oops! There is a problem with the current puzzle number")
                || output
                        .equals("The guess you have entered is either not of length 5 or is not in the dictionary - try again")) {
            responseList.add(output);
            responseList.add("");
        } else {
            responseList.add(String.valueOf(output.charAt(6)));
            responseList.add(String.valueOf(output.charAt(8)));
        }
        String[] responseArray = new String[2];
        responseArray[0] = responseList.get(0);
        responseArray[1] = responseList.get(1);
        return responseArray;
    }

    /**
     * Clears the guessTable
     */
    private void clearTable() {
        int numRows = tableModel.getRowCount();
        while (numRows > 0) {
            tableModel.removeRow(numRows - 1);
            numRows--;
        }
        rowNumber = 0;
    }

    /**
     * Reads the current input in the newPuzzleNumber text field If it is empty
     * or the input is not a positive integer, returns a random integer in the
     * range [1, 10000] Else it returns whatever the input is
     * 
     * @return A positive integer
     */
    private int newPuzzleReader() {
        int nextPuzzle;
        String input = newPuzzleNumber.getText();
        try {
            nextPuzzle = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            nextPuzzle = makeRandomNumber();
        }
        if (nextPuzzle < 1) {
            nextPuzzle = makeRandomNumber();
        }
        return nextPuzzle;
    }

    /**
     * Creates a random number in the range [1, 10,000]
     * 
     * @return A random integer in the range [1, 10,000]
     */
    private static int makeRandomNumber() {
        double numPuzzles = 10000.0;
        int minPuzzle = 1;
        Integer ran = minPuzzle + (int) (Math.random() * numPuzzles);
        return ran;

    }


    /**
     * New class representing the table model to be used for guessTable
     */
    class MyModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JottoGUI main = new JottoGUI();

                main.setVisible(true);
            }
        });
    }
}
