package commands;

import application.MainGameFormController;
import game.objects.Banana;
import game.objects.Fruit;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class SliceAllCommand implements Command {
    @Override
    public void execute() {
        for(Node n: MainGameFormController.getInstance().getFruitsPane().getChildren().toArray(new Node[0])) {
            if(n instanceof ImageView && !(n.getUserData() instanceof Banana)
                    && n.getUserData() instanceof Fruit && n.getProperties().get("isSliced") == null)
                Controller.executeCommand(new SliceCommand((ImageView)n));
        }
    }
}
