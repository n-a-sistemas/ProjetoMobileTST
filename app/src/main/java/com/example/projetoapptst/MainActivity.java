package com.example.projetoapptst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    private TextView textView;
    private Integer pontos;
    private Integer pontosAtual;
    private String data;
    private String dataAtual;
    private Button btn;
    private Boolean validar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectarBanco();

        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        String resultado = sharedPreferences.getString("LOGIN", "");

        if (!Boolean.parseBoolean(resultado)) {

            login();
        }


        btn= findViewById(R.id.btn_ponto);

        textView = findViewById(R.id.textView_test);

        listView = findViewById(R.id.list_view_menu_inicial);

        editText = findViewById(R.id.edit_text_nome);



        DataAtual();
        listView.invalidateViews();
        eventoBanco();
        eventoData();
        adm();



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                eventoBanco();
            }
        });

    }

    private void conectarBanco() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void eventoBanco() {
        //Leitura do Banco

        databaseReference.child("projetotst").child("funcionario")
                .orderByChild("nome")
                .startAt(editText.getText().toString())
                .endAt(editText.getText().toString() + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        funcionarios.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Funcionario funcionario = snapshot.getValue(Funcionario.class);
                            funcionarios.add(funcionario);
                        }
                        arrayAdapterTarefa = new Adapter(MainActivity.this,
                                (ArrayList<Funcionario>) funcionarios);

                        listView.setAdapter(arrayAdapterTarefa);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Funcionario fun = (Funcionario) listView.getItemAtPosition(i);
                                Intent intent = new Intent(getApplicationContext(), FuncionariosActivity.class);
                                intent.putExtra(TITULO, fun.getUuid());
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    public void login() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), 123
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {

                if (response.isNewUser()) {
                    this.func.setUuid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    this.func.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    this.func.setNome(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    this.func.setValido("false");
                    this.func.setPontos("0");
                    this.func.setImgScr("");
                    this.func.setProfissao("");
                    databaseReference.child("projetotst")
                            .child("funcionario")
                            .child(func.getUuid())
                            .setValue(func);
                }
                sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("LOGIN", "true");
                editor.apply();
                adm();



            }
            else {

                if (response == null) {
                    finish();
                }

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.id_sair) {
            sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("LOGIN", "false");
            editor.apply();
            login();

        }
        return super.onOptionsItemSelected(item);
    }


    public void retirarPonto(View view) {
        for (int u = 0; u < funcionarios.size(); u++) {
            pontos = 50;

            pontosAtual = Integer.parseInt(funcionarios.get(u).getPontos());
            pontosAtual += pontos;

            databaseReference.child("projetotst").child("funcionario")
                    .child(funcionarios.get(u).getUuid())
                    .child("pontos").setValue(pontosAtual.toString());

            databaseReference.child("projetotst")
                    .child("ultima_pontuacao").setValue(dataAtual);
        }
    }

    public void eventoData() {

        databaseReference.child("projetotst")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        data = (dataSnapshot.child("ultima_pontuacao").getValue().toString());

                       if (data.equals(dataAtual)){
                            btn.setEnabled(false);
                        }
                        else{
                           btn.setEnabled(true);
                       }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        }

        public void DataAtual(){

            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataAtual = sdf.format(date);



    }
    public void adm(){
        databaseReference.child("projetotst").child("funcionario")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String  valido  = ((String) dataSnapshot.child("valido")
                                .getValue());

                        validar = Boolean.parseBoolean(valido);
                        if (validar == false){
                            Intent intent= new Intent(MainActivity
                                    .this,SairActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else {


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


}





