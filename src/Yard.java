import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
    private static final int ROWS = 40;
    private static final int COLS = 40;
    private static final int BLOCK_SIZE = 25;
    private static final int WIDTH = ROWS * BLOCK_SIZE;
    private static final int HEIGHT = COLS * BLOCK_SIZE;

    public void launch() {
        setTitle("Retro Snaker");
        setLocation(100, 100);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setBackground(Color.GRAY);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.DARK_GRAY);
        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, WIDTH, BLOCK_SIZE * i);
        }
        for (int i = 1; i < COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, HEIGHT);
        }
    }

    public static void main(String[] args) {
        new Yard().launch();
    }
}
