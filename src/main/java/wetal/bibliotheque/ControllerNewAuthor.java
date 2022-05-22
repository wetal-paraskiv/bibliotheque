package wetal.bibliotheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wetal.bibliotheque.crud.CRUDAuthor;

import java.io.IOException;
import java.util.Objects;

public class ControllerNewAuthor {

    @FXML
    private TextField newAuthorName;

    @FXML
    private TextField newAuthorEmail;

    @FXML
    private Button buttonSave;

    @FXML
    void saveAuthor() throws IOException {
        new CRUDAuthor().writeNewAuthor(
                newAuthorName.getText(),
                newAuthorEmail.getText());
        backToTable();
    }

    private void authorsTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-authors.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void toSceneHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backToTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-book.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
