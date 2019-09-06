package com.example.todolist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todolist.model.Tarefa;

import java.util.List;

public class TarefaDAO implements iTarefaDAO {

    //Atributo utilizado para gravar dados em uma tabela
    private SQLiteDatabase escreve;
    //Atributo utilizado para ler dados de uma tabela
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        try {
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO", "Sucesso ao salvar tarefa!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar tarefa!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        return false;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        return false;
    }

    @Override
    public List<Tarefa> listar() {
        return null;
    }
}