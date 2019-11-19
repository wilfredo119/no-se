package com.example.crud_receta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "prueba", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table datos(Nombre text, ingredientes text, categoria text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public String guardar(String nombre, String ingredientes, String categoria) {
        String mensaje = "";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", nombre);
        contenedor.put("ingredientes", ingredientes);
        contenedor.put("categoria", categoria);
        try {
            database.insertOrThrow("datos", null, contenedor);
            mensaje = "Ingresado Correctamente";

        } catch (SQLException e) {
            mensaje = "Error, al Ingresar";
        }
        return mensaje;
    }

    public String[] buscar_reg(String buscar) {
        String[] datos = new String[4];
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM datos WHERE nombre= '" + buscar + "'";
        Cursor registros = database.rawQuery(q, null);

        if (registros.moveToFirst()) {
            for (int i = 0; i < 3; i++){

                datos[i] = registros.getString(i);


            }
            datos[3] = "encontrados " + buscar;


        }else{

            datos [3] = "no se encontro "+ buscar;
        }
        return datos;
}
public  String eliminar(String nombre){
        String mensaje = "";
        SQLiteDatabase database = this.getWritableDatabase();
        int cantidad = database.delete("datos","nombre= '" + nombre +"'",null);
        if (cantidad !=0){
            mensaje= "Eliminado Correctamente";

        }else {
            mensaje =  "No Existe";
        }

        return mensaje;
    }
}
