package wetal.bibliotheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class ControllerLoginView extends KeyCombination {

    @FXML
    private Label warningLabel;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void loginAction() throws IOException {
        String name = userNameTextField.getText();
        String password = passwordField.getText();
        if (userNameTextField.getText().isBlank()) {
            warningLabel.setText("Provide a username..");
        } else if (passwordField.getText().isBlank()) {
            warningLabel.setText("enter the password please..");
        } else if ((name.equals("wetal")) && (password.equals("pass"))) {
            toSceneHome();
        } else {
            warningLabel.setText("provide a correct username or password!");
        }
        toSceneHome();
    }


    private void toSceneHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void setCancelButton() throws IOException {
        userNameTextField.setText("");
        passwordField.setText("");
        warningLabel.setText("");
    }
}
