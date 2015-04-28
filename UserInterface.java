/**
 * A user interface for the MineSweeper game, can be implemented by CLI using curses
 * or by a GUI with Swing (or any other GUI)
 * @author Samuel Lord
 */
public interface UserInterface {
    /**
     * Uncovers a single field, showing the number of surrounding mines
     * @param coord The field to uncover
     * @param surrounding The number of surrounding mines
     */
    void uncover(Coord coord, int surrounding);

    /**
     * Win the game, takes appropriate action and congratulates the user
     */
    void win();

    /**
     * Lose the game, takes appropriate action
     */
    void lose();

    /**
     * Starts the timer
     */
    void startTimer();
}
