package com.example.tap2023.vistas;

import com.example.tap2023.componets.ButtonCell;
import com.example.tap2023.models.CategoriasDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CRUDCategorias extends Stage {
    CategoriasDAO categoriasDAO;
    private VBox vbox;
    private TableView<CategoriasDAO> tbvCategorias;
    private Button btnAgregar;
    private Scene escena;

    public CRUDCategorias() {
        createGUI();
        escena = new Scene(vbox, 640, 480);
        this.setTitle("CRUD Categorias");
        this.setScene(escena);
        this.sizeToScene();
        this.show();
    }

    public void createGUI() {
        categoriasDAO = new CategoriasDAO();
        tbvCategorias = new TableView<>();
        createTable();
        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(event -> new CategoriasForm(tbvCategorias, null));
        vbox = new VBox(tbvCategorias, btnAgregar);
    }

    private void createTable() {
        TableColumn<CategoriasDAO, Integer> tbcIdCategoria = new TableColumn<>("ID");
        tbcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));

        TableColumn<CategoriasDAO, String> tbcNomCategoria = new TableColumn<>("Categoría");
        tbcNomCategoria.setCellValueFactory(new PropertyValueFactory<>("nom_categoria"));

        TableColumn<CategoriasDAO, String> tbcBorrar = new TableColumn<>("Eliminar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
            @Override
            public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                return new ButtonCell(1);
            }
        });

        TableColumn<CategoriasDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
            @Override
            public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                return new ButtonCell(2);
            }
        });

        tbvCategorias.getColumns().addAll(tbcIdCategoria, tbcNomCategoria, tbcEditar, tbcBorrar);
        tbvCategorias.setItems(categoriasDAO.listarCategorias());
    }
}
