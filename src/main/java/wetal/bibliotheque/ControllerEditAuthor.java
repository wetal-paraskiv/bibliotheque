package wetal.bibliotheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wetal.bibliotheque.crud.CRUDAuthor;
import wetal.bibliotheque.object_holders.AuthorHolder;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerEditAuthor implements Initializable {

    @FXML
    private TextField authorName;

    @FXML
    private TextField authorEmail;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonDelete;

    @FXML
    void backToTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-authors.fxml")));
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteAuthor() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("if I appeared => that's a must..");
        alert.setHeaderText("warning!");
        alert.setContentText("Are U sure, U wanna delete that author ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            CRUDAuthor.deleteAuthor(AuthorHolder.getInstance().getAuthor().getId());
            authorsTable();
        }
    }

    @FXML
    void updateAuthor() throws IOException {
        CRUDAuthor.updateAuthor(
                authorName.getText(),
                authorEmail.getText(),
                AuthorHolder.getInstance().getAuthor().getId());
        authorsTable();
    }

    @FXML
    public void toSceneHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) buttonUpdate.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void authorsTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-authors.fxml")));
        Stage stage = (Stage) buttonDelete.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authorName.setText(AuthorHolder.getInstance().getAuthor().getName());
        authorEmail.setText(AuthorHolder.getInstance().getAuthor().getEmail());
    }
}
