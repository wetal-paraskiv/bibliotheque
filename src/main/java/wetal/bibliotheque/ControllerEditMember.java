package wetal.bibliotheque;

import wetal.bibliotheque.crud.CRUDMember;
import wetal.bibliotheque.object_holders.MemberHolder;
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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerEditMember implements Initializable {

    @FXML
    private TextField memberName;

    @FXML
    private TextField memberEmail;

    @FXML
    private TextField memberPhone;

    @FXML
    private Button buttonCancel;

    @FXML
    void backToTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-members.fxml")));
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteMember() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("if I appeared => that's a must..");
        alert.setHeaderText("warning!");
        alert.setContentText(String.format("Are U sure, U wanna delete '%1$s' member ?", memberName.getText()));
        if (alert.showAndWait().get() == ButtonType.OK) {
            CRUDMember.deleteMember(MemberHolder.getInstance().getMember().getId());
            backToTable();
        }
    }

    @FXML
    void updateMember() throws IOException {
        CRUDMember.updateMember(
                memberName.getText(),
                memberEmail.getText(),
                memberPhone.getText(),
                MemberHolder.getInstance().getMember().getId());
        backToTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        memberName.setText(MemberHolder.getInstance().getMember().getName());
        memberEmail.setText(MemberHolder.getInstance().getMember().getEmail());
        memberPhone.setText(MemberHolder.getInstance().getMember().getPhone());
    }
}
