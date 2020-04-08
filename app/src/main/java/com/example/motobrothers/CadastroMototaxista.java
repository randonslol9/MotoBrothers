package com.example.motobrothers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroMototaxista extends AppCompatActivity {

    TextView TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mototaxista);

        TextView = (android.widget.TextView) findViewById(R.id.txtTermos);
        TextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroMototaxista.this, TermosUso.class);
                startActivity(intent);

                Toast.makeText(CadastroMototaxista.this, "you clicked on text", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
