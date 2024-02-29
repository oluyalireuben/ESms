package com.example.esms;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.esms.adapters.MessagesAdapter;
import com.example.esms.databinding.ActivityViewMessagesBinding;
import com.example.esms.models.Message;
import com.example.esms.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ViewMessages extends AppCompatActivity {
    private ActivityViewMessagesBinding binding;
    private String friendName;
    private String roomId;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setChatRoom();
        messages = new ArrayList<>();
        MessagesAdapter adapter = new MessagesAdapter(ViewMessages.this, messages);
        binding.messagesRecycler.setLayoutManager(new LinearLayoutManager(ViewMessages.this));
        binding.messagesRecycler.setAdapter(adapter);

        friendName = getIntent().getStringExtra("nameOfMyFriend");
        String friendEmail = getIntent().getStringExtra("emailOfMyFriend");
        binding.chattingWith.setText(friendName);

        binding.send.setOnClickListener(view -> {
            if (binding.typeMessage.getText().toString().isEmpty()) {
                return;
            }
            FirebaseDatabase.getInstance().getReference("Message/" + roomId).push().setValue(
                    new Message(
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            friendEmail,
                            binding.typeMessage.getText().toString()
                    )
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    binding.typeMessage.setText("");
                } else {
                    Toast.makeText(this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setChatRoom() {
        FirebaseDatabase.getInstance().getReference("User/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String myName = snapshot.getValue(User.class).getName();
                        if (friendName.compareTo(myName) > 0) {
                            roomId = myName + friendName;
                        } else if (friendName.compareTo(myName) == 0) {
                            roomId = myName + friendName;
                        } else {
                            roomId = friendName + myName;
                        }
                        messageListener(roomId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void messageListener(String chatRoomId) {
        FirebaseDatabase.getInstance().getReference("Message/" + chatRoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                binding.messagesRecycler.setHasFixedSize(true);
                binding.messagesRecycler.scrollToPosition(messages.size() - 1);
                binding.progress4.setVisibility(View.GONE);
                binding.messagesRecycler.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}