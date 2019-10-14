package com.example.projetoapptst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetoapptst.modelos.Funcionario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FuncionariosActivity extends AppCompatActivity {
    public static final String ID = "com.example.projetoapptst.ID";


    TextView textView, textView2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ImageView imageView;
    CheckBox checkBoxBonificacao1,
            checkBoxBonificacao2, checkBoxinfra1,checkBoxinfra2, checkBoxinfra3;

    private Funcionario funcionario = new Funcionario();


    Integer ponto = 0;
    Integer pontoaTual;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);
        conectarBanco();
        pesquisa();


        checkBoxBonificacao1 = findViewById(R.id.check_box_bonificacao1);
        checkBoxBonificacao2 = findViewById(R.id.check_box_bonificacao2);
        checkBoxinfra1 = findViewById(R.id.check_box_infracao1);
        checkBoxinfra2 = findViewById(R.id.checkBox_infracao2);
        checkBoxinfra3 = findViewById(R.id.check_box_infracao3);

       // checkbox();
    }


    public void conectarBanco() {
        FirebaseApp.initializeApp(FuncionariosActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public void pesquisa() {

        final String titulo = getIntent().getStringExtra(MainActivity.TITULO);


        databaseReference.child("projetotst").child("funcionario")
                .child(titulo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                textView = findViewById(R.id.textView_1);
                textView2 = findViewById(R.id.textView2);

                String nome = (dataSnapshot.child("nome").getValue().toString());
                String profissao = (dataSnapshot.child("profissao").getValue().toString());


                textView.setText(nome);
                textView2.setText(profissao);

                ImageView imageView = findViewById(R.id.imageView_1);
                Picasso.get().load(dataSnapshot.child("imgScr")
                        .getValue().toString())
                        .resize(120, 120).centerCrop().into(imageView);

                pontoaTual = Integer.parseInt(dataSnapshot.child("pontos").getValue().toString());

                id = titulo;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void retirar(View view) {
        if (checkBoxBonificacao1.isChecked() ) {
            ponto = 50;
            confirmar(ponto);



        }
        if (checkBoxBonificacao2.isChecked()){
            ponto = 100;
          confirmar(ponto);
        }
        if (checkBoxinfra1.isChecked()||checkBoxinfra2.isChecked()||checkBoxinfra3.isChecked()){
            ponto = 25;
            retirar(ponto);

        }

    }

    public void abrirEpi(View view){
        Intent intent = new Intent(FuncionariosActivity.this,EpiActivity.class);
        intent.putExtra(ID, id);
        startActivity(intent);
    }

    public void confirmar(final Integer ponto){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.bonificacao1);
        builder.setMessage("Você está prestes a atribuir " + this.ponto + " pontos, deseja prosseguir?");
        builder.setIcon(R.drawable.rules);
        builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                pontoaTual = pontoaTual + ponto;

                databaseReference.child("projetotst")
                        .child("funcionario")
                        .child(id).child("pontos")
                        .setValue(pontoaTual.toString());



            }
        });  builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void retirar(final Integer ponto){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.infracao1);
        builder.setMessage("Você está prestes a retirar " + this.ponto + " pontos, deseja prosseguir?");
        builder.setIcon(R.drawable.rules);
        builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                pontoaTual = pontoaTual - ponto;

                databaseReference.child("projetotst")
                        .child("funcionario")
                        .child(id).child("pontos")
                        .setValue(pontoaTual.toString());



            }
        });  builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



}
