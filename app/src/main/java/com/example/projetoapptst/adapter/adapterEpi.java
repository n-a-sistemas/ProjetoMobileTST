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
import com.example.projetoapptst.modelos.Epi;

import java.util.ArrayList;
import java.util.List;

public class adapterEpi extends ArrayAdapter<Epi> {
    private Context context;
    private List<Epi> epis;

    public adapterEpi(android.content.Context context, ArrayList<Epi> epis){
        super(context,0,epis);
        this.context = context;
        this.epis = epis;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View epiitem = convertView;


        if (epiitem == null) {
            epiitem = LayoutInflater.from(context)
                    .inflate(R.layout.epi_pequisa,parent,false);

        }
        Epi epipego = epis.get(position);

        TextView nome = epiitem.findViewById(R.id.text_view_nome);
        nome.setText(epipego.getNome());
        TextView validaCA = epiitem.findViewById(R.id.text_view_dataCA);
        validaCA.setText(epipego.getValidaCA());
        TextView validade = epiitem.findViewById(R.id.text_view_validade);
        validade.setText(epipego.getValidade());




        return epiitem;
    }
}
