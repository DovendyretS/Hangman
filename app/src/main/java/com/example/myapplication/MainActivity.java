package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Player> tempPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPref.init(getApplicationContext());

        TextView title = findViewById(R.id.title);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/titlefont.otf");
        title.setTypeface(typeface);


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
        Intent highscoreActivity = new Intent(this, HighscoreActivity.class);
        startActivity(highscoreActivity);
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
