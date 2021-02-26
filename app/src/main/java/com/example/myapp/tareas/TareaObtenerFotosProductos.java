package com.example.myapp.tareas;

import com.example.myapp.clases.FotoProducto;
import com.example.myapp.modelos.ProductoDB;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class TareaObtenerFotosProductos implements Callable<ArrayList<FotoProducto>> {

    private int width;
    private int height;

    public TareaObtenerFotosProductos(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public ArrayList<FotoProducto> call() throws Exception {
        ArrayList<FotoProducto> fotosProductos = ProductoDB.obtenerFotosProductos(this.width, this.height);
        return fotosProductos;
    }
}
