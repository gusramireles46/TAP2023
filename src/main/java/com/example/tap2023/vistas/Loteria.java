package com.example.tap2023.vistas;

import javafx.geometry.Insets;
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

    private Scene escena;
    private HBox hPrincipal, hBtnSeleccion;
    private VBox vTablilla, vMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Image imgCarta;
    private ImageView imageCarta, imvCarta;
    private final Button[][] btnTablilla = new Button[4][4];
    private GridPane gdpTablilla;
    private String[] arElementos = {"ln0", "ln1", "ln2", "ln3", "ln4", "ln5", "ln6", "ln7", "ln8", "ln9", "ln10", "ln11", "ln12", "ln13", "ln14", "ln15"};
    private Timer timer;
    private List<String> arrRandom, tablilla1, tablilla2, tablilla3, tablilla4, tablilla5;

    private void CrearGUI(){
        CrearTablilla();
        crearMazo();
        btnAnterior = new Button("<-");
        btnAnterior.setPrefSize(210, 100);
        btnSiguiente = new Button("->");
        btnSiguiente.setPrefSize(210, 100);
        hBtnSeleccion = new HBox(btnAnterior, btnSiguiente);
        vTablilla = new VBox(gdpTablilla, hBtnSeleccion);
        //vTablilla.setSpacing(20);
        hPrincipal = new HBox(vTablilla, vMazo);
        hPrincipal.setPadding(new Insets(20));
    }

    private void CrearTablilla() {
        gdpTablilla = new GridPane();
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    Image imgCartaP = new Image(Objects.requireNonNull(getClass().getResource("/images/loteria/" +arElementos[pos]+".jpg")).toExternalForm());
                    imageCarta = new ImageView(imgCartaP);
                    imageCarta.setFitHeight(130);
                    imageCarta.setFitWidth(90);
                    btnTablilla[i][j] = new Button();
                    btnTablilla[i][j].setPrefSize(100, 140);
                    btnTablilla[i][j].setGraphic(imageCarta);
                    gdpTablilla.add(btnTablilla[i][j], i, j);
                    pos++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Clase Timer para cambiar las cartas del mazo
    // Randomizar todas las cartas
    // Hacer un efecto de clicked en el botón cuando se inicia el juego

    private void crearMazo() {
        Image imgDorso = new Image(Objects.requireNonNull(getClass().getResource("/images/loteria/ln1.jpg")).toExternalForm());
        imvCarta = new ImageView(imgDorso);
        imvCarta.setFitWidth(100);
        imvCarta.setFitHeight(200);
        btnIniciar = new Button("Iniciar");
        btnIniciar.setOnAction(event -> iniciarJuago());
        vMazo = new VBox(imvCarta, btnIniciar);
    }

    public Loteria() {
        CrearGUI();
        escena = new Scene(hPrincipal, 850, 650);
        this.setTitle("Lotería");
        this.setScene(escena);
        this.show();
    }

    public void revolverCartas(String[] arElementos) {
        arrRandom = Arrays.asList(arElementos);
        Collections.shuffle(arrRandom);
    }

    private void sacarCartaMazo() {

    }

    private void iniciarJuago() {
        revolverCartas(arElementos);

    }

}
