package com.sample.loginsignup.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sample.loginsignup.R;
import com.sample.loginsignup.databinding.ActivityLoginBinding;
import com.sample.loginsignup.dialogs.ProgressDialog;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private EditText etEmail, etPassword;
    private TextView tvSignUp, tvLogin, tvForgot;
    private ImageView ivPasswordVisibility;
    private boolean passwordHidden = true;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialise();

        ivPasswordVisibility.setOnClickListener(v -> {
            if (passwordHidden) {
                ivPasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_24);
                etPassword.setTransformationMethod(null);
                passwordHidden = false;
            } else {
                ivPasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                etPassword.setTransformationMethod(new PasswordTransformationMethod());
                passwordHidden = true;
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(this);

        tvLogin.setOnClickListener(v -> {
            String password = etPassword.getText().toString(),
                    eMail = etEmail.getText().toString();
            if (isLoginEligible(eMail, password)) {
                progressDialog.showDialog();
                firebaseAuth.signInWithEmailAndPassword(eMail, password).addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Failed to login", Toast.LENGTH_SHORT).show();
                    progressDialog.dismissDialog();
                });
            }
        });

        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });

        tvForgot.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class)));
    }

    private void initialise() {
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        tvLogin = binding.tvLogin;
        tvForgot = binding.tvForgot;
        tvSignUp = binding.tvSignUp;
        ivPasswordVisibility = binding.ivPasswordVisibility;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private boolean isLoginEligible(String eMail, String password) {
        if (eMail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}