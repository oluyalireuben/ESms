package com.example.esms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esms.adapters.FriendsAdapter;
import com.example.esms.databinding.ActivityChatsBinding;
import com.example.esms.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chats extends AppCompatActivity {

    private ActivityChatsBinding binding;
    private ArrayList<User> users;

    private FriendsAdapter.OnUserClicked onUserClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        users = new ArrayList<>();

        onUserClicked = (position -> startActivity(new Intent(this, ViewMessages.class)
                .putExtra("emailOfMyFriend", users.get(position).getEmail())
                .putExtra("nameOfMyFriend", users.get(position).getName())));


        FirebaseDatabase.getInstance().getReference("User")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            users.add(dataSnapshot.getValue(User.class));
                        }
                        binding.recycler.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Chats.this);
                        binding.recycler.setLayoutManager(layoutManager);
                        binding.recycler.setAdapter(new FriendsAdapter(users, Chats.this, onUserClicked));
                        binding.progress3.setVisibility(View.GONE);
                        binding.recycler.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}