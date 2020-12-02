package commands;

import application.MainGameFormController;
import game.Game;
import game.strategies.LivesStrategy;

public class LoseLifeCommand implements Command {

    @Override
    public void execute() {
        LivesStrategy livesStrategy = (LivesStrategy) Game.getCurrentGame().getStrategy();
        livesStrategy.decrementLives();
        MainGameFormController.getInstance().updateLives(livesStrategy.getLives());
    }
}
