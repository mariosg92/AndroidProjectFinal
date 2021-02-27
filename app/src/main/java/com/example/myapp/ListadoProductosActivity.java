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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
    private ArrayList<Producto> productosBuscados;
    private ArrayList<FotoProducto> fotosProductos;
    private EditText edt_busqueda;
    private String genero;
    private Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_productos);
        mostrarToast("SWIPE RIGHT TO DELETE AN ITEM");
        edt_busqueda = findViewById(R.id.edt_buscar);
        Intent intent = getIntent();
        genero = intent.getStringExtra(EXTRA_GENERO);
        categoria = (Categoria) intent.getSerializableExtra(EXTRA_OBJETO_CATEGORIAS);
        productos = ProductoController.obtenerProductos(genero, categoria.getNombre());
        fotosProductos = FotoProductoController.obtenerFotosProductos();
        if (productos != null) {
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
                    if (direction == ItemTouchHelper.RIGHT) {
                        mostrarAlerta(viewHolder, genero, categoria.getNombre());
                    }
                }
            });
            helper.attachToRecyclerView(mRecyclerView);
        } else {
            mostrarToast("No se pudo conectar con la base de datos");
        }


    }

    private void mostrarToast(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
    }

    private void mostrarAlerta(RecyclerView.ViewHolder viewHolder, String genero, String categoria) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Desea borrar el Producto?");
        alerta.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mAdapter.getListaProductos() != productosBuscados) {
                    Producto p = productos.get(viewHolder.getAdapterPosition());
                    productos.remove(viewHolder.getAdapterPosition());
                    mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    FotoProductoController.borrarFotoProducto(p.getIdproducto());
                    ProductoController.borrarProducto(p);
                    refrescarProductos(genero, categoria);
                }else{
                    Producto p = productosBuscados.get(viewHolder.getAdapterPosition());
                    productosBuscados.remove(viewHolder.getAdapterPosition());
                    mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    FotoProductoController.borrarFotoProducto(p.getIdproducto());
                    ProductoController.borrarProducto(p);
                    refrescarProductos(genero, categoria);
                }

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
        if (productos != null) {
            mAdapter.setListaProductos(productos);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void buscarProductos(View view) {
        String busqueda = edt_busqueda.getText().toString();
        productosBuscados = new ArrayList<Producto>();
        if (busqueda.isEmpty()) {
            refrescarProductos(genero, categoria.getNombre());
        } else {
            for (Producto p : productos) {
                if (p.returnStringBusqueda().toLowerCase().contains(busqueda.toLowerCase())) {
                    productosBuscados.add(p);
                }
            }
            mAdapter.setListaProductos(productosBuscados);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

}