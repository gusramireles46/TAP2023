package com.example.tap2023.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Restaurante extends Stage {

    private HBox hbox;
    private VBox vbox;
    private BorderPane bdpRaiz;
    private Button btnAgregar;
    private Scene escena;

    public Restaurante () {
        bdpRaiz = new BorderPane();
        escena = new Scene(bdpRaiz, 450, 320);
        this.setScene(escena);
        this.show();
    }
}
