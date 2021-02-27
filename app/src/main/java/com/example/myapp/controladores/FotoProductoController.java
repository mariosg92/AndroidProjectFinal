package com.example.myapp.controladores;

import com.example.myapp.clases.FotoProducto;
import com.example.myapp.tareas.TareaActualizarFotoProducto;
import com.example.myapp.tareas.TareaBorrarFotoProducto;
import com.example.myapp.tareas.TareaInsertarFotoProducto;
import com.example.myapp.tareas.TareaObtenerFotosProductos;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FotoProductoController {
    public static ArrayList<FotoProducto> obtenerFotosProductos() {
        ArrayList<FotoProducto> fotosProductos = null;
        FutureTask t = new FutureTask (new TareaObtenerFotosProductos(100,100));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(t);
        try {
            fotosProductos = (ArrayList<FotoProducto>)t.get();
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
        return fotosProductos;
    }

    public static  boolean insertarFoto(FotoProducto fp, String nombre) {
        FutureTask t = new FutureTask(new TareaInsertarFotoProducto(fp, nombre));
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

    public static boolean actualizarFoto(FotoProducto fp, int idProducto) {
        FutureTask t = new FutureTask(new TareaActualizarFotoProducto(fp, idProducto));
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

    public static boolean borrarFotoProducto(int idProducto) {
        FutureTask t = new FutureTask(new TareaBorrarFotoProducto(idProducto));
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
}
