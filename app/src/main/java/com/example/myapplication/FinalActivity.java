package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        printMessage();
    }

    public void printMessage(){
        String word = getIntent().getStringExtra("correct_word");
        int tries = getIntent().getIntExtra("tries",0);
        if (word == null){
            TextView message = findViewById(R.id.textView);
            message.setText("Du vandt!\nDu brugte "+tries+" forsøg");
        }else{
            TextView message = findViewById(R.id.textView);
            message.setText("Du tabte!\nOrdet var:\n"+ word);
        }

    }


    public void end_game(View v){
        Intent myintent = new Intent(this,GameActivity.class);
        startActivity(myintent);
    }
}

