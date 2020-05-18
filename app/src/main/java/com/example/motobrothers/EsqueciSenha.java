package com.example.motobrothers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EsqueciSenha extends AppCompatActivity {

    private Button ResetPasswordSendEmailButton;
    private EditText ResetEmailInput;
    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        firebaseauth = FirebaseAuth.getInstance();


        ResetPasswordSendEmailButton = (Button)findViewById(R.id.bntEnviar);
        ResetEmailInput = (EditText)findViewById(R.id.editEmail);

        ResetPasswordSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = ResetEmailInput.getText().toString();
                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(EsqueciSenha.this, "Não foi especificado nenhum email!", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseauth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EsqueciSenha.this, "As instruções de redefinição de senha foram enviadas\n"+"Cheque o seu email por favor", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EsqueciSenha.this, MainActivity.class));
                            }else{
                                Toast.makeText(EsqueciSenha.this, "Você digitou um email inválido ou não existe uma conta com esse email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
