package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    // Tony new game
    Button newGameButton;

    // Tony view leader board
    Button viewScoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tony new game
        newGameButton = (Button) findViewById(R.id.newGameButton);

        // Tony view leader board
        viewScoreButton = (Button) findViewById(R.id.viewScoreButton);


    }

    // Tony new game function (for Button)
    public void newGame(View view) {
        startActivity(new Intent(getApplicationContext(), PlayGameActivity.class));
    }

    // Tony view leader board function (for Button)
    public void viewScore(View view) {
        startActivity(new Intent(getApplicationContext(), ViewScoreActivity.class));
    }



}
