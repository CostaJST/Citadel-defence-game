package edu.curtin;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Robot implements Runnable{
    
    private final int id;
    private final int delay;
    private int x, y; //Use this to handle wall directions
    private static int robot_count = 0;
    private Grid grid;
    //private String name;
    private BlockingQueue<String> commandQueue; //To deal with player commands/ to deal with main game logic(e.g: UP, DOWN keys...)
    //Adding mutex for command queue
    private final Object lock = new Object();
    public Robot(int delay, Grid grid, String name, BlockingQueue<String> commandQueue) {
        this.id = ++robot_count;
        this.delay = delay;
        this.grid = grid;
        //this.name = name;
        this.commandQueue = commandQueue;

        //Getting a robot in a random corner of the grid
        Random random = new Random();
        x = random.nextInt(Grid.SIZE);
        y = random.nextInt(Grid.SIZE);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void run() {

        try {
            while (true) {
                String command;
                synchronized (lock) {
                    if (commandQueue.isEmpty()) {
                        lock.wait();
                    }
                    command = commandQueue.take();
                }

                if (command != null) {
                    if (command.equals("UP")) {
                        moveUp();
                    } else if (command.equals("DOWN")) {
                        moveDown();
                    } else if (command.equals("LEFT")) {
                        moveLeft();
                    } else if (command.equals("RIGHT")) {
                        moveRight();
                    } else if (command.equals("EXIT")) {
                        break;
                    }
                }
                TimeUnit.MILLISECONDS.sleep(delay);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //Implementing robot's movements
    private void moveUp() {
        if (y > 0 && grid.isGridEmpty(x, y - 1)) {
            grid.clearGrid(x, y);
            y--;
            grid.setGrid(x, y, this);
        }
    }

    private void moveDown() {
        if (y < Grid.SIZE - 1 && grid.isGridEmpty(x, y + 1)) {
            grid.clearGrid(x, y);
            y++;
            grid.setGrid(x, y, this);
        }
    }

    private void moveLeft() {
        if (x > 0 && grid.isGridEmpty(x - 1, y)) {
            grid.clearGrid(x, y);
            x--;
            grid.setGrid(x, y, this);
        }
    }
    private void moveRight() {
        if (x < Grid.SIZE - 1 && grid.isGridEmpty(x + 1, y)) {
            grid.clearGrid(x, y);
            x++;
            grid.setGrid(x, y, this);
        }
    }

}
