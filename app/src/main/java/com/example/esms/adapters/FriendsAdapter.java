package com.example.esms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esms.R;
import com.example.esms.models.User;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {


    private final ArrayList<User> users;
    private final Context context;
    private final OnUserClicked onUserClicked;


    public FriendsAdapter(ArrayList<User> users, Context context, OnUserClicked onUserClicked) {
        this.users = users;
        this.context = context;
        this.onUserClicked = onUserClicked;
    }

     public interface OnUserClicked {
        void onUserClicked(int position);
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.textView.setText(users.get(position).getName());

        holder.itemView.setOnClickListener(view -> onUserClicked.onUserClicked(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class FriendsViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.friend);
        }
    }
}
