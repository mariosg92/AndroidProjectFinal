package com.example.myapp.tareas;

import com.example.myapp.clases.Categoria;
import com.example.myapp.clases.Producto;
import com.example.myapp.modelos.CategoriaDB;
import com.example.myapp.modelos.ProductoDB;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class TareaObtenerProductos implements Callable<ArrayList<Producto>> {
    private String genero = "";
    private String categoria = "";

    public TareaObtenerProductos(String genero, String categoria) {
        this.genero = genero;
        this.categoria = categoria;
    }

    @Override
    public ArrayList<Producto> call() throws Exception {
        ArrayList<Producto> productos = ProductoDB.obtenerProductos(genero, categoria);
        return productos;
    }
}
