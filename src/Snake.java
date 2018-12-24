import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
    private class Node {
        public static final int WIDTH = Yard.BLOCK_SIZE;
        public static final int HEIGHT = Yard.BLOCK_SIZE;
        int row;
        int col;
        Direction dir = Direction.LEFT;
        Node next = null;
        Node prev = null;

        public Node(int row, int col, Direction dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.fillRect(col * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
            g.setColor(c);
        }
    }

    Node head = null;
    Node tail = null;
    int size = 0;
    Node init = new Node(20, 20, Direction.LEFT);
    Yard y = null;

    public Snake(Yard y) {
        head = init;
        tail = init;
        size = 1;
        this.y = y;
    }

    public void addToTail() {
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
        size++;
    }

    public void addToHead() {
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
        size++;
    }

    public void draw(Graphics g) {
        if (size <= 0)
            return;
        move();
        for (Node node = head; node != null; node = node.next) {
            node.draw(g);
        }
    }

    private void deleteTail() {
        if (size == 0)
            return;
        tail = tail.prev;
        tail.next = null;
    }

    private void move() {
        addToHead();
        deleteTail();
        checkDeadth();
    }

    public boolean checkDeadth() {
        if (head.row < 2 || head.col < 0 || head.row > Yard.ROWS || head.col > Yard.COLS) {
            y.stop();
            return true;
        }
        for (Node node = head.next; node != null; node = node.next) {
            if (head.row == node.row && head.col == node.col) {
                y.stop();
                return true;
            }
        }
        return false;
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

    public Rectangle getRect() {
        return new Rectangle(Node.WIDTH * head.col, Node.HEIGHT * head.row, Node.WIDTH, Node.HEIGHT);
    }

    public void eat(Egg e) {
        if (this.getRect().intersects(e.getRect())) {
            e.refresh();
            addToHead();
            y.setScore(y.getScore() + 5);
        }
    }
}
