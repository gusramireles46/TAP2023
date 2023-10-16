package com.example.tap2023.componets;

import com.example.tap2023.models.CategoriasDAO;
import com.example.tap2023.vistas.CategoriasForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCell extends TableCell<CategoriasDAO, String> {
    private Button btnCelda;
    private TableView<CategoriasDAO> tbvCategorias;
    private CategoriasDAO objCat;
    private int opc;

    public ButtonCell(int opc) {
        this.opc = opc;

        String txtBtn = this.opc == 1 ? "Borrar" : "Editar";
        if (opc == 1) {
            btnCelda = new Button(txtBtn);
            btnCelda.setOnAction(event -> accionBoton());
        } else if (opc == 2) {
            btnCelda = new Button(txtBtn);
            btnCelda.setOnAction(e -> accionBoton());
        }
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if (!b)
            this.setGraphic(btnCelda);
    }

    private void accionBoton() {
        if (this.opc == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("Confirmando acción");
            alert.setContentText("¿Seguro que deseas eliminar la categoría?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tbvCategorias = ButtonCell.this.getTableView();
                objCat = tbvCategorias.getItems().get(ButtonCell.this.getIndex());
                objCat.eliminar();
                //Refrescar tabla
                tbvCategorias.setItems(objCat.listarCategorias());
                tbvCategorias.refresh();
            }
        } else {
            tbvCategorias = ButtonCell.this.getTableView();
            objCat = tbvCategorias.getItems().get(ButtonCell.this.getIndex());
            new CategoriasForm(tbvCategorias, objCat);
        }
    }
}
