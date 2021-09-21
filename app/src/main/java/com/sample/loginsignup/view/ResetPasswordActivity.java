package com.sample.loginsignup.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sample.loginsignup.databinding.ActivityResetPasswordBinding;
import com.sample.loginsignup.dialogs.ProgressDialog;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText etEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResetPasswordBinding binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etEmail = binding.etEmail;
        TextView tvReset = binding.tvReset;

        firebaseAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);

        tvReset.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (email.isEmpty()) {
                etEmail.setError("This is a required field");
                etEmail.requestFocus();
            } else {
                progressDialog.showDialog();
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Toast.makeText(getApplicationContext(), "Password reset link sent. Check email", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismissDialog();
                });
            }
        });
    }
}