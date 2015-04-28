import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

/**
 * Minesweeper UI
 * @author Samuel Lord
 */
public class GameGui implements UserInterface {
    // The game itself, containing all game logic
    private Game game;
    // The game's window
    private JFrame frame;
    // All of the fields in the game
    private List<Field> fields;
    // Game timer
    private Timer timer;

    /**
     * Constructor, makes the GUI, a new game, and sets the minimum frame size
     */
    public GameGui() {
        makeFrame();
        newGame();
        frame.setMinimumSize(frame.getSize());
    }

    /**
     * Main method, starts the game
     */
    public static void main(String[] args) {
        GameGui game = new GameGui();
    }

    /**
     * Makes the main frame, only call this once!
     */
    private void makeFrame() {
        frame = new JFrame("Minesweeper");
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        timer = new Timer();
        container.add(timer, BorderLayout.NORTH);
        frame.setJMenuBar(makeMenuBar());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Makes the menu bar, and attaches event listeners to the items
     * @return Menu bar
     */
    private JMenuBar makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // These objects are used repeatedly to save typing
        JMenu menu;
        JMenuItem item;

        menu = new JMenu("Game");
        menuBar.add(menu);

        item = new JMenuItem("New Game");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        menu.add(item);

        item = new JMenuItem("Quit");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        menu.add(item);

        menu = new JMenu("Help");
        menuBar.add(menu);

        item = new JMenuItem("How to play");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                howTo();
            }
        });
        menu.add(item);

        item = new JMenuItem("About");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                about();
            }
        });
        menu.add(item);

        return menuBar;
    }

    /**
     * Makes a new game panel
     * @return new game panel
     */
    private SquarePanel makeGamePanel() {
        Map<String, Image> images = new HashMap<>();
        try {
            images.put("default", ImageIO.read(getClass().getResource("/res/default.png")));
            images.put("pressed", ImageIO.read(getClass().getResource("/res/pressed.png")));
            images.put("pressedflag", ImageIO.read(getClass().getResource("/res/pressedflag.png")));
            images.put("flag", ImageIO.read(getClass().getResource("/res/flag.png")));
            images.put("mine", ImageIO.read(getClass().getResource("/res/mine.png")));
            images.put("0", ImageIO.read(getClass().getResource("/res/0.png")));
            images.put("1", ImageIO.read(getClass().getResource("/res/1.png")));
            images.put("2", ImageIO.read(getClass().getResource("/res/2.png")));
            images.put("3", ImageIO.read(getClass().getResource("/res/3.png")));
            images.put("4", ImageIO.read(getClass().getResource("/res/4.png")));
            images.put("5", ImageIO.read(getClass().getResource("/res/5.png")));
            images.put("6", ImageIO.read(getClass().getResource("/res/6.png")));
            images.put("7", ImageIO.read(getClass().getResource("/res/7.png")));
            images.put("8", ImageIO.read(getClass().getResource("/res/8.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fields = new ArrayList<>();
        SquarePanel panel = new SquarePanel(game.getDimension());
        int grid = game.getDimension() * game.getDimension();
        Field button;
        for (int i = 0; i < grid; i++) {
            button = new Field(images);
            button.setActionCommand(String.valueOf(i));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clicked(e);
                }
            });
            fields.add(button);
            panel.add(button);
        }
        return panel;
    }

    /**
     * Sets the game panel
     * @param panel The new game panel
     */
    private void setGamePanel(SquarePanel panel) {
        Container container = frame.getContentPane();
        Component component = ((BorderLayout) container.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (component != null) {
            container.remove(component);
        }
        container.add(panel, BorderLayout.CENTER);
    }

    /**
     * Starts a new game
     */
    private void newGame() {
        game = new Game(this);
        setGamePanel(makeGamePanel());
        timer.reset();
        frame.pack();
    }

    /**
     * Quits the game, ends process
     */
    private void quit() {
        System.exit(0); // 0 is the success exit code
    }

    /**
     * Shows a message describing the game
     */
    private void about() {
        JOptionPane.showMessageDialog(frame,
                "Minesweeper is a game where you must clear\n" +
                        "all the mines, developed by Samuel Lord",
                "About Minesweeper",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows a dialog box explaining how to play
     */
    private void howTo() {
        JOptionPane.showMessageDialog(frame,
                "Left click on spaces you are certain aren't mines.\n" +
                        "Right click on spaces that are mines to flag them.\n" +
                        "The underneath a square represents how many mines\n" +
                        "surround it. If there is no number, then no mines surround it.",
                "How to play",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Tells the game to uncover this square
     * @param e Event containing info about the square to uncover
     */
    private void clicked(ActionEvent e) {
        game.uncover(game.getCoord(Integer.valueOf(e.getActionCommand())));
    }

    /**
     * Uncovers a square, writes the number of surrounding mines to that square
     * @param coord The square to uncover
     * @param surrounding The number of surrounding mines (to write on the square)
     */
    public void uncover(Coord coord, int surrounding) {
        Field field = fields.get(coord.toInt());
        if (field.hasFlag()) {
            field.toggleFlag();
        }
        field.uncover(surrounding);
    }

    /**
     * Lose the game
     */
    public void lose() {
        timer.stop();
        for (Field button : fields) {
            button.setEnabled(false);
        }
        JOptionPane.showMessageDialog(frame, "Boom! You lose!");
    }

    /**
     * Win the game
     */
    public void win() {
        timer.stop();
        for (Field button : fields) {
            button.setEnabled(false);
        }
        JOptionPane.showMessageDialog(frame, "You won in " + timer.getText() + " seconds!");
    }

    /**
     * Starts the timer
     */
    public void startTimer() {
        timer.start();
    }
}
