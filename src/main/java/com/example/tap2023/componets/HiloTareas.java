package com.example.tap2023.componets;

import com.example.tap2023.models.Tarea;
import com.example.tap2023.vistas.SimuladorImpresion;
import javafx.application.Platform;

public class HiloTareas implements Runnable {
    private final SimuladorImpresion simulador;
    private boolean removeTaskScheduled = false;

    public HiloTareas(SimuladorImpresion simulador) {
        this.simulador = simulador;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (simulador.isSimuladorActivo() && !simulador.getTbvTareas().getItems().isEmpty()) {
                Tarea tarea = simulador.getTbvTareas().getItems().get(0);

                for (int i = 0; i <= tarea.getNumHojas(); i++) {
                    final double progress = (double) i / tarea.getNumHojas();
                    actualizarProgreso(progress);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                Platform.runLater(() -> simulador.getPgbProgreso().setProgress(0));

                if (!removeTaskScheduled) {
                    removeTaskScheduled = true;
                    Platform.runLater(() -> {
                        simulador.getTbvTareas().getItems().remove(0);
                        removeTaskScheduled = false;
                    });
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void actualizarProgreso(double progress) {
        Platform.runLater(() -> {
            simulador.getPgbProgreso().setProgress(progress);
            double percentage = (progress * 100);
            simulador.actualizarProgreso(percentage);
        });
    }
}
