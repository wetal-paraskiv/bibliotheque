package wetal.bibliotheque.crud;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateTables {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private final String url = resourceBundle.getString("db.url");
    private final String owner = resourceBundle.getString("db.username");
    private final String password = resourceBundle.getString("db.password");

    public void createAllTables() {
        createTableBooks();
        createTableAuthors();
        createTableBooksAuthors();
        createTableMembers();
        createTableCarts();
        createTableBooksLendings();
        createTableCategories();
        createTableLanguages();
        createTablePublishers();
    }


    private void createTableBooks() {
        Statement statement;
        String query = "create table if not exists books(" +
                       "book_id int primary key auto_increment," +
                       "title varchar(100) not null," +
                       "publisher varchar(15) not null," +
                       "category varchar(15) not null," +
                       "lang varchar(15) not null," +
                       "is_available boolean DEFAULT true);";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTableAuthors() {
        Statement statement;
        String query = "create table if not exists authors(" +
                       "author_id int primary key auto_increment," +
                       "name varchar(50) not null," +
                       "email varchar(50) not null);";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTableBooksAuthors() {
        Statement statement;
        String query = "create table if not exists books_authors(" +
                       "id int primary key auto_increment," +
                       "book int not null," +
                       "author int not null," +
                       "FOREIGN KEY(book) REFERENCES books(book_id)," +
                       "FOREIGN KEY(author) REFERENCES authors(author_id));";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTableMembers() {
        Statement statement;
        String query = "CREATE TABLE IF NOT EXISTS members(" +
                       "id int PRIMARY KEY auto_increment," +
                       "name VARCHAR(30) NOT NULL," +
                       "email VARCHAR(30) NOT NULL," +
                       "phone VARCHAR(30) NOT NULL," +
                       "register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTableCarts() {
        Statement statement;
        String query = "CREATE TABLE IF NOT EXISTS carts(" +
                       "cart_id int PRIMARY KEY auto_increment," +
                       "member_id int NOT NULL," +
                       "cart_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                       "FOREIGN KEY(member_id) REFERENCES members(id));";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTableBooksLendings() {
        Statement statement;
        String query = "CREATE TABLE IF NOT EXISTS books_lendings(" +
                       "id int PRIMARY KEY AUTO_INCREMENT," +
                       "cart_id int NOT NULL," +
                       "book_id int NOT NULL," +
                       "book_duty boolean DEFAULT true," +
                       "return_date TIMESTAMP DEFAULT NULL," +
                       "FOREIGN KEY(cart_id) REFERENCES carts(cart_id)," +
                       "FOREIGN KEY(book_id) REFERENCES books(book_id));";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTableCategories() {
        Statement statement;
        String query = "CREATE TABLE IF NOT EXISTS categories(" +
                       "id int PRIMARY KEY AUTO_INCREMENT," +
                       "name varchar(20) NOT NULL," +
                       "description varchar(255) NOT NULL);";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTableLanguages() {
        Statement statement;
        String query = "CREATE TABLE IF NOT EXISTS languages(" +
                       "id int PRIMARY KEY AUTO_INCREMENT," +
                       "name varchar(20) NOT NULL," +
                       "description varchar(100) NOT NULL);";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void createTablePublishers() {
        Statement statement;
        String query = "CREATE TABLE IF NOT EXISTS publishers(" +
                       "id int PRIMARY KEY AUTO_INCREMENT," +
                       "name varchar(25) NOT NULL," +
                       "description varchar(100) NOT NULL);";

        try (Connection connection = DriverManager.getConnection(url, owner, password)) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CreateTables.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
