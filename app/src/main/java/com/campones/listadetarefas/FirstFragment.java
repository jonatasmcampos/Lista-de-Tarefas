package com.campones.listadetarefas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campones.listadetarefas.adapter.TarefaAdapter;
import com.campones.listadetarefas.databinding.FragmentFirstBinding;
import com.campones.listadetarefas.helper.RecyclerItemClickListener;
import com.campones.listadetarefas.helper.TarefaDAO;
import com.campones.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        //recyclerView
        recyclerView = binding.recyclerView;

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Recuperar tarefa para edi????o
                                Tarefa tarefaSelecionada = listaTarefas.get(position);

                                //Enviar tarefa para tela adicionar tarefa
                                Intent intent = new Intent( getContext(), AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                                startActivity( intent );
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recuperar tarefa para deletar
                                tarefaSelecionada = listaTarefas.get( position );

                                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                                //Configura o titulo e mensagem
                                dialog.setTitle("Excluir");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + "?");

                                //Configurar os botoes
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        TarefaDAO tarefaDAO = new TarefaDAO( getContext() );
                                        if( tarefaDAO.deletar( tarefaSelecionada ) ){

                                            carregarListaTarefas();
                                            Toast.makeText(getContext(),
                                                    "Sucesso ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(),
                                                    "Erro ao excluir tarefa!",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                dialog.setNegativeButton("N??o", null);

                                //Exibir dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
        return binding.getRoot();
    }

    public void carregarListaTarefas(){
        //Listar tarefas
        TarefaDAO tarefaDAO = new TarefaDAO( getActivity() );
        listaTarefas = tarefaDAO.listar();

        //Configurar um adapter
        tarefaAdapter = new TarefaAdapter( listaTarefas );

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarListaTarefas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}