package com.example.todolist.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.R;
import com.example.todolist.adapter.TarefaAdapter;
import com.example.todolist.helper.RecyclerItemClickListener;
import com.example.todolist.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                                Toast.makeText(getApplicationContext(), "ItemClick", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Toast.makeText(getApplicationContext(), "LongItemClick", Toast.LENGTH_LONG).show();
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
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setNomeTarefa("Tarefa de inglês");
        listaTarefas.add(tarefa1);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setNomeTarefa("Concluir projeto de programação");
        listaTarefas.add(tarefa2);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
