package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Player> tempPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPref.init(getApplicationContext());

        TextView textView = findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/titlefont.otf");
        textView.setTypeface(typeface);

    }

    public void playGame(View v){
        Intent gameActivity = new Intent(this,GameActivity.class);
        EditText name = findViewById(R.id.playername);
        String playerName = String.valueOf(name.getText());

        //If playername is empty then popup warning
        if (playerName.isEmpty()) {
           popup("Please enter a name");

        }else{
            gameActivity.putExtra("player_name",playerName);
            startActivity(gameActivity);
        }
    }

    public void highscore(View v){

        //load data from previous games
       // tempPlayers = SharedPref.load();

        // Check if highscore data is empty or null
        if (tempPlayers == null || tempPlayers.isEmpty())
            popup("Highscores not available");
        else {
            Intent highscoreActivity = new Intent(this, HighscoreActivity.class);
            startActivity(highscoreActivity);
        }

    }

    public void popup(String warning){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(warning)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
        dialog.show();
    }
}
