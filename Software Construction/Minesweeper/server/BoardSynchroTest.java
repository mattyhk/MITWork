// Create 10000 threads by doing a for loop
// For each thread in run: if cell has bomb remove, else add a bomb
// Start each thread 
// after for loop join each thread by iterating through some thread list
// check that all counts add up correctly

package minesweeper.server;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BoardSynchroTest{
    
    @Test
    public void synchroTest(){
        final Board board = new Board(2);
        board.setCellBomb(0, 0, true);
        List<Thread> threadList = new ArrayList<Thread>();
        for (int i = 0; i < 10000; i++){
            Thread t = new Thread(new Runnable() {
                public void run(){
                    if (board.cellContainsBomb(0, 0)){
                        board.setCellBomb(0, 0, false);
                        board.setCellState(1, 0, Integer.toString(board.countBombs(1,0)));
                        board.setCellState(1, 1, Integer.toString(board.countBombs(1,1)));
                        board.setCellState(0, 1, Integer.toString(board.countBombs(1,0)));
                    }else{
                        board.setCellBomb(0, 0, true);
                    }
                }
            });
            t.start();
            threadList.add(t);
        }
        for (Thread thread: threadList){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(board.getCellState(1, 0) == "1" || board.getCellState(1, 0) == "0");
        assertTrue(board.getCellState(0, 1) == "1" || board.getCellState(0, 1) == "0");
        assertTrue(board.getCellState(1, 1) == "1" || board.getCellState(1, 1) == "0");
    }
}