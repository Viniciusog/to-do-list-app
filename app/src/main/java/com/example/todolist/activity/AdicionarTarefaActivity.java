package com.example.todolist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.helper.TarefaDAO;
import com.example.todolist.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar: {
                //Executa ação para salvar o item
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                String nomeTarefa = editTarefa.getText().toString();

                //Apenas adiciona a tarefa se o nome não for vazio
                if (!nomeTarefa.isEmpty()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(nomeTarefa);
                    tarefaDAO.salvar(tarefa);
                    finish();
                }

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
