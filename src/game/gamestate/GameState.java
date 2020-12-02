package game.gamestate;

import commands.Controller;
import commands.DispenseCommand;
import game.Game;

import java.util.Observer;
import java.util.Random;

public abstract class GameState {
    static Random random = new Random();
    int delay = 0;
    int maxInterval;
    private Observer endGameObserver;
    private Thread scheduler = new Thread(() -> {
        while(!Thread.interrupted()) {
            Controller.executeCommand(nextDispense());
            try {
                Thread.sleep(delay);
                delay = 0;
            } catch (InterruptedException e) {
                break;
            }
        }
    });

    GameState(int maxInterval) {
        this.maxInterval = maxInterval;
        System.out.println("Max interval between fruit = " + maxInterval + "ms");
        scheduler.setDaemon(true);
        scheduler.start();
        endGameObserver = (observable, arg) -> {
            if(Game.GAME_STOPPED.equals(arg))
                scheduler.interrupt();
        };
        Game.getCurrentGame().addObserver(endGameObserver);
    }


    void stop() {
        scheduler.interrupt();
        Game.getCurrentGame().deleteObserver(endGameObserver);
    }

    abstract DispenseCommand nextDispense();
}
