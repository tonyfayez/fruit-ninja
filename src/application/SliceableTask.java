package application;

import game.objects.Sliceable;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.TimerTask;

public class SliceableTask extends TimerTask {
	private AnchorPane pn_fruits;
	private Sliceable sliceable;
	private boolean isRunning = false;

	public SliceableTask(Sliceable sliceable) {
		this.sliceable = sliceable;
		this.pn_fruits = MainGameFormController.getInstance().getFruitsPane();
	}

	@Override
    public void run() {
		if(isRunning)
			return;
		isRunning = true;
		ImageView image = new ImageView();
		image.setUserData(sliceable);
		image.setImage(sliceable.getImages().get(0));
		ProjectileAnimation animation = new ProjectileAnimation(image);
		image.getProperties().put("projectileAnimation", animation);
		Platform.runLater(() -> {
			pn_fruits.getChildren().add(image);
			animation.play();
		});
		isRunning = false;
	}
}
