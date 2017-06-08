package com.example.user.newpath.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.newpath.R;
import com.facebook.AccessToken;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class OptionLogin extends AppCompatActivity {

    Button btnCadastrarEmail;
    private CallbackManager calbCallbackManager;
    private LoginButton loginButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListenner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_login);
       
        btnCadastrarEmail = (Button) findViewById(R.id.btnCadastrarEmail);
        loginButton = (LoginButton) findViewById(R.id.loginButton);

        //Instancias Globais firebase
        firebaseAuth = FirebaseAuth.getInstance();
        calbCallbackManager = CallbackManager.Factory.create();

        //Permissões
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(calbCallbackManager, new FacebookCallback<LoginResult>(){
            @Override
            public void onSuccess(LoginResult loginResult){
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel(){
                Toast.makeText(OptionLogin.this, "Login Cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error){
                Toast.makeText(OptionLogin.this, "Fatal Error", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuthListenner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    goMainScreen();
                }
            }
        };

        btnCadastrarEmail.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                chamarCadEmail();
            }
        });
    }

    private  void handleFacebookAccessToken(AccessToken accessToken){
        Log.d("MEU TOKEN", "handleFacebookAccessToken:" + accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(OptionLogin.this, "Error Login", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        calbCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListenner);
    }
    protected void onStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListenner);
    }

    public void chamarCadEmail() {
        Intent cadastrarEmail = new Intent(OptionLogin.this, RegisterEmail.class);
        startActivity(cadastrarEmail);
    }
    private void goMainScreen(){
        Intent intent = new Intent(OptionLogin.this, MainActivity.class );
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
