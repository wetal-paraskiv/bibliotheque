package wetal.bibliotheque;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.SearchableComboBox;
import wetal.bibliotheque.crud.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerHome implements Initializable {

    CRUDCart crudCart = new CRUDCart();

    @FXML
    private SearchableComboBox<String> cartMemberOUT;
    @FXML
    private SearchableComboBox<String> cartIdList;

    @FXML
    private SearchableComboBox<String> cartBook1OUT;
    @FXML
    private SearchableComboBox<String> cartBook2OUT;
    @FXML
    private SearchableComboBox<String> cartBook3OUT;
    @FXML
    private Button saveCart;

    @FXML
    private SearchableComboBox<String> idReturnBook;
    @FXML
    private Button saveReturn;


    @FXML
    void writeNewCart() throws IOException {

        if (cartMemberOUT.getValue() != null && cartBook1OUT.getValue() != null) {

            // saving new cart
            String member_id = new CRUDMember().getMemberID(cartMemberOUT.getValue());

            // get the list of cart books id
            List<String> cartBooksIdList = new ArrayList<>();
            cartBooksIdList.add(cartBook1OUT.getValue());
            if (cartBook2OUT.getValue() != null && !cartBooksIdList.contains(cartBook2OUT.getValue()))
                cartBooksIdList.add(cartBook2OUT.getValue());
            if (cartBook3OUT.getValue() != null && !cartBooksIdList.contains(cartBook3OUT.getValue()))
                cartBooksIdList.add(cartBook3OUT.getValue());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(saveCart.getScene().getWindow());
            alert.setTitle("Bibliotheque Application!");
            alert.setHeaderText("Lending Books?");
            alert.setContentText(
                    String.format("Confirm registration of lending books id: %1$s\n to: %2$s. :)",
                            Arrays.toString(cartBooksIdList.toArray()), cartMemberOUT.getValue()));

            if (alert.showAndWait().get() == ButtonType.OK) {
                String cart_id = crudCart.writeNewCart(member_id);

                for (String book_id : cartBooksIdList) {
                    // save each book in books_lending
                    new CRUDBooksLending().writeNew(cart_id, book_id);

                    // set is_available for each book to false
                    new CRUDBook().setBookUnavailable(book_id);
                }
                Notifications.create()
                        .title("Take care of BOOKS! :)")
                        .text(String.format(
                                "Cart id: %1$s, Name: %2$s successfully borrowed\nbooks: %3$s.",
                                cart_id, cartMemberOUT.getValue(), Arrays.toString(cartBooksIdList.toArray())))
                        .showInformation();
                toSceneHome();
            } else {
                toSceneHome();
            }
        } else {
            Notifications.create()
                    .title("Data completion ERROR :(")
                    .text("You have to fill at least member field and one book id!")
                    .showWarning();
        }
    }

    @FXML
    void returnBooks() throws IOException {

        if (cartIdList.getValue() != null && idReturnBook.getValue() != null) {
            String cart_id = cartIdList.getValue().split(";")[0];
            String book_id = idReturnBook.getValue();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(saveReturn.getScene().getWindow());
            alert.setTitle("Bibliotheque Application!");
            alert.setHeaderText("Returning Books?");
            alert.setContentText(
                    String.format("Confirm returning book id: %1$s\nfrom CART id: %2$s. :)",
                            book_id, cart_id));
            if (alert.showAndWait().get() == ButtonType.OK) {

                // if return book is successfully, make those books available for others
                if (new CRUDBooksLending().returnBook(cart_id, book_id) > 0) {
                    new CRUDBook().setBookToAvailable(book_id);
                    Notifications.create()
                            .title("Thanks for taking care of BOOK! :)")
                            .text(String.format("Cart id: %1$s, successfully returned book with id: %2$s :)",
                                    cart_id, book_id))
                            .showInformation();
                    toSceneHome();
                } else {
                    Notifications.create()
                            .title("ERROR 404 resource not found...  :(")
                            .text("There was an error on cart or book id...  :(")
                            .showWarning();
                }
            }
        } else {
            Notifications.create()
                    .title("Data completion ERROR :(")
                    .text("You have to fill cart id and book id fields!")
                    .showWarning();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // get list of members
        ObservableList<String> members = new CRUDMember().readAllMembers().stream()
                .sorted()
                .map(m -> m.getName())
                .collect(Collector.of(
                        FXCollections::observableArrayList,
                        ObservableList::add,
                        (l1, l2) -> { l1.addAll(l2); return l1; })
                );
        cartMemberOUT.setItems(members);

        // get list of cart id for return operations
        ObservableList<String> cartList = crudCart.readAllCartsWithDutyBooks();
        cartIdList.setItems(cartList);

        // get list of available book ids to borrow
        ObservableList<String> availableBooksId = null;
        try {
            availableBooksId = new CRUDBook().readAllAvailableBooksId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cartBook1OUT.setItems(availableBooksId);
        cartBook2OUT.setItems(availableBooksId);
        cartBook3OUT.setItems(availableBooksId);

        // get list of all duty_books to return
        ObservableList<String> allDutyBooks = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = new CRUDBooksLending().readAllIdForDutyBooks();
            while (resultSet.next()) {
                allDutyBooks.add(
                        resultSet.getString("book_id")
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        idReturnBook.setItems(allDutyBooks);
    }

    @FXML
    private void toSceneHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage) saveCart.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
