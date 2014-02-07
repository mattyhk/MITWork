// Testing for the representation of a Minesweeper board
// Tests the non trivial methods (i.e. no setters or getters) over different input spaces
// Different input spaces means cells either in the corners, on the edges or in the middle
// Also, different input spaces for bomb testing means the cell can be surrounded by no bombs, one bomb, or more bombs
// Finally different input spaces cover different states for different amount and locations of cells

package minesweeper.server;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BoardTest{
    
    /**
     * Testing the getNeighbours for a non edge case cell
     */
    @Test
    public void nonEdgeNeighboursTest(){
        Board board = new Board(3);
        List<Cell> output = board.getNeighbours(1, 1);
        List<Cell> expected = new ArrayList<Cell>();
        expected.add(new Cell(0, 0, false));
        expected.add(new Cell(0, 1, false));
        expected.add(new Cell(0, 2, false));
        expected.add(new Cell(1, 0, false));
        expected.add(new Cell(1, 2, false));
        expected.add(new Cell(2, 0, false));
        expected.add(new Cell(2, 1, false));
        expected.add(new Cell(2, 2, false));
        Cell[] outputArray = new Cell[output.size()];
        Cell[] expectedArray = new Cell[expected.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
            expectedArray[i] = expected.get(i);
        }
        assertArrayEquals(expectedArray, outputArray);
    }
    
    /**
     * Testing the getNeighbours for a x = 0 case cell
     */
    @Test
    public void xZeroEdgeNeighboursTest(){
        Board board = new Board(3);
        List<Cell> output = board.getNeighbours(0, 1);
        List<Cell> expected = new ArrayList<Cell>();
        expected.add(new Cell(0, 0, false));
        expected.add(new Cell(0, 2, false));
        expected.add(new Cell(1, 0, false));
        expected.add(new Cell(1, 1, false));
        expected.add(new Cell(1, 2, false));
        Cell[] outputArray = new Cell[output.size()];
        Cell[] expectedArray = new Cell[expected.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
            expectedArray[i] = expected.get(i);
        }
        assertArrayEquals(expectedArray, outputArray);
    }
    
    /**
     * Testing getNeighbours for a x = N - 1 case cell
     */
    @Test
    public void xNMinuesOneNeighboursTest(){
        Board board = new Board(3);
        List<Cell> output = board.getNeighbours(2, 1);
        List<Cell> expected = new ArrayList<Cell>();
        expected.add(new Cell(1, 0, false));
        expected.add(new Cell(1, 1, false));
        expected.add(new Cell(1, 2, false));
        expected.add(new Cell(2, 0, false));
        expected.add(new Cell(2, 2, false));
        Cell[] outputArray = new Cell[output.size()];
        Cell[] expectedArray = new Cell[expected.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
            expectedArray[i] = expected.get(i);
        }
        assertArrayEquals(expectedArray, outputArray);
    }
    
    /**
     * Testing getNeighbours for a y = 0 case cell
     */
    @Test
    public void yZeroEdgeNeighboursTest(){
        Board board = new Board(3);
        List<Cell> output = board.getNeighbours(1, 0);
        List<Cell> expected = new ArrayList<Cell>();
        expected.add(new Cell(0, 0, false));
        expected.add(new Cell(0, 1, false));
        expected.add(new Cell(1, 1, false));
        expected.add(new Cell(2, 0, false));
        expected.add(new Cell(2, 1, false));
        Cell[] outputArray = new Cell[output.size()];
        Cell[] expectedArray = new Cell[expected.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
            expectedArray[i] = expected.get(i);
        }
        assertArrayEquals(expectedArray, outputArray);
    }
    
    /**
     * Testing getNeighbours for a y = N - 1 case cell
     */
    @Test
    public void yNMinusOneEdgeNeighboursTest(){
        Board board = new Board(3);
        List<Cell> output = board.getNeighbours(1, 2);
        List<Cell> expected = new ArrayList<Cell>();
        expected.add(new Cell(0, 1, false));
        expected.add(new Cell(0, 2, false));
        expected.add(new Cell(1, 1, false));
        expected.add(new Cell(2, 1, false));
        expected.add(new Cell(2, 2, false));
        Cell[] outputArray = new Cell[output.size()];
        Cell[] expectedArray = new Cell[expected.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
            expectedArray[i] = expected.get(i);
        }
        assertArrayEquals(expectedArray, outputArray);
    }
    
    /**
     * Testing the getUntouchedNeighbours for a random case
     * Note that since getUntouchedNeighbours applies the same logic as getNeighbours to find the initial neighbours before applying the 
     * untouched check it is not necessary to test the edge cases
     */
    @Test
    public void randomUntouchedNeighboursTest(){
        Board board = new Board(3);
        board.setCellState(0, 0, "flagged");
        board.setCellState(2, 0, "dug");
        board.setCellState(1, 2, "flagged");
        List<Cell> output = board.getUntouchedNeighbours(1, 1);
        List<Cell> expected = new ArrayList<Cell>();
        expected.add(new Cell(0, 1, false));
        expected.add(new Cell(0, 2, false));
        expected.add(new Cell(1, 0, false));
        expected.add(new Cell(2, 1, false));
        expected.add(new Cell(2, 2, false));
        Cell[] outputArray = new Cell[output.size()];
        Cell[] expectedArray = new Cell[expected.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
            expectedArray[i] = expected.get(i);
        }
        assertArrayEquals(expectedArray, outputArray);
    }
    
    /**
     * Testing the getUntouchedNeighbours for the case where every neighbour is untouched
     */
    @Test
    public void AllNeighboursUntouchedTest(){
        Board board = new Board(3);
        List<Cell> output = board.getUntouchedNeighbours(1, 1);
        List<Cell> expected = new ArrayList<Cell>();
        expected.add(new Cell(0, 0, false));
        expected.add(new Cell(0, 1, false));
        expected.add(new Cell(0, 2, false));
        expected.add(new Cell(1, 0, false));
        expected.add(new Cell(1, 2, false));
        expected.add(new Cell(2, 0, false));
        expected.add(new Cell(2, 1, false));
        expected.add(new Cell(2, 2, false));
        Cell[] outputArray = new Cell[output.size()];
        Cell[] expectedArray = new Cell[expected.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
            expectedArray[i] = expected.get(i);
        }
        assertArrayEquals(expectedArray, outputArray);
    }
    
    /**
     * Testing the getUntouchedNeighbours for the case where every neighbour is not untouched
     */
    @Test
    public void AllNeighboursNotUntouchedTest(){
        Board board = new Board(3);
        board.setCellState(0, 0, "flagged");
        board.setCellState(0, 1, "flagged");
        board.setCellState(0, 2, "flagged");
        board.setCellState(1, 0, "dug");
        board.setCellState(1, 1, "flagged");
        board.setCellState(1, 2, "flagged");
        board.setCellState(2, 0, "dug");
        board.setCellState(2, 1, "dug");
        board.setCellState(2, 2, "dug");
        List<Cell> output = board.getUntouchedNeighbours(1, 1);
        Cell[] outputArray = new Cell[output.size()];
        for (int i = 0; i < output.size(); i++){
            outputArray[i] = output.get(i);
        }
        assertEquals(outputArray.length, 0);
    }
    
    /**
     * Testing countBombs for the case where no neighbours contain bombs
     */
    @Test
    public void noNeighboursHaveBombs(){
        Board board = new Board(3);
        int output = board.countBombs(1, 1);
        int expected = 0;
        assertEquals(expected, output);
    }
    
    /**
     * Testing countBombs for the case where every neighbour contains a bomb
     */
    @Test
    public void allNeughboursHaveBombs(){
        Board board = new Board(3);
        board.setCellBomb(0, 0, true);
        board.setCellBomb(0, 1, true);
        board.setCellBomb(0, 2, true);
        board.setCellBomb(1, 0, true);
        board.setCellBomb(1, 2, true);
        board.setCellBomb(2, 0, true);
        board.setCellBomb(2, 1, true);
        board.setCellBomb(2, 2, true);
        int output = board.countBombs(1, 1);
        int expected = 8;
        assertEquals(expected, output);
    }
    
    /**
     * Testing countBombs for the case half + 1 the neighbours contain bombs
     */
    @Test
    public void halfNeighboursHaveBombs(){
        Board board = new Board(3);
        board.setCellBomb(0, 2, true);
        board.setCellBomb(1, 2, true);
        board.setCellBomb(2, 0, true);
        board.setCellBomb(2, 1, true);
        board.setCellBomb(2, 2, true);
        int output = board.countBombs(1, 1);
        int expected = 5;
        assertEquals(expected, output);
    }
    
    /**
     * Testing createBoard() for the case when every square is untouched and some squares have bombs
     */
    @Test
    public void allNeighboursUntouchedCreateBoard(){
        Board board = new Board(3);
        board.setCellBomb(2, 0, true);
        board.setCellBomb(2, 1, true);
        board.setCellBomb(2, 2, true);
        String output = board.look();
        String expected = "- - -" + "/n" +
                            "- - -" + "/n" +
                            "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing createBoard() for the case when middle cell is dug and every neighbour has a bomb
     */
    @Test
    public void middleCellDugAllNeighboursHaveBombs(){
        Board board = new Board(3);
        board.setCellState(1, 1, "dug");
        board.setCellBomb(0, 0, true);
        board.setCellBomb(0, 1, true);
        board.setCellBomb(0, 2, true);
        board.setCellBomb(1, 0, true);
        board.setCellBomb(1, 2, true);
        board.setCellBomb(2, 0, true);
        board.setCellBomb(2, 1, true);
        board.setCellBomb(2, 2, true);
        String output = board.look();
        String expected = "- - -" + "/n" +
                              "- 8 -" + "/n" +
                              "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing createBoard() for the case when middle cell is Flagged and some squares have bombs
     */
    @Test
    public void middleCellFlaggedSomeNeighboursHaveBombs(){
        Board board = new Board(3);
        board.setCellState(1, 1, "flagged");
        board.setCellBomb(1, 2, true);
        board.setCellBomb(2, 0, true);
        board.setCellBomb(2, 1, true);
        board.setCellBomb(2, 2, true);
        String output = board.look();
        String expected = "- - -" + "/n" +
                              "- F -" + "/n" +
                              "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing createBoard() for the case when the top left corner three squares are dug and have some neighbouring bombs 
     */
    @Test
    public void topLeftCellsDugSomeBombs(){
        Board board = new Board(3);
        board.setCellState(0, 0, "dug");
        board.setCellState(0, 1, "dug");
        board.setCellState(1, 0, "dug");
        board.setCellBomb(1, 2, true);
        board.setCellBomb(2, 1, true);
        board.setCellBomb(2, 2, true);
        String output = board.look();
        String expected = "  1 -" + "/n" +
                          "1 - -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing createBoard() for the case when the top left corner three squares are dug and have no neighbouring bombs 
     */
    @Test
    public void topLeftCellsDugNoBombs(){
        Board board = new Board(3);
        board.setCellState(0, 0, "dug");
        board.setCellState(0, 1, "dug");
        board.setCellState(1, 0, "dug");
        board.setCellBomb(2, 2, true);
        String output = board.look();
        String expected = "    -" + "/n" +
                          "  - -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing createBoard() for the case when the top left corner three squares are dug and have some neighbouring bombs and some squares flagged 
     */
    @Test
    public void topLeftCellsDugSomeBombsSomeFlagged(){
        Board board = new Board(3);
        board.setCellState(0, 0, "dug");
        board.setCellState(0, 1, "dug");
        board.setCellState(1, 0, "dug");
        board.setCellBomb(1, 1, true);
        board.setCellBomb(2, 0, true);
        board.setCellBomb(2, 2, true);
        board.setCellState(1, 2, "flagged");
        board.setCellState(2, 0, "flagged");
        String output = board.look();
        String expected = "1 1 -" + "/n" +
                          "2 - F" + "/n" +
                          "F - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing halfDig() for the case when every square is untouched and there are no bombs in the board
     */
    @Test
    public void halfDigAllSquaresUntouchedNoBombs(){
        Board board = new Board(3);
        board.halfDig(0, 0);
        String output = board.look();
        String expected = "     " + "/n" +
                          "     " + "/n" +
                          "     " + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing halfDig() for the case when every square is untouched and there are some bombs in the board and starting square has no neighbour bombs
     */
    @Test
    public void halfDigAllSquaresUntouchedSomeBombsStartNoBombs(){
        Board board = new Board(3);
        board.setCellBomb(2, 2, true);
        board.setCellBomb(1, 2, true);
        board.halfDig(0, 0);
        String output = board.look();
        String expected = "  1 -" + "/n" +
                          "  2 -" + "/n" +
                          "  2 -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing halfDig() for the case when the intial square is surrounded by bombs
     */
    @Test
    public void halfDigSquareSurroundedByBombs(){
        Board board = new Board(3);
        board.setCellBomb(2, 2, true);
        board.setCellBomb(1, 2, true);
        board.setCellState(0, 0, "flagged");
        board.halfDig(1, 1);
        String output = board.look();
        String expected = "F - -" + "/n" +
                          "- - -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing halfDig() for the case when every square is untouched and there are some bombs in the board and starting square has no neighbour bombs
     */
    @Test
    public void halfDigSomeSquaresUntouchedSomeBombsStartNoBombs(){
        Board board = new Board(3);
        board.setCellBomb(2, 2, true);
        board.setCellBomb(1, 2, true);
        board.setCellState(0, 1, "flagged");
        board.setCellState(2, 0, "flagged");
        board.halfDig(0, 0);
        String output = board.look();
        String expected = "  F -" + "/n" +
                          "  2 -" + "/n" +
                          "F 2 -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing fullDig() for the case when every square is untouched and there are no bombs in the board
     */
    @Test
    public void fullDigAllSquaresUntouchedNoBombs(){
        Board board = new Board(3);
        String output = board.fullDig(0, 0);
        String expected = "     " + "/n" +
                          "     " + "/n" +
                          "     " + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing fullDig() for the case when every square is untouched and there are some bombs in the board
     */
    @Test
    public void fullDigAllSquaresUntouchedSomeBombs(){
        Board board = new Board(3);
        board.setCellBomb(2, 2, true);
        board.setCellBomb(2, 1, true);
        String output = board.fullDig(0, 0);
        String expected = "     " + "/n" +
                          "1 2 2" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing fullDig() for the case when some squares are untouched and there are some bombs in the board
     */
    @Test
    public void fullDigSomeSquaresUntouchedSomeBombs(){
        Board board = new Board(3);
        board.setCellBomb(2, 2, true);
        board.setCellBomb(2, 1, true);
        board.setCellState(1, 1, "flagged");
        board.setCellState(0, 1, "dug");
        String output = board.fullDig(0, 0);
        String expected = "    -" + "/n" +
                          "1 F -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing fullDig() for the case when the initial square is surrounded by bombs
     */
    @Test
    public void fullDigStartSquareSurrounded(){
        Board board = new Board(3);
        board.setCellBomb(2, 2, true);
        board.setCellBomb(2, 1, true);
        board.setCellState(0, 0, "flagged");
        String output = board.fullDig(1, 1);
        String expected = "F - -" + "/n" +
                          "- 2 -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing fullDig() for the case when the initial square is not untouched
     */
    @Test
    public void fullDigStartNotUntouched(){
        Board board = new Board(3);
        board.setCellBomb(2, 2, true);
        board.setCellBomb(2, 1, true);
        board.setCellState(0, 0, "dug");
        String output = board.fullDig(0, 0);
        String expected = "  - -" + "/n" +
                          "- - -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing fullDig() for the case when the initial square is a bomb
     */
    @Test
    public void fullDigStartBomb(){
        Board board = new Board(3);
        board.setCellBomb(0, 0, true);
        board.setCellBomb(0, 2, true);
        board.fullDig(0, 0);
        String output = board.look();
        String expected = "  1 -" + "/n" +
                          "  1 1" + "/n" +
                          "     " + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing flag() for the case when the initial square is untouched
     */
    @Test
    public void flagStartUntouched(){
        Board board = new Board(3);
        String output = board.flag(1, 1);
        String expected = "- - -" + "/n" +
                          "- F -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing flag() for the case when the initial square is not untouched
     */
    @Test
    public void flagStartNotUntouched(){
        Board board = new Board(3);
        board.setCellState(1, 1, "dug");
        board.setCellBomb(0, 0, true);
        String output = board.flag(1, 1);
        String expected = "- - -" + "/n" +
                          "- 1 -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing flag() for the case when the square is not within the board
     */
    @Test
    public void flagStartNotInBoard(){
        Board board = new Board(3);
        board.setCellState(1, 1, "dug");
        board.setCellBomb(0, 0, true);
        String output = board.flag(-1, 1);
        String expected = "- - -" + "/n" +
                          "- 1 -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing deFlag for the case when the initial square is flagged
     */
    @Test 
    public void deFlagStartFlagged(){
        Board board = new Board(3);
        board.setCellState(1, 1, "flagged");
        board.setCellState(0, 0, "flagged");
        String output = board.deFlag(1, 1);
        String expected = "F - -" + "/n" +
                          "- - -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing deFlag for the case when the initial square is not flagged
     */
    @Test 
    public void deFlagStartNotFlagged(){
        Board board = new Board(3);
        board.setCellState(1, 1, "dug");
        String output = board.deFlag(1, 1);
        String expected = "- - -" + "/n" +
                          "-   -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
    
    /**
     * Testing deFlag for the case when the initial square is not in the board
     */
    @Test 
    public void deFlagStartNotInBoard(){
        Board board = new Board(3);
        board.setCellState(1, 1, "dug");
        board.setCellState(0, 0, "flagged");
        String output = board.deFlag(-1, 0);
        String expected = "F - -" + "/n" +
                          "-   -" + "/n" +
                          "- - -" + "/n";
        assertEquals(expected, output);
    }
}