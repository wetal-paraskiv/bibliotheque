package wetal.bibliotheque;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import wetal.bibliotheque.crud.CreateTables;


public class ApplicationBibliotheque extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        try {
            CreateTables createTables = new CreateTables();
            createTables.createAllTables();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setTitle("Wetal's BibliothequeApp Welcomes YOU!");
            Image icon = new Image("file:images\\icon.png");
            stage.getIcons().add(icon);
//            stage.setMaximized(true);

            stage.setScene(scene);

            stage.setOnCloseRequest(e -> {
                e.consume();
                quitProgram(stage);
            });

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quitProgram(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage.getScene().getWindow());
        alert.setTitle("BibliothequeApp alert!");
        alert.setHeaderText("You are about to QUIT ?");
        alert.setContentText("Do you want to save the changes before quitting ?\n" +
                             "\tit's a joke...\nthe changes are already saved. :)");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Notifications.create()
                    .title("QUIT INFO")
                    .text("Thanks for using Bibliotheque  :)")
                    .showWarning();

            stage.setOpacity(.85);
            PauseTransition closeDelay = new PauseTransition(Duration.millis(2500));
            closeDelay.setOnFinished(event -> stage.close());
            closeDelay.play();

        } else {
            Notifications.create()
                    .title("Ok INFO")
                    .text("Ok, good option, I like to work :)")
                    .showWarning();
        }
    }

    public static void main(String[] args) {
        launch();
    }


}