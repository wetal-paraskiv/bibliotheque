package wetal.bibliotheque.crud;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.Notifications;
import wetal.bibliotheque.models.Author;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CRUDAuthor {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final String url = resourceBundle.getString("db.url");
    private static final String owner = resourceBundle.getString("db.username");
    private static final String password = resourceBundle.getString("db.password");


    public void writeNewAuthor(String name, String email) {
        String query = "INSERT INTO authors (name, email) VALUES (?, ?);";
        try (Connection connection = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.executeUpdate();
            System.out.println("New Author successfully saved..");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDAuthor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }


    public ObservableList<String> getAuthorsNames() throws SQLException {
        ObservableList<String> authorsNames = FXCollections.observableArrayList();
        String query = "SELECT * FROM authors;";

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                authorsNames.add(resultSet.getString("name"));
            }
            return authorsNames;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDAuthor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ObservableList<Author> readAll() throws SQLException {
        String query = "SELECT * FROM authors;";
        ObservableList<Author> tableList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tableList.add(new Author(
                        resultSet.getString("author_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                ));
            }
            resultSet.close();
            return tableList;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDAuthor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }


    public void updateAuthor(String name, String email, String id) {
        String query = String.format("UPDATE authors SET name = ?, email = ? WHERE author_id = %1$s;", id);

        try (Connection con = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDAuthor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void deleteAuthor(String id) {
        String check = String.format("Select * FROM books_authors WHERE author = %1$s;", id);
        String deleteQuery = String.format("DELETE FROM authors WHERE author_id = %1$s;", id);

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             Statement statement = connection.createStatement()) {

            ResultSet constrained = statement.executeQuery(check);

            if (!constrained.next()) {
                statement.executeUpdate(deleteQuery);
                Notifications.create()
                        .title("Delete Author INFO")
                        .text("You successfully deleted author which has no records..  :)")
                        .showInformation();
            } else {
                Notifications.create()
                        .title("Delete Author INFO")
                        .text("You can't delete this author because there are records made..  :)")
                        .showWarning();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDAuthor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public String getID(String name) {
        String query = String.format("SELECT author_id from authors WHERE name = '%1$s';", name);
        try (Connection connection = DriverManager.getConnection(url, owner, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) return resultSet.getString(1);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDAuthor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
