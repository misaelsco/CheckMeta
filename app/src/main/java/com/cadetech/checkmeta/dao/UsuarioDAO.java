package com.cadetech.checkmeta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.cadetech.checkmeta.dominio.Usuario;

/**
 * Created by misael.correia on 21/09/2016.
 */
public class UsuarioDAO {

    private SQLiteHelper helper;
    private SQLiteDatabase db;
    private static final String TABLE_USER = "usuario";

    public UsuarioDAO(){

    }
    public UsuarioDAO(Context context){
        helper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    //Transforma uma conta em um mapa (ContentValues) e
    //usa o recurso da classe db (metodo insert)
    public Long insert(Usuario usuario){
        open();
        Long insertedId = Long.valueOf(-1);

        ContentValues values = new ContentValues();
        values.put("nome",usuario.getNome());
        values.put("senha",usuario.getSenha());
        values.put("email", usuario.getEmail());

        try{
            insertedId = db.insert(TABLE_USER,null,values);
        }catch (SQLiteException ex){
            close();
        }
        close();
        return insertedId;
    }

    public Usuario  isValidCredentials(String email, String senha)
    {
        Usuario usuario = null;
        open();
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE email = '" + email + "' AND senha = '" + senha + "'", null);

            if(c.getCount() > 0) {
                usuario = new Usuario();
                c.moveToFirst();
                usuario.setId(c.getLong(c.getColumnIndex("id")));
                usuario.setNome(c.getString(c.getColumnIndex("nome")));
                usuario.setEmail(c.getString(c.getColumnIndex("email")));
                usuario.setSenha(c.getString(c.getColumnIndex("senha")));
                c.close();
            }
            else {
                c.close();
            }
        }
        catch (SQLiteException ex){
            Log.d("LogError", ex.toString());
            close();
        }
        close();
        return usuario;
    }


    public boolean isDuplicated(String email) {
        open();
        try {
            Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USER + " WHERE email = '" + email + "'", null);
            c.moveToFirst();
            if (c.getInt(0) == 0) {
                return false;
            }
        } catch (SQLiteException ex) {
            Log.d("LogError", "Email '" + email + "' é duplicado [" + ex.toString() + "]");
        }
        return true;
    }
}
