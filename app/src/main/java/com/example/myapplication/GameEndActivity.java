package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.jinatonic.confetti.CommonConfetti;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class GameEndActivity extends AppCompatActivity {

    KonfettiView konfettiView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        konfettiView = findViewById(R.id.viewKonfetti);


        printMessage();
    }

    public void printMessage(){
        CommonConfetti.rainingConfetti(findViewById(R.id.parent_layout), new int[] {Color.WHITE} ).infinite();

        // Gets the correct word only if the player died
        String word = getIntent().getStringExtra("correct_word");

        // Gets the amount of tries used by the player
        int tries = getIntent().getIntExtra("tries",0);

        // Checks if the player won or lost and prints accordingly
        if (word == null){
            mediaPlayer = MediaPlayer.create(this,R.raw.kids);
            mediaPlayer.start();
            konfettiView.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 5f))
                    .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                    .streamFor(300, 5000L);
            TextView message = findViewById(R.id.textView);
            message.setText("Du vandt!\nDu brugte "+tries+" forsøg");
        }else{
            mediaPlayer = MediaPlayer.create(this,R.raw.loose);
            mediaPlayer.start();
            ImageView lost = findViewById(R.id.looseimage);
            lost.setVisibility(View.VISIBLE);
            TextView message = findViewById(R.id.textView);
            message.setText("Du tabte!\nOrdet var:\n"+ word);
        }
    }

    public void mainMenu(View v){
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        Intent myIntent = new Intent(this,HighscoreActivity.class);
        startActivity(myIntent);
    }

    public void highscore(View v){
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

    public void playAgain(View v){
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        Intent myIntent = new Intent(this,GameActivity.class);
        myIntent.putExtra("player_name",getIntent().getStringExtra("player_name"));
        startActivity(myIntent);
    }
}

