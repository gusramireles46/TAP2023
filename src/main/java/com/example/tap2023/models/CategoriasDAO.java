package com.example.tap2023.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriasDAO {

    private int id_categoria;
    private String nom_categoria;

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNom_categoria() {
        return nom_categoria;
    }

    public void setNom_categoria(String nom_categoria) {
        this.nom_categoria = nom_categoria;
    }

    public void insertar() {
        String query = "INSERT INTO tblCategorias (nom_categoria) VALUES ('"+ this.nom_categoria +"')";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        String query = "UPDATE tblCategorias SET nom_categoria = '"+this.nom_categoria+"' WHERE id_categoria = " + this.id_categoria;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        String query = "DELETE FROM tblCategorias WHERE id_categoria="+this.id_categoria;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<CategoriasDAO> listarCategorias() {
        ObservableList<CategoriasDAO> listCat = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblCategorias";
        CategoriasDAO objC = null;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                objC = new CategoriasDAO();
                objC.setId_categoria(res.getInt("id_categoria"));
                objC.setNom_categoria(res.getString("nom_categoria"));
                listCat.add(objC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCat;
    }
}
