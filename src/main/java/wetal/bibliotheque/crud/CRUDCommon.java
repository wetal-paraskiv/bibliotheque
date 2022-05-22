package wetal.bibliotheque.crud;

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
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, owner, password);
            PreparedStatement pst = connection.prepareStatement(query);

            pst.setString(1, name);
            pst.setString(2, description);
            pst.execute();

            System.out.println("Successfully created object in: ..");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCommon.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public ResultSet readAll(String source) throws SQLException {
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        String query = String.format("SELECT * FROM %1$s;", source);
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCommon.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }


    public void update(String name, String email, String id) {
        int iD = Integer.parseInt(id);
        String query = String.format("UPDATE authors SET name = ?, email = ? WHERE id = %1$s;", iD);

        try (Connection con = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCommon.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void delete(String id) {
        String query = String.format("DELETE FROM publishers WHERE id = %1$s;", id);

        try (Connection con = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDCommon.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
