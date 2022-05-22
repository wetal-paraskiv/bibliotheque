package wetal.bibliotheque;

import wetal.bibliotheque.crud.CRUDBook;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ControllerInfoView {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private void showMessage() throws SQLException {
        String size = String.valueOf(new CRUDBook().getNumOfDutyBooks());
        label5.setText(size);
        Label[] labels = {label1, label2, label3, label4, label5, label6};
        label6.setText("books on duty :)");
        for (int i = 0; i < labels.length; i++) {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            int finalI = i;
            executorService.schedule(() -> labels[finalI].setOpacity(1), i + 1, TimeUnit.SECONDS);
        }
    }
}
