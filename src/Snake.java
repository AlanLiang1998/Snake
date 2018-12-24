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

    public Snake() {
        head = init;
        tail = init;
        size = 1;
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
        for (Node node = head; node != null; node = node.next) {
            node.draw(g);
        }
        move();
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
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                head.dir = Direction.LEFT;
                break;
            case KeyEvent.VK_W:
                head.dir = Direction.UP;
                break;
            case KeyEvent.VK_D:
                head.dir = Direction.RIGHT;
                break;
            case KeyEvent.VK_S:
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
        }
    }
}
