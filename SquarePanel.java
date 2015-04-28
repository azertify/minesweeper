import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A panel which will always stay square
 * @author Samuel Lord
 */
public class SquarePanel extends JPanel {
    public SquarePanel(int dimension) {
        super();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int x = getWidth();
                int y = getHeight();
                if (x < y) {
                    setBorder(BorderFactory.createEmptyBorder((y - x) / 2, 0, (y - x) / 2, 0));
                } else {
                    setBorder(BorderFactory.createEmptyBorder(0, (x - y) / 2, 0, (x - y) / 2));
                }
            }
        });

        GridLayout layout = new GridLayout(dimension, dimension);
        setLayout(layout);
    }
}
