package wetal.bibliotheque.crud;


import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CRUDMember {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final String url = resourceBundle.getString("db.url");
    private static final String owner = resourceBundle.getString("db.username");
    private static final String password = resourceBundle.getString("db.password");


    public void writeNewMember(String name, String email, String phone) {
        String query = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.executeUpdate();
            System.out.println("New Member successfully saved to members");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDMember.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }


    public ResultSet readAllMembers() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM members;";

        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDMember.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public void updateMember(String name, String email, String phone, String id) {
        int iD = Integer.parseInt(id);
        String query = String.format("UPDATE members SET name = ?, email = ?, phone = ? WHERE id = %1$s;", iD);

        try (Connection con = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDMember.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void deleteMember(String id) {
        String query = String.format("DELETE FROM members WHERE id = %1$s;", id);

        try (Connection con = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDMember.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public String getMemberID(String name) {
        String query = String.format("SELECT id FROM members WHERE name = '%1$s';", name);
        try {
            Connection connection = DriverManager.getConnection(url, owner, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            String id = resultSet.getString(1);
            connection.close();
            return id;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDMember.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

}
