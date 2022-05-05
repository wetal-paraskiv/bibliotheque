package wetal.bibliotheque;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import wetal.bibliotheque.crud.CRUDMember;
import wetal.bibliotheque.object_holders.MemberHolder;
import wetal.bibliotheque.models.Member;
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

public class ControllerMembersView extends ControllerHome implements Initializable {

    @FXML
    private TableView<Member> myTableView;

    @FXML
    private TableColumn<Member, String> id;

    @FXML
    private TableColumn<Member, String> name;

    @FXML
    private TableColumn<Member, String> email;

    @FXML
    private TableColumn<Member, String> phone;

    @FXML
    private TableColumn<Member, String> date;

    @FXML
    private TextField filterField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Member> tableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CRUDMember.readAllMembers();
            while (resultSet.next()) {
                tableList.add(new Member(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("register_date")
                ));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));


        FilteredList<Member> filteredList = new FilteredList<>(tableList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (member.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (member.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (member.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else if (member.getRegister_date().toLowerCase().indexOf(lowerCaseFilter) != -1) return true;
                else return false;
            });
        });

        SortedList<Member> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableList.sorted().comparatorProperty());
        myTableView.setItems(sortedList);

        myTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            MemberHolder memberHolder = MemberHolder.getInstance();
            Member selectedItem = myTableView.getSelectionModel().getSelectedItem();
            memberHolder.setMember(selectedItem);
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("edit-member.fxml")));
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
