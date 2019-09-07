package com.example.todolist.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.R;
import com.example.todolist.adapter.TarefaAdapter;
import com.example.todolist.helper.DbHelper;
import com.example.todolist.helper.RecyclerItemClickListener;
import com.example.todolist.helper.TarefaDAO;
import com.example.todolist.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tarefas");

        //Configurar Recycler
        recyclerView = findViewById(R.id.recyclerView);

        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {

                                //Recuperar uma tarefa selecionada para edição
                                Tarefa tarefaSelecionada = listaTarefas.get(position);

                                //Envia tarefa para a tela adicionar tarefa
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaselecionada", tarefaSelecionada);

                                startActivity(intent);
                            }

                            //Método responsável por deletar uma tarefa
                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recupera a tarefa selecionada para deletar
                                tarefaSelecionada = listaTarefas.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //Configura titulo e mensagem do alert dialog
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + " ?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                        if (tarefaDAO.deletar(tarefaSelecionada)) {

                                            //Recarrega a lista novamente
                                            carregarListaDeTarefas();
                                            Toast.makeText(getApplicationContext(),
                                                    "Tarefa excluída com sucesso!",
                                                    Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Erro ao exluir a tarefa!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibir dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        })
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }


    public void carregarListaDeTarefas() {

        //Listar Tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();

        /*
            Exibe lista de tarefas
         */

        //Configura um adapter
        tarefaAdapter = new TarefaAdapter(listaTarefas);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tarefaAdapter);

    }

    @Override
    protected void onStart() {
        carregarListaDeTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itemColors) {
            Toast.makeText(getApplicationContext(), "Item click", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
