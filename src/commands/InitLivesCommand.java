package commands;

import application.MainGameFormController;
import application.MainMenuFormController;
import application.ProjectileAnimation;
import game.Game;
import game.objects.Fruit;
import game.strategies.LivesStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InitLivesCommand implements Command {
    private Stage window;

    public InitLivesCommand(Stage window) {
        this.window = window;
    }

    @Override
    public void execute() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(MainMenuFormController.class.getResource("MainGameForm.fxml"));
            root = (AnchorPane) loader.load();
            Scene MainFormScene = new Scene(root);
            window.setScene(MainFormScene);
            MainGameFormController controller = loader.getController();
            controller.img_lives.setVisible(true);
            controller.updateLives(((LivesStrategy)Game.getCurrentGame().getStrategy()).getLives());
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProjectileAnimation.defaultOnFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node source = (Node) event.getSource();
                if(source.getProperties().get("isSliced") == null
                        && source.getUserData() instanceof Fruit)
                    Controller.executeCommand(new LoseLifeCommand());
                MainGameFormController.getInstance().getFruitsPane().getChildren().remove(source);
            }
        };
    }
}
