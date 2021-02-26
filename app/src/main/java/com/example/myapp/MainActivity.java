package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_GENERO = "com.example.MainActivity.genero";
    private String genero = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sectionHombres(View view) {
        genero = "hombre";
        Intent intent = new Intent(this, ListadoCategoriasActivity.class);
        intent.putExtra(EXTRA_GENERO, genero);
        startActivity(intent);
    }

    public void sectionMujeres(View view) {
        genero = "mujer";
        Intent intent = new Intent(this, ListadoCategoriasActivity.class);
        intent.putExtra(EXTRA_GENERO, genero);
        startActivity(intent);
    }

    public void addProducto(View view) {
        Intent intent = new Intent(this, NewProductoActivity.class);
        startActivity(intent);
    }
}