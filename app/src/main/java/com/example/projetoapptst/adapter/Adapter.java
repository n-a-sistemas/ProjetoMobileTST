package com.example.projetoapptst.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetoapptst.R;
import com.example.projetoapptst.modelos.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Funcionario> {

    private Context context;
    private List<Funcionario> funcionarios;

    public Adapter(Context context, ArrayList<Funcionario> funcionarios){
        super(context,0,funcionarios);
        this.context = context;
        this.funcionarios = funcionarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View funcionarioItem = convertView;


        if (funcionarioItem == null) {
            funcionarioItem = LayoutInflater.from(context)
                    .inflate(R.layout.layout_pesquisa_funcionario, parent, false);
        }
        Funcionario funcionarioPesquisado = funcionarios.get(position);


            TextView nome = funcionarioItem.findViewById(R.id.text_view_nome);
            nome.setText(funcionarioPesquisado.getNome());

            TextView profissao = funcionarioItem.findViewById(R.id.textView2);
            profissao.setText(funcionarioPesquisado.getProfissao());






        return funcionarioItem;

    }





}
