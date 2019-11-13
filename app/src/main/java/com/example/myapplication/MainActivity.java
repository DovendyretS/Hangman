package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

        TextView textView = findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/titlefont.otf");
        textView.setTypeface(typeface);

        loadData();

    }

    public void sendMessage(View v){
        Intent gameActivity = new Intent(this,GameActivity.class);
        EditText name = findViewById(R.id.playername);
        String playerName = String.valueOf(name.getText());

        if (playerName.isEmpty()) {
           popup("Please enter a name");

        }else{
            gameActivity.putExtra("player_name",playerName);
            startActivity(gameActivity);
        }
    }

    public void sendMessage1(View v){
        try {
            if (loadData().isEmpty()) {
                popup("There are no highscores yet");
            }else {
                Intent highscoreActivity = new Intent(this, HighscoreActivity.class);
                startActivity(highscoreActivity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            popup("There are no highscores yet");
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

    public List<Player> loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("player list",null);
        Type type = new TypeToken<List<Player>>() {}.getType();

        return tempPlayers = gson.fromJson(json,type);

    }

}
