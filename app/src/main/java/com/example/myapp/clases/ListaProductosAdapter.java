package com.example.myapp.clases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListaProductosAdapter extends RecyclerView.Adapter<ProductosViewHolder> {

    private Context c;
    private List<Producto> listaProductos;
    private ArrayList<FotoProducto> listaImagenes;
    private LayoutInflater mInflater;



    public ListaProductosAdapter(Context c, List<Producto> listaProductos, ArrayList<FotoProducto> listaImagenes) {
        this.c = c;
        this.listaProductos = listaProductos;
        this.listaImagenes = listaImagenes;
        mInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_rv_productos, parent, false);
        return new ProductosViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
        if(listaProductos!=null){
            Producto productoActual = listaProductos.get(position);
            holder.txt_rv_nombrep.setText(productoActual.getNombre());
            holder.txt_rv_marca.setText(productoActual.getMarca());
            if(this.listaImagenes != null){
                for(FotoProducto fp: this.listaImagenes){
                    if(fp.getIdproducto() == productoActual.getIdproducto()){
                        holder.img_producto.setImageBitmap(fp.getFoto());
                        break;
                    }else{
                        Bitmap noImage = BitmapFactory.decodeResource(c.getResources(),R.drawable.noimage);
                        holder.img_producto.setImageBitmap(noImage);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public ArrayList<FotoProducto> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(ArrayList<FotoProducto> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        if(this.listaProductos == null){
            Log.i("productos","La lista productos es nulo");
        }else{
            for(Producto p: listaProductos){
                Log.i("productos","producto: "+p.getNombre());
            }
        }
        notifyDataSetChanged();
    }
}
