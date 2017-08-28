package com.senamoviles.alcayata.alcayata.LogicaLista;

import com.senamoviles.alcayata.alcayata.R;

import java.util.ArrayList;

/**
 * Created by macbook on 8/25/17.
 */

public class Paso {

    String nombre;
    int foto;
    String descripcion;
    String sabias;
    String sabias_mas;

    public Paso(String nombre, int foto) {
        this.nombre = nombre;
        this.foto = foto;
    }

    public Paso(String nombre, int foto, String descripcion, String sabias, String sabias_mas) {
        this.nombre = nombre;
        this.foto = foto;
        this.descripcion = descripcion;
        this.sabias = sabias;
        this.sabias_mas = sabias_mas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSabias() {
        return sabias;
    }

    public void setSabias(String sabias) {
        this.sabias = sabias;
    }

    public String getSabias_mas() {
        return sabias_mas;
    }

    public void setSabias_mas(String sabias_mas) {
        this.sabias_mas = sabias_mas;
    }
}
