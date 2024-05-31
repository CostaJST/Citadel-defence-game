package edu.curtin;

public class Wall {
    //Used to coordinate with walls
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
    }  

    public void add(Wall wall) {}

}
