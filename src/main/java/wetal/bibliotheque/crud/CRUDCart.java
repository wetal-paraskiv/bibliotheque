package wetal.bibliotheque.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDCart {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final String url = resourceBundle.getString("db.url");
    private static final String owner = resourceBundle.getString("db.username");
    private static final String password = resourceBundle.getString("db.password");


    public String writeNewCart(String member_id) {
        String query = "INSERT INTO carts (member_id) VALUES (?);";
        String lastInsertedId = null;
        try (Connection connection = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, member_id);
            pst.executeUpdate();

            // getting last inserted id
            ResultSet resultSet = pst.executeQuery("SELECT LAST_INSERT_ID();");
            if (resultSet.next()) {
                lastInsertedId = resultSet.getString(1);
            }
            return lastInsertedId;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCart.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return lastInsertedId;
    }


    public ObservableList<String> readAllCartsWithDutyBooks() {
        ObservableList<String> cartList = FXCollections.observableArrayList();
        String query = "SELECT carts.cart_id, member_id, cart_date FROM carts\n" +
                       "LEFT JOIN books_lending \n" +
                       "ON carts.cart_id = books_lending.cart_id\n" +
                       "WHERE book_duty = true\n" +
                       "GROUP BY carts.cart_id\n" +
                       "ORDER BY cart_date;";
        try (Connection connection = DriverManager.getConnection(url, owner, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                cartList.add(
                        resultSet.getString("cart_id") +
                        "; member: " +
                        resultSet.getString("member_id") +
                        "; on: " +
                        resultSet.getString("cart_date").substring(0, 11)
                );
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCart.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return cartList;
    }


    public String getMemberID(String name) {
        String query = String.format("SELECT id FROM members WHERE name = '%1$s';", name);
        try (Connection connection = DriverManager.getConnection(url, owner, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDMember.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

}
