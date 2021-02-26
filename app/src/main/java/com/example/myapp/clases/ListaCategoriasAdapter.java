package com.example.myapp.clases;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.List;

public class ListaCategoriasAdapter extends RecyclerView.Adapter<CategoriasViewHolder> {

    private Context c;
    private List<Categoria> listaCategorias;
    private LayoutInflater mInflater;



    public ListaCategoriasAdapter(Context c, List<Categoria> listaCategorias) {
        this.c = c;
        this.listaCategorias = listaCategorias;
        mInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public CategoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_rv_categorias, parent, false);
        return new CategoriasViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriasViewHolder holder, int position) {
        if(listaCategorias!=null){
            Categoria categoriaActual = listaCategorias.get(position);
            holder.txt_rv_nombrec.setText(categoriaActual.getNombre());
        }
    }

    @Override
    public int getItemCount() {
            return listaCategorias.size();
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
        if(this.listaCategorias == null){
            Log.i("categorias","La lista categorias es nulo");
        }else{
            for(Categoria c:listaCategorias){
                Log.i("categorias","categoria: "+c.getNombre());
            }
        }
        notifyDataSetChanged();
    }
}
