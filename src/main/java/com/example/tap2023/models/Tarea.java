package com.example.tap2023.models;

public class Tarea {
    private int noArchivo;
    private String nombreArchivo;
    private int numHojas;
    private String horaAcceso;

    public Tarea(int noArchivo, String nombreArchivo, int numHojas, String horaAcceso) {
        this.noArchivo = noArchivo;
        this.nombreArchivo = nombreArchivo;
        this.numHojas = numHojas;
        this.horaAcceso = horaAcceso;
    }

    public int getNoArchivo() {
        return noArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public int getNumHojas() {
        return this.numHojas;
    }

    public String getHoraEntrada() {
        return this.horaAcceso;
    }
}
