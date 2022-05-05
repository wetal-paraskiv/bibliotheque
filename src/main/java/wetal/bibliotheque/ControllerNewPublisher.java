package wetal.bibliotheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wetal.bibliotheque.crud.CRUDCommon;

import java.io.IOException;
import java.util.Objects;

public class ControllerNewPublisher {

    @FXML
    private TextField newPublisherName;

    @FXML
    private TextField newPublisherDescription;

    @FXML
    private Button buttonSave;

    @FXML
    void savePublisher() throws IOException {
        CRUDCommon.writeNew(
                "publishers",
                newPublisherName.getText(),
                newPublisherDescription.getText());
        backToTable();
    }

    @FXML
    public void toSceneHome() throws IOException {
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
