package wetal.bibliotheque;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import wetal.bibliotheque.crud.CRUDBooksLending;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerStatistics implements Initializable {

    private CRUDBooksLending crudBooksLending = new CRUDBooksLending();

    @FXML
    private Label resultSet;

    @FXML
    private DatePicker startPeriod;

    @FXML
    private DatePicker endPeriod;

    @FXML
    private Button allInfoByDateButton;

    @FXML
    private Button readAllButton;

    @FXML
    private void datePlusNumOfBooks() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.datePlusNumOfBooks()));
        resultSet.setText(text);
    }

    @FXML
    private void authorBooks() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.authorBooks()));
        resultSet.setText(text);
    }

    @FXML
    private void topTenCarts() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.topTenCarts()));
        resultSet.setText(text);
    }

    @FXML
    private void topTenByBooks() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.topTenByBooks()));
        resultSet.setText(text);
    }

    @FXML
    private void cartsDutyBooks() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.cartsWithDutyBooks()));
        resultSet.setText(text);
    }

    @FXML
    private void overDueInfo() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.overDueInfo()));
        resultSet.setText(text);
    }

    @FXML
    private void memberPlusAllBooks() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.memberPlusAllBooks()));
        resultSet.setText(text);
    }

    @FXML
    private void allInfoByDateAction() throws SQLException {
        Date start = java.sql.Date.valueOf(startPeriod.getValue());
        Date end = java.sql.Date.valueOf(endPeriod.getValue());

        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.readAllByDates(start, end)));
        resultSet.setText(text);
    }

    @FXML
    private void readAllAction() throws SQLException {
        String text = String.valueOf(crudBooksLending.getString(crudBooksLending.readAll()));
        resultSet.setText(text);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startPeriod.setValue(LocalDate.now());
        endPeriod.setValue(LocalDate.now());
    }
}

