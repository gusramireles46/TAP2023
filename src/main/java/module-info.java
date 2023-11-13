module com.example.tap2023 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mariadb.java.client;

    opens com.example.tap2023 to javafx.fxml;
    exports com.example.tap2023;
    exports com.example.tap2023.vistas;
    opens com.example.tap2023.vistas to javafx.fxml;
    opens com.example.tap2023.models;
    exports com.example.tap2023.models;
}