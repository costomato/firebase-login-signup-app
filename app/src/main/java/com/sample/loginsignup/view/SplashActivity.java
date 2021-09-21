package com.sample.loginsignup.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sample.loginsignup.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (firebaseUser != null)
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            else startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 500);
    }
}