package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.clases.Categoria;
import com.example.myapp.clases.ListaCategoriasAdapter;
import com.example.myapp.controladores.CategoriaController;

import java.util.ArrayList;

import static com.example.myapp.MainActivity.EXTRA_GENERO;

public class ListadoCategoriasActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ListaCategoriasAdapter mAdapter;
    private ArrayList<Categoria> categorias;
    public static String genero = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_categorias);
        Intent intent = getIntent();
        genero = intent.getStringExtra(EXTRA_GENERO);
        categorias = CategoriaController.obtenerCategorias();
        if(categorias!=null){
            mRecyclerView = findViewById(R.id.rv_categorias);
            mAdapter = new ListaCategoriasAdapter(this, categorias);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else{
            mostrarToast("No se pudo conectar con la base de datos");
        }

    }

    private void mostrarToast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    public void refrescarCategorias(View view) {
        categorias = CategoriaController.obtenerCategorias();
        if(categorias != null) {
            mAdapter.setListaCategorias(categorias);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }


}