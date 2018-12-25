import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Snake {
    private static Random rand;
    private Node head;
    private Node tail;
    private int length;
    private UI ui;

    private class Node {
        static final int WIDTH = UI.BLOCK_SIZE;
        static final int HEIGHT = UI.BLOCK_SIZE;
        int row;
        int col;
        Direction dir;
        Node next = null;
        Node prev = null;

        Node(int row, int col, Direction dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.drawOval(col * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
            g.setColor(Color.YELLOW);
            g.fillOval(col * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
            g.setColor(c);
        }
    }

    public Snake(UI ui) {
        init();
        Direction dir = getRandDir();
        Node initNode = new Node(20, 20, dir);
        head = initNode;
        tail = initNode;
        length = 1;
        this.ui = ui;
    }

    private void init() {
        head = null;
        tail = null;
        length = 0;
        ui = null;
        rand = new Random();
    }

    private Direction getRandDir() {
        Direction[] dirs = Direction.values();
        int d = rand.nextInt(dirs.length);
        return dirs[d];
    }

    public int getLength() {
        return length;
    }

    private void addToTail() {
        Node node = null;
        switch (tail.dir) {
            case LEFT:
                node = new Node(tail.row, tail.col + 1, Direction.LEFT);
                break;
            case UP:
                node = new Node(tail.row + 1, tail.col, Direction.UP);
                break;
            case RIGHT:
                node = new Node(tail.row, tail.col - 1, Direction.RIGHT);
                break;
            case DOWN:
                node = new Node(tail.row - 1, tail.col, Direction.DOWN);
                break;
        }
        tail.next = node;
        node.prev = tail;
        tail = node;
        length++;
    }

    private void addToHead() {
        Node node = null;
        switch (head.dir) {
            case LEFT:
                node = new Node(head.row, head.col - 1, Direction.LEFT);
                break;
            case UP:
                node = new Node(head.row - 1, head.col, Direction.UP);
                break;
            case RIGHT:
                node = new Node(head.row, head.col + 1, Direction.RIGHT);
                break;
            case DOWN:
                node = new Node(head.row + 1, head.col, Direction.DOWN);
                break;
        }
        node.next = head;
        head.prev = node;
        head = node;
        length++;
    }

    public void draw(Graphics g) {
        if (length <= 0)
            return;
        move();
        for (Node node = head; node != null; node = node.next) {
            node.draw(g);
        }
    }

    private void deleteTail() {
        if (length <= 0)
            return;
        tail = tail.prev;
        tail.next = null;
        length--;
    }

    private void checkDeadth() {
        if (head.row < 2 || head.col < 0 || head.row >= UI.ROWS || head.col >= UI.COLS) {
            ui.stop();
        }
        for (Node node = head.next; node != null; node = node.next) {
            if (head.row == node.row && head.col == node.col) {
                ui.stop();
            }
        }
    }

    private void move() {
        addToHead();
        deleteTail();
        checkDeadth();
    }

    private Rectangle getRect() {
        return new Rectangle(Node.WIDTH * head.col, Node.HEIGHT * head.row, Node.WIDTH, Node.HEIGHT);
    }


    public boolean checkPosition(int row, int col) {
        for (Node node = head; node != null; node = node.next) {
            if (row == node.row && col == node.col) {
                return false;
            }
        }
        return true;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                if (head.dir != Direction.RIGHT)
                    head.dir = Direction.LEFT;
                break;
            case KeyEvent.VK_W:
                if (head.dir != Direction.DOWN)
                    head.dir = Direction.UP;
                break;
            case KeyEvent.VK_D:
                if (head.dir != Direction.LEFT)
                    head.dir = Direction.RIGHT;
                break;
            case KeyEvent.VK_S:
                if (head.dir != Direction.UP)
                    head.dir = Direction.DOWN;
                break;
        }
    }

    public void eat(Egg e) {
        if (this.getRect().intersects(e.getRect())) {
            e.refresh();
            addToHead();
            ui.setScore(ui.getScore() + 5);
        }
    }
}
