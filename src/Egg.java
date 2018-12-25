import java.awt.*;
import java.util.Random;

public class Egg {
    private static final int WIDTH = UI.BLOCK_SIZE;
    private static final int HEIGHT = UI.BLOCK_SIZE;
    private static Random rand = new Random();
    private int row;
    private int col;
    private UI ui = null;
    Color color = Color.RED;

    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Egg(UI ui) {
        this(rand.nextInt(UI.ROWS - 3) + 3, rand.nextInt(UI.COLS - 3) + 3);
        this.ui = ui;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(color);
        g.fillOval(WIDTH * col, HEIGHT * row, WIDTH, HEIGHT);
        g.setColor(c);
        if (color == Color.RED)
            color = Color.YELLOW;
        else
            color = Color.RED;
    }

    public Rectangle getRect() {
        return new Rectangle(WIDTH * col, HEIGHT * row, WIDTH, HEIGHT);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void refresh() {
        int row = rand.nextInt(UI.ROWS - 3) + 3;
        int col = rand.nextInt(UI.COLS - 3) + 3;
        while (!ui.s.checkPosition(row, col)) {
            row = rand.nextInt(UI.ROWS - 3) + 3;
            col = rand.nextInt(UI.COLS - 3) + 3;
        }
        setRow(row);
        setCol(col);
    }

}
