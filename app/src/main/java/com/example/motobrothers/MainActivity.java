package com.example.motobrothers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private LoginButton loginButton;
    private static final String TAG = "FacebookAuthentication";
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    TextView TextView;
    EditText mEmail, mSenha, mUsuario;
    Button mLogin;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        setContentView(R.layout.activity_main);


        mEmail = findViewById(R.id.editEmail);
        mSenha = findViewById(R.id.editSenha);

        mLogin = findViewById(R.id.bntLogin);


        fAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult );
                handleFacebookToken(loginResult.getAccessToken());
                startActivity(new Intent(getApplicationContext(),MenuPassageiro.class));
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: " + error );
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    updateUI(user);

                } else {
                    updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null){
                    fAuth.signOut();
                }
            }
        };






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
                if(senha.length() < 6){
                    mSenha.setError("A senha deve conter 6 ou mais digitos!");
                    return;
                }


                fAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MenuPassageiro.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Login/Senha não conferem!\n"+"ou está conta não existe!", Toast.LENGTH_SHORT).show();
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
                }
    });
        TextView = (TextView) findViewById(R.id.txtCadastro);
        TextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);
            }
        });
}
    private void handleFacebookToken(AccessToken token) {
        Log.d(TAG, "handleFacebookToken: " + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        fAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Login com credencial efetuado com sucesso");
                    FirebaseUser user = fAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.d(TAG, "onComplete: teste");
                    Toast.makeText(MainActivity.this, "falhou",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            /* terminar o código de user */
            /* terminar o código de user *//* terminar o código de user */
            /* terminar o código de user */
            /* terminar o código de user */
            /* terminar o código de user */


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            fAuth.removeAuthStateListener(authStateListener);
        }
    }
}

