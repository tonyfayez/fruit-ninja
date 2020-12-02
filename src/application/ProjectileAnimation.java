package application;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class ProjectileAnimation {
    public static EventHandler<ActionEvent> defaultOnFinished = null;

    private Node node;
    private AnimationTimer timer;
    private double maxX, maxY;
    private double initialVx, initialVy;
    private long startTime = 0;
    private double g;

    private EventHandler<ActionEvent> onFinished;
    private ActionEvent event;

    public ProjectileAnimation(Node node) {
        this(node, 1196, 747);
    }
    public ProjectileAnimation(Node node, double parentWidth, double parentHeight) {
        this.node = node;
        this.maxX = parentWidth;
        this.maxY = parentHeight;
        this.g = parentHeight*1.5;
        this.event = new ActionEvent(node, null);
        this.onFinished = defaultOnFinished;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move(now);
            }
        };
        doMafs();
    }
    private void doMafs() {
        node.setLayoutX(Math.random()*0.6*maxX+0.2*maxX);
        node.setLayoutY(maxY);
        double y = Math.random()*maxY*0.2+maxY*0.75;
        initialVy = Math.sqrt(2*g*y);
        double duration = 2 * Math.abs(initialVy / g);
        double layoutX = node.getLayoutX();
        int sign;
        if(layoutX < maxX/2)
            sign = -1;
        else if(layoutX > maxX/2)
            sign = 1;
        else {
            sign = (Math.random()>0.5 ? 1: -1);
        }
        double x = Math.random()*(maxX*(sign==1?0:1)+sign*layoutX)*0.8*sign;
        initialVx = x/duration;
    }

    public void play() {
        timer.start();
        startTime = System.nanoTime();
    }


    public void stop() {
        timer.stop();
    }

    private void move(long now) {
        double t = (now - startTime)/1000000000.0;
        node.setTranslateX(-initialVx * t);
        double dy = initialVy * t - 0.5 * g * t * t;
        node.setTranslateY(-dy);
        if (node.getTranslateY() >= 0) {
            stop();
            if(onFinished != null)
                onFinished.handle(event);
        }
    }

    public void setOnFinished(EventHandler<ActionEvent> onFinished) {
        this.onFinished = onFinished;
    }
}
