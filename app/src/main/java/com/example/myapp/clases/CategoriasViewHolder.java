package com.example.myapp.clases;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.ListadoCategoriasActivity;
import com.example.myapp.ListadoProductosActivity;
import com.example.myapp.R;

import java.util.List;

import static com.example.myapp.MainActivity.EXTRA_GENERO;

public class CategoriasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String EXTRA_OBJETO_CATEGORIAS = "com.example.CategoriasViewHolder.objeto_categorias";
    public TextView txt_rv_nombrec = null;
    final ListaCategoriasAdapter lcAdapter;

    public CategoriasViewHolder(@NonNull View itemView, ListaCategoriasAdapter lcAdapter) {
        super(itemView);
        txt_rv_nombrec = (TextView) itemView.findViewById(R.id.txt_marca);
        this.lcAdapter = lcAdapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int mPosition = getAdapterPosition();
        List<Categoria> categorias = this.lcAdapter.getListaCategorias();
        Categoria categoria = categorias.get(mPosition);
        lcAdapter.notifyDataSetChanged();
        Intent intent = new Intent(lcAdapter.getC(), ListadoProductosActivity.class);
        intent.putExtra(EXTRA_OBJETO_CATEGORIAS, categoria);
        intent.putExtra(EXTRA_GENERO, ListadoCategoriasActivity.genero);
        lcAdapter.getC().startActivity(intent);
    }
}
