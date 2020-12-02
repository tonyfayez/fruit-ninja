package commands;

import application.MainGameFormController;
import game.Game;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EndGameCommand implements Command {
    @Override
    public void execute() {
        Game game = Game.getCurrentGame();
        game.stopGame();
        MainGameFormController.getInstance().gameOver();
        int highScore = game.getHighScore();
        String fileName = game.getStrategy().toString() + "_high_score";
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName));
            outputStream.writeInt(highScore);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
