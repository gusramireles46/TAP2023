package com.example.tap2023.vistas;

import com.example.tap2023.componets.HiloTareas;
import com.example.tap2023.models.ProgressListener;
import com.example.tap2023.models.Tarea;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class SimuladorImpresion extends Stage implements ProgressListener {
    private Button btnAgregarTarea, btnControlSimulador;
    private TableView<Tarea> tbvTareas;
    private ProgressBar pgbProgreso;
    private BorderPane bdpSimulador;
    private boolean simuladorActivo = true;
    private VBox vbxTareas;
    private Scene escena;
    private HiloTareas hiloTareas;
    private Thread threadHiloTareas;
    private int noArchivo = 0;
    private Label lblProgreso;

    public SimuladorImpresion() {
        crearGUI();
        escena = new Scene(bdpSimulador, 650, 500);
        this.setScene(escena);
        this.setTitle("Simulador de impresión");
        this.show();
        this.sizeToScene();
        this.setOnCloseRequest(e -> accionSalir());
        Platform.runLater(this::accionSalir);
        ejecutarSimulador();
    }

    private void crearGUI() {
        tbvTareas = new TableView<>();
        TableColumn<Tarea, Integer> tbcNoArchivo = new TableColumn<>("No. Archivo");
        tbcNoArchivo.setCellValueFactory(new PropertyValueFactory<>("noArchivo"));

        TableColumn<Tarea, String> tbcNombreArchivo = new TableColumn<>("Nombre Archivo");
        tbcNombreArchivo.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));

        TableColumn<Tarea, Integer> tbcNoHojas = new TableColumn<>("No. Hojas");
        tbcNoHojas.setCellValueFactory(new PropertyValueFactory<>("numHojas"));

        TableColumn<Tarea, String> tbcHora = new TableColumn<>("Hora de entrada");
        tbcHora.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));


        tbvTareas.getColumns().addAll(tbcNoArchivo, tbcNombreArchivo, tbcNoHojas, tbcHora);

        btnAgregarTarea = new Button("Agregar tarea");
        btnAgregarTarea.setOnAction(e -> agregarTarea());

        btnControlSimulador = new Button("Apagar Simulador");
        btnControlSimulador.setOnAction(e -> cambiarEstadoSimulador());

        pgbProgreso = new ProgressBar(0);

        bdpSimulador = new BorderPane();
        HBox hbxBotones = new HBox(btnAgregarTarea, btnControlSimulador);
        hbxBotones.setSpacing(10);
        hbxBotones.setAlignment(Pos.CENTER);
        lblProgreso = new Label(pgbProgreso.getProgress() * 100 + "%");
        HBox hbxProgreso = new HBox(pgbProgreso, lblProgreso);
        hbxProgreso.setAlignment(Pos.CENTER);
        hbxProgreso.setSpacing(10);
        vbxTareas = new VBox(tbvTareas, hbxProgreso, hbxBotones);
        vbxTareas.setSpacing(10);
        vbxTareas.setAlignment(Pos.CENTER);
        Label lblTitulo = new Label("Simulador de impresión");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        bdpSimulador.setTop(lblTitulo);
        bdpSimulador.setCenter(vbxTareas);

    }

    private void agregarTarea() {
        if (simuladorActivo) {
            Random random = new Random();

            noArchivo++;
            String nombreArchivo = generarNombreArchivo();
            int numHojas = random.nextInt(150) + 1;
            String horaAcceso = obtenerHoraActual();

            Tarea tarea = new Tarea(noArchivo, nombreArchivo, numHojas, horaAcceso);
            tbvTareas.getItems().add(tarea);
        }
    }

    private String generarNombreArchivo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaHora = dateFormat.format(new Date());
        return "Archivo_" + fechaHora + ".pdf";
    }

    private String obtenerHoraActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }

    private void cambiarEstadoSimulador() {
        simuladorActivo = !simuladorActivo;
        String textoBoton = simuladorActivo ? "Apagar Simulador" : "Encender Simulador";
        btnControlSimulador.setText(textoBoton);

        if (simuladorActivo) {
            if (threadHiloTareas != null && threadHiloTareas.isAlive()) {
                threadHiloTareas.interrupt();
            }
        } else {
            if (!threadHiloTareas.isInterrupted()) {
                threadHiloTareas = new Thread(hiloTareas);
                threadHiloTareas.start();
            }
        }
    }

    private void accionSalir() {
        this.setOnCloseRequest(e -> {
            if (!tbvTareas.getItems().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Mensaje del sistema");
                a.setHeaderText("La cola no está vacía");
                a.setContentText("Aún hay tareas en la cola de impresión, se eliminará toda la cola. ¿Seguro que desea salir?");
                Optional<ButtonType> result = a.showAndWait();
                if (result.get() == ButtonType.OK) {
                    detenerHiloTareas();
                    this.close();
                } else {
                    e.consume();
                }
            } else {
                detenerHiloTareas();
                this.close();
            }
        });
    }

    private void detenerHiloTareas() {
        if (threadHiloTareas != null) {
            threadHiloTareas.interrupt();
            try {
                threadHiloTareas.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadHiloTareas = null;
        }
    }

    private void ejecutarSimulador() {
        hiloTareas = new HiloTareas(this);
        threadHiloTareas = new Thread(hiloTareas);
        threadHiloTareas.start();
    }

    public boolean isSimuladorActivo() {
        return simuladorActivo;
    }

    public TableView<Tarea> getTbvTareas() {
        return tbvTareas;
    }

    public ProgressBar getPgbProgreso() {
        return pgbProgreso;
    }

    @Override
    public void actualizarProgreso(double progress) {
        Platform.runLater(() -> {
            String formattedProgress = String.format("%.1f", progress);
            lblProgreso.setText(formattedProgress + "%");
        });
    }
}
