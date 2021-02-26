package com.example.myapp.clases;

import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable {

    private int idcategoria;
    private String nombre;

    public Categoria(int idcategoria, String nombre) {
        this.idcategoria = idcategoria;
        this.nombre = nombre;
    }

    public Categoria() {
        this.idcategoria = 0;
        this.nombre = "";
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return idcategoria == categoria.idcategoria;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcategoria);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
