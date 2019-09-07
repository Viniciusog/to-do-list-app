package com.example.todolist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.helper.TarefaDAO;
import com.example.todolist.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        //Configurando titulo action bar
        getSupportActionBar().setTitle("Tarefas");

        editTarefa = findViewById(R.id.textTarefa);

        //Recuperar tarefa caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaselecionada");

        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
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


                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                //Editanto tarefa
                if (tarefaAtual != null) {

                    String nomeTarefa = editTarefa.getText().toString();
                    //Edita tarefa apenas se o nome não for vazio
                    if(!nomeTarefa.isEmpty()){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizando no banco de dados
                        if( tarefaDAO.atualizar(tarefa) ){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao editar Tarefa!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao editar Tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //Salvando tarefa
                else {
                    String nomeTarefa = editTarefa.getText().toString();
                    //Apenas adiciona a tarefa se o nome não for vazio
                    if (!nomeTarefa.isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if (tarefaDAO.salvar(tarefa)) {
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar Tarefa!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
