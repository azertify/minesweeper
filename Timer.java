import javax.swing.*;
import java.awt.*;

/**
 * A JLabel timer
 * @author Samuel Lord
 */
public class Timer extends JLabel {
    private boolean go;
    private Runnable task;

    /**
     * Makes a new timer
     */
    public Timer() {
        super("0", SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // do nothing, this is fine
                }
                while (go) {
                    setText(String.valueOf(Integer.valueOf(getText()) + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // do nothing, this is fine
                    }

                }
            }
        };
        go = false;
    }

    /**
     * Starts the timer
     */
    public void start() {
        go = true;
        new Thread(task).start();
    }

    /**
     * Stops the timer
     */
    public void stop() {
        go = false;
    }

    /**
     * Resets the timer
     */
    public void reset() {
        go = false;
        setText("0");
    }
}
