package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Class that handles guesses made by clients from the GUI. Stores guesses made by clients in a queue and sends them to a server which returns a response
 * Handles the response and sends back the appropriate output to the GUI.
 * Class is thread safe because the only method that can mutate the state of this class, i.e. the guesses queue represented by a list, is modified
 * by the synchronized keyword ensuring the instance of this class is locked. Therefore the invariant that the guesses list stores
 * guesses made by clients in chronological order is preserved 
 * @param aL A list of the guesses made by clients that have yet to be responded to
 */
public class JottoModel {

    private final ArrayList<String> guesses;

    public JottoModel(ArrayList<String> aL) {
        guesses = aL;
    }

    /**
     * Sends a guess to the server with the correct puzzle number, reads the
     * reply, and returns the reply.
     * 
     * @param guess
     *            The current guess
     * @param puzzleNumber
     *            The puzzle number
     * @return A string representing the response from the server
     * @throws RuntimeException Throws an exception if a MalformedURLException is thrown by the URL class when a connection is attempted
     * @throws RuntimeException Throws an exception if an IOException is thrown by the URL class when a connection is attempted
     */
    public static String makeGuess(String guess, Integer puzzleNumber) {
        String output;
        String input = guess;
        String puzzleNumberInput = puzzleNumber.toString();
        try {
            URL server = new URL(
                    "http://courses.csail.mit.edu/6.005/jotto.py?puzzle="
                            + puzzleNumberInput + "&guess=" + input);
            URLConnection serverConnection = server.openConnection();
            serverConnection.connect();
            BufferedReader received = new BufferedReader(new InputStreamReader(
                    serverConnection.getInputStream()));
            output = received.readLine();
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            e.printStackTrace();
            throw new RuntimeException("Malformed URL");
        } catch (IOException e) {
            System.out.println("Bad Connection");
            e.printStackTrace();
            throw new RuntimeException("Bad Connection");
        }
        return output;
    }

    /**
     * Creates a new thread to handle the current guesses on the guess queue. 
     * Synchronized in order to preserve the invariant that all guesses are handled in order and their responses are returned in order. 
     * Achieves this by placing a lock on the instance of this class and only releases it once all the guesses still on the queue are handled 
     * - thus no new guesses can be handled before any existing ones making this method thread safe 
     * @param puzzleNumber
     *            The current puzzle number
     * @param responses
     *            The current blocking queue representing the responses already
     *            found by
     */
    public synchronized ArrayList<String> handleGuesses(final int puzzleNumber, final ArrayList<String> r) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (!(guesses.isEmpty())) {
                    String response;
                    String guess = guesses.remove(0);
                    response = makeGuess(guess, puzzleNumber);
                    r.add(response);
                }
            };
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * Adds a guess to the guesses list
     * @param guess The guess string to be added to the guesses list
     * @modifies guesses The list of current guesses that need a response
     */
    public void addGuess(String guess){
        guesses.add(guess);
    }
}
