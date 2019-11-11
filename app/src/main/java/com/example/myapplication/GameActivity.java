package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {



    ArrayList<Player> players = new ArrayList<>();
    Player player;

    ArrayList<View> views = new ArrayList<>();
    Galgelogik logik = new Galgelogik();
    private Integer[] images = {R.drawable.galge, R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3
            , R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setWord();

        Gson gson = new Gson();
        player = gson.fromJson(getIntent().getStringExtra("myjson"),Player.class);

        if (player == null) {
            player = new Player(getIntent().getStringExtra("player_name"));
        }
    }

    public void sendMessage(View v) {
        views.add(v);
        String content = ((TextView) v).getText().toString();
        logik.gætBogstav(content.toLowerCase());

        Drawable defaultButtonColor = v.getBackground();

        isGameOver();
        updateButtons(v, defaultButtonColor);
        updateText();
        updateImage();

    }

    public void updateText() {
        TextView hiddenLetter = findViewById(R.id.word_to_guess);
        hiddenLetter.setText(logik.getSynligtOrd());
    }

    public void updateImage() {
        ImageView galge = findViewById(R.id.imageView);
        galge.setImageResource(images[logik.getAntalForkerteBogstaver()]);
    }

    public void updateButtons(View v, Drawable defaultButtonColor) {
        v.getBackground();
        if (logik.erSidsteBogstavKorrekt()) {
            v.setEnabled(false);
            v.setBackgroundColor(0xFF00FF00);
        } else {
            v.setBackgroundColor(0xFFFFFFFF);
            v.setEnabled(false);
        }

        if (logik.erSpilletSlut()) {
            for (View a : views) {
                a.setEnabled(true);
                a.setBackground(defaultButtonColor);
            }

        }
    }

    public void isGameOver() {
        if (logik.erSpilletTabt()) {
            Intent gameLost = new Intent(this, FinalActivity.class);
            gameLost.putExtra("correct_word", logik.getOrdet());
            startActivity(gameLost);
        }

        if (logik.erSpilletVundet()) {
            player.addPoint();
            saveData();
            Intent gameWon = new Intent(this, FinalActivity.class);
            gameWon.putExtra("tries", logik.getBrugteBogstaver().size());
            startActivity(gameWon);
        }

        views.clear();
        updateText();
        updateImage();

    }

    public void setWord() {
        class getWord extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    logik.hentOrdFraRegneark("2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                TextView word = findViewById(R.id.word_to_guess);
                word.setText(logik.getSynligtOrd());
            }
        }
        new getWord().execute();
    }

    public void saveData(){
        players = loadData();



        for (Player tempPlayer : players) {
            if (tempPlayer.getName().equals(getIntent().getStringExtra("player_name"))) {
                tempPlayer.setPoints(player.getPoints());
                break;
            }else{
                players.add(player);
                break;
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(players);
        editor.putString("player list",json);
        editor.apply();
    }

    public ArrayList<Player> loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("player list",null);
        Type type = new TypeToken<List<Player>>() {}.getType();

        return gson.fromJson(json,type);
    }

}
