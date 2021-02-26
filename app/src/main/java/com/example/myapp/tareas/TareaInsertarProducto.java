package com.example.myapp.tareas;

import com.example.myapp.clases.Producto;
import com.example.myapp.modelos.ProductoDB;

import java.util.concurrent.Callable;

public class TareaInsertarProducto implements Callable<Boolean> {
    private Producto p = null;
    public TareaInsertarProducto(Producto p) {
        this.p = p;
    }

    @Override
    public Boolean call() throws Exception {
        boolean insertadoOK= ProductoDB.insertarProducto(p);
        return insertadoOK;
    }
}
