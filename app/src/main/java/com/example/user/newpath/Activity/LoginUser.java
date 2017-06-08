package com.example.user.newpath.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.newpath.DAO.FirebaseConfig;
import com.example.user.newpath.Entity.User;
import com.example.user.newpath.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginUser extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastrar;
    private Button btnRegistrar;
    private FirebaseAuth autentication;
    private User usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regitraNewUser();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){
                    usuario = new User();
                    usuario.setEmail(edtEmail.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());
                    validarLogin();

                }else{
                    Toast.makeText(LoginUser.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void validarLogin(){
        autentication = FirebaseConfig.getFirebaseAuth();
        autentication.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    cleanField();
                    abrirTelaPrincipal();
                    Toast.makeText(LoginUser.this, "Bem Vindo ao NewPath", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginUser.this, "Usuario não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void abrirTelaPrincipal(){
        Intent intentTelaPrincioal = new Intent(LoginUser.this, MainActivity.class );
        startActivity(intentTelaPrincioal);
    }
    private void regitraNewUser(){
        Intent registraUser = new Intent(LoginUser.this, OptionLogin.class);
        startActivity(registraUser);
    }
    public void cleanField(){
        edtEmail.setText("");
        edtSenha.setText("");
    }
}
