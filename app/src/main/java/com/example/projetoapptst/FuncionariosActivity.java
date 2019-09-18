package com.example.projetoapptst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projetoapptst.modelos.Funcionario;

public class FuncionariosActivity extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);

        String titulo = getIntent().getStringExtra(MainActivity.TITULO);

        textView = findViewById(R.id.textView_1);
        textView.setText(titulo);


    }



}
