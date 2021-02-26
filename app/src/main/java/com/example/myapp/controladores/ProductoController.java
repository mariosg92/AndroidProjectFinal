package com.example.myapp.controladores;

import com.example.myapp.clases.Categoria;
import com.example.myapp.clases.FotoProducto;
import com.example.myapp.clases.Producto;
import com.example.myapp.tareas.TareaActualizarProducto;
import com.example.myapp.tareas.TareaBorrarProducto;
import com.example.myapp.tareas.TareaInsertarProducto;
import com.example.myapp.tareas.TareaObtenerCategorias;
import com.example.myapp.tareas.TareaObtenerProductos;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ProductoController {

    public static ArrayList<Producto> obtenerProductos(String genero, String categoria)
    {
        ArrayList<Producto> productosDevueltos = null;
        FutureTask t = new FutureTask (new TareaObtenerProductos(genero, categoria));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        try {
            productosDevueltos= (ArrayList<Producto>)t.get();
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
        return productosDevueltos;
    }

    public static boolean insertarProducto(Producto p) {
        FutureTask t = new FutureTask(new TareaInsertarProducto(p));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        boolean insercionOK = false;
        try {
            insercionOK = (boolean) t.get();
            es.shutdown();
            try {
                if (!es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    es.shutdownNow();
                }
            } catch (InterruptedException e) {
                es.shutdownNow();
            }
        } catch (
                ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            return insercionOK;
        }
    }

    public static  boolean borrarProducto(Producto p){
        FutureTask t = new FutureTask(new TareaBorrarProducto(p));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        boolean borradoOK = false;
        try {
            borradoOK = (boolean) t.get();
            es.shutdown();
            try {
                if (!es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    es.shutdownNow();
                }
            } catch (InterruptedException e) {
                es.shutdownNow();
            }
        } catch (
                ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            return borradoOK;
        }

    }

    public static boolean actualizarProducto(Producto p) {
        FutureTask t = new FutureTask(new TareaActualizarProducto(p));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        boolean actualizadoOK = false;
        try {
            actualizadoOK = (boolean) t.get();
            es.shutdown();
            try {
                if (!es.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    es.shutdownNow();
                }
            } catch (InterruptedException e) {
                es.shutdownNow();
            }
        } catch (
                ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            return actualizadoOK;
        }
    }
}
