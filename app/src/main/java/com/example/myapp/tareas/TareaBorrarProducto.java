package com.example.myapp.tareas;

import com.example.myapp.clases.Producto;
import com.example.myapp.modelos.ProductoDB;

import java.util.concurrent.Callable;

public class TareaBorrarProducto implements Callable<Boolean> {
    private Producto p = null;
    public TareaBorrarProducto(Producto p) {
        this.p = p;
    }

    @Override
    public Boolean call() throws Exception {
        boolean borradoOK = ProductoDB.borrarProducto(p);
        return borradoOK;
    }
}
