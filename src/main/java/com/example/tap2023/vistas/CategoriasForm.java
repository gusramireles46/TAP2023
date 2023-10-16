package com.example.tap2023.vistas;

import com.example.tap2023.models.CategoriasDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CategoriasForm extends Stage {

    private Scene escena;
    private HBox hbox;
    private TextField txfNombreCat;
    private Button btnGuardar;
    private TableView<CategoriasDAO> tbvCategorias;
    private final CategoriasDAO objCatDAO;

    public CategoriasForm(TableView<CategoriasDAO> tbvCat, CategoriasDAO objCat) {
        this.tbvCategorias = tbvCat;
        this.objCatDAO = objCat == null ? new CategoriasDAO() : objCat;
        crearGUI();
        escena = new Scene(hbox);
        this.setTitle("Gestión de categorías");
        this.setScene(escena);
        this.show();
    }

    private void crearGUI() {
        txfNombreCat = new TextField();
        txfNombreCat.setText(objCatDAO.getNom_categoria());
        txfNombreCat.setPromptText("Nombre de la categoría");
        txfNombreCat.setFocusTraversable(false);
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> guardarCategoria());
        hbox = new HBox(txfNombreCat, btnGuardar);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
    }

    private void guardarCategoria() {
        objCatDAO.setNom_categoria(txfNombreCat.getText());
        if (objCatDAO.getId_categoria() > 0)
            objCatDAO.actualizar();
        else
            objCatDAO.insertar();
        tbvCategorias.setItems(objCatDAO.listarCategorias());
        tbvCategorias.refresh();
        this.close();
    }
}
