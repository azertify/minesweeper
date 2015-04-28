import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Minesweeper Game Logic
 * @author Samuel Lord
 */
public class Game {
    // Dimension, must be 10 due to Coordinate logic
    private static final int DIMENSION = 10;
    // Number of mines on field
    private static final int MINES = 10;
    // The user interface it talks back to
    private UserInterface ui;
    // List of all the coordinates making up the field
    private List<Coord> field;

    /**
     * Starts new game, with a UI to report back to.
     * @param ui The user interface which the game talks to
     */
    public Game(UserInterface ui) {
        this.ui = ui;
        field = new ArrayList<>();
        generateField();
    }

    private void generateField() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                field.add(new Coord(i, j));
            }
        }
    }

    private void generateMines(Coord firstHit) {
        Random rand = new Random();
        for (int i = 0; i < MINES; i++) {
            int randInt = rand.nextInt(DIMENSION * DIMENSION);
            if (field.get(randInt).isMine() || field.get(randInt) == firstHit) {
                i--; // collision, need new mine
            } else {
                field.get(randInt).setMine(true);
            }
        }
    }

    public int getDimension() {
        return DIMENSION;
    }

    public Coord getCoord(int x, int y) {
        return field.get(x * 10 + y);
    }

    public Coord getCoord(int i) {
        if (i >= 0 && i < field.size()) {
            return field.get(i);
        } else {
            return null;
        }
    }

    public List<Coord> surroundingSquares(Coord coord) {
        List<Coord> coords = new ArrayList<>();
        int x = coord.getX();
        int y = coord.getY();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0
                        && x + i < DIMENSION
                        && y + j >= 0
                        && y + j < DIMENSION) {
                    coords.add(getCoord(x + i, y + j));
                }
            }
        }
        return coords;
    }

    public List<Coord> surroundingMineSquares(Coord coord) {
        List<Coord> coords = new ArrayList<>();
        surroundingSquares(coord)
                .stream()
                .filter(Coord::isMine)
                .forEach(coords::add);
        return coords;
    }

    public void uncover(Coord coord) {
        if (field.stream().allMatch(c -> !c.isMine())) {
            generateMines(coord);
            ui.startTimer();
        }
        if (coord.isUncovered()) {
            return;
        }
        if (!coord.isMine()) {
            int mines = surroundingMineSquares(coord).size();
            ui.uncover(coord, mines);
            coord.uncover();
            if (winCondition()) {
                ui.win();
                return;
            }
            if (mines == 0) {
                surroundingSquares(coord)
                        .stream()
                        .forEach(this::uncover);
            }
        } else {
            field
                    .stream()
                    .filter(Coord::isMine)
                    .forEach(c ->  ui.uncover(c, Field.MINE));
            ui.lose();
        }
    }

    public boolean winCondition() {
        return field
                .stream()
                .filter(coord -> !coord.isMine())
                .allMatch(Coord::isUncovered);
    }
}
