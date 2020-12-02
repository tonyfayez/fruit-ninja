package game.strategies;

import commands.Controller;
import commands.EndGameCommand;
import commands.UpdateTimeCommand;
import game.Game;
import game.gamestate.TimeDispenser;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class TimeStrategy implements GameStrategy {
    private int gameTime;
    private Timer timer;

    @Override
    public void initialize() {
        Controller.execute();
        gameTime = 90;
        Controller.executeCommand(new UpdateTimeCommand(gameTime));
        timer = new Timer(true);
        startTimer();
        Game.getCurrentGame().setState(new TimeDispenser(1300));
        Game.getCurrentGame().addObserver((observable, arg) -> {
            if(Game.GAME_STOPPED.equals(arg))
                timer.cancel();
        });
    }

    private void startTimer(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(gameTime-- == 0) {
                    timer.cancel();
                    Platform.runLater(() -> Controller.executeCommand(new EndGameCommand()));
                }
                else
                    Controller.executeCommand(new UpdateTimeCommand(gameTime));
            }
        }, 1000, 1000);
    }

    @Override
    public String toString() {
        return "Arcade";
    }
}
