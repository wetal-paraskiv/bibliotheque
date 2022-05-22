package wetal.bibliotheque.crud;


import org.controlsfx.control.Notifications;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CRUDBook {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final String url = resourceBundle.getString("db.url");
    private static final String owner = resourceBundle.getString("db.username");
    private static final String password = resourceBundle.getString("db.password");


    public void writeNewBook(String title, String publisher, String category, String language, List<String> authors) {
        String saveBook = "INSERT INTO bibliotheque.books (title, publisher, category, lang) VALUES (?, ?, ?, ?);";
        String book_id = "";
        try (Connection connection = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = connection.prepareStatement(saveBook)) {

            pst.setString(1, title);
            pst.setString(2, publisher);
            pst.setString(3, category);
            pst.setString(4, language);
            pst.executeUpdate();
            System.out.println("Book successfully saved to db..");

            // getting last inserted id
            ResultSet resultSet = pst.executeQuery("SELECT LAST_INSERT_ID();");
            if (resultSet.next()) {
                book_id = resultSet.getString(1);
            }

            String saveAuthors = "INSERT INTO books_authors (book, author) VALUES (?, ?);";
            PreparedStatement pst2 = connection.prepareStatement(saveAuthors);
            for (String author : authors) {
                pst2.setInt(1, Integer.parseInt(book_id));
                pst2.setInt(2, Integer.parseInt(author));
                pst2.executeUpdate();
            }
            System.out.println("Authors successfully saved to db..");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBook.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public ResultSet readAllAvailableBooksId() throws SQLException {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT book_id FROM books WHERE is_available = true;");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(this.getClass().getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public ResultSet allBooksAuthorsFilteredByTitle(String q) throws SQLException {
        String filterString = "('%" + q.toUpperCase() + "%')";
        Connection connection;
        Statement statement;
        String query = "SELECT books.*, \n" +
                       "\t(SELECT GROUP_CONCAT(authors.name)\n" +
                       "\tFROM books_authors\n" +
                       "\tWHERE books.book_id = book\n" +
                       "\tGROUP BY book) AS authors\n" +
                       "FROM bibliotheque.books\n" +
                       "\tRIGHT JOIN books_authors\n" +
                       "\tON books_authors.book = books.book_id\n" +
                       "\t\tRIGHT JOIN authors\n" +
                       "        ON authors.author_id = books_authors.author\n" +
                       "WHERE UPPER(books.title) LIKE %1$s\n" +
                       "GROUP BY books.title;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            return statement.executeQuery(String.format(query, filterString));

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBook.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }


    public void updateBook(String title, String publisher, String category, String language, String id, List<String> authors) {
        int book_id = Integer.parseInt(id);
        String query = String.format(
                "UPDATE books SET title = ?, publisher = ?, category = ?, lang = ? WHERE book_id = %1$s;", book_id);

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, title);
            pst.setString(2, publisher);
            pst.setString(3, category);
            pst.setString(4, language);
            pst.executeUpdate();

            // deleting old authors values
            String deleteBookAuthors = "DELETE FROM books_authors WHERE book = ?;";
            PreparedStatement deletePS = connection.prepareStatement(deleteBookAuthors);
            deletePS.setInt(1, book_id);
            deletePS.executeUpdate();

            // inserting new authors values
            String updateBookAuthors = "INSERT INTO books_authors (book, author) VALUES (?, ?);";
            PreparedStatement updatePS = connection.prepareStatement(updateBookAuthors);
            for (String author : authors) {
                updatePS.setInt(1, book_id);
                updatePS.setInt(2, Integer.parseInt(author));
                updatePS.executeUpdate();
            }
            System.out.println("Authors updated successfully..");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBook.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void deleteBook(String book_id) {
        String query = String.format("DELETE FROM books WHERE book_id = %1$s " +
                                     "AND NOT EXISTS(select author from books_authors where book = %1$s);", book_id);
        try (Connection con = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            int success = pst.executeUpdate();
            if (success > 0) {
                Notifications.create()
                        .title("Deleting info :)")
                        .text(String.format("The book with id %1$s was successfully deleted!", book_id))
                        .showWarning();
            } else {
                Notifications.create()
                        .title("ERROR deleting book! :)")
                        .text(String.format(
                                "Can't delete book with id: %1$s, there are records with it. :(", book_id))
                        .showWarning();
            }

        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(CRUDBook.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public int getNumOfDutyBooks() {
        String query = ("SELECT COUNT(*) AS num_of_rows FROM books WHERE is_available = false;");
        try {
            Connection connection = DriverManager.getConnection(url, owner, password);
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            return resultset.getInt("num_of_rows");

        } catch (Exception e) {
            System.out.println("Error getting row count");
            e.printStackTrace();
        }
        return 0;
    }


    public void setBookUnavailable(String book_id) {
        Connection connection;
        Statement statement;
        String setIsAvailableToFalse = String.format(
                "UPDATE books SET is_available = false WHERE book_id = %1$s;", book_id);
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            statement.executeUpdate(setIsAvailableToFalse);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBook.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void setBookToAvailable(String book_id) {
        Connection connection;
        Statement statement;
        String setIsAvailableToTrue = String.format(
                "UPDATE books SET is_available = true WHERE book_id = %1$s;", book_id);
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            statement.executeUpdate(setIsAvailableToTrue);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBook.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
