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

import static com.example.myapplication.SharedPref.save;


public class GameActivity extends AppCompatActivity {

    Player player;

    List<Player> players = new ArrayList<>();
    ArrayList<View> views = new ArrayList<>();

    Galgelogik logik = new Galgelogik();
    private Integer[] images = {R.drawable.galge, R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3
            , R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setWord();

        Player temp = new Player(getIntent().getStringExtra("player_name"));

        if (SharedPref.load() == null || SharedPref.load().isEmpty()) {
            player = temp;
            players.add(player);
        }

        else{
            players = SharedPref.load();
            if ( !checkPlayer(temp)) {
                player = temp;
                players.add(player);
            }
        }
    }

    public boolean checkPlayer(Player p){
        for (Player tempPlayer: players) {
            if (p.getName().equals(tempPlayer.getName())) {
                player = tempPlayer;
                return true;
            }
        }
        return false;
    }

    public void checkLetter(View v) {
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
            SharedPref.save(players);
            Intent gameWon = new Intent(this, FinalActivity.class);
            gameWon.putExtra("tries", logik.getBrugteBogstaver().size());
            gameWon.putExtra("player_name",getIntent().getStringExtra("player_name"));
            startActivity(gameWon);

        }

        views.clear();
        updateText();
        updateImage();

    }

    public void setWord() {
        class getWord extends AsyncTask<Void, Void, Void> {

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

}
