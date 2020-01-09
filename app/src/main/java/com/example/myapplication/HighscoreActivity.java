package com.example.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class HighscoreActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;


    List<Player> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        TextView highscoreTitle = findViewById(R.id.highscore_title);
        TextView sections = findViewById(R.id.rankNamePoints);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/ARCADE_N.TTF" );

        highscoreTitle.setTypeface(type);
        sections.setTypeface(type);

        players = SharedPref.load();

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlayerAdapter((ArrayList<Player>) players, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        Toast.makeText(this, "Hold fingeren nede på et navn\nfor at slette en spiller", Toast.LENGTH_LONG).show();
    }

}
