package com.example.myapp.tareas;

import com.example.myapp.clases.Categoria;
import com.example.myapp.modelos.CategoriaDB;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class TareaObtenerCategorias implements Callable<ArrayList<Categoria>> {
    @Override
    public ArrayList<Categoria> call() throws Exception {
        ArrayList<Categoria> categorias = CategoriaDB.obtenerCategorias();
        return categorias;
    }
}
