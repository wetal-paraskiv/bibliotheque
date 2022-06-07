package wetal.bibliotheque.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDCommon {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final String url = resourceBundle.getString("db.url");
    private static final String owner = resourceBundle.getString("db.username");
    private static final String password = resourceBundle.getString("db.password");


    public void writeNew(String destination, String name, String description) {
        String query = String.format("INSERT INTO %1$s (name, description) VALUES (?, ?);", destination);

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, description);
            pst.execute();

            System.out.println("Successfully created instance in: ...");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCommon.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public ObservableList<String> readAll(String source) throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = String.format("SELECT * FROM %1$s;", source);

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(
                        resultSet.getString("name")
                );
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCommon.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return list;
    }

}
