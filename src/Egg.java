import java.awt.*;
import java.util.Random;

public class Egg {
    public static final int WIDTH = Yard.BLOCK_SIZE;
    public static final int HEIGHT = Yard.BLOCK_SIZE;
    int row;
    int col;
    Yard y = null;
    static Random rand = new Random();

    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Egg(Yard y) {
        this(rand.nextInt(Yard.ROWS - 3) + 3, rand.nextInt(Yard.COLS - 3) + 3);
        this.y = y;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillOval(WIDTH * col, HEIGHT * row, WIDTH, HEIGHT);
        g.setColor(c);
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
        int row = rand.nextInt(Yard.ROWS - 3) + 3;
        int col = rand.nextInt(Yard.COLS - 3) + 3;
        while (!y.s.checkPosition(row, col)) {
            row = rand.nextInt(Yard.ROWS - 3) + 3;
            col = rand.nextInt(Yard.COLS - 3) + 3;
        }
        setRow(row);
        setCol(col);
    }


}
