package com.example.tap2023.vistas;

import com.example.tap2023.componets.Hilo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PistaAtletismo extends Stage {
    private ProgressBar[] pgbCorredores = new ProgressBar[6];
    private Hilo[] thrCorredores = new Hilo[6];
    String[] nombres = {"Yuno", "Jara", "Martina", "Vanessa", "Rubensin", "Germán"};
    private Label lblNombre[] = new Label[6];
    private VBox vbox;
    private Scene escena;
    private Button btnIniciar;

    public PistaAtletismo() {
        crearGUI();
        escena = new Scene(vbox, 250, 250);
        this.setTitle("Pista de atletismo");
        this.setScene(escena);
        this.show();
    }

    private void crearGUI() {
        vbox = new VBox();
        for (int i = 0; i < pgbCorredores.length; i++) {
            pgbCorredores[i] = new ProgressBar(0);
            thrCorredores[i] = new Hilo(nombres[i], pgbCorredores[i]);
            vbox.getChildren().addAll(lblNombre[i], pgbCorredores[i]);
        }
        btnIniciar = new Button("Iniciar carrera");
        btnIniciar.setOnAction(e -> accionBoton());
        vbox.getChildren().add(btnIniciar);
    }

    private void accionBoton() {
        for (int i = 0; i < thrCorredores.length; i++) {
            thrCorredores[i].start();
        }
    }
}
