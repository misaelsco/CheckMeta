package com.cadetech.checkmeta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.cadetech.checkmeta.dominio.Meta;

import java.util.ArrayList;

/**
 * Created by misael.correia on 23/09/2016.
 */
public class MetaDAO {

    private SQLiteHelper helper;
    private SQLiteDatabase db;
    private static final String TABLE = "meta";

    public MetaDAO (Context context) {
        helper = new SQLiteHelper(context);
    }


    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public long insert(Meta meta){
        open();
        Long insertedRow = Long.valueOf(-1);

        ContentValues values = new ContentValues();
        values.put("idUsuario", meta.getIdUsuario());
        values.put("titulo", meta.getTitulo());
        values.put("descricao", meta.getDescricao());
        values.put("dataDesejada", meta.getDataDesejada());
        values.put("status", meta.getStatus());

        try{
            insertedRow = db.insert(TABLE,null,values);
        }catch (SQLiteException ex){
            close();
        }
        close();
        return insertedRow;
    }


    public ArrayList<Meta> selectAll(){
        ArrayList<Meta> metas = new ArrayList<>();

        open();
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE + " WHERE idUsuario = " + 1, null);

            if(c.getCount() > 0) {
                c.moveToFirst();
                Meta retrievedMeta;
                do {
                    retrievedMeta = new Meta();
                    retrievedMeta.setId(c.getLong(c.getColumnIndex("id")));
                    retrievedMeta.setIdUsuario(c.getLong(c.getColumnIndex("idUsuario")));
                    retrievedMeta.setTitulo(c.getString(c.getColumnIndex("titulo")));
                    retrievedMeta.setDescricao(c.getString(c.getColumnIndex("descricao")));
                    retrievedMeta.setDataDesejada(c.getString(c.getColumnIndex("dataDesejada")));
                    retrievedMeta.setStatus(c.getString(c.getColumnIndex("status")));
                    Log.i("[META] ", retrievedMeta.toString());
                    metas.add(retrievedMeta);
                }
                while (c.moveToNext());
                c.close();
            }
            else {
                c.close();
            }
        }
        catch (SQLiteException ex){
            Log.d("LogError", "Erro ao buscar metas [" + ex.toString() + "]");
            close();
        }
        close();
        return metas;
    }

    public Meta select(Long idMeta){
        Meta meta = new Meta();

        open();
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE + " WHERE id = " + idMeta, null);

            if(c.getCount() > 0) {
                c.moveToFirst();

                meta = new Meta();
                meta.setId(c.getLong(c.getColumnIndex("id")));
                meta.setTitulo(c.getString(c.getColumnIndex("titulo")));
                meta.setDescricao(c.getString(c.getColumnIndex("descricao")));
                meta.setDataDesejada(c.getString(c.getColumnIndex("dataDesejada")));
                meta.setStatus(c.getString(c.getColumnIndex("status")));
                meta.setDataRealizada(c.getString(c.getColumnIndex("dataRealizada")));

                c.close();
            }
            else {
                c.close();
            }
        }
        catch (SQLiteException ex){
            Log.d("LogError", "Erro ao buscar meta de id = " + idMeta + "[" + ex.toString() + "]");
            close();
        }
        close();
        return meta;
    }

    public boolean update(Meta meta){

        open();

        ContentValues values = new ContentValues();
        values.put("titulo", meta.getTitulo());
        values.put("descricao", meta.getDescricao());
        values.put("dataDesejada", meta.getDataDesejada());
        values.put("status", meta.getStatus());
        values.put("dataRealizada", meta.getDataRealizada());

        try {
            int update = db.update(TABLE, values, "id=" + meta.getId().toString(), null);
            Log.d("DEBUG", "Retorno do update: " + update);
            close();
            return true;
        }catch (Exception e){
            Log.d("ERROR", "Erro ao atualizar meta [" + e.toString() + "]");
            close();
        }
        close();
        return false;
    }

    public boolean delete(String idMeta) {
        open();
        try {
            int delete = db.delete(TABLE, "id=" + idMeta, null);
            Log.d("DEBUG", "Retorno do delete: " + delete);
            close();
            return true;
        }catch (Exception e){
            Log.d("ERROR", "Erro ao excluir meta [" + e.toString() + "]");
            close();
        }
        close();
        return false;


    }
}
