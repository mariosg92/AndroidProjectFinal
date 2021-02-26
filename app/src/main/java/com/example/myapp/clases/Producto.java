package com.example.myapp.clases;

import java.io.Serializable;
import java.util.Objects;

public class Producto implements Serializable {

    private int idproducto;
    private String nombre;
    private String genero;
    private String marca;
    private int idcategoria;

    public Producto(int idproducto, String nombre, String genero, String marca, int idcategoria) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.genero = genero;
        this.marca = marca;
        this.idcategoria = idcategoria;
    }

    public Producto(){
        this.idproducto = 0;
        this.nombre = "";
        this.genero = "";
        this.marca = "";
        this.idcategoria = 1;
    }

    public Producto(String nombre, String genero, String marca, int idcategoria){
        this.nombre = nombre;
        this.genero = genero;
        this.marca = marca;
        this.idcategoria = idcategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idproducto == producto.idproducto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idproducto=" + idproducto +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", marca='" + marca + '\'' +
                ", idcategoria=" + idcategoria +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(idproducto);
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public String getMarca() {
        return marca;
    }

    public int getIdcategoria() {
        return idcategoria;
    }
}
