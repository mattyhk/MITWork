package minesweeper.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// The board data structure is thread safe because:
// 1) Each method accessible to a thread that mutates the state of the Board, i.e. fullDig(), halfDig(), flag(), deFlag() 
//    is wrapped in a synchronized wrapper
//      - therefore, whichever each thread will acquire the lock only after an earlier thread has fully mutated the board
//        and will release it before any other thread can mutate the board
// 2) Only non-mutable objects are returned to the threads - in this case they are Strings.
// 3) Each observer method, i.e. look(), that is available to a thread is also wrapped in a synchronized wrapper
//      - therefore, a thread can only observe the state of a board after an earlier thread has mutated it and before a later thread mutates it.

/**
 * Class that represents a minesweeper board of size N*N
 */
public class Board {

    private Cell[][] board;
    private final int size;

    public Board(int N, double probability) {
        size = N;
        board = new Cell[N][N];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (Math.random() < probability) {
                    board[x][y] = new Cell(x, y, true);
                } else {
                    board[x][y] = new Cell(x, y, false);
                }
            }
        }
    }

    public Board(int N) {
        size = N;
        board = new Cell[N][N];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                board[x][y] = new Cell(x, y, false);
            }
        }
    }

    @SuppressWarnings("resource")
    public Board(File file, boolean debug) {
        ArrayList<Cell> boardList = new ArrayList<Cell>();
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        int numLines = 0;
        int x = 0;
        int y = 0;
        ArrayList<Integer> lineLengths = new ArrayList<Integer>();
        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() returns 0 if the file does not have more lines.
            while (dis.available() != 0) {
                @SuppressWarnings("deprecation")
                String currentLine = dis.readLine();
                y = 0;
                numLines++;
                lineLengths.add(currentLine.length());

                // Check the first character of the line
                char c = currentLine.charAt(0);

                if (c != '0' && c != '1') {
                    throw new RuntimeException(
                            "invalid input - first character not 0 or 1.");
                }

                // Loop through all the characters of the line
                for (int i = 0; i < currentLine.length(); i++) {

                    char currentChar = currentLine.charAt(i);

                    // Check validity of each character
                    if (currentChar != '1' && currentChar != '0'
                            && currentChar != ' ' && currentChar != '\n') {
                        throw new RuntimeException(
                                "invalid input - character is something other than 1, 0 or space");
                    }

                    else if (currentChar == '1' || currentChar == '0') {
                        if (i + 1 < currentLine.length()) {
                            char next = currentLine.charAt(i + 1);
                            if (next != ' ' && next != '\n') {
                                throw new RuntimeException(
                                        "invalid input - something other than a space or new line follows a 1 or 0");
                            }
                        }
                        if (currentChar == '1') {
                            boardList.add(new Cell(x, y, true));
                            y++;
                        } else {
                            boardList.add(new Cell(x, y, false));
                            y++;
                        }
                    } else if (currentChar == ' ') {
                        if (i + 1 < currentLine.length()) {
                            char next = currentLine.charAt(i + 1);
                            if (next != '1' && next != '0') {
                                throw new RuntimeException(
                                        "invalid input - a 1 or 0 does not follow a space");
                            }
                        }
                    }
                }

                // moving on to next line - increment x
                x++;
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check the dimensions
        if (x != y) {
            throw new RuntimeException("invalid input - dimensions differ");
        }

        // Check every line is the same length
        for (int i = 0; i < lineLengths.size() - 1; i++) {
            if (lineLengths.get(i) != lineLengths.get(i + 1)) {
                throw new RuntimeException(
                        "invalid input - line lengths differ");
            }
        }

        // Everything is fine
        size = numLines;
        board = new Cell[size][size];
        for (Cell cell : boardList) {
            board[cell.getCoordinates()[0]][cell.getCoordinates()[1]] = cell;
        }
    }

    /**
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @return The cell at (x, y)
     * @throws IllegalArgumentException
     *             if coordinates are not valid
     */
    private Cell getCell(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException(
                    "Non valid coordinates are being accessed");
        }
        return board[x][y];
    }

    /**
     * Sets the state of the cell at (x, y) Assumes valid coordinates
     * 
     * @param x
     *            X coordinate of the cell
     * @param y
     *            Y coordinate of the cell
     * @param state
     *            The state to be set
     */
    public void setCellState(int x, int y, String state) {
        getCell(x, y).setState(state);
    }

    /**
     * Sets whether or not the cell at (x, y) contains a bomb Assumes valid
     * coordinates
     * 
     * @param x
     *            X coordinate of the cell
     * @param y
     *            Y coordinate of the cell
     * @param bomb
     *            True if cell contains a bomb
     */
    public void setCellBomb(int x, int y, boolean bomb) {
        getCell(x, y).setBomb(bomb);
    }

    /**
     * Gets the state of the cell at (x, y) Assumes valid coordinates
     * 
     * @param x
     *            X coordinate of the cell
     * @param y
     *            Y coordinate of the cell
     * @return The state of the cell at (x, y)
     */
    public String getCellState(int x, int y) {
        return getCell(x, y).getState();
    }

    /**
     * Returns true if there is a bomb at (x, y); false otherwise Assumes valid
     * coordinates
     * 
     * @param x
     *            X coordinate of the cell
     * @param y
     *            Y coordinate of the cell
     * @return True if there is a bomb in (x, y); false otherwise
     */
    public boolean cellContainsBomb(int x, int y) {
        return getCell(x, y).hasBomb();
    }

    /**
     * Returns a list of the cells representing all the possible neighbours of
     * the cell at (x, y) within the board
     * 
     * @param x
     *            X Coordinate
     * @param y
     *            Y Coordinate
     * @return List of all neighbours
     * @throws IllegalArgumentException
     *             if non valid coordinates passed in
     */
    public List<Cell> getNeighbours(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException(
                    "Non valid coordinates are being accessed");
        }
        ArrayList<Cell> neighbours = new ArrayList<Cell>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i >= 0 && x + i < size && y + j >= 0 && y + j < size) {
                    if (!(x + i == x && y + j == y)) {
                        neighbours.add(getCell(x + i, y + j));
                    }
                }
            }

        }
        return neighbours;
    }

    /**
     * Returns a list containing all of the neighbouring cells whose state is
     * "untouched"
     * 
     * @param x
     *            X Coordinate
     * @param y
     *            Y Coordinate
     * @return List of all "untouched" neighbours
     * @throws IllegalArgumentException
     *             if non valid coordinates passed in
     */
    public List<Cell> getUntouchedNeighbours(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException(
                    "Non valid coordinates are being accessed");
        }
        ArrayList<Cell> neighbours = new ArrayList<Cell>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i >= 0 && x + i < size && y + j >= 0 && y + j < size) {
                    if (!(x + i == x && y + j == y)) {
                        Cell cell = getCell(x + i, y + j);
                        if (cell.getState() == "untouched") {
                            neighbours.add(cell);
                        }
                    }
                }
            }

        }
        return neighbours;
    }

    /**
     * Counts the number of neighbouring cells of the cell at (x, y) that
     * contain bombs
     * 
     * @param x
     *            X Coordinate
     * @param y
     *            Y Coordinate
     * @return The number of neighbouring cells that contain bombs
     * @throws IllegalArgumentException
     *             if non valid coordinates passed in
     */
    public int countBombs(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException(
                    "Non valid coordinates are being accessed");
        }
        List<Cell> neighbours = getNeighbours(x, y);
        int count = 0;
        for (Cell cell : neighbours) {
            if (cell.hasBomb()) {
                count++;
            }
        }
        return count;
    }

    /**
     * If none of the neighbours of the cell at (x, y) contain bombs, changes
     * the state all of the untouched neighbours to "dug". Recurses for each
     * untouched neighbour of (x, y)
     * 
     * @assumes (x, y) are valid cell coordinates
     * @param x
     *            X Coordinate
     * @param y
     *            Y Coordinate
     * @modifies If (x, y) has no neighbouring bombs, changes the state of all
     *           the untouched neighbours to "dug"
     */
    public synchronized void halfDig(int x, int y) {
        if (countBombs(x, y) == 0) {
            for (Cell cell : getUntouchedNeighbours(x, y)) {
                cell.setState("dug");
                halfDig(cell.getCoordinates()[0], cell.getCoordinates()[1]);
            }
        }
    }

    /**
     * Fulfills the obligations of a DIG message being passed in. If either x or
     * y is less than 0, or either x or y is equal to or greater than the board
     * size, or square x,y is not in the 'untouched' state, do nothing and
     * return a BOARD message. If square x,y's state is 'untouched', change
     * square x,y's state to 'dug'. If square x,y contains a bomb, change it so
     * that it contains no bomb and send a BOOM message to the user. Then, if
     * the DEBUG flag is set to 'false', terminate the user's connection If the
     * square x,y has no neighbor squares with bombs, then for each of x,y's
     * 'untouched' neighbor squares, change said square to 'dug' and repeat
     * 
     * @param x
     *            X Coordinate
     * @param y
     *            Y Coordinate
     * @modifies If state of (x, y) is "untouched" change it to "dug"
     * @modifies If (x, y) contains a bomb, it no longer does
     * @modifies If (x, y) has no neighbouring bombs, changes the state of all
     *           the untouched neighbours to "dug"
     * @return If no boom message is sent, return a board message, else return
     *         boom
     */
    public synchronized String fullDig(int x, int y) {
        if (x < 0 || y < 0 || x >= size || y >= size
                || getCell(x, y).getState() != "untouched") {
            String board = createBoard();
            return board;
        } else {
            if (cellContainsBomb(x, y)) {
                setCellBomb(x, y, false);
                String boom = createBoom();
                // return Boom message - terminate client connection if need be
                fullDig(x, y);
                digAfterBoom(x, y);
                return boom;
            } else {
                setCellState(x, y, "dug");
                halfDig(x, y);
                String board = createBoard();
                return board;
            }
        }
    }
    
    /**
     * Updates the count of the cell around a 'dug' bomb, expanding the dug cells if they have no neighbouring bombs
     * @param x X Coordinate
     * @param y Y Coordinate
     * @modifies If a neighbouring cell of a bomb location (x,y) now has no surrounding bombs, calls halfDig on it to expand the area of "dug" cells 
     */
    public synchronized void digAfterBoom(int x, int y){
        for (Cell cell : getNeighbours(x, y)) {
            if (cell.getState() == "dug"){
                if (countBombs(cell.getCoordinates()[0], cell.getCoordinates()[1]) == 0){
                    halfDig(cell.getCoordinates()[0], cell.getCoordinates()[1]);
                }
            }
        }
    }

    /**
     * Creates and returns a BOARD message representing the board's state
     * 
     * @return A string representing the board's state
     */
    private  synchronized String createBoard() {
        StringBuilder boardMessage = new StringBuilder();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (getCellState(x, y) == "untouched") {
                    boardMessage.append("-");
                } else if (getCellState(x, y) == "flagged") {
                    boardMessage.append("F");
                } else if (getCellState(x, y) == "dug") {
                    int count = countBombs(x, y);
                    if (count == 0) {
                        boardMessage.append(" ");
                    } else if (count > 8) {
                        throw new RuntimeException(
                                "uh oh count is doing something weird - returns more bombs then neighbours");
                    } else {
                        boardMessage.append(count);
                    }
                }
                if (y == size - 1) {
                    boardMessage.append("\n");
                } else {
                    boardMessage.append(" ");
                }
            }
        }
        return boardMessage.toString();
    }

    /**
     * Creates and returns a BOOM message
     * 
     * @return A string corresponding to a Boom message
     */
    private String createBoom() {
        String boom = "BOOM!" + "\n";
        return boom;
    }

    /**
     * Fulfills the obligations of a LOOK message being passed in. Returns a
     * BOARD message, a string representation of the board's state.
     * 
     * @return A string that represents the BOARD Message
     */
    public synchronized String look() {
        String boardMessage = createBoard();
        return boardMessage;
    }

    /**
     * Fulfills the obligations of a FLAG message being passed in. If x and y
     * are both greater than or equal to 0, and less than the board size, and
     * square x,y is in the 'untouched' state, change it to be in the 'flagged'
     * state. Otherwise, do not mutate any state on the server. Return a BOARD
     * message
     * 
     * @param x
     *            X Coordinate
     * @param y
     *            Y Coordinate
     * @return a String representing a BOARD message
     */
    public synchronized String flag(int x, int y) {
        String boardMessage;
        if (x < 0 || y < 0 || x >= size || y >= size
                || getCell(x, y).getState() != "untouched") {
            boardMessage = createBoard();
        } else {
            setCellState(x, y, "flagged");
            boardMessage = createBoard();
        }
        return boardMessage;
    }

    /**
     * Fulfills the obligations of a DEFLAG message being passed in. If x and y
     * are both greater than or equal to 0, and less than the board size, and
     * square x,y is in the 'flagged' state, change it to be in the 'untouched'
     * state. Otherwise, do not mutate any state on the server. For any DEFLAG
     * message, return a BOARD message
     */
    public synchronized String deFlag(int x, int y) {
        String boardMessage;
        if (x < 0 || y < 0 || x >= size || y >= size
                || getCell(x, y).getState() != "flagged") {
            boardMessage = createBoard();
        } else {
            setCellState(x, y, "untouched");
            boardMessage = createBoard();
        }
        return boardMessage;
    }

    /**
     * Fulfills the obligations of a HELP_REQ message being passed in. Returns a
     * HELP message. Does not mutate anything on the server.
     */
    public String help() {
        String helpMessage = "Valid commands are: look, dig X Y, flag X Y, deflag X Y."
                + "\n"
                + "Look returns a message representing the current state of the board "
                + "where a - indicates a square is untouched, a F indicates a square is flagged, "
                + "a space indicates a square has been dug and has no neighbours that contain bombs, "
                + "and a digit between (1-8) indicates a square has been dug and has that number of neighbours that contain bombs. "
                + "\n"
                + "Dig is a command allowing you to dig a square at coordinates X,Y. Ensure that the coordinates are valid coordinates "
                + "- otherwise nothing will happen. X and Y are in the range (0, "
                + size
                + "). "
                + "\n"
                + "Flag allows you to mark a square with a flag. Deflag allows you to unmark a flagged square."
                + "\n";
        return helpMessage;

    }

    /**
     * Fulfills the obligations of a BYE message being passed in Terminates the
     * connection with the client
     */
    public void bye() {
        // terminates the connection
    }

    /**
     * Creates a HELLO message fulfilling the obligations
     * 
     * @param n
     *            The number of clients connected
     * @return a String containing the HELLO message
     */
    public static String hello(int n) {
        String hello = "Welcome to Minesweeper. " + n
                + " people are playing including you. Type 'help' for help."
                + "\n";
        return hello;
    }

}