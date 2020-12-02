package game.objects;

import commands.Controller;
import commands.SliceAllCommand;
import javafx.scene.image.Image;

import java.util.List;

public class Banana extends Fruit {
    Banana(List<Image> images, int fruitScore) {
        super(images, fruitScore);
    }
    @Override
    public void slice() {
        Controller.executeCommand(new SliceAllCommand());
    }
}
