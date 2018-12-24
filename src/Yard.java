import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*需完善的地方：
1、边界处理
2、Egg出现在Snake身上的bug*/
public class Yard extends Frame {
    public static final int ROWS = 30;
    public static final int COLS = 30;
    public static final int BLOCK_SIZE = 30;
    public static final int WIDTH = ROWS * BLOCK_SIZE;
    public static final int HEIGHT = COLS * BLOCK_SIZE;
    Image offScreenImage = null;
    Snake s = new Snake(this);
    Egg e = new Egg(this);
    PaintThread pt = new PaintThread();
    boolean gameOver = false;
    private int score = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void launch() {
        setTitle("Retro Snaker");
        setLocation(100, 100);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setBackground(Color.GREEN);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        new Thread(pt).start();
        addKeyListener(new KeyMonitor());
        setVisible(true);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setFont(new Font("华文彩云", Font.BOLD, 30));
        g.setColor(Color.RED);
        g.drawString("score: " + getScore(), 10, 60);
        if (gameOver == true) {
            g.setFont(new Font("华文彩云", Font.BOLD, 50));
            g.drawString("GAME OVER", 300, 450);
            pt.over();
        }
        g.setColor(Color.DARK_GRAY);
        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, WIDTH, BLOCK_SIZE * i);
        }
        for (int i = 1; i < COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, HEIGHT);
        }
        g.setColor(c);
        s.draw(g);
        e.draw(g);
        s.eat(e);
    }

    public void update(Graphics g) {
        if (offScreenImage == null)
            offScreenImage = createImage(WIDTH, HEIGHT);
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        paint(gOffScreen);
        gOffScreen.setColor(c);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void stop() {
        gameOver = true;
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            s.keyPressed(e);
        }
    }

    private class PaintThread implements Runnable {
        boolean running = true;

        @Override
        public void run() {
            while (running) {
                repaint();
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void over() {
            running = false;
        }
    }

    public static void main(String[] args) {
        new Yard().launch();
    }
}
