package com.example.motobrothers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class CadastroPassageiro extends AppCompatActivity {
    TextView TextView;
    EditText mNome, mEmail, mNumero, mData, mCPF, mSenha, mConfirmarSenha;
    Button mConfirmar;
    CheckBox mCheck;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_passageiro);

        mNome = findViewById(R.id.editNome);
        mEmail = findViewById(R.id.editEmail);
        mNumero = findViewById(R.id.editCelular);
        mData = findViewById(R.id.editdtNascimento);
        mCPF = findViewById(R.id.editCPF);
        mSenha = findViewById(R.id.editSenha);
        mConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        mCheck = findViewById(R.id.checkTermos);

        mConfirmar = findViewById(R.id.bntConfirmar);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        mConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String numero = mNumero.getText().toString().trim();
                String data = mData.getText().toString().trim();
                String cpf = mCPF.getText().toString().trim();
                String senha = mSenha.getText().toString().trim();
                String confirmarsenha = mConfirmarSenha.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Este campo não pode ficar em branco");
                    return;
                }
                if (TextUtils.isEmpty(senha)) {
                    mSenha.setError("Este campo não pode ficar em branco");
                    return;
                }
                if (senha.length() < 6) {
                    mSenha.setError("A senha deve conter pelo menos 11 digitos.");
                    return;
                }
                if (!senha.equals(confirmarsenha)){
                    Toast.makeText(CadastroPassageiro.this, "As senhas devem ser iguais!", Toast.LENGTH_SHORT).show();
                    mSenha.getText().clear();
                    mConfirmarSenha.getText().clear();
                    return;
                }
                if (TextUtils.isEmpty(numero)) {
                    mNumero.setError("Este campo não pode ficar em branco");
                    return;
                }
                if (TextUtils.isEmpty(data)) {
                    mData.setError("Este campo não pode ficar em branco");
                    return;
                }
                if (TextUtils.isEmpty(cpf)) {
                    mCPF.setError("Este campo não pode ficar em branco");
                    return;
                }
                if(!mCheck.isChecked()){
                    Toast.makeText(CadastroPassageiro.this, "Você precisa aceitar os termos e condições de uso do aplicativo", Toast.LENGTH_SHORT).show();
                    mCheck.setError("Você precisa aceitar os termos e condições de uso do aplicativo");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroPassageiro.this, "Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            Toast.makeText(CadastroPassageiro.this, "Não foi possivel se registrar, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        TextView = (android.widget.TextView) findViewById(R.id.txtTermos);
        TextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroPassageiro.this, TermosUso.class);
                startActivity(intent);

            }
        });
    }
}
