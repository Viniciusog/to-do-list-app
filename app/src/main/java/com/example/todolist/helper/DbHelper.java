package com.example.todolist.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    //É chamado apenas uma vez quando o usuário instalar o aplicativo
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " nome TEXT NOT NULL ); ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar a tabela " + e.getMessage());
        }
    }

    //É usado mais de uma vez quando é necessário atualizar o aplicativo
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + " ;";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar app");
        } catch (Exception e) {
            Log.i("INFO BR", "Erro ao atualizar app" + e.getMessage());
        }
    }
}