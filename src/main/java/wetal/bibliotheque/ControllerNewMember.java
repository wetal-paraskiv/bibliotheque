package wetal.bibliotheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wetal.bibliotheque.crud.CRUDMember;

import java.io.IOException;
import java.util.Objects;

public class ControllerNewMember {

    @FXML // fx:id="newMemberName"
    private TextField newMemberName; // Value injected by FXMLLoader

    @FXML // fx:id="newMemberEmail"
    private TextField newMemberEmail; // Value injected by FXMLLoader

    @FXML // fx:id="newMemberPhone"
    private TextField newMemberPhone; // Value injected by FXMLLoader

    @FXML // fx:id="buttonSave"
    private Button buttonSave; // Value injected by FXMLLoader

    @FXML
    void saveMember() throws IOException {
        CRUDMember.writeNewMember(
                newMemberName.getText(),
                newMemberEmail.getText(),
                newMemberPhone.getText()
        );
        membersTable();
    }

    @FXML
    void toSceneHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void membersTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-members.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
