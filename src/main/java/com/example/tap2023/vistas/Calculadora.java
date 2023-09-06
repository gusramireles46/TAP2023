package com.example.tap2023.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Calculadora extends Stage {
    Scene escena;
    private VBox vbox;
    private GridPane gdpTeclado;
    private TextField txfResultado;
    private final Button[][] arrTeclado = new Button[5][4];
    private final String[] arrDigitos = {
            "C", "←", "√", "±",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
    };
    private String entrada = "";
    private String signo = "";
    private double num1 = 0.0;
    private double num2 = 0.0;

    public Calculadora() {
        CrearGUI();
        escena = new Scene(vbox, 260, 375);
        escena.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/tap2023/recursos/css/estilos_calculadora.css")).toExternalForm());
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.setResizable(false);
        this.show();
    }

    private void CrearGUI() {
        vbox = new VBox();
        gdpTeclado = new GridPane();
        txfResultado = new TextField("");
        txfResultado.setAlignment(Pos.CENTER_RIGHT);
        txfResultado.setEditable(false);
        txfResultado.setPrefHeight(50);

        // Creación de los elementos de la calculadora
        int pos = 0;
        for (int i = 0; i < arrTeclado.length; i++) {
            for (int j = 0; j < arrTeclado[i].length; j++) {
                //String textoBoton = arrDigitos[pos];
                arrTeclado[i][j] = new Button(arrDigitos[pos]);
                arrTeclado[i][j].setPrefSize(65, 65);

                if (arrDigitos[pos].matches("[0-9.]")) {
                    arrTeclado[i][j].getStyleClass().add("number");
                } else if (arrDigitos[pos].matches("[-+*/=]")) {
                    arrTeclado[i][j].getStyleClass().add("operator");
                } else if (arrDigitos[pos].equals("C")) {
                    arrTeclado[i][j].getStyleClass().add("clear");
                } else if (arrDigitos[pos].equals("←")) {
                    arrTeclado[i][j].getStyleClass().add("clear");
                } else if (arrDigitos[pos].equals("√")) {
                    arrTeclado[i][j].getStyleClass().add("operator");
                } else if (arrDigitos[pos].equals("±")) {
                    arrTeclado[i][j].getStyleClass().add("operator");
                }

                int finalPos = pos;
                arrTeclado[i][j].setOnAction(event -> procesarBoton(arrDigitos[finalPos]));

                gdpTeclado.add(arrTeclado[i][j], j, i);
                pos++;
            }
        }
        vbox.getChildren().addAll(txfResultado, gdpTeclado);

    }

    private void procesarBoton(String textoBoton) {
        // Se realiza la condición para comprobar si se presiona un dígito.
        if (textoBoton.matches("[0-9.]")) {
            generarExpresion(textoBoton);
        } else if (textoBoton.equals("=")) {
            if (!entrada.isEmpty() && signo.isEmpty()) {
                // Si no hay operador y se presiona =, el resultado es igual al número ingresado.
                double resultado = Double.parseDouble(entrada);
                mostrarResultado(resultado);
                entrada = Double.toString(resultado);
            } else if (!entrada.isEmpty()) {
                num2 = Double.parseDouble(entrada);
                double resultado = realizarOperacion();
                mostrarResultado(resultado);
                entrada = Double.toString(resultado);
                signo = "";
            }
            // Se hace la condición para comprobar si se presiona un signo.
        } else if (textoBoton.matches("[-+*/]")) {
            if (!entrada.isEmpty() && signo.isEmpty()) {
                signo = textoBoton;
                num1 = Double.parseDouble(entrada);
                entrada = "";
            }
        } else if (textoBoton.equals("C")) {
            limpiar();
        } else if (textoBoton.equals("←")) {
            borrarCaracter();
        } else if (textoBoton.equals("√")) {
            calcularRaiz();
        } else if (textoBoton.equals("±")) {
            cambiarSigno();
        }
    }

    private void generarExpresion(String textoBoton) {
        if (textoBoton.equals(".")) {
            // Validar si ya existe un punto decimal en el valor introducido actualmente
            if (!entrada.contains(".")) {
                entrada += textoBoton;
                txfResultado.setText(entrada);
            }
        } else {
            entrada += textoBoton;
            txfResultado.setText(entrada);
        }
    }


    private void mostrarResultado(double resultado) {
        if (Double.isNaN(resultado))
            txfResultado.setText("Error: División entre cero");
        else
            txfResultado.setText(Double.toString(resultado));
        entrada = Double.toString(resultado);
        signo = "";
    }

    private void limpiar() {
        entrada = "";
        signo = "";
        num1 = 0.0;
        num2 = 0.0;
        txfResultado.setText("");
    }

    private void borrarCaracter() {
        if (!entrada.isEmpty()) {
            entrada = entrada.substring(0, entrada.length() - 1);
            txfResultado.setText(entrada);
        }
    }

    private void calcularRaiz() {
        if (!entrada.isEmpty()) {
            double num = Double.parseDouble(entrada);
            if (num >= 0) {
                double resultado = Math.sqrt(num);
                txfResultado.setText(Double.toString(resultado));
                entrada = Double.toString(resultado);
            } else {
                txfResultado.setText("Error: Raíz de número negativo");
                entrada = "";
            }
        } else {
            txfResultado.setText("Error: No se ha introducido ningún número");
        }
    }

    private void cambiarSigno() {
        if (!entrada.isEmpty()) {
            double num = Double.parseDouble(entrada);
            num *= -1;
            entrada = Double.toString(num);
            txfResultado.setText(entrada);
        } else {
            txfResultado.setText("Error: No se ha introducido ningún número");
        }
    }

    private double realizarOperacion() {
        double resultado = 0.0;
        switch (signo) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "*":
                resultado = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    resultado = Double.NaN;
                }
                break;
        }
        return resultado;
    }
}
