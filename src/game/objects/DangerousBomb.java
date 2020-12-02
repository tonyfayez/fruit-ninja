package game.objects;

import commands.Controller;
import commands.LoseLifeCommand;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;

public class DangerousBomb extends Sliceable {
    private static final List<Image> IMAGES = new ArrayList<>();
    private static final List<Media> SOUNDS = new ArrayList<>();
    static {
        IMAGES.add(new Image("/bombTime.png"));
        SOUNDS.add(new Media(DangerousBomb.class.getResource("/bomb.mp3").toString()));
    }

    DangerousBomb() {
        super(IMAGES, SOUNDS);
    }

    @Override
    public void slice() {
        Controller.executeCommand(new LoseLifeCommand());
    }
}
