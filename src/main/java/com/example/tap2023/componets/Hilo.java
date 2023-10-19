package com.example.tap2023.componets;

import javafx.scene.control.ProgressBar;

public class Hilo extends Thread {

    private ProgressBar pgbCorredor;

    /*public Hilo(String nombre) {
        //super(nombre);
        this.setName(nombre);
    }*/

    public Hilo(String nombre, ProgressBar pgbCorredor) {
        this.setName(nombre);
        this.pgbCorredor = pgbCorredor;
    }

    //@Override
    /*public void run() {
        super.run();
        try {
            for (int i = 1; i <= 10; i++) {
                //System.out.println("Ejecutando iteración: " + i);
                System.out.println("Corredor: " + this.getName() + " llegó al KM" + i);
                sleep((long)Math.random() * 1500);

            }

            System.out.println("Corredor: " + this.getName() + " llegó a la meta");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void run() {
        super.run();
        double avance = 0;
        while (avance < 1) {
            avance += Math.random()/10;
            this.pgbCorredor.setProgress(avance);
            try {
                sleep((long) (Math.random()*1500));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
