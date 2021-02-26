package com.example.myapp.clases;

import android.graphics.Bitmap;

import java.io.Serializable;

public class FotoProducto implements Serializable {
    private int idfoto;
    private Bitmap foto;
    private int idproducto;

    public FotoProducto(int idfoto, Bitmap foto, int idproducto) {
        this.idfoto = idfoto;
        this.foto = foto;
        this.idproducto = idproducto;
    }

    public FotoProducto() {

    }

    public int getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(int idfoto) {
        this.idfoto = idfoto;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FotoProducto that = (FotoProducto) o;
        return idfoto == that.idfoto &&
                idproducto == that.idproducto &&
                foto.equals(that.foto);
    }

    @Override
    public String toString() {
        return "FotoProducto{" +
                "idfoto=" + idfoto +
                ", idproducto=" + idproducto +
                '}';
    }
}
