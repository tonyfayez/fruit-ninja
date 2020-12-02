package commands;

import application.MainGameFormController;
import javafx.application.Platform;

public class UpdateTimeCommand implements Command {
    private int seconds;
    public UpdateTimeCommand(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> MainGameFormController.getInstance().updateTime(String.format("%01d:%02d",seconds/60,seconds%60)));
    }
}
