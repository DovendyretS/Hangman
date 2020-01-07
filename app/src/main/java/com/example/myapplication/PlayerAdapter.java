package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<Player> mPlayers;


    public PlayerAdapter(ArrayList<Player> players){
        mPlayers = players;

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.bindPlayer(mPlayers.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{
        TextView mNameTextview;
        TextView mPointTextview;


        public PlayerViewHolder(View itemView){
            super(itemView);
            mNameTextview = itemView.findViewById(R.id.playerNameTextView);
            mPointTextview = itemView.findViewById(R.id.pointsTextView);

        }

        public void bindPlayer(@NonNull Player player){
            mNameTextview.setText(player.getName()+" You have:");
            mPointTextview.setText(player.getPoints() +" correct answers");
        }
    }




}
