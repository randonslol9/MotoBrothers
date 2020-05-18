package com.example.motobrothers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CadastroPassageiro extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView TextView;
    EditText mNome, mEmail, mNumero, mData, mCPF, mSenha, mConfirmarSenha;
    Button mConfirmar;
    CheckBox mCheck;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


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

        mCPF = findViewById(R.id.editCPF);
        mCPF.addTextChangedListener(MaskEditUtil.mask(mCPF, MaskEditUtil.FORMAT_CPF));

        mNumero = findViewById(R.id.editCelular);
        mNumero.addTextChangedListener(MaskEditUtil.mask(mNumero, MaskEditUtil.FORMAT_FONE));

        mData = findViewById(R.id.editdtNascimento);
        mData.addTextChangedListener(MaskEditUtil.mask(mData, MaskEditUtil.FORMAT_DATE));

        fStore = FirebaseFirestore.getInstance();
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
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CadastroPassageiro.this, "Verificação de email enviada com sucesso!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: o email de verificação não foi enviado" + e.getMessage());
                                }
                            });
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
