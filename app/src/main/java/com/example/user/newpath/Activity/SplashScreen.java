package com.example.user.newpath.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.newpath.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();

        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                chamarLogin();
            }
        }, 3000);
    }
    private void chamarLogin(){
        Intent toCadastro = new Intent(this, LoginUser.class);
        startActivity(toCadastro);
        overridePendingTransition(R.anim.fade_in, 0);

        finish();
    }
}
