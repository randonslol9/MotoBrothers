package com.example.motobrothers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {

    private Button button;
    private TextView TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        button = findViewById(R.id.bntPassageiro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCadastroPassageiro();
            }
        });

        button = findViewById(R.id.bntMototaxista);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCadastroMototaxista();
            }
        });
        TextView = (TextView) findViewById(R.id.txtLogin);
        TextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cadastro.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
    public void openCadastroPassageiro(){
        Intent intent = new Intent(this, CadastroPassageiro.class);
        startActivity(intent);

    }
    public void openCadastroMototaxista(){
        Intent intent = new Intent(this, CadastroMototaxista.class);
        startActivity(intent);

    }
}
