package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


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

        // sets the word to be guessed
        setWord();


        System.out.println("hej\n "+logik.getOrdet());
        // temporary player
        Player temp = new Player(getIntent().getStringExtra("player_name"));

        // if the database is empty or null a new player is added to the player list
        if (SharedPref.load() == null || SharedPref.load().isEmpty()) {
            player = temp;
            players.add(player);
        }
        // if the database is NOT empty we will check if a player with same name exists already
        // and add to his existing points otherwise we add a new player to the player list
        else {
            players = SharedPref.load();
            if (!checkPlayerExists(temp)) {
                player = temp;
                players.add(player);
            }
        }

        TextView playerName = findViewById(R.id.player_name);
        playerName.setText("Du spiller som "+player.getName());

    }

    // checks if a player with the same name already exists
    public boolean checkPlayerExists(Player p) {
        for (Player tempPlayer : players) {
            if (p.getName().equals(tempPlayer.getName())) {
                player = tempPlayer;
                return true;
            }
        }
        return false;
    }


    // checks if the letter from the button pressed is part of the word-to-be guessed
    public void checkLetter(View v) {

        // adds the letter to a list so we can re-enable them all after game has ended
        views.add(v);

        // gets the letter from the corresponding button
        String content = ((TextView) v).getText().toString();

        logik.gætBogstav(content.toLowerCase());


        // updates the buttons with colors and disables them untill next turn
        updateButtons(v);

        // updates the displayed text when correct letter is guessed
        updateText();


        updateImage();


        isGameOver();

    }


    public void updateText() {
        TextView hiddenLetter = findViewById(R.id.word_to_guess);
        hiddenLetter.setText(logik.getSynligtOrd());
    }

    public void updateImage() {
        ImageView galge = findViewById(R.id.imageView);
        galge.setImageResource(images[logik.getAntalForkerteBogstaver()]);
    }

    public void updateButtons(View v) {

        // disables the buttons and changes their color
        if (logik.erSidsteBogstavKorrekt()) {
            v.setEnabled(false);
            v.setBackgroundColor(0xFF00FF00);
        } else {
            v.setBackgroundColor(0X00574B);
            v.setEnabled(false);
        }


        // re-enables all the buttons after the game has ended
        if (logik.erSpilletSlut()) {
            for (View a : views) {
                a.setEnabled(true);
                a.setBackgroundColor(0x008577);

            }
        }
    }

    public void isGameOver() {
        if (logik.erSpilletTabt()) {
            Intent gameLost = new Intent(this, GameEndActivity.class);
            gameLost.putExtra("correct_word", logik.getOrdet());
            gameLost.putExtra("player_name", getIntent().getStringExtra("player_name"));
            finish();
            startActivity(gameLost);

        }

        if (logik.erSpilletVundet()) {

            // adds a point to the player and saves it in  the database
            player.addPoint();
            SharedPref.save(players);


            Intent gameWon = new Intent(this, GameEndActivity.class);
            gameWon.putExtra("tries", logik.getBrugteBogstaver().size());
            gameWon.putExtra("player_name",getIntent().getStringExtra("player_name"));
            finish();
            startActivity(gameWon);


        }

        /*
        views.clear();
        updateText();
        updateImage();
*/
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
