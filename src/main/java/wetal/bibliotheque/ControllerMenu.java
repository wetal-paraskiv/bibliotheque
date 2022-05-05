package wetal.bibliotheque;

import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerMenu implements Initializable {

    private Stage stage;

    @FXML
    private MenuBar myMenuBar;

    @FXML
    private BorderPane borderPane;

    @FXML
    private void aboutScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("info-view.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void booksTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-books.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void membersTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-members.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void authorsTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-authors.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void issuesTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-lendings.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loginViewScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void quit(ActionEvent e) {
        stage = (Stage) myMenuBar.getScene().getWindow();
        e.consume();
        new ApplicationBibliotheque().quitProgram(stage);
    }

    @FXML
    private void newBookAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-book.fxml")));
        stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void newMemberAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-member.fxml")));
        stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void newAuthorAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-author.fxml")));
        stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toSceneHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void topTenMembersByBooks() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-members.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void toStatistics() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("statistics.fxml")));
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//        System.out.println(screenBounds.getWidth());
//        stage = (Stage) borderPane.getScene().getWindow();
//        myMenuBar.prefWidthProperty().bind(borderPane.widthProperty());
    }
}
