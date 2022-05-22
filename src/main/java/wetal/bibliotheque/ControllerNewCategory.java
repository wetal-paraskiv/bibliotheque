package wetal.bibliotheque;

import javafx.event.ActionEvent;
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

public class ControllerNewCategory {

    @FXML
    private TextField newCategoryName;

    @FXML
    private TextField newCategoryDescription;

    @FXML
    private Button buttonSave;

    @FXML
    void saveCategory() throws IOException {
        new CRUDCommon().writeNew(
                "categories",
                newCategoryName.getText(),
                newCategoryDescription.getText());
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
