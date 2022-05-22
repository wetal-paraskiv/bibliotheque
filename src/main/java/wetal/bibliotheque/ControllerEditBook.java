package wetal.bibliotheque;

import org.controlsfx.control.SearchableComboBox;
import wetal.bibliotheque.crud.*;
import wetal.bibliotheque.object_holders.BookHolder;
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

public class ControllerEditBook implements Initializable {

    CRUDAuthor crudAuthor = new CRUDAuthor();
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
    private Button buttonCancel;

    @FXML
    void backToTable() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view-all-books.fxml")));
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteBook() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("if I appeared => that's a must..");
        alert.setHeaderText("warning!");
        alert.setContentText("Are U sure, U wanna delete that book ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            new CRUDBook().deleteBook(BookHolder.getInstance().getBook().getId());
            backToTable();
        }
    }

    @FXML
    void updateBook() throws IOException {

        // get author id for each author -> to authors list
        List<String> authors = new ArrayList<>();
        if (chooseAuthor1.getValue() != null)
            authors.add(crudAuthor.getID(chooseAuthor1.getValue().trim()));
        if (chooseAuthor2.getValue() != null)
            authors.add(crudAuthor.getID(chooseAuthor2.getValue().trim()));
        if (chooseAuthor3.getValue() != null)
            authors.add(crudAuthor.getID(chooseAuthor3.getValue().trim()));

        new CRUDBook().updateBook(
                title.getText(),
                choosePublisher.getValue(),
                chooseCategory.getValue(),
                chooseLanguage.getValue(),
                BookHolder.getInstance().getBook().getId(),
                authors);

        backToTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(BookHolder.getInstance().getBook().getTitle());

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

        String[] authors = BookHolder.getInstance().getBook().getAuthors().split(",");
        // filling Author1, Author2, Author3 choiceBox's
        chooseAuthor1.setItems(authorsList);
        chooseAuthor2.setItems(authorsList);
        chooseAuthor3.setItems(authorsList);
        chooseAuthor1.setValue(authors[0]);
        if (authors.length - 1 > 0) chooseAuthor2.setValue(authors[1]);
        if (authors.length - 1 > 1) chooseAuthor3.setValue(authors[2]);

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
        choosePublisher.setValue(BookHolder.getInstance().getBook().getPublisher());

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
        chooseCategory.setValue(BookHolder.getInstance().getBook().getCategory());

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
        chooseLanguage.setValue(BookHolder.getInstance().getBook().getLanguage());
    }

}
