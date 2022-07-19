package com.campones.listadetarefas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.campones.listadetarefas.helper.TarefaDAO;
import com.campones.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

            editTarefa = findViewById(R.id.textTarefa);
    }

    //Configurando o menu
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate( R.menu.menu_adicionar_tarefa, menu );
        return super.onCreateOptionsMenu(menu);
    }

    //Verificando o click no item do menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar:
                //Executa ação para salvar
                TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext() );

                String nomeTarefa = editTarefa.getText().toString();
                if( !nomeTarefa.isEmpty() ){
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa( nomeTarefa );
                    tarefaDAO.salvar( tarefa );
                    finish();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}