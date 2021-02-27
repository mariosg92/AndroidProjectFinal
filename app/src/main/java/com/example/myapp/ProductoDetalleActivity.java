package com.example.myapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.clases.Categoria;
import com.example.myapp.clases.FotoProducto;
import com.example.myapp.clases.Producto;
import com.example.myapp.clases.ProductosViewHolder;
import com.example.myapp.controladores.CategoriaController;
import com.example.myapp.utilidades.ImagenesBlobBitmap;

import java.util.ArrayList;

public class ProductoDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "com.example.ProductoDetalleActivity.nombre" ;
    public static final String EXTRA_MARCA = "com.example.ProductoDetalleActivity.marca" ;
    public static final String EXTRA_CATEGORIA = "com.example.ProductoDetalleActivity.categoria" ;
    public static final String EXTRA_GENERO = "com.example.ProductoDetalleActivity.genero" ;
    public static final String EXTRA_IMAGEN = "com.example.ProductoDetalleActivity.imagen" ;
    public static final String EXTRA_IDPRODUCTO = "com.example.ProductoDetalleActivity.idproducto";
    TextView txt_detalle_marca = null;
    TextView txt_detalle_nombre = null;
    ImageView img_detalle_producto = null;
    Producto p = null;
    byte[] imagen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);
        txt_detalle_marca = findViewById(R.id.txt_marca);
        txt_detalle_nombre = findViewById(R.id.txt_nombrep);
        img_detalle_producto = findViewById(R.id.img_producto);
        Intent intent = getIntent();
        if(intent != null){
            p = (Producto) intent.getSerializableExtra(ProductosViewHolder.EXTRA_OBJETO_PRODUCTOS);
            imagen = intent.getByteArrayExtra(ProductosViewHolder.EXTRA_IMAGEN_PRODUCTO);
            txt_detalle_marca.setText(p.getMarca());
            txt_detalle_nombre.setText(p.getNombre());
            img_detalle_producto.setImageBitmap(ImagenesBlobBitmap.bytes_to_bitmap(imagen));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            img_detalle_producto.setImageURI(path);
        }
    }

    public void modificarActivity(View view) {
        Intent intent = new Intent(this, NewProductoActivity.class);
        String marca = String.valueOf(txt_detalle_marca.getText());
        String nombre = String.valueOf(txt_detalle_nombre.getText());
        String genero = p.getGenero();
        int idproducto = p.getIdproducto();
        ArrayList<Categoria> categorias = CategoriaController.obtenerCategorias();
        String categoria = "";
        for(Categoria c: categorias){
            if(c.getIdcategoria() == p.getIdcategoria()){
                categoria = c.getNombre();
            }
        }
        intent.putExtra(EXTRA_NOMBRE, nombre);
        intent.putExtra(EXTRA_MARCA, marca);
        intent.putExtra(EXTRA_GENERO, genero);
        intent.putExtra(EXTRA_CATEGORIA, categoria);
        intent.putExtra(EXTRA_IMAGEN, imagen);
        intent.putExtra(EXTRA_IDPRODUCTO, idproducto);
        startActivity(intent);
    }
}
