package wetal.bibliotheque;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import wetal.bibliotheque.crud.CRUDBook;
import wetal.bibliotheque.object_holders.BookHolder;
import wetal.bibliotheque.models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wetal.bibliotheque.table_controls.PTableColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerBooksView implements Initializable {
    @FXML
    private TableView<Book> myTableView;

    @FXML
    private PTableColumn<Book, String> title;

    @FXML
    private PTableColumn<Book, String> authors;

    @FXML
    private PTableColumn<Book, String> publisher;

    @FXML
    private PTableColumn<Book, String> category;

    @FXML
    private PTableColumn<Book, String> language;

    @FXML
    private PTableColumn<Book, String> id;

    @FXML
    private PTableColumn<Book, String> presence;

    @FXML
    private TextField filterField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Book> tableList = null;
        try {
            tableList = new CRUDBook().allBooksAuthorsFilteredByTitle("");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        language.setCellValueFactory(new PropertyValueFactory<>("language"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        presence.setCellValueFactory(new PropertyValueFactory<>("presence"));


        FilteredList<Book> filteredList = new FilteredList<>(tableList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (book.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (book.getAuthors().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (book.getPublisher().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (book.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (book.getLanguage().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });

        SortedList<Book> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableList.sorted().comparatorProperty());
        myTableView.setItems(sortedList);

        myTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            BookHolder bookHolder = BookHolder.getInstance();
            Book selectedBook = myTableView.getSelectionModel().getSelectedItem();
            bookHolder.setBook(selectedBook);
            System.out.println(bookHolder.getBook().getTitle());
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("edit-book.fxml")));
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

