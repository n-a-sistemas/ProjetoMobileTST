package com.example.projetoapptst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetoapptst.adapter.Adapter;
import com.example.projetoapptst.modelos.Funcionario;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.auth.AuthUI;


import java.net.Authenticator;
import java.util.ArrayList;
import java.util.Arrays;
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
    private SharedPreferences sharedPreferences;
    private Funcionario func = new Funcionario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        String resultado = sharedPreferences.getString("LOGIN","");

        if (!Boolean.parseBoolean(resultado)) {

            login();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view_menu_inicial);

        editText = findViewById(R.id.edit_text_nome);

        conectarBanco();

       listView.invalidateViews();
        eventoBanco();




    }

    private void  conectarBanco(){
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void eventoBanco(){
        //Leitura do Banco
        databaseReference.child("projetotst").child("funcionario").addValueEventListener(new ValueEventListener() {
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


    public void login(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),123
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 ){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK){

                if (response.isNewUser()){
                    this.func.setUuid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    this.func.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    this.func.setValido(false);
                    databaseReference.child("projetotst")
                            .child("funcionario")
                            .child(func.getUuid())
                            .setValue(func);
                }
                sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("LOGIN", "true");
                editor.apply();
            }
            else {
                if (response == null){
                    finish();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();

        if (id==R.id.id_sair){
            sharedPreferences = getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("LOGIN","false");
            editor.apply();
            login();

        }
        return super.onOptionsItemSelected(item);
    }

}
