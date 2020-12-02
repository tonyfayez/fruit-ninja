package application;

import com.jfoenix.controls.JFXButton;
import game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;

public class MainGameFormController {
	private static MainGameFormController instance;
	public static MainGameFormController getInstance() {
		return instance;
	}

	@FXML
	private JFXButton btn_mainmenu, btn_mainmenu1;
	@FXML
	private AnchorPane pn_main, pn_fruits, pn_gameOver;
	@FXML
	private Path path;
	@FXML
	public ImageView img_lives;
	@FXML
	public Label lbl_time;
	@FXML
	private Label lbl_score,lbl_highScore, lbl_gameOverScore, lbl_gameOverBestScore;

	private Thread thread;
	private final Timer TIMER = new Timer(true);

	private LocalTime swordSoundTime = LocalTime.now();

	@FXML
	public void initialize() {
		instance = this;
		pn_gameOver.setVisible(false);
		startThread();
		updateScore(0);
		Game game = Game.getCurrentGame();
		updateHighScore(game.getHighScore());
		game.addObserver((observable, arg) -> {
			if(Game.GAME_STOPPED.equals(arg)) {
				TIMER.cancel();
				TIMER.purge();
			}
		});
		game.addObserver((observable, arg) -> {
			if(Game.SCORE_CHANGED.equals(arg)) {
				updateScore(Game.getCurrentGame().getScore());
				updateHighScore(Game.getCurrentGame().getHighScore());
			}
		});
	}

	private void startThread() {
		thread = new IntersectionThread(pn_fruits,pn_main, path);
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	void onButtonAction(ActionEvent event) throws IOException {
		if(event.getSource() == btn_mainmenu || event.getSource() == btn_mainmenu1) {
			Game.getCurrentGame().stopGame();
			Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainMenuForm.fxml"));
			Scene MainFormScene = new Scene(root);
			Stage window = (Stage)(((Node) event.getSource()).getScene().getWindow());
			window.setScene(MainFormScene);
			window.show();
		}
	}

	@FXML
	void onMousePressed(MouseEvent event) {
		path.toBack();
		path.getElements().clear();
		path.toFront();
		path.getElements()
				.add(new MoveTo(event.getSceneX(), event.getSceneY()));
	}

	@FXML
	void onMouseDragged(MouseEvent event) {
		if(path.getElements().size() > 20) {
			MoveTo temp2 = null;
			if( path.getElements().get(10).getClass() == MoveTo.class){
				temp2 = (MoveTo) path.getElements().get(10);
			}else if(path.getElements().get(10).getClass() == LineTo.class) {
				LineTo temp = (LineTo) path.getElements().get(10);
				temp2 = new MoveTo(temp.getX(),temp.getY());
			}
			playSwordSound();
			path.getElements().clear();
			path.getElements().add(temp2);
		}

		path.getElements()
				.add(new LineTo(event.getSceneX(), event.getSceneY()));

		if(!thread.isAlive()) {
			startThread();
		}
	}

	@FXML
	void onMouseReleased(MouseEvent event) {
		path.toBack();
		path.getElements().clear();
		path.toFront();
		thread.interrupt();
	}

	private void playSwordSound() {
		if( swordSoundTime.plusSeconds(1).isBefore(LocalTime.now())) {
			MediaPlayer mediaPlayer = new MediaPlayer( new Media(getClass().getResource("/sword.mp3").toString()));
			mediaPlayer.setOnReady(new Runnable() {
				@Override
				public void run() {
					mediaPlayer.stop();
					mediaPlayer.play();
				}
			});
			mediaPlayer.stop();
			mediaPlayer.play();
			swordSoundTime = LocalTime.now();
		}
	}

	public AnchorPane getFruitsPane() {
		return pn_fruits;
	}
	public AnchorPane getMainPane() {
		return pn_main;
	}

	public void gameOver() {
		MediaPlayer mediaPlayer = new MediaPlayer( new Media(getClass().getResource("/gameOver.mp3").toString()));
		mediaPlayer.setOnReady(() -> {
			mediaPlayer.stop();
			mediaPlayer.play();
		});

		for(Node node: new ArrayList<>(pn_fruits.getChildren())) {
			if(!(node instanceof ImageView))
				continue;
			((ProjectileAnimation)node.getProperties().get("projectileAnimation")).stop();
			pn_fruits.getChildren().remove(node);
		}
		pn_gameOver.setVisible(true);
		lbl_gameOverScore.setText("Score: " + Game.getCurrentGame().getScore());
		lbl_gameOverBestScore.setText("High Score: " + Game.getCurrentGame().getHighScore());
		pn_gameOver.toFront();
	}

	public void scheduleSliceable(SliceableTask task, long delay) {
		TIMER.schedule(task, delay);
	}
	public void updateTime(String time) {
		lbl_time.setText(time);
	}
	public void updateLives(int live) {
		if(live>3 || live<0)
			return;

		img_lives.setImage(new Image("/lives"+live+".png",true));
	}
	private void updateScore(int score) {
		lbl_score.setText(String.valueOf(score));
	}
	private void updateHighScore(int highScore) {
		lbl_highScore.setText("Best: " + highScore);
	}
}
