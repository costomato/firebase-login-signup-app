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
import com.sample.loginsignup.databinding.ActivitySignUpBinding;
import com.sample.loginsignup.dialogs.ProgressDialog;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private EditText etEmail, etPassword, etConfirmPass;
    private TextView tvSignUp, tvLogin;
    private ImageView ivPasswordVisibility, ivConfirmPasswordVisibility;
    private FirebaseAuth firebaseAuth;
    private boolean passwordHidden = true;
    private boolean confirmPasswordHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
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
        ivConfirmPasswordVisibility.setOnClickListener(v -> {
            if (confirmPasswordHidden) {
                ivConfirmPasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_24);
                etConfirmPass.setTransformationMethod(null);
                confirmPasswordHidden = false;
            } else {
                ivConfirmPasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                etConfirmPass.setTransformationMethod(new PasswordTransformationMethod());
                confirmPasswordHidden = true;
            }
        });

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        ProgressDialog progressDialog = new ProgressDialog(this);
        tvSignUp.setOnClickListener(v -> {
            String password = etPassword.getText().toString(),
                    confirmPassword = etConfirmPass.getText().toString(),
                    eMail = etEmail.getText().toString();
            if (isSignUpEligible(eMail, password, confirmPassword)) {
                progressDialog.showDialog();
                firebaseAuth.createUserWithEmailAndPassword(eMail, password).addOnCompleteListener(SignUpActivity.this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismissDialog();
                });
            }
        });
    }

    private void initialise() {
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        etConfirmPass = binding.etConfirmPassword;
        tvLogin = binding.tvLogin;
        tvSignUp = binding.tvSignUp;
        ivPasswordVisibility = binding.ivPasswordVisibility;
        ivConfirmPasswordVisibility = binding.ivConfirmPassVisibility;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private boolean isSignUpEligible(String eMail, String password, String confirmPassword) {
        if (eMail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (confirmPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please enter a password with minimum 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "Make sure that passwords match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}