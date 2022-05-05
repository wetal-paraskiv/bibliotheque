package wetal.bibliotheque.crud;


import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CRUDBooksLending {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final String url = resourceBundle.getString("db.url");
    private static final String owner = resourceBundle.getString("db.username");
    private static final String password = resourceBundle.getString("db.password");


    public static void writeNew(String cart_id, String book_id) {
        String query = "INSERT INTO books_lending (cart_id, book_id) VALUES (?, ?);";

        try (Connection connection = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, cart_id);
            pst.setString(2, book_id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public static int returnBook(String cart_id, String book_id) {
        int response = 0;
        String query = "UPDATE books_lending " +
                       "SET book_duty = false, return_date = CURRENT_TIMESTAMP " +
                       "WHERE cart_id = ?" +
                       "AND book_id = ?;";
        try (Connection con = DriverManager.getConnection(url, owner, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, cart_id);
            pst.setString(2, book_id);
            response = pst.executeUpdate();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return response;
    }


    public static ResultSet readAll() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "SELECT \n" +
                          "\tmembers.id, \n" +
                          "    members.name, \n" +
                          "    carts.cart_id, \n" +
                          "    DATE(cart_date) AS date, \n" +
                          "    books.book_id, title, \n" +
                          "    book_duty, Date(return_date) as return_date, \n" +
                          "    Date(return_date) - DATE(cart_date) AS lending_days \n" +
                          "FROM members\n" +
                          "JOIN carts ON carts.member_id = members.id\n" +
                          "\tJOIN books_lending ON books_lending.cart_id = carts.cart_id\n" +
                          "\t\tJOIN books ON books.book_id = books_lending.book_id;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet readAllByDates(Date start, Date end) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String queryByDates = "SELECT " +
                          "members.id, " +
                          "members.name, " +
                          "carts.cart_id, " +
                          "DATE(cart_date) AS 'date', " +
                          "books.book_id, " + "title, " +
                          "book_duty, Date(return_date) AS 'returned', " +
                          "Date(return_date) - DATE(cart_date) AS total_days " +
                          "FROM members " +
                          "JOIN carts ON carts.member_id = members.id " +
                          "JOIN books_lending ON books_lending.cart_id = carts.cart_id " +
                          "JOIN books ON books.book_id = books_lending.book_id " +
                          "WHERE DATE(cart_date) >= ? AND DATE(cart_date) <= ?;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            preparedStatement = connection.prepareStatement(queryByDates);
            preparedStatement.setString(1, start.toString());
            preparedStatement.setString(2, end.toString());
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet datePlusNumOfBooks() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "select DATE(cart_date) AS Date, cart_id AS Cart, COUNT(book_id) AS 'Number of Books' \n" +
                          "from books_lending\n" +
                          "join carts using(cart_id)\n" +
                          "group by Date;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet authorBooks() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "SELECT authors.name AS 'Author Name', \n" +
                          "\t(SELECT GROUP_CONCAT(books.title)\n" +
                          "\tFROM books_authors\n" +
                          "\tWHERE authors.author_id = author\n" +
                          "\tGROUP BY author) AS 'Book List'\n" +
                          "FROM bibliotheque.authors\n" +
                          "\tRIGHT JOIN books_authors\n" +
                          "\tON books_authors.author = authors.author_id\n" +
                          "\t\tRIGHT JOIN books\n" +
                          "        ON books.book_id = books_authors.book\n" +
                          "GROUP BY author;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static StringBuilder getString(ResultSet resultSet) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) stringBuilder.append(",  ");
                String columnValue = resultSet.getString(i);
                stringBuilder.append(resultSetMetaData.getColumnName(i) + ": " + columnValue);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }


    public static ResultSet readAllIdForDutyBooks() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM books_lending WHERE book_duty = true;");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet topTenCarts() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "SELECT name AS Name, COUNT(cart_id) AS 'Number of Carts'\n" +
                          "FROM members\n" +
                          "JOIN carts \n" +
                          "ON members.id = carts.member_id\n" +
                          "GROUP BY Name\n" +
                          "ORDER BY 'Number of Carts' DESC\n" +
                          "LIMIT 10;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet topTenByBooks() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "-- select top ten members by number of books\n" +
                          "SELECT name, COUNT(book_id) AS BooksNum\n" +
                          "\tFROM members\n" +
                          "\tJOIN carts \n" +
                          "\tON members.id = carts.member_id\n" +
                          "\t\tJOIN books_lending\n" +
                          "        ON books_lending.cart_id = carts.cart_id\n" +
                          "GROUP BY name\n" +
                          "ORDER BY BooksNum DESC\n" +
                          "LIMIT 10;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet cartsWithDutyBooks() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "-- select all carts with duty_books\n" +
                          "SELECT carts.cart_id, member_id, cart_date FROM carts\n" +
                          "LEFT JOIN books_lending \n" +
                          "ON carts.cart_id = books_lending.cart_id\n" +
                          "WHERE book_duty = true\n" +
                          "GROUP BY carts.cart_id\n" +
                          "ORDER BY cart_date;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet overDueInfo() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "-- select books info which have duty limit of 7 days\\n\"\n" +
                          "SELECT name, " +
                          "cart_id AS 'Cart id', " +
                          "Date(cart_date) AS 'Lending Date', " +
                          "book_id AS 'Book id', " +
                          "(Date(current_timestamp) - Date(cart_date) - 7) AS 'OverDue Days'\n" +
                          "FROM members\n" +
                          "JOIN carts\n" +
                          "ON members.id = carts.member_id\n" +
                          "JOIN books_lending using(cart_id)\n" +
                          "WHERE book_duty = true\n" +
                          "AND (Date(current_timestamp()) - Date(cart_date)) > 7;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }


    public static ResultSet memberPlusAllBooks() {
        Connection connection;
        Statement statement;
        ResultSet resultSet = null;
        String queryAll = "-- select all time history of all member's borrowed books\n" +
                          "select members.name AS 'Name', " +
                          "Date(cart_date) AS Date, " +
                          "COUNT(books.title) as Total, " +
                          "group_concat(books.title) as 'Book List'\n" +
                          "\tfrom members\n" +
                          "\tjoin carts on members.id = carts.member_id\n" +
                          "\tjoin books_lending using(cart_id)\n" +
                          "\tjoin books using(book_id)\n" +
                          "group by Name\n" +
                          "order by Date;";
        try {
            connection = DriverManager.getConnection(url, owner, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryAll);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(CRUDBooksLending.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultSet;
    }

}
