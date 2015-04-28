/**
 * A coordinate in a game of minesweeper, knows whether it has been
 * uncovered, and whether it is a mine.
 * @author Samuel Lord
 */
public class Coord {
    // The X-coordinate
    private final int x;
    // The Y-coordinate
    private final int y;
    // Am I a mine?
    private boolean mine;
    // Have I been uncovered?
    private boolean uncovered;
    // Have I got a flag
    private boolean flag;

    /**
     * New coordinate
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
        mine = false;
        uncovered = false;
        flag = false;
    }

    /**
     * Getter for X coordinate
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for Y coordinate
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if this coordinate is a mine
     * @return whether this coordinate is a mine
     */
    public boolean isMine() {
        return mine;
    }

    /**
     * Sets whether this coordinate is a mine
     * @param mine Whether this coordinate is a mine
     */
    public void setMine(boolean mine) {
        this.mine = mine;
    }

    /**
     * Makes a numeric representation of this location
     * @return Location in numeric form
     */
    public int toInt() {
        return x*10 + y;
    }

    /**
     * Makes this coordinate uncovered
     */
    public void uncover() {
        this.uncovered = true;
    }

    /**
     * Checks whether this coordinate has been uncovered
     * @return Whether this has been uncovered
     */
    public boolean isUncovered() {
        return uncovered;
    }

    /**
     * Toggles whether this coordinate has a flag
     */
    public void toggleFlag() {
        flag = !flag;
    }

    /**
     * Checks if this coordinate has a flag
     * @return Whether this has a flag
     */
    public boolean hasFlag() {
        return flag;
    }
}
