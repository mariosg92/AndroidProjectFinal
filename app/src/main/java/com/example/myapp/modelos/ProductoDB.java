package com.example.myapp.modelos;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.myapp.clases.Categoria;
import com.example.myapp.clases.FotoProducto;
import com.example.myapp.clases.Producto;
import com.example.myapp.utilidades.ImagenesBlobBitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductoDB {

    public static ArrayList<Producto> obtenerProductos(String genero, String categoria){
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if(conexion == null)
        {
            return null;
        }
        ArrayList<Producto> productosDevueltos = new ArrayList<Producto>();
        try {
            String ordenSQL = "select * from productos where genero like ? and idCategoria = (select idCategoria from categorias where nombreCategoria like ?)";
            PreparedStatement sentencia = conexion.prepareStatement(ordenSQL);
            sentencia.setString(1, genero);
            sentencia.setString(2, categoria);
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next())
            {
                int idproducto = resultado.getInt("idProducto");
                String nombrep = resultado.getString("nombreProducto");
                String generop = resultado.getString("genero");
                String marcap = resultado.getString("marca");
                int idcategoria = resultado.getInt("idCategoria");
                Producto p = new Producto(idproducto,nombrep,generop,marcap,idcategoria);
                productosDevueltos.add(p);
            }
            resultado.close();
            sentencia.close();
            conexion.close();
            return productosDevueltos;
        } catch (SQLException e) {
            Log.i("sql", "error sql");
            e.printStackTrace();
            return productosDevueltos;
        }
    }

    public static ArrayList<FotoProducto> obtenerFotosProductos(int width, int height) {
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if(conexion == null){
            return null;
        }
        ArrayList<FotoProducto> fotosProductosDevueltas = new ArrayList<FotoProducto>();
        try{
            Statement sentencia = conexion.createStatement();
            String ordensql = "select * from images";
            ResultSet rs = sentencia.executeQuery(ordensql);
            while(rs.next()){
                int idfoto = rs.getInt("idImage");
                Blob image = rs.getBlob("image");
                Bitmap bmImage = ImagenesBlobBitmap.blob_to_bitmap(image, width, height);
                int idproducto = rs.getInt("idProducto");
                FotoProducto fp = new FotoProducto(idfoto, bmImage, idproducto);
                fotosProductosDevueltas.add(fp);
            }
            rs.close();
            sentencia.close();
            conexion.close();
            return fotosProductosDevueltas;
        }catch(SQLException e){
            Log.i("sql","error sql");
            return null;
        }
    }

    public static boolean borrarProducto(Producto p) {
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if (conexion == null) {
            return false;
        }
        try {
            String ordensql = "DELETE FROM productos WHERE idProducto = ?;";
            PreparedStatement pst = conexion.prepareStatement(ordensql);
            pst.setInt(1, p.getIdproducto());
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            conexion.close();
            if (filasAfectadas > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean borrarFotoProducto(int idProducto){
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if (conexion == null) {
            return false;
        }
        try {
            String ordensql = "DELETE FROM images WHERE idProducto = ?;";
            PreparedStatement pst = conexion.prepareStatement(ordensql);
            pst.setInt(1, idProducto);
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            conexion.close();
            if (filasAfectadas > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertarProducto(Producto p) {
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if(conexion == null){
            return false;
        }
        try {
            String ordensql = "INSERT INTO productos (nombreProducto, genero, marca, idCategoria) VALUES (?, ?, ?, ?);";
            PreparedStatement pst = conexion.prepareStatement(ordensql);
            pst.setString(1, p.getNombre());
            pst.setString(2, p.getGenero());
            pst.setString(3, p.getMarca());
            pst.setInt(4,p.getIdcategoria());
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            conexion.close();
            if(filasAfectadas > 0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean insertarFotoProducto(FotoProducto fp, String nombre) {
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if(conexion == null){
            return false;
        }
        try {
            String ordensql = "INSERT INTO images (image, idProducto) VALUES (?, (SELECT idProducto FROM productos WHERE nombreProducto like ?));";
            PreparedStatement pst = conexion.prepareStatement(ordensql);
            Bitmap imagenBlob = fp.getFoto();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagenBlob.compress(Bitmap.CompressFormat.PNG,0,baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            pst.setBinaryStream(1,bais);
            pst.setString(2,nombre);
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            conexion.close();
            if(filasAfectadas > 0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean actualizarProducto(Producto p) {
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if(conexion == null){
            return false;
        }
        try{
            String ordensql = "UPDATE productos SET nombreProducto = ?, genero = ?, marca = ?, idCategoria = ? WHERE idProducto = ?;";
            PreparedStatement pst = conexion.prepareStatement(ordensql);
            pst.setString(1, p.getNombre());
            pst.setString(2, p.getGenero());
            pst.setString(3, p.getMarca());
            pst.setInt(4, p.getIdcategoria());
            pst.setInt(5, p.getIdproducto());
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            conexion.close();
            if(filasAfectadas > 0){
                return true;
            }else{
                return false;
            }


        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean actualizarFotoProducto(FotoProducto fp, int idProducto) {
        Connection conexion = BaseDB.conectarConBaseDeDatos();
        if(conexion == null){
            return false;
        }
        try{
            String ordensql = "UPDATE images SET image = ? WHERE idProducto = ?;";
            PreparedStatement pst = conexion.prepareStatement(ordensql);
            Bitmap imagenBlob = fp.getFoto();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagenBlob.compress(Bitmap.CompressFormat.PNG,0,baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            pst.setBinaryStream(1,bais);
            pst.setInt(2, idProducto);
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            conexion.close();
            if(filasAfectadas > 0){
                return true;
            }else{
                return false;
            }


        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
