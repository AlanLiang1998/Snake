import java.awt.*;
import java.util.Random;

public class Egg {
    public static final int WIDTH = Yard.BLOCK_SIZE;
    public static final int HEIGHT = Yard.BLOCK_SIZE;
    int row;
    int col;
    static Random rand = new Random();

    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Egg() {
        this(rand.nextInt(Yard.ROWS - 3) + 3, rand.nextInt(Yard.COLS - 3) + 3);
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
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
        setRow(rand.nextInt(Yard.ROWS - 3) + 3);
        setCol(rand.nextInt(Yard.COLS - 3) + 3);
    }


}
