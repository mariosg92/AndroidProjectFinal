package com.example.myapp.clases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.ListadoProductosActivity;
import com.example.myapp.ProductoDetalleActivity;
import com.example.myapp.R;
import com.example.myapp.utilidades.ImagenesBlobBitmap;

import java.util.ArrayList;
import java.util.List;



public class ProductosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public static final String EXTRA_OBJETO_PRODUCTOS = "com.example.ProductosViewHolder.objeto_productos";
    public static final String EXTRA_IMAGEN_PRODUCTO = "com.example.ProductosViewHolder.imagen_producto";
    public TextView txt_rv_nombrep = null;
    public TextView txt_rv_marca = null;
    public ImageView img_producto = null;
    final ListaProductosAdapter lpAdapter;

    public ProductosViewHolder(@NonNull View itemView, ListaProductosAdapter lpAdapter) {
        super(itemView);
        txt_rv_marca = (TextView) itemView.findViewById(R.id.txt_marca);
        txt_rv_nombrep = (TextView) itemView.findViewById(R.id.txt_nombrep);
        img_producto = (ImageView) itemView.findViewById(R.id.img_producto);
        this.lpAdapter = lpAdapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int mPosition = getAdapterPosition();
        List<Producto> productos = this.lpAdapter.getListaProductos();
        ArrayList<FotoProducto> fotos = this.lpAdapter.getListaImagenes();
        Bitmap foto = null;
        byte[] fotoBytes;
        Producto producto = productos.get(mPosition);
        for(FotoProducto fp: fotos) {
            if (fp.getIdproducto() == producto.getIdproducto()) {
                foto = fp.getFoto();
                break;
            }else{
                foto = BitmapFactory.decodeResource(lpAdapter.getC().getResources(),R.drawable.noimage);
            }
        }
        fotoBytes = ImagenesBlobBitmap.bitmap_to_bytes(foto);
        lpAdapter.notifyDataSetChanged();
        Intent intent = new Intent(lpAdapter.getC(), ProductoDetalleActivity.class);
        intent.putExtra(EXTRA_OBJETO_PRODUCTOS, producto);
        intent.putExtra(EXTRA_IMAGEN_PRODUCTO, fotoBytes);
        lpAdapter.getC().startActivity(intent);
    }


    @Override
    public boolean onLongClick(View v) {

        return false;
    }
}
