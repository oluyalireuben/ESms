package com.example.esms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esms.R;
import com.example.esms.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter  extends RecyclerView.Adapter<MessagesAdapter.MessagesVieHolder> {

    private final Context context;
    private final ArrayList<Message> messages;

    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessagesVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagesVieHolder(LayoutInflater.from(context).inflate(R.layout.message_holder , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesVieHolder holder, int position) {
        holder.messageContent.setText(messages.get(position).getContent());
        ConstraintLayout constraintLayout = holder.constraintLayout;

        if (messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.messageContent , ConstraintSet.LEFT);
            constraintSet.connect(R.id.messageContent , ConstraintSet.RIGHT , R.id.constraintLayout , ConstraintSet.RIGHT);
            constraintSet.applyTo(constraintLayout);
        } else {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.messageContent , ConstraintSet.RIGHT);
            constraintSet.connect(R.id.messageContent , ConstraintSet.LEFT , R.id.constraintLayout , ConstraintSet.LEFT);
            constraintSet.applyTo(constraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessagesVieHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView messageContent;
        public MessagesVieHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            messageContent = itemView.findViewById(R.id.messageContent);
        }
    }
}
