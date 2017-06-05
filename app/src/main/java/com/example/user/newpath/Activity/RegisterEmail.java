package com.example.user.newpath.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.newpath.Entity.User;
import com.example.user.newpath.Helpers.MD5Custom;
import com.example.user.newpath.Helpers.Preferences;
import com.example.user.newpath.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.NoSuchAlgorithmException;

public class RegisterEmail extends AppCompatActivity {

    EditText edtNome, edtEmail, edtTelefone, edtSenha;
    Button btnGravar;
    private User usuario;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnGravar = (Button) findViewById(R.id.btnGravar);

        usuario = new User();
        usuario.setNome(edtNome.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setTelefone(edtTelefone.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());

        btnGravar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario(){

        if(edtSenha.getText().toString().equals("") || edtSenha.getText().toString().length() < 6){
            Toast.makeText(RegisterEmail.this, "Digite uma senha valida", Toast.LENGTH_SHORT).show();

        }else{
            usuario.setEmail(edtEmail.getText().toString());
            usuario.setSenha(edtSenha.getText().toString());

//            Log.i(usuario.getEmail(), usuario.getSenha());

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        Toast.makeText(RegisterEmail.this, "Usuario Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();

                        String identificadorUsuario = null;
                        try {
                            identificadorUsuario = MD5Custom.codificarMd5(usuario.getSenha());
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        FirebaseUser usuarioFirebase = task.getResult().getUser();
                        usuario.setSenha(identificadorUsuario);

                        Preferences preferencias = new Preferences(RegisterEmail.this);
                        preferencias.salvarUsuarioPref(identificadorUsuario, usuario.getNome());

                        insert();

                    }else{
                        String exception = "";
                        try{
                            throw task.getException();
                        }catch(FirebaseAuthWeakPasswordException e){

                            exception = "Senha muito fraca, Digite novamente";

                        }catch(FirebaseAuthInvalidCredentialsException e){

                            exception = "Email digitado invalido";

                        }
                        catch(FirebaseAuthUserCollisionException e){

                            exception = "Email já cadastrado no sistema";

                        }catch (Exception e){
                            exception="Erro ao efetuar o cadastro";
                            e.printStackTrace();
                        }
                        Toast.makeText(RegisterEmail.this, "Erro " + exception, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    public void insert(){

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("user");

//        usuario.setId(UUID.randomUUID().toString());

        usuario.setNome(edtNome.getText().toString());
        usuario.setTelefone(edtTelefone.getText().toString());

        String id = reference.push().getKey();

        reference.child(id).setValue(usuario);

        openPrinpalView();

        cleanAll();
    }
    public void openPrinpalView(){
        Intent intent = new Intent(RegisterEmail.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void cleanAll(){
        edtEmail.setText("");
        edtSenha.setText("");
        edtTelefone.setText("");
        edtNome.setText("");
    }
}
