package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.clases.Categoria;
import com.example.myapp.clases.FotoProducto;
import com.example.myapp.clases.ListaProductosAdapter;
import com.example.myapp.clases.Producto;
import com.example.myapp.controladores.FotoProductoController;
import com.example.myapp.controladores.ProductoController;
import com.example.myapp.modelos.ProductoDB;

import java.util.ArrayList;

import static com.example.myapp.MainActivity.EXTRA_GENERO;
import static com.example.myapp.clases.CategoriasViewHolder.EXTRA_OBJETO_CATEGORIAS;

public class ListadoProductosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ListaProductosAdapter mAdapter;
    private ArrayList<Producto> productos;
    private ArrayList<FotoProducto> fotosProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_productos);
        Intent intent = getIntent();
        String genero = intent.getStringExtra(EXTRA_GENERO);
        Categoria categoria = (Categoria) intent.getSerializableExtra(EXTRA_OBJETO_CATEGORIAS);
        productos = ProductoController.obtenerProductos(genero, categoria.getNombre());
        fotosProductos = FotoProductoController.obtenerFotosProductos();
        if(productos!=null){
            mRecyclerView = findViewById(R.id.rv_productos);
            mAdapter = new ListaProductosAdapter(this, productos, fotosProductos);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    if(direction == ItemTouchHelper.RIGHT)
                    {
                        mostrarAlerta(viewHolder, genero, categoria.getNombre());
                    }
                }
            });
            helper.attachToRecyclerView(mRecyclerView);
        }else{
            mostrarToast("No se pudo conectar con la base de datos");
        }


    }

    private void mostrarToast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
    private void mostrarAlerta(RecyclerView.ViewHolder viewHolder, String genero, String categoria){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Desea borrar el Producto?");
        alerta.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Producto p = productos.get(viewHolder.getAdapterPosition());
                productos.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                ProductoController.borrarProducto(p);
            }
        });
        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refrescarProductos(genero, categoria);
            }
        });
        alerta.show();
    }

    public void refrescarProductos(String genero, String categoria) {
        productos = ProductoController.obtenerProductos(genero, categoria);
        if(productos != null) {
            mAdapter.setListaProductos(productos);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

}