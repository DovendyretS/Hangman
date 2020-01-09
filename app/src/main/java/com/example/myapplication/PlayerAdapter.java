package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

   private Context c;


    private List<Player> mPlayers;

    public PlayerAdapter(ArrayList<Player> players, Context context){
        mPlayers = players;
        this.c = context;

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Collections.sort(mPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Integer.compare(o2.getPoints(), o1.getPoints());
            }
        });
        holder.bindPlayer(mPlayers.get(position));

    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{
        TextView mNameTextview;
        TextView mPointTextview;
        ImageView mImageView;
        TextView mRank;


        public PlayerViewHolder(View itemView){
            super(itemView);

            Typeface type = Typeface.createFromAsset(c.getAssets(),"fonts/ARCADE_N.TTF" );

            mRank = itemView.findViewById(R.id.rank);
            mNameTextview = itemView.findViewById(R.id.navn);
            mPointTextview = itemView.findViewById(R.id.point);
            mImageView = itemView.findViewById(R.id.trophy);

            mRank.setTypeface(type);
            mNameTextview.setTypeface(type);
            mPointTextview.setTypeface(type);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    Toast.makeText(c, "Du har slettet "+mPlayers.get(pos).getName(), Toast.LENGTH_SHORT).show();
                    mPlayers.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, mPlayers.size());
                    SharedPref.save(mPlayers);
                    return true;
                }
            });

        }

        public void bindPlayer(@NonNull Player player){
            String rank = "";
            for (Player p: mPlayers) {
                if (p == player) {
                    rank = String.valueOf(mPlayers.indexOf(p) + 1);
                }
            }

            try {
                if (player == mPlayers.get(0)) {
                    mImageView.setImageResource(R.drawable.firstplace);
                    mImageView.setVisibility(View.VISIBLE);
                }
                if (player == mPlayers.get(1)) {
                    mImageView.setImageResource(R.drawable.secondplace);
                    mImageView.setVisibility(View.VISIBLE);
                }
                if (player == mPlayers.get(2)) {
                    mImageView.setImageResource(R.drawable.thirdplace);
                    mImageView.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception ignored){

            }

            mRank.setText(rank);
            mNameTextview.setText(player.getName());
            mPointTextview.setText(String.valueOf(player.getPoints()));

        }

    }
}
