package com.example.myapp.controladores;

import com.example.myapp.clases.Categoria;
import com.example.myapp.tareas.TareaObtenerCategorias;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CategoriaController {

    public static ArrayList<Categoria> obtenerCategorias()
    {
        ArrayList<Categoria> categoriasDevueltas = null;
        FutureTask t = new FutureTask (new TareaObtenerCategorias());
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        try {
            categoriasDevueltas= (ArrayList<Categoria>)t.get();
            es.shutdown();
            try {
                if (!es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    es.shutdownNow();
                }
            } catch (InterruptedException e) {
                es.shutdownNow();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return categoriasDevueltas;
    }
}
