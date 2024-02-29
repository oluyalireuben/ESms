package com.example.esms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esms.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private String email , password;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.account1.setOnClickListener(view -> startActivity(new Intent(this , MainActivity.class)));

        binding.btnSignIn.setOnClickListener(view -> {
            email = binding.edtEmail1.getText().toString().trim();
            password = binding.edtPassword1.getText().toString();

            if (email.isEmpty()) {
                binding.edtEmail1.setError("This is a required field");
                binding.edtEmail1.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                binding.edtPassword1.setError("This is a required field");
                binding.edtPassword1.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail1.setError("Enter a valid email address");
                binding.edtEmail1.requestFocus();
                return;
            }

            binding.progress2.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    binding.progress2.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "User signed in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this , HomeActivity.class));
                    finish();
                    binding.edtEmail1.setText("");
                    binding.edtPassword1.setText("");
                } else {
                    binding.progress2.setVisibility(View.GONE);
                    Toast.makeText(this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}