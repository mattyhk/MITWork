package minesweeper.server;

import java.io.IOException;
import java.net.Socket;

/**
 * An object that creates a thread for every client connection and runs that
 * thread
 */
public class ClientThread {

    private final Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Creates a thread, modifies its run method to call
     * MinesweeperServer.handleConnection on the particular socket, and starts
     * the thread
     */
    public void runHandleInThread() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    MinesweeperServer.handleConnection(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
        t.start();
    }
}
