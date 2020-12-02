package application;

import commands.Controller;
import commands.SliceCommand;
import game.Game;
import game.objects.Banana;
import game.objects.Fruit;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.time.LocalTime;

class IntersectionThread extends Thread{
	private AnchorPane pn_fruits, pn_main;
	private Path path;
	private LocalTime comboTime = LocalTime.now();
	private boolean firstComboSlice = true;
	private int combo = 0;


	IntersectionThread(AnchorPane pn_fruits,AnchorPane pn_main, Path path){
		this.pn_fruits = pn_fruits;
		this.pn_main = pn_main;
		this.path = path;
	}

	@Override
	public void run(){
		while(!Thread.interrupted()){
			try {
				Thread.sleep(150);
				for(Node node: pn_fruits.getChildren().toArray(new Node[0])) {
					if (node instanceof ImageView
							&& node.getProperties().get("isSliced") == null
							&& isIntersecting(node) ) {
						Platform.runLater(() -> Controller.executeCommand(new SliceCommand((ImageView)node)));
					}
				}
			} catch (InterruptedException ex) {
				System.out.println("Interrupted");
			}
		}
	}

	private boolean isIntersecting(Node node) {
		if(path.getBoundsInParent().intersects(node.getBoundsInParent())){
			if( node.getUserData() instanceof Banana)
				specialFruitLabel(node);
			if(!(node.getUserData() instanceof Fruit)) {
				firstComboSlice = true;
				combo = 0;
			}
			else {
				if (firstComboSlice) {
					comboTime = LocalTime.now();
					combo++;
					firstComboSlice = false;
				} else if (comboTime.plusNanos(300000000).isAfter(LocalTime.now())) {
					combo++;
					updateCombo(node);
					System.out.println("COMBO " + combo);
				} else {
					firstComboSlice = true;
					combo = 0;
				}
			}
			return true;
		}

		return false;
	}

	private void updateCombo(Node node) {
		if(combo > 1) {
			int temp = combo;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
			Game.getCurrentGame().addScore(combo*5);
			Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
			Label comboLabel = new Label();
			comboLabel.setText(temp + " FRUIT COMBO!\n+" + combo*5);
			comboLabel.setFont(new Font("Gang of Three",30));
			comboLabel.setStyle("-fx-text-fill: #ffce36");
			comboLabel.setLayoutX(boundsInScene.getMinX());
			comboLabel.setLayoutY(boundsInScene.getMinY());
			FadeTransition ft = new FadeTransition(Duration.millis(4000), comboLabel);
			ft.setToValue(0);
			ft.setOnFinished(event -> pn_main.getChildren().remove(comboLabel));
			ft.play();

			MediaPlayer mediaPlayer = new MediaPlayer( new Media(getClass().getResource("/combo.mp3").toString()));
			mediaPlayer.setOnReady(new Runnable() {
				@Override
				public void run() {
					mediaPlayer.stop();
					mediaPlayer.play();
				}
			});

			pn_main.getChildren().add(comboLabel);
			System.out.println("COMBO TIME'S UP");
				}
			});

		}	}

	private void specialFruitLabel(Node node) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		Game.getCurrentGame().addScore(combo*5);
		Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
		Label fruitLabel = new Label();
		fruitLabel.setText("SPECIAL FRUIT!");
		fruitLabel.setFont(new Font("Gang of Three",30));
		fruitLabel.setStyle("-fx-text-fill: #ffce36");
		fruitLabel.setLayoutX(boundsInScene.getMinX());
		fruitLabel.setLayoutY(boundsInScene.getMinY());
		FadeTransition ft = new FadeTransition(Duration.millis(4000), fruitLabel);
		ft.setToValue(0);
		ft.setOnFinished(event -> pn_main.getChildren().remove(fruitLabel));
		ft.play();

		MediaPlayer mediaPlayer = new MediaPlayer( new Media(getClass().getResource("/combo.mp3").toString()));
		mediaPlayer.setOnReady(new Runnable() {
			@Override
			public void run() {
				mediaPlayer.stop();
				mediaPlayer.play();
			}
		});

		pn_main.getChildren().add(fruitLabel);
			}
		});
	}
}
