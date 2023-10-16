package com.example.tap2023.vistas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class Loteria extends Stage {

    public static boolean juegoIniciado = false;
    public static String carta;
    private Scene escena;
    private HBox hPrincipal, hBtnSeleccion;
    private VBox vTablilla, vMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar, btnReiniciar;
    private Image imgCarta;
    private ImageView imageCarta, imvCarta;
    private final Button[][] btnTablilla = new Button[4][4];
    private GridPane gdpTablilla;
    private String[] arElementos = {"bully_boy", "bully_girl", "chefs", "doctor", "Ghost", "gnomo", "guest", "hunter", "janitor", "lady", "mono", "normal_six", "patients", "Six", "six_music_box", "teacher", "thinman", "viewers", "yellow_raincoat_girl", "Alone", "Low", "pigtail_girl", "rk", "six_distorcionada", "ferryman", "hands", "thinman_hat"};
    private static List<String> arrRandom;
    private Tablilla[] tablilla = new Tablilla[5];
    private GridPane[] gridPanes = new GridPane[5];
    private int numTablilla = 0;
    public static Timer timer;
    public static int cartaActual = 0;

    private void CrearGUI() {
        CrearTablilla();
        crearMazo();
        btnAnterior = new Button("◀");
        btnAnterior.getStyleClass().add("btnAnterior");
        btnAnterior.setPrefSize(210, 100);
        btnAnterior.setOnAction(event -> mostrarAnterior());
        btnSiguiente = new Button("▶");
        btnSiguiente.getStyleClass().add("btnSiguiente");
        btnSiguiente.setPrefSize(210, 100);
        btnSiguiente.setOnAction(event -> mostrarSiguiente());
        hBtnSeleccion = new HBox(btnAnterior, btnSiguiente);
        numTablilla = 0;
        vTablilla = new VBox(gridPanes[numTablilla], hBtnSeleccion);
        vTablilla.setSpacing(20);
        hPrincipal = new HBox(vTablilla, vMazo);
        hPrincipal.setPadding(new Insets(20));
        hPrincipal.setSpacing(20);
        hPrincipal.setAlignment(Pos.CENTER);
    }

    private void CrearTablilla() {
        for (int i = 0; i < tablilla.length; i++) {
            tablilla[i] = new Tablilla();
            gridPanes[i] = new GridPane();
            gridPanes[i] = tablilla[i].getGdpTablilla(arElementos);
        }
    }

    private void mostrarSiguiente() {
        if (numTablilla == 4)
            numTablilla = 0;
        else
            numTablilla++;
        vTablilla.getChildren().clear();
        vTablilla.getChildren().addAll(gridPanes[numTablilla], hBtnSeleccion);
    }

    private void mostrarAnterior() {
        if (numTablilla == 0)
            numTablilla = 4;
        else
            numTablilla--;
        vTablilla.getChildren().clear();
        vTablilla.getChildren().addAll(gridPanes[numTablilla], hBtnSeleccion);
    }

    private void crearMazo() {
        Image imgDorso = new Image(Objects.requireNonNull(getClass().getResource("/images/loteria/dorso.jpg")).toExternalForm());
        imvCarta = new ImageView(imgDorso);
        imvCarta.setFitWidth(200);
        imvCarta.setFitHeight(350);
        btnIniciar = new Button("Iniciar");
        btnIniciar.setOnAction(event -> iniciarJuego());
        btnIniciar.getStyleClass().add("btnIniciar");
        btnReiniciar = new Button("Reiniciar");
        btnReiniciar.getStyleClass().add("btnReiniciar");
        btnReiniciar.setOnAction(event -> reiniciarLoteria());
        vMazo = new VBox(imvCarta, btnIniciar, btnReiniciar);
        vMazo.setSpacing(20);
        vMazo.setAlignment(Pos.CENTER);
    }

    private void reiniciarLoteria() {
        this.close();
        if (timer != null)
            timer.cancel();
        new Loteria();
    }

    public Loteria() {
        CrearGUI();
        escena = new Scene(hPrincipal, 850, 650);
        escena.getStylesheets().add(getClass().getResource("/css/estilos_loteria.css").toString());
        this.setTitle("Lotería");
        this.setScene(escena);
        this.setOnCloseRequest(event -> {
            if (timer != null)
                timer.cancel();
        });
        this.show();
    }

    public void revolverCartas(String[] arElementos) {
        arrRandom = Arrays.asList(arElementos);
        Collections.shuffle(arrRandom);
    }

    public void sacarCartaMazo() {
        if (cartaActual < arrRandom.size()) {
            carta = arrRandom.get(cartaActual);
            Image imgCarta = new Image(getClass().getResource("/images/loteria/" + carta + ".png").toExternalForm());
            imvCarta.setImage(imgCarta);
            cartaActual++;
        } else {
            timer.cancel();
            if (Tablilla.elementosSeleccionados != 16 && juegoIniciado) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Información");
                    a.setHeaderText("Has perdido el juego");
                    a.setContentText("No has completado tu tablilla");
                    a.showAndWait();
                    cartaActual = 0;
                    juegoIniciado = false;
                    timer.cancel();
                });
            }
        }
    }

    private void iniciarJuego() {
        juegoIniciado = true;
        btnIniciar.setDisable(true);
        btnSiguiente.setDisable(true);
        btnAnterior.setDisable(true);
        revolverCartas(arElementos);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sacarCartaMazo();
            }
        }, 0, 5000);
    }
}
