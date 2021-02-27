package com.example.myapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.myapp.clases.Categoria;
import com.example.myapp.clases.FotoProducto;
import com.example.myapp.clases.Producto;
import com.example.myapp.controladores.CategoriaController;
import com.example.myapp.controladores.FotoProductoController;
import com.example.myapp.controladores.ProductoController;
import com.example.myapp.modelos.BaseDB;
import com.example.myapp.modelos.ProductoDB;
import com.example.myapp.utilidades.ImagenesBlobBitmap;

import java.io.IOException;
import java.util.ArrayList;


public class NewProductoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edt_nombre_producto = null;
    EditText edt_marca = null;
    Spinner sp_categoria = null;
    Spinner sp_genero = null;
    ArrayList<Categoria> categorias = null;
    String[] generos = {"Hombre", "Mujer"};
    ArrayAdapter<Categoria> adapterCategorias = null;
    ArrayAdapter<String> adapterGenero = null;
    Categoria cseleccionada = null;
    String gseleccionado = null;
    ImageView imagen = null;
    Bitmap bitmap = null;
    int idProducto = 0;
    boolean modify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_producto);
        edt_nombre_producto = findViewById(R.id.edt_nombre);
        edt_marca = findViewById(R.id.edt_marca);
        sp_categoria = findViewById(R.id.spinner_categoria);
        sp_genero = findViewById(R.id.spinner_genero);
        imagen = findViewById(R.id.imgView);
        if (sp_categoria != null) {
            sp_categoria.setOnItemSelectedListener(this);
            categorias = CategoriaController.obtenerCategorias();
            if (categorias != null) {
                adapterCategorias = new ArrayAdapter<Categoria>(this, R.layout.spinner_categoria, categorias);
                sp_categoria.setAdapter(adapterCategorias);
            }
        }
        if (sp_genero != null) {
            sp_genero.setOnItemSelectedListener(this);
            if (generos != null) {
                adapterGenero = new ArrayAdapter<String>(this, R.layout.spinner_categoria, generos);
                sp_genero.setAdapter(adapterGenero);
            }
        }
        Intent intent = getIntent();
        if(intent.getStringExtra(ProductoDetalleActivity.EXTRA_NOMBRE) != null){
            edt_nombre_producto.setText(intent.getStringExtra(ProductoDetalleActivity.EXTRA_NOMBRE));
            edt_marca.setText(intent.getStringExtra(ProductoDetalleActivity.EXTRA_MARCA));
            String genero = intent.getStringExtra(ProductoDetalleActivity.EXTRA_GENERO);
            String categoria = intent.getStringExtra(ProductoDetalleActivity.EXTRA_CATEGORIA);
            byte[] img = intent.getByteArrayExtra(ProductoDetalleActivity.EXTRA_IMAGEN);
            idProducto = intent.getIntExtra(ProductoDetalleActivity.EXTRA_IDPRODUCTO,0);
            sp_categoria.setSelection(getIndex(sp_categoria, categoria));
            sp_genero.setSelection(getIndex(sp_genero, genero));
            imagen.setImageBitmap(ImagenesBlobBitmap.bytes_to_bitmap(img));
            modify = true;
        }
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_categoria) {
            Categoria categoria = (Categoria) sp_categoria.getItemAtPosition(position);
            cseleccionada = categoria;
        }else if(parent.getId() == R.id.spinner_genero){
            String genero = (String) sp_genero.getItemAtPosition(position);
            gseleccionado = genero;
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void exitActivity(View view) {
        finish();
    }

    public void insertarProducto(View view) {

        Producto p = new Producto();
        FotoProducto fp = new FotoProducto();
        p.setNombre(String.valueOf(edt_nombre_producto.getText()));
        p.setMarca(String.valueOf(edt_marca.getText()));
        p.setGenero(gseleccionado);
        p.setIdcategoria(cseleccionada.getIdcategoria());
        if (bitmap != null) {
            fp.setFoto(bitmap);
        } else {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.noimage);
            fp.setFoto(bitmap);
        }
        if(!modify) {
            ProductoController.insertarProducto(p);
            FotoProductoController.insertarFoto(fp,p.getNombre());
        }
        if(modify){
            p.setIdproducto(idProducto);
            fp.setFoto(bitmap);
            ProductoController.actualizarProducto(p);
            FotoProductoController.actualizarFoto(fp,p.getIdproducto());
        }
    }

    public void cargarImagen(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecciona la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void confirmacion(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        if(modify){
            alerta.setTitle("Desea modificar este Producto?");
        }else{
            alerta.setTitle("Desea añadir este Producto?");
        }
        alerta.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertarProducto(view);
                principal();
            }
        });
        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alerta.show();
    }

    public void principal(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}