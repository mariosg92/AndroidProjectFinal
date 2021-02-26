package com.example.myapp.modelos;

import android.util.Log;

import com.example.myapp.clases.Categoria;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriaDB {

    public static ArrayList<Categoria> obtenerCategorias() {
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if(conexion == null)
        {
            return null;
        }
        ArrayList<Categoria> categoriasDevueltas = new ArrayList<Categoria>();
        try {
            Statement sentencia = conexion.createStatement();
            String ordenSQL = "select * from categorias";
            ResultSet resultado = sentencia.executeQuery(ordenSQL);
            while(resultado.next())
            {
                int idcategoria = resultado.getInt("idCategoria");
                String nombrec = resultado.getString("nombreCategoria");
                Categoria c = new Categoria(idcategoria, nombrec);
                categoriasDevueltas.add(c);
            }
            resultado.close();
            sentencia.close();
            conexion.close();
            return categoriasDevueltas;
        } catch (SQLException e) {
            Log.i("sql", "error sql");
            e.printStackTrace();
            return categoriasDevueltas;
        }
    }
}
