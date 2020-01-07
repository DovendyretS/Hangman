package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        printMessage();
    }

    public void printMessage(){

        // Gets the correct word only if the player died
        String word = getIntent().getStringExtra("correct_word");

        // Gets the amount of tries used by the player
        int tries = getIntent().getIntExtra("tries",0);

        // Checks if the player won or lost and prints accordingly
        if (word == null){
            TextView message = findViewById(R.id.textView);
            message.setText("Du vandt!\nDu brugte "+tries+" forsøg");
        }else{
            TextView message = findViewById(R.id.textView);
            message.setText("Du tabte!\nOrdet var:\n"+ word);
        }
    }

    public void mainMenu(View v){
        Intent myIntent = new Intent(this,HighscoreActivity.class);
        startActivity(myIntent);
    }

    public void highscore(View v){
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

    public void playAgain(View v){
        Intent myIntent = new Intent(this,GameActivity.class);
        myIntent.putExtra("player_name",getIntent().getStringExtra("player_name"));
        startActivity(myIntent);
    }
}

