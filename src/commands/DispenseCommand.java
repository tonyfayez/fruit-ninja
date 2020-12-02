package commands;

import application.MainGameFormController;
import application.SliceableTask;
import game.objects.Sliceable;

public class DispenseCommand implements Command {
    private SliceableTask task;

    public DispenseCommand(Sliceable sliceable) {
        task = new SliceableTask(sliceable);
    }
    @Override
    public void execute() {
        MainGameFormController.getInstance().scheduleSliceable(task, 0);
    }
}
