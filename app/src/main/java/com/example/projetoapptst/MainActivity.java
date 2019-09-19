package com.example.projetoapptst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetoapptst.adapter.Adapter;
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

public class MainActivity extends AppCompatActivity {

    public static final String TITULO = "com.example.projetoapptst.TITULO";


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
    private ArrayAdapter<Funcionario> arrayAdapterTarefa;
    private ListView listView;
    private EditText editText;
    Integer id = 2;
    private Integer pontus = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view_menu_inicial);

        editText = findViewById(R.id.edit_text_nome);

        conectarBanco();
       listView.invalidateViews();
        eventoBanco();
        //salvarfuncionario();



    }
    private void  conectarBanco(){
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void eventoBanco(){
        //Leitura do Banco
        databaseReference.child("projetotst").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            funcionarios.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Funcionario funcionario = snapshot.getValue(Funcionario.class);
                funcionarios.add(funcionario);

            }
            arrayAdapterTarefa = new Adapter(MainActivity.this,
                    (ArrayList<Funcionario>)funcionarios);

            listView.setAdapter(arrayAdapterTarefa);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Funcionario fun = (Funcionario) listView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), FuncionariosActivity.class);
                intent.putExtra(TITULO,fun.getUuid());
                startActivity(intent);

                }
            });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void  salvarfuncionario(){
        Funcionario funcionario = new Funcionario("1","Yan","pedreiro"
                ,"https://image.flaticon.com/icons/png/512/38/38002.png" );
        databaseReference.child("projetotst")
                .child(funcionario.getUuid())
                .setValue(funcionario);





    }



    public  void  media(View view){

        for (int i =0; i<id; i++) {

            databaseReference.child("projetotst")
                    .child(id.toString()).child("pontos")
                    .setValue(pontus +100);

        }

    }





}
