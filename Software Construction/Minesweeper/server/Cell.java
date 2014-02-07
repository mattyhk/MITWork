package minesweeper.server;

import java.util.Arrays;

/**
 * An object that represents a cell on the board
 */
public class Cell{
    
    private boolean bomb;
    private String state;
    private final int x;
    private final int y;
    private final int[] coordinates = new int[2];
    
    public Cell(int x, int y, boolean bomb){
        this.state = "untouched";
        this.bomb = bomb;
        this.x = x;
        this.y = y;
        coordinates[0] = this.x;
        coordinates[1] = this.y;
        
    }
    
    /**
     * Sets whether or not the cell has a bomb
     * @param hasBomb True if cell contains a bomb, False otherwise
     */
    public void setBomb(boolean hasBomb){
        bomb = hasBomb;
    }
    
    /**
     * Sets the state of a cell to either "flagged", "dug" or "untouched"
     * @param state The state of the cell
     * @throws IllegalArgumentException If the state is being set to something other than the three allowed, exception is thrown
     */
    public void setState(String state){
        if (state == "flagged" || state == "dug" || state == "untouched"){
            this.state = state;
        }else {
            throw new IllegalArgumentException("Illegal state being passed in");
        }
    }
    
    /**
     * @return True if the cell contains a bomb, false otherwise
     */
    public boolean hasBomb(){
        if (bomb){return true;}
        else{return false;}
        
    }
    
    /**
     * @return The current state of the cell - either "flagged", "dug" or "untouched"
     */
    public String getState(){
        String currentState = state;
        return currentState;
    }
    
    /**
     * @return A size 2 array of the cell coordinates (x, y)
     */
    public int[] getCoordinates(){
        int[] coordinateOut = coordinates.clone();
        return coordinateOut;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (bomb ? 1231 : 1237);
        result = prime * result + Arrays.hashCode(coordinates);
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Cell))
            return false;
        Cell other = (Cell) obj;
        if (bomb != other.bomb)
            return false;
        if (!Arrays.equals(coordinates, other.coordinates))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        return true;
    }
    
    
}