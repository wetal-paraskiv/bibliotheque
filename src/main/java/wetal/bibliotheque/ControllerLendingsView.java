package wetal.bibliotheque;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import wetal.bibliotheque.crud.CRUDBooksLending;
import wetal.bibliotheque.models.Lending;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLendingsView implements Initializable {

    @FXML
    private TableView<Lending> myTableView;

    @FXML
    private TableColumn<Lending, String> member_id;

    @FXML
    private TableColumn<Lending, String> name;

    @FXML
    private TableColumn<Lending, String> cart_id;

    @FXML
    private TableColumn<Lending, String> cart_date;

    @FXML
    private TableColumn<Lending, String> book_id;

    @FXML
    private TableColumn<Lending, String> title;

    @FXML
    private TableColumn<Lending, String> book_duty;

    @FXML
    private TableColumn<Lending, String> return_date;

    @FXML
    private TableColumn<Lending, String> lending_days;

    @FXML
    private TextField filterField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Lending> tableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CRUDBooksLending.readAll();
            while (resultSet.next()) {
                tableList.add(new Lending(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("cart_id"),
                        resultSet.getString("date"),
                        resultSet.getString("book_id"),
                        resultSet.getString("title"),
                        resultSet.getBoolean("book_duty"),
                        resultSet.getString("return_date"),
                        resultSet.getInt("lending_days")
                ));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        member_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        cart_id.setCellValueFactory(new PropertyValueFactory<>("cart_id"));
        cart_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        book_duty.setCellValueFactory(new PropertyValueFactory<>("book_duty"));
        return_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        lending_days.setCellValueFactory(new PropertyValueFactory<>("lending_days"));


        FilteredList<Lending> filteredList = new FilteredList<>(tableList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(lend -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (lend.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (String.valueOf(lend.getCart_id()).indexOf(lowerCaseFilter) != -1) return true;
                else if (lend.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (lend.getBook_id().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (lend.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (lend.getBook_duty().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });
        SortedList<Lending> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableList.sorted().comparatorProperty());
        myTableView.setItems(sortedList);

    }
}

