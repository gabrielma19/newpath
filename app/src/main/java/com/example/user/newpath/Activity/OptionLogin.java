package com.example.user.newpath.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.newpath.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class OptionLogin extends AppCompatActivity {


    Button btnCadastrarEmail;
    Button btnCadastrarFacebook;
    private  CallbackManager callbackManager;
    private FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_login);
        btnCadastrarEmail = (Button) findViewById(R.id.btnCadastrarEmail);
        btnCadastrarFacebook = (Button) findViewById(R.id.btnLoginFacebook);
        firebase = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(OptionLogin.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        btnCadastrarFacebook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loginFacebook();
            }
        });
        btnCadastrarEmail.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                chamarCadEmail();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode , data);
    }

    public void loginFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    private void acessoFacebookLogin (AccessToken accessToken){
        if(accessToken != null){

        }else{

        }
    }

    public void chamarCadEmail() {
        Intent cadastrarEmail = new Intent(OptionLogin.this, RegisterEmail.class);
        startActivity(cadastrarEmail);

    }
}
