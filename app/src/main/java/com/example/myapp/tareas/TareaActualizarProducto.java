package com.example.myapp.tareas;

import com.example.myapp.clases.Producto;
import com.example.myapp.modelos.ProductoDB;

import java.util.concurrent.Callable;

public class TareaActualizarProducto implements Callable<Boolean> {
    private Producto p = null;

    public TareaActualizarProducto(Producto p) {
        this.p = p;
    }

    @Override
    public Boolean call() throws Exception {
        boolean actualizadoOK = ProductoDB.actualizarProducto(p);
        return actualizadoOK;
    }
}
