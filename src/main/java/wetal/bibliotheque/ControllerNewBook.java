package wetal.bibliotheque;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.SearchableComboBox;
import wetal.bibliotheque.crud.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerNewBook implements Initializable {

    CRUDCommon crudCommon = new CRUDCommon();

    @FXML
    private TextField title;

    @FXML
    private SearchableComboBox<String> choosePublisher;

    @FXML
    private ChoiceBox<String> chooseLanguage;

    @FXML
    private ChoiceBox<String> chooseCategory;

    @FXML
    private SearchableComboBox<String> chooseAuthor1;

    @FXML
    private SearchableComboBox<String> chooseAuthor2;

    @FXML
    private SearchableComboBox<String> chooseAuthor3;

    @FXML
    private Button buttonSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // get all authors
        ObservableList<String> authorsList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = new CRUDAuthor().readAllAuthors();
            while (resultSet.next()) {
                authorsList.add(
                        resultSet.getString("name")
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        // filling Author1, Author2, Author3 choiceBox's
        chooseAuthor1.setItems(authorsList);
        chooseAuthor2.setItems(authorsList);
        chooseAuthor3.setItems(authorsList);

        // filling Publishers choiceBox
        ObservableList<String> publisherList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = crudCommon.readAll("publishers");
            while (resultSet.next()) {
                publisherList.add(
                        resultSet.getString("name")
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        choosePublisher.setItems(publisherList);

        // filling Category choiceBox
        ObservableList<String> categoryList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = crudCommon.readAll("categories");
            while (resultSet.next()) {
                categoryList.add(
                        resultSet.getString("name")
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        chooseCategory.setItems(categoryList);

        // filling Language choiceBox
        ObservableList<String> languageList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = crudCommon.readAll("languages");
            while (resultSet.next()) {
                languageList.add(
                        resultSet.getString("name")
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        chooseLanguage.setItems(languageList);
    }

    @FXML
    private void addPublisher() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-publisher.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addAuthor() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-author.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addCategory() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-category.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addLanguage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-language.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void saveBook() {
        CRUDAuthor crudAuthor = new CRUDAuthor();
        try {
            // get author id for each author -> to authors list
            List<String> authors = new ArrayList<>();
            if (chooseAuthor1.getValue() != null)
                authors.add(crudAuthor.getID(chooseAuthor1.getValue().trim()));
            if (chooseAuthor2.getValue() != null)
                authors.add(crudAuthor.getID(chooseAuthor2.getValue().trim()));
            if (chooseAuthor3.getValue() != null)
                authors.add(crudAuthor.getID(chooseAuthor3.getValue().trim()));

            // check if all fields are completed
            if (title.getText().equals("") ||
                authors.size() < 1 ||
                choosePublisher.getValue() == null ||
                chooseCategory.getValue() == null ||
                chooseLanguage.getValue() == null
            ) {
                Notifications.create()
                        .title("Fields completion INFO")
                        .text("Fill all necessary fields to save new Book :)")
                        .showWarning();
            } else {
                new CRUDBook().writeNewBook(
                        title.getText(),
                        choosePublisher.getValue(),
                        chooseCategory.getValue(),
                        chooseLanguage.getValue(),
                        authors);
                booksTable();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void booksTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-books.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void toSceneHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
