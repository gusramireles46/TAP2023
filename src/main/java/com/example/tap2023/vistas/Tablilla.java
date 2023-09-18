package com.example.tap2023.vistas;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Tablilla {
    public static int elementosSeleccionados;
    private GridPane gdpTablilla;
    private ImageView imageCarta;
    private Button[][] btnTablilla = new Button[4][4];
    private List<String> arrCartas;

    public GridPane getGdpTablilla(String[] arrElementos) {
        gdpTablilla = new GridPane();
        gdpTablilla.getStylesheets().add(getClass().getResource("/css/estilos_loteria.css").toString());
        arrCartas = Arrays.asList(arrElementos);
        Collections.shuffle(arrCartas);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    String carta = arrCartas.get(pos);
                    Image imgCartaP = new Image(Objects.requireNonNull(getClass().getResource("/images/loteria/" + carta + ".png")).toExternalForm());
                    imageCarta = new ImageView(imgCartaP);
                    imageCarta.setFitHeight(130);
                    imageCarta.setFitWidth(90);
                    btnTablilla[i][j] = new Button();
                    btnTablilla[i][j].setPrefSize(100, 140);
                    btnTablilla[i][j].setGraphic(imageCarta);
                    btnTablilla[i][j].getStyleClass().add("elemento-carta");

                    int finalI = i;
                    int finalJ = j;
                    btnTablilla[i][j].setOnAction(event -> {
                        if (Loteria.juegoIniciado) {
                            if (Loteria.carta.equals(carta)) {
                                btnTablilla[finalI][finalJ].setDisable(true);
                                elementosSeleccionados++;
                                if (elementosSeleccionados == 16) {
                                    Platform.runLater(() -> {
                                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                                        a.setTitle("Información");
                                        a.setHeaderText("Has ganado el juego");
                                        a.setContentText("Has completado tu tablilla");
                                        a.showAndWait();
                                        Loteria.timer.cancel();
                                    });
                                }
                            }
                        }
                    });

                    gdpTablilla.add(btnTablilla[i][j], i, j);
                    pos++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return gdpTablilla;
    }
}
