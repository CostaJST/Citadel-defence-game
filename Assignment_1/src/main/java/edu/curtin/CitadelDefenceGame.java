package edu.curtin;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



public class CitadelDefenceGame {

    private Grid grid;
    private boolean gameOver = false;

    public CitadelDefenceGame() {
        grid = new Grid();
    }

    public void update() {
        grid.update();
        grid.wallBuildingQueue();

        if(!gameOver && isGameOver()) {
            endGame();
        }
    }

    private boolean isGameOver() {
        for(Robot robot : grid.getRobots()) {
            if (robot.getX() == grid.getCitadel().getX() && robot.getY() == grid.getCitadel().getY()) {
                return true;
            }
        }
        return false;
    }

    private void endGame() {
        int finalScore = calculateScore();
        System.out.println("The Game is Over!! Your Final Score is: " + finalScore);
        gameOver = true;
    }

    private int calculateScore() {
        
        return 0;
    }
    public static void main(String[] args) {
        //Used for managing the dynamic creation of robots
        BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();
        //Creating the thread pool
        ExecutorService executorService = Executors. newCachedThreadPool();

        //Starting a thread to create robots randomly
        Runnable robotCreator = (() -> {
            int robot_count = 0;
            while (true) {
                Robot robot = new Robot(++robot_count, null, null, commandQueue);
                executorService.submit(robot);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                executorService.shutdown();
                try {
                    executorService.awaitTermination(1, TimeUnit.MINUTES);  
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        });

        CitadelDefenceGame game = new CitadelDefenceGame();
        game.run();

    }

    private void run() {
    } 
    
}

