package com.example.myapp.tareas;

import com.example.myapp.clases.FotoProducto;
import com.example.myapp.modelos.ProductoDB;

import java.util.concurrent.Callable;

public class TareaActualizarFotoProducto implements Callable<Boolean> {
    private FotoProducto fp = null;
    private int idProducto;
    public TareaActualizarFotoProducto(FotoProducto fp, int idProducto) {
        this.fp = fp;
        this.idProducto = idProducto;
    }

    @Override
    public Boolean call() throws Exception {
        boolean actualizadoOK = ProductoDB.actualizarFotoProducto(fp, idProducto);
        return actualizadoOK;
    }
}
