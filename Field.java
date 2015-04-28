import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

/**
 * A single field where a mine may be
 * @author Samuel Lord
 */
public class Field extends JButton {
    public static final int MINE = -1;
    private static final int COVERED = -2;

    private Map<String, Image> images;
    private boolean flag = false;
    private int uncovered = COVERED;

    /**
     * Makes a new field
     * @param images List of all the images it uses, already read into memory
     */
    public Field(Map<String, Image> images) {
        super();
        this.images = images;
        setMinimumSize(new Dimension(32, 32));
        setPreferredSize(new Dimension(32, 32));
        setBackground(new Color(192, 192, 192));
        setIcon(toIcon(images.get("default")));
        setPressedIcon(toIcon(images.get("pressed")));
        setBorderPainted(false);
        setFocusable(false);
        setMargin(new Insets(0, 0, 0, 0));
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (flag) {
                    setIcon(toIcon(images.get("flag")));
                    setPressedIcon(toIcon(images.get("pressedflag")));
                } else if (uncovered >= 0) {
                    uncover(uncovered);
                } else {
                    setIcon(toIcon(images.get("default")));
                    setPressedIcon(toIcon(images.get("pressed")));
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                    toggleFlag();
                }
            }
        });
    }

    /**
     * Returns whether this square has a flag
     * @return Whether this has a flag
     */
    public boolean hasFlag() {
        return flag;
    }

    /**
     * Toggles whether this square has a flag
     */
    public void toggleFlag() {
        if (uncovered < 0) {
            flag = !flag;
            if (flag) {
                setIcon(toIcon(images.get("flag")));
                setPressedIcon(toIcon(images.get("pressedflag")));
            } else {
                setIcon(toIcon(images.get("default")));
                setPressedIcon(toIcon(images.get("pressed")));
            }
        }
    }

    /**
     * Reveals the number (or mine) underneath the square
     * @param i The number of adjacent mines (or MINE if just a mine)
     */
    public void uncover(int i) {
        uncovered = i;
        if (i > 8) {
            return;
        }
        switch (i) {
            case MINE:
                setIcon(toIcon(images.get("mine")));
                setPressedIcon(toIcon(images.get("mine")));
                break;
            case 0:
                setIcon(toIcon(images.get("0")));
                setPressedIcon(toIcon(images.get("0")));
                break;
            case 1:
                setIcon(toIcon(images.get("1")));
                setPressedIcon(toIcon(images.get("1")));
                break;
            case 2:
                setIcon(toIcon(images.get("2")));
                setPressedIcon(toIcon(images.get("2")));
                break;
            case 3:
                setIcon(toIcon(images.get("3")));
                setPressedIcon(toIcon(images.get("3")));
                break;
            case 4:
                setIcon(toIcon(images.get("4")));
                setPressedIcon(toIcon(images.get("4")));
                break;
            case 5:
                setIcon(toIcon(images.get("5")));
                setPressedIcon(toIcon(images.get("5")));
                break;
            case 6:
                setIcon(toIcon(images.get("6")));
                setPressedIcon(toIcon(images.get("6")));
                break;
            case 7:
                setIcon(toIcon(images.get("7")));
                setPressedIcon(toIcon(images.get("7")));
                break;
            case 8:
                setIcon(toIcon(images.get("8")));
                setPressedIcon(toIcon(images.get("8")));
                break;
        }
    }

    /**
     * Scales icon to right size
     * @param image Image to scale
     * @return Icon!
     */
    public ImageIcon toIcon(Image image) {
        if (getWidth() > 0 && getHeight() > 0) {
            return new ImageIcon(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
        } else {
            return new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_FAST));
        }
    }
}
