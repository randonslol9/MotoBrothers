package com.example.motobrothers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView TextView;
    EditText mEmail, mSenha, mUsuario;
    Button mLogin;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.editLogin);
        mSenha = findViewById(R.id.editSenha);

        mLogin = findViewById(R.id.bntLogin);

        fAuth = FirebaseAuth.getInstance();


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String senha = mSenha.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Este campo não pode ficar em branco!");
                    return;
                }
                if(TextUtils.isEmpty(senha)){
                    mSenha.setError("Este campo não pode ficar em branco!");
                    return;
                }
                if(senha.length() < 11){
                    mSenha.setError("A senha deve conter 11 ou mais digitos!");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Não foi possivel fazer login, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

            TextView = (TextView) findViewById(R.id.txtEsquecisenha);
            TextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EsqueciSenha.class);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }
    });
        TextView = (TextView) findViewById(R.id.txtCadastro);
        TextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
}
}
