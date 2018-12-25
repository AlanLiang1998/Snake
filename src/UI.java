import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UI extends Frame {
    public static final int ROWS = 30;
    public static final int COLS = 30;
    public static final int BLOCK_SIZE = 30;
    private static final int WIDTH = ROWS * BLOCK_SIZE;
    private static final int HEIGHT = COLS * BLOCK_SIZE;
    Snake s;
    private Image offScreenImage;
    private Egg e;
    private PaintThread pt;
    private boolean gameOver;
    private int score;
    private long startTime;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static void main(String[] args) {
        new UI().launch();
    }

    private void startGame() {
        offScreenImage = null;
        s = new Snake(this);
        e = new Egg(this);
        pt = new PaintThread();
        gameOver = false;
        score = 0;
        startTime = System.currentTimeMillis();
    }

    private void launch() {
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
        addKeyListener(new KeyMonitor());
        startGame();
        new Thread(pt).start();
        setVisible(true);
    }

    private void drawRecordFont(Graphics g) {
        Color c = g.getColor();
        g.setFont(new Font("华文彩云", Font.BOLD, 30));
        g.setColor(Color.RED);
        g.drawString("score: " + getScore(), 50, 60);
        g.drawString("length: " + s.getLength(), 250, 60);
        long currentTime = System.currentTimeMillis();
        long usedTime = (currentTime - startTime) / 1000;
        g.drawString("time: " + usedTime + "s", 450, 60);
        g.setColor(c);
    }

    private void drawGameOverFont(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.setFont(new Font("华文彩云", Font.BOLD, 50));
        g.drawString("GAME OVER", 300, 450);
        pt.over();
        g.setColor(c);
    }

    private void drawGrid(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.DARK_GRAY);
        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, WIDTH, BLOCK_SIZE * i);
        }
        for (int i = 1; i < COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, HEIGHT);
        }
        g.setColor(c);
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

    public void paint(Graphics g) {
        drawRecordFont(g);
        drawGrid(g);
        if (gameOver)
            drawGameOverFont(g);
        s.draw(g);
        e.draw(g);
        s.eat(e);
    }
}
