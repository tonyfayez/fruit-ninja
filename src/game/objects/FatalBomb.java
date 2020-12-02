package game.objects;

import commands.Controller;
import commands.EndGameCommand;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;

public class FatalBomb extends Sliceable {
    private static final List<Image> IMAGES = new ArrayList<>();
    private static final List<Media> SOUNDS = new ArrayList<>();
    static {
        IMAGES.add(new Image("/bombFatal.png"));
        SOUNDS.add(new Media(FatalBomb.class.getResource("/bomb.mp3").toString()));
    }

    FatalBomb() {
        super(IMAGES, SOUNDS);
    }

    @Override
    public void slice() {
        Controller.executeCommand(new EndGameCommand());
    }
}
