package com.example.projetoapptst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetoapptst.adapter.Adapter;
import com.example.projetoapptst.adapter.adapterEpi;
import com.example.projetoapptst.modelos.Epi;
import com.example.projetoapptst.modelos.Funcionario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EpiActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Epi> epis = new ArrayList<Epi>();
    private ArrayAdapter<Epi> arrayAdapterEpi;
    private ListView listView;
    private List<Epi> novosEpis = new ArrayList<Epi>();
    private Funcionario funcionario = new Funcionario();
    private Epi epii = new Epi();
    String ID;


    private EditText editTextnome, editTextvalidadeCA,editTextvalidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epi);

        editTextnome = findViewById(R.id.edit_text_epi_nome);
        editTextvalidade= findViewById(R.id.edit_text_validade);
        editTextvalidadeCA= findViewById(R.id.edit_text_validadeCA);
        listView = findViewById(R.id.list_epi);
        conectarBanco();
        pesquisaEpi();
        listView.invalidateViews();
    }


    private void conectarBanco() {
        FirebaseApp.initializeApp(EpiActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void pesquisaEpi(){
        final String id = getIntent().getStringExtra(FuncionariosActivity.ID);

        databaseReference.child("projetotst").child("funcionario")
                .child(id).child("Epi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                epis.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    epii = snapshot.getValue(Epi.class);

                    epis.add(epii);
                }

                arrayAdapterEpi = new adapterEpi(EpiActivity.this,
                        (ArrayList<Epi>) epis);
                listView.setAdapter(arrayAdapterEpi);
                ID=id;
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick
                            (AdapterView<?> adapterView, View view, int i, long l) {
                        excluirDado(epis.get(i));


                        return false;

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void adicionarEpi(View v){
        Epi eps = new Epi();
        eps.setUuid(UUID.randomUUID().toString());
        eps.setNome(editTextnome.getText().toString());
        eps.setValidadeCA(editTextvalidadeCA.getText().toString());
        eps.setValidade(editTextvalidade.getText().toString());
        databaseReference.child("projetotst").child("funcionario")
                .child(ID).child("Epi").child(eps.getUuid())
                .setValue(eps);


    }

    public void excluirDado(final Epi epi){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.msg_box);
        builder.setMessage("Você deseja mesmo remover este Epi?");
        builder.setIcon(R.drawable.delete);
        builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("projetotst").child("funcionario")
                        .child(ID).child("Epi").child(epi.getUuid()).removeValue();

            }
        });
        builder.setNegativeButton("nao", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
