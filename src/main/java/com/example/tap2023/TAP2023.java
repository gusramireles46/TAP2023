package com.example.tap2023;

import com.example.tap2023.vistas.Calculadora;
import com.example.tap2023.vistas.Loteria;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TAP2023 extends Application {

    private Scene escena;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Menu menuParcial1, menuParcial2, menuOpciones;
    private MenuItem mniCalculadora, mniLoteria, mniSalir;

    private void CrearGUI() {
        // Menú Items
        mniCalculadora = new MenuItem("Calculadora");
        mniCalculadora.setOnAction(event -> new Calculadora());

        mniLoteria = new MenuItem("Lotería");
        mniLoteria.setOnAction(event -> new Loteria());

        mniSalir = new MenuItem("Salir");
        mniSalir.setOnAction(event -> salir());

        // Menú
        menuParcial1 = new Menu("Parcial 1");
        menuParcial1.getItems().addAll(mniCalculadora, mniLoteria);

        menuParcial2 = new Menu("Parcial 2");

        menuOpciones = new Menu("Más opciones");
        menuOpciones.getItems().add(mniSalir);

        // MenuBar
        menuBar = new MenuBar(menuParcial1, menuParcial2, menuOpciones);
    }

    private void salir() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Información");
        a.setHeaderText("Salir");
        a.setContentText("Estas saliendo del programa");
        Optional<ButtonType> option = a.showAndWait();
        if (option.get() == ButtonType.OK)
            System.exit(0);
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