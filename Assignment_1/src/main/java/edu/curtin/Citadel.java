package edu.curtin;

public class Citadel {
    private int q, r;

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public Citadel(int q, int r) {
        this.q = q;
        this.r = r;
    }

    //TO check whether if a robot has reached the citadel or not
    public boolean isReached(int robot_q, int robot_r) {
        return q == robot_q && r == robot_r;
    }

    public void getCitadel() {

    }











    
}
