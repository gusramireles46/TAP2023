package com.example.tap2023;

import com.example.tap2023.vistas.Calculadora;
import com.example.tap2023.vistas.Loteria;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TAP2023 extends Application {

    private Scene escena;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Menu menuParcial1, menuParcial2;
    private MenuItem mniCalculadora, mniLoteria;

    private void CrearGUI() {
        // Menú Items
        mniCalculadora = new MenuItem("Calculadora");
        mniCalculadora.setOnAction(event -> new Calculadora());

        mniLoteria = new MenuItem("Lotería");
        mniLoteria.setOnAction(event -> new Loteria());

        // Menú
        menuParcial1 = new Menu("Parcial 1");
        menuParcial1.getItems().addAll(mniCalculadora, mniLoteria);

        menuParcial2 = new Menu("Parcial 2");

        // MenuBar
        menuBar = new MenuBar(menuParcial1, menuParcial2);
    }

    @Override
    public void start(Stage stage) throws IOException {
        CrearGUI();
        borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        escena = new Scene(borderPane, 640, 480);
        escena.getStylesheets().add(getClass().getResource("/com/example/tap2023/recursos/css/estilos_principal.css").toExternalForm());
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}