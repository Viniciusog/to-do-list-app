package com.example.todolist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todolist.model.Tarefa;

import java.util.ArrayList;
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

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            //O primeiro valor do array de string (id) será usado para
            //substituir a interrogação encontrada no método de update
            String[] args = {tarefa.getId().toString()};

            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO", "Sucesso ao salvar tarefa!");

        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar tarefa!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        return false;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " +
                DbHelper.TABELA_TAREFAS + " ;";

        Cursor c = le.rawQuery(sql, null);



        while (c.moveToNext()) {
            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
        }
        return tarefas;
    }
}