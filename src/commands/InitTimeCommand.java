package commands;

import application.MainGameFormController;
import application.MainMenuFormController;
import application.ProjectileAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InitTimeCommand implements Command {
    private Stage window;

    public InitTimeCommand(Stage window) {
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
            controller.lbl_time.setVisible(true);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProjectileAnimation.defaultOnFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainGameFormController.getInstance().getFruitsPane().getChildren().remove(event.getSource());
            }
        };
    }
}
