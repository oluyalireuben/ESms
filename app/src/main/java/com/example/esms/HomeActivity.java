package com.example.esms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navHome:
                            // Start HomeActivity or perform relevant action
                            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                            Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.navchat:
                            // Start VaccinationActivity or perform relevant action
                            startActivity(new Intent(HomeActivity.this, Chats.class));
                            Toast.makeText(HomeActivity.this, "Chat here", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navLogout:
                            // Start ChangePasswordActivity or perform relevant action
                            startActivity(new Intent(HomeActivity.this, Login.class));
                            Toast.makeText(HomeActivity.this, "You have successfully Logged out", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                    finish();
                    return false;

                }
            };
}