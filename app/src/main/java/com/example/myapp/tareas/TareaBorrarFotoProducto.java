package com.example.myapp.tareas;

import com.example.myapp.modelos.ProductoDB;

import java.util.concurrent.Callable;

public class TareaBorrarFotoProducto implements Callable<Boolean>{
    private int idProducto;
    public TareaBorrarFotoProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public Boolean call() throws Exception {
        boolean borradoOK = ProductoDB.borrarFotoProducto(idProducto);
        return borradoOK;
    }
}
