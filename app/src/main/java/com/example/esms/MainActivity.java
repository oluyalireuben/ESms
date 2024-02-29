package com.example.esms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esms.databinding.ActivityMainBinding;
import com.example.esms.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
private String username , email , password;

private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        if (FirebaseAuth.getInstance().getCurrentUser() != null) startActivity(new Intent(this , Login.class));
        username = binding.edtUserName.getText().toString();
        email = binding.edtEmail.getText().toString();
        password = binding.edtPassword.getText().toString();

        binding.account.setOnClickListener(view -> startActivity(new Intent(MainActivity.this , Login.class)));

        binding.btnSignUp.setOnClickListener(view -> {

            username = binding.edtUserName.getText().toString();
            email = binding.edtEmail.getText().toString().trim();
            password = binding.edtPassword.getText().toString();

            if (username.isEmpty()) {
                binding.edtUserName.setError("This is a required field");
                binding.edtUserName.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                binding.edtEmail.setError("This is a required field");
                binding.edtEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                binding.edtPassword.setError("This is a required field");
                binding.edtPassword.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.setError("Enter a valid email address");
                binding.edtEmail.requestFocus();
                return;
            }

            binding.progress1.setVisibility(View.VISIBLE);

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email , password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference("User/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(
                            new User(username , email , password)
                    );
                    binding.progress1.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this , Login.class));
                    finish();
                    binding.edtUserName.setText("");
                    binding.edtEmail.setText("");
                    binding.edtPassword.setText("");
                } else {
                    binding.progress1.setVisibility(View.GONE);
                    Toast.makeText(this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}