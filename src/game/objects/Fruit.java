package game.objects;

import game.Game;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;

public class Fruit extends Sliceable {
    private static final List<Image> APPLE_IMAGES = new ArrayList<>();
    private static final List<Image> ORANGE_IMAGES = new ArrayList<>();
    private static final List<Image> KIWI_IMAGES = new ArrayList<>();
    private static final List<Image> POM_IMAGES = new ArrayList<>();
    private static final List<Image> PEAR_IMAGES = new ArrayList<>();
    private static final List<Image> LEMON_IMAGES = new ArrayList<>();
    private static final List<Image> SPECIAL_1_IMAGES = new ArrayList<>();
    private static final List<Image> SPECIAL_2_IMAGES = new ArrayList<>();
    private static final List<Media> SOUNDS = new ArrayList<>();
    static {
        APPLE_IMAGES.add(new Image("/apple.png"));
        APPLE_IMAGES.add(new Image("/appleSplit.png"));
        APPLE_IMAGES.add(new Image("/colorSplash3.png"));
        ORANGE_IMAGES.add(new Image("/orange.png"));
        ORANGE_IMAGES.add(new Image("/orangeSplit.png"));
        ORANGE_IMAGES.add(new Image("/colorSplash2.png"));
        KIWI_IMAGES.add(new Image("/kiwi.png"));
        KIWI_IMAGES.add(new Image("/kiwiSplit.png"));
        KIWI_IMAGES.add(new Image("/colorSplash3.png"));
        POM_IMAGES.add(new Image("/pom.png"));
        POM_IMAGES.add(new Image("/pomSplit.png"));
        POM_IMAGES.add(new Image("/colorSplash1.png"));
        SOUNDS.add(new Media(Fruit.class.getResource("/fruit0.mp3").toString()));
        SOUNDS.add(new Media(Fruit.class.getResource("/fruit1.mp3").toString()));
        SOUNDS.add(new Media(Fruit.class.getResource("/fruit2.mp3").toString()));
        LEMON_IMAGES.add(new Image("/lemon.png"));
        LEMON_IMAGES.add(new Image("/lemonSplit.png"));
        LEMON_IMAGES.add(new Image("/colorSplash2.png"));
        PEAR_IMAGES.add(new Image("/pear.png"));
        PEAR_IMAGES.add(new Image("/pearSplit.png"));
        PEAR_IMAGES.add(new Image("/colorSplash3.png"));
        SPECIAL_1_IMAGES.add(new Image("/specialFruit1.png"));
        SPECIAL_1_IMAGES.add(new Image("/specialFruit1Split.png"));
        SPECIAL_1_IMAGES.add(new Image("/colorSplash3.png"));
        SPECIAL_2_IMAGES.add(new Image("/specialFruit2.png"));
        SPECIAL_2_IMAGES.add(new Image("/specialFruit2Split.png"));
        SPECIAL_2_IMAGES.add(new Image("/colorSplash2.png"));
    }


    private int fruitScore;

    Fruit(List<Image> images, int fruitScore) {
        super(images, SOUNDS);
        this.fruitScore = fruitScore;
    }

    @Override
    public void slice() {
        Game.getCurrentGame().addScore(fruitScore);
    }

    public int getFruitScore() {
        return fruitScore;
    }

    static List<Image> getAppleImages() {
        return APPLE_IMAGES;
    }
    static List<Image> getOrangeImages() {
        return ORANGE_IMAGES;
    }
    static List<Image> getKiwiImages() {
        return KIWI_IMAGES;
    }
    static List<Image> getPomImages() {
        return POM_IMAGES;
    }
    static List<Image> getPearImages() {
        return PEAR_IMAGES;
    }
    static List<Image> getLemonImages() {
        return LEMON_IMAGES;
    }
    static List<Image> getSpecial1Images() {
        return SPECIAL_1_IMAGES;
    }
    static List<Image> getSpecial2Images() {
        return SPECIAL_2_IMAGES;
    }
}
