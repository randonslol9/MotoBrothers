package com.example.motobrothers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView TextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            TextView = (TextView) findViewById(R.id.txtEsquecisenha);
            TextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EsqueciSenha.class);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "you clicked on text", Toast.LENGTH_SHORT).show();
                }
    });
        TextView = (TextView) findViewById(R.id.txtCadastro);
        TextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "you clicked on text", Toast.LENGTH_SHORT).show();
            }
        });
}
}
