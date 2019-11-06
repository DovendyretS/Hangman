package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/titlefont.otf");
        textView.setTypeface(typeface);

    }

    public void sendMessage(View v){
        EditText name = findViewById(R.id.playername);
        String playerName = String.valueOf(name.getText());
        if (playerName.isEmpty()) {
           popup();
        }else{
            Intent myintent = new Intent(this, GameActivity.class);
            myintent.putExtra("playerName", playerName);
            startActivity(myintent);
        }
    }


    public void popup(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Please enter a name")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
        dialog.show();
    }
}
