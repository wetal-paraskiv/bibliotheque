package wetal.bibliotheque;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import wetal.bibliotheque.crud.CRUDAuthor;
import wetal.bibliotheque.object_holders.AuthorHolder;
import wetal.bibliotheque.models.Author;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerAuthorsView implements Initializable {

    @FXML
    private TableView<Author> myTableView;

    @FXML
    private TableColumn<Author, String> id;

    @FXML
    private TableColumn<Author, String> name;

    @FXML
    private TableColumn<Author, String> email;

    @FXML
    private TextField filterField;

    @FXML
    private void newAuthorAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new-author.fxml")));
        Stage stage = (Stage) myTableView.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Author> tableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = new CRUDAuthor().readAllAuthors();
            while (resultSet.next()) {
                tableList.add(new Author(
                        resultSet.getString("author_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                ));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        FilteredList<Author> filteredList = new FilteredList<>(tableList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(author -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (author.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (author.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });

        SortedList<Author> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableList.sorted().comparatorProperty());
        myTableView.setItems(sortedList);

        myTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            AuthorHolder authorHolder = AuthorHolder.getInstance();
            Author selectedItem = myTableView.getSelectionModel().getSelectedItem();
            authorHolder.setAuthor(selectedItem);
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("edit-author.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) myTableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}
