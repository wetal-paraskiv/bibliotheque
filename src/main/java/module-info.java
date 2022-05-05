module wetal.bibliotheque {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;

    opens wetal.bibliotheque to javafx.fxml;
    exports wetal.bibliotheque;

    opens wetal.bibliotheque.table_controls to javafx.fxml;
    exports wetal.bibliotheque.table_controls;

    opens wetal.bibliotheque.models to javafx.fxml;
    exports wetal.bibliotheque.models;
}