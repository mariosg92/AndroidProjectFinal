package com.example.myapp.tareas;

import com.example.myapp.clases.FotoProducto;
import com.example.myapp.modelos.ProductoDB;

import java.util.concurrent.Callable;

public class TareaInsertarFotoProducto implements Callable<Boolean> {
    private FotoProducto fp = null;
    private String nombre = "";
    public TareaInsertarFotoProducto(FotoProducto fp, String nombre){
        this.fp = fp;
        this.nombre = nombre;
    }
    @Override
    public Boolean call() throws Exception {
        boolean insertadoOK = ProductoDB.insertarFotoProducto(fp, nombre);
        return insertadoOK;
    }
}
