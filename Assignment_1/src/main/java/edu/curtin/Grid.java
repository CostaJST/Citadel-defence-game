package edu.curtin;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Grid {
    public static final int SIZE = 9;
    private List<Robot> robots;
    private boolean[][] occupied; //Use this to track the occupied squares in the grid
    private Random random = new Random();
    //Blocking Queue to pile up aall the wall building requests
    private Queue<WallBuildingRequest> wallBuildingQueue = new LinkedBlockingQueue<>();

    public List<Robot> getRobots() {
        return robots;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    public Grid() {
        robots = new ArrayList<>();
    }

    public boolean isGridEmpty(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }
        return !occupied[x][y];
    }

    public void clearGrid(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            occupied[x][y] = false;
        }
    }

    public void setGrid(int x, int y, Robot robot) {
        occupied[x][y] = true;
        robot.setX(x);
        robot.setY(y);
    }

    public void update() {}

    //To add the robot to the grid
    public void addRobot(Robot robot) {
        int s, t;
        do {
            s = random.nextInt(SIZE);
            t = random.nextInt(SIZE);
        } while (!isGridEmpty(s, t));

        robot.setX(s);
        robot.setY(t);
        robots.add(robot);

        occupied[s][t] = true;
    }

    public void wallBuildingQueue() {
        long currentTime = System.currentTimeMillis();
        while(!wallBuildingQueue.isEmpty()) {
            WallBuildingRequest request = wallBuildingQueue.peek();
            if(currentTime - request.getTimeStamp() >= 2000) {
                buidWall(request.getX, request.getY());
                wallBuildingQueue.poll();
            } else {
                break;
            }
        }
    }

    private void buidWall(Object getX, Object y) {
    }

    public void buildWall(int x, int y) {
        if (isGridEmpty(x, y)) {
            Wall wall = new Wall(x, y);
            wall.add(wall);
            occupied[x][y] = true;
        }
    }

    public Robot getCitadel() {
        return null;
    }


    

    
}
